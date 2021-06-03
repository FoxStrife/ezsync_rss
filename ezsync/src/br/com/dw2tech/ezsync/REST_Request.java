package br.com.dw2tech.ezsync;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;
import java.io.IOException;

public class REST_Request {

	public static String json;
	public static String retorno;
	// one instance, reuse
	private final static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static void sendGet() throws Exception {

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

	public static JSONObject sendPost() throws Exception {

		HttpPost post = new HttpPost(Produtor.urlapi + Produtor.urlparamapi);
		post.setHeader("Authorization", Propriedades.token);
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		post.setEntity(entity);

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post)) {
			retorno = EntityUtils.toString(response.getEntity());
			System.out.println(retorno);
			JSONObject jsonarr = new JSONObject(retorno);
			if (jsonarr.getString("code").equals("200") || jsonarr.getString("code").equals("201")) {
				/*Produtor.con4 = Conexao.conectar(); // inicio conexão com banco de dados Cliente
				Produtor.con4.setAutoCommit(false);
				Produtor.stmt4 = Produtor.con4.createStatement();
				Produtor.stmt4.executeUpdate("UPDATE DWSYNC SET SYNC = 1 WHERE ID IN (" + Produtor.pkdwsync + ")");
				Produtor.con4.commit();
				Produtor.con4.close(); // fim conexão com banco de dados Cliente*/
			}
			else {
				EnviarEmail.enviar(jsonarr.getString("code"));
			}
		}
		return null;
	}

	public static void close() throws IOException {
		httpClient.close();
	}
}