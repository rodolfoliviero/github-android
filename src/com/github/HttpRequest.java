package com.github;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpRequest {

    public static JSONObject get(String url) throws JSONException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        BufferedReader in = null;
        StringBuffer sb = new StringBuffer();

        try {
            HttpEntity entity = httpClient.execute(get).getEntity();
            in = new BufferedReader(new InputStreamReader(entity.getContent()));

            String line = "";

            while ((line = in.readLine()) != null) {
                sb.append(line);
            }

        } catch (ClientProtocolException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new JSONObject(sb.toString());
    }
}
