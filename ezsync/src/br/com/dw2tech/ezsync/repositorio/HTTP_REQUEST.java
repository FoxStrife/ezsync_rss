package br.com.dw2tech.ezsync.repositorio;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import java.io.IOException;

public class HTTP_REQUEST {

	public static String json;
	public static String retorno;
	// one instance, reuse
	private final CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void main(String[] args) throws Exception {

		HTTP_REQUEST obj = new HTTP_REQUEST();

		try {
			System.out.println("Testing 1 - Send Http GET request");
			obj.sendGet();

			System.out.println("Testing 2 - Send Http POST request");
			obj.sendPost();
		} finally {
			obj.close();
		}
	}

	void close() throws IOException {
		httpClient.close();
	}

	void sendGet() throws Exception {

		HttpGet request = new HttpGet("http://test.nela.com.br:1344/jobFunctions");

		// add request headers
		request.addHeader("Authorization", "19314be0-edeb-11ea-acff-e5a61518017a");

		try (CloseableHttpResponse response = httpClient.execute(request)) {

			// Get HttpResponse Status

			HttpEntity entity = response.getEntity();
			String headers = entity.getContentType();
			System.out.println(headers);

			if (entity != null) {
				// return it as a String
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			}

		}

	}

	public void sendPost() throws Exception {

		HttpPost post = new HttpPost("http://test.nela.com.br:1344/jobFunctions");

		post.setHeader("Authorization", "19314be0-edeb-11ea-acff-e5a61518017a");
		post.setHeader("Content-type", "application/json");
		StringEntity entity = new StringEntity(json);
		post.setEntity(entity);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {

			System.out.println(EntityUtils.toString(response.getEntity()));
			retorno = EntityUtils.toString(response.getEntity());
		}
	}
}