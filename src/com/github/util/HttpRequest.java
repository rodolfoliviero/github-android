package com.github.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import br.pelom.android.utils.LogManager;

public class HttpRequest {

	/**
	 * 
	 * @param url
	 * @return
	 * @throws JSONException
	 */
	public JSONObject urlToJson(String url) {

		JSONObject json = null;

		try {
			//criar entrada de dados.
			final InputStream in = readUrl(url);
			//obiter string da url
			final String stringUrl = obterString(in);
			//criar objeto json
			json = new JSONObject(stringUrl);

		} catch (JSONException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);

		} catch (ClientProtocolException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);

		} catch (IOException e) {
			LogManager.e(Utils.NOME_LOG, e.getMessage(), e);

		}

		return json;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private InputStream readUrl(String url) throws ClientProtocolException, IOException {
		final HttpClient httpClient = new DefaultHttpClient();
		final HttpGet get = new HttpGet(url);

		HttpEntity entity = httpClient.execute(get).getEntity();

		return entity.getContent();
	}

	/**
	 * Obter string da entrada IO
	 * 
	 * @param in
	 * @return
	 * 
	 * @throws IOException
	 */
	private String obterString(InputStream in) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line = "";

		BufferedReader buf = new BufferedReader(new InputStreamReader(in));
		while((line = buf.readLine()) != null) {
			sb.append(line);
		}

		if (buf != null) {
			buf.close();
		}

		return sb.toString();
	}

	/**
	 * 
	 * @param gravatarID
	 * @return
	 * @throws IOException
	 */
	public byte[] obterGravatar(String gravatarID) throws IOException {
		URL url = new URL(Utils.URL_GRAVATAR + gravatarID);

		InputStream inputStream = url.openStream();

		final byte[] buf = Utils.readBytes(inputStream);
		
		if(inputStream != null) {
			inputStream.close();
		}
		
		return buf;
	}
}
