package com.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;
import br.pelom.android.utils.LogManager;

import com.github.adapter.FeedListaAdapter;
import com.github.model.Commit;
import com.github.model.Feed;
import com.github.model.Repository;
import com.github.util.HttpRequest;
import com.github.util.JsonGit;
import com.github.util.Utils;

/** 
 * @author pelom
 */
public class GithubActivity extends Activity {

	private FeedListaAdapter listaAdapter = null;
	private List<Feed> listaFeeds = null;
	private final Timer timer = new Timer();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setProgressBarIndeterminateVisibility(false);

		setContentView(R.layout.main);

		this.listaFeeds = new ArrayList<Feed>();
		ListView listView = (ListView) findViewById(R.id.feeds);

		this.listaAdapter = new FeedListaAdapter(listaFeeds, this); 
		listView.setAdapter(listaAdapter);

		synchronizeFeeds();
	}

	private void synchronizeFeeds() {
		Toast.makeText(this, "retrieving feeds", Toast.LENGTH_LONG).show();

		timer.schedule(new SearchFeed(
		"http://github.com/api/v2/json/repos/watched/JoseRibeiro"), 0, 1000 * 60 * 2);
	}

	/**
	 * @author pelom
	 */
	private class SearchFeed extends TimerTask {

		/** Objeto http para realizar a conexao com a internet **/
		private HttpRequest request = null;

		/** url do git hub do usuario **/
		private String urlRepositorio = null;

		private JsonGit jsonGit = null;

		/** Data da ultima verificacao **/
		private Date dataUltimaVeri = null;
		/**
		 * 
		 */
		public SearchFeed(String urlRepositorio) {
			request = new HttpRequest();
			jsonGit = new JsonGit();
			this.urlRepositorio = urlRepositorio;
			dataUltimaVeri = new GregorianCalendar(2011, Calendar.JANUARY, 15, 1, 1, 1).getTime(); 
		}

		@Override
		public void run() {
			setProgressBarIndeterminateVisibility(true);

			LogManager.i(Utils.NOME_LOG, "iniciando a buscar por feeds url:" + urlRepositorio);

			if(urlRepositorio == null) return;

			final JSONObject jsonRepositorio = request.urlToJson(urlRepositorio);
			if(jsonRepositorio == null) {
				return;
			}

			List<Repository> listRepository = jsonGit.obterListRepositorios(jsonRepositorio);

			if(listRepository.size() == 0) {
				return;
			}

			LogManager.i(Utils.NOME_LOG, "numero de repositorios:" + listRepository.size());

			int size = listRepository.size();

			listaFeeds.clear();

			for (int i = 0; i < size; i++) {
				Repository repositorio = listRepository.get(i);
				String urlCommit = repositorio.commitsUrl();

				final JSONObject jsonCommit = request.urlToJson(urlCommit);
				if(jsonCommit == null) {
					continue;
				}

				List<Commit> listCommits = jsonGit.obterListCommit(jsonCommit);

				LogManager.i(Utils.NOME_LOG, "numero de commits:" + listCommits.size());

				int sizeCommit = listCommits.size();
				for (int j = 0; j < sizeCommit; j++) {
					Commit commit = listCommits.get(j);

					if (commit.getData().after(dataUltimaVeri)) {

						//criar feeds
						Feed feed = new Feed(commit, repositorio);
						//carregar imagem
						carregarImagemUsuario(feed);
						//add a lista
						listaFeeds.add(feed);

						runOnUiThread(new Runnable() {
							public void run() {
								listaAdapter.notifyDataSetChanged();
							}
						});
					}
				}
			}

			LogManager.i(Utils.NOME_LOG, "atualizar lista:" + listaFeeds.size());
			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(GithubActivity.this, "Total new feeds:" + listaFeeds.size(), Toast.LENGTH_SHORT).show();
					setProgressBarIndeterminateVisibility(false);

					Utils.ordenarPorDateCommit(listaFeeds);
					listaAdapter.notifyDataSetChanged();
				}
			});

		}

		/**
		 * 
		 * @param feed
		 */
		private void carregarImagemUsuario(Feed feed) {
			final JSONObject jsonUser = 
				request.urlToJson(Utils.URL_USER + feed.getCommit().getAuthor());

			String urlImage = jsonGit.obterString(jsonUser);

			try {
				feed.setImagem(request.obterGravatar(urlImage));

			} catch (IOException e) {
				LogManager.e(Utils.NOME_LOG, e.getMessage(), e);

			}
		}
	}
}