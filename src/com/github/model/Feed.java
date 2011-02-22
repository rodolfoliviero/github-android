package com.github.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author
 */
public class Feed {

	private Repository repository;
	
	/** Commit **/
	private Commit commit = null;
	
	/**
	 * 
	 * @param author
	 * @param message
	 * @param date
	 * @param gravatarID
	 * @param repository
	 */
	public Feed(Commit commit, Repository repository) {
		this.commit = commit;
		this.repository = repository;
		//decodeGravatar(gravatarID);
	}

	

	/**
	 * @return the repository
	 */
	public Repository getRepository() {
		return repository;
	}



	/**
	 * @param repository the repository to set
	 */
	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	/**
	 * @return the commit
	 */
	public Commit getCommit() {
		return commit;
	}



	/**
	 * @param commit the commit to set
	 */
	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	/**
	 * 
	 * @param gravatarID
	 */
	private void decodeGravatar(String gravatarID) {
		try {
			URL url = new URL("http://www.gravatar.com/avatar/" + gravatarID);
			InputStream stream = url.openStream();
			//gravatar = BitmapFactory.decodeStream(stream);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}