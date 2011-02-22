/**
 * 
 */
package com.github.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.pelom.android.utils.LogManager;

import com.github.model.Commit;
import com.github.model.Repository;

/**
 * @author pelom
 */
public class JsonGit {

	public static final String REPOSITORIO = "repositories";

	public static final String NAME = "name";

	public static final String OWNER = "owner";

	public static final String MESSAGE = "message";

	public static final String DATE_COMMIT = "committed_date";

	public static final String COMMITER = "committer";

	public static final String LOGIN = "login";

	public static final String COMMITS = "commits";

	public static final String USER = "user";
	
	public static final String GRAVATAR_ID = "gravatar_id";
	
	
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public List<Repository> obterListRepositorios(JSONObject json) {
		final List<Repository> repositories = new ArrayList<Repository>();
		JSONArray repositoriesJson = null;
		try {
			repositoriesJson = json.getJSONArray(REPOSITORIO);

		} catch (JSONException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);

			return repositories;
		}

		final int size = repositoriesJson.length();
		Repository repository = null;
		String name = null;
		String owner = null;

		for (int i = 0; i < size; i++) {
			JSONObject jsonI = null;
			try {
				jsonI = repositoriesJson.getJSONObject(i);
			} catch (JSONException e) {
				LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
				continue;
			}
			try {
				name = jsonI.getString(NAME);	
				owner = jsonI.getString(OWNER);

			} catch (JSONException e) {
				LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
				continue;
			}

			repository = new Repository(owner, name);
			repositories.add(repository);
		}

		return repositories;
	} 

	/**
	 * 
	 * @param json
	 * @return
	 */
	public List<Commit> obterListCommit(JSONObject json) {
		final List<Commit> listCommits = new ArrayList<Commit>();
		JSONArray commits;
		try {
			commits = json.getJSONArray(COMMITS);
		} catch (JSONException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
			
			return listCommits;
		}
		
		int size = commits.length();
		Commit commit = null;
		
		for (int j = 0; j < size; j++) {
			JSONObject commitJ;
			try {
				commitJ = commits.getJSONObject(j);
			} catch (JSONException e) {
				LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
				continue;
			}

			String message;
			String data;
			String author;
			try {
				message = commitJ.getString(MESSAGE);
				data = commitJ.getString(DATE_COMMIT);
				author = commitJ.getJSONObject(COMMITER).getString(LOGIN);
				
			} catch (JSONException e) {
				LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
				continue;
			}
			
			commit = new Commit(message, author, data);
			listCommits.add(commit);
		}
		
		return listCommits;
	}
	
	/**
	 * 
	 * @param json
	 * @return
	 */
	public String obterString(JSONObject jsonUser) {
		
		try {
			return jsonUser.getString(GRAVATAR_ID);
		
		} catch (JSONException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);
			
		}
		
		return null;
	}
}