/**
 * 
 */
package com.github.adapter;

import java.text.DateFormat;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.R;
import com.github.model.Feed;

/**
 * @author pelom
 */

public class FeedListaAdapter extends BaseAdapter {
	/** Lista de feed **/
	private List<Feed> lista = null;

	/** Contexto **/
	private Context ctx = null;

	/**
	 * Construtor da classe.
	 * 
	 * @param lista
	 * @param ctx
	 */

	public FeedListaAdapter(List<Feed> lista, 
			Context ctx) {
		super();

		this.ctx = ctx;
		this.lista = lista;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return lista.size();
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return lista.get(index);
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Feed feed = lista.get(position);

		LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.feed, null);

		TextView event = (TextView) view.findViewById(R.id.event);
		TextView message = (TextView) view.findViewById(R.id.message);
		ImageView gravatar = (ImageView) view.findViewById(R.id.gravatar);

		String date = DateFormat.getDateTimeInstance().format(feed.getCommit().getData());

		event.setText(feed.getCommit().getAuthor() + " pushed to master at " + feed.getRepository() + " " + date);
		message.setText(feed.getCommit().getMessage());
		
		final byte[] buf = feed.getImagem();
		gravatar.setImageBitmap(BitmapFactory.decodeByteArray(buf, 0, buf.length));
		
		return view;
	}
}