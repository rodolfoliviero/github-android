package com.github.model;



/**
 * @author
 */
public class Feed {

	private Repository repository;

	/** Commit **/
	private Commit commit = null;

	/** Imagem do usuario **/
	private byte[] imagem = null;

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
	 * @return the imagem
	 */
	public byte[] getImagem() {
		return imagem;
	}



	/**
	 * @param imagem the imagem to set
	 */
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}

}