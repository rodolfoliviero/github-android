package com.github.model;

import java.util.Date;

import com.github.util.Utils;

public class Commit {

	private String message;

	private String author;
	
	private Date data;

	public Commit(String message, String author, String data) {
		this.message = message;
		this.author = author;
		this.data = Utils.stringToDate(data);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(Date data) {
		this.data = data;
	}
}
