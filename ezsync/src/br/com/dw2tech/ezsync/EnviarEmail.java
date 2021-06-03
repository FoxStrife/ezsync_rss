package br.com.dw2tech.ezsync;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

public class EnviarEmail {

	public static void enviar(String erro) throws MalformedURLException, EmailException {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		
		// load your HTML email template
		String htmlEmailTemplate = "<html>"
				  				 + "<head>"
				  				 + "<style>"
				  				 + "h1 {text-align: center;}"
				  				 + "p {text-align: center;}"
				  				 + "</style>"
				  				 + "</head>"
				  				 + "<img src=\"https://i.ibb.co/SnYf59c/cab.jpg\">"
				  				 + "<br>"
				  				 + "<br>"
				  				 + "<b>LOG DO SOFTWARE Dw2tech Integração! - " + dateFormat.format(date) + "</b>"
				  				 + "<br>"
				  				 + "<br>"
				  				 + "Cliente:" + Propriedades.getCnpj()
				  				 + "<br>"
				  				 + "Sistema:" + Propriedades.getSistema()
				  				 + "<br>"
				  				 + "Banco:" + Propriedades.getBanco()
				  				 + "<br>"
				  				 + "Caminho:" + Propriedades.getCaminho()
				  				 + "<br>"
				  				 + "IP:" + Propriedades.getIp()
				  				 + "<br>"
				  				 + "Token:" + Propriedades.getToken()
				  				 + "<br>"
				  				 + "<br>"
				  				 + "Erro: " + erro
				  				 + "<br>"
				  				 + "<br>"
				  				 + "SQL: " + Produtor.sql
				  				 + "<br>"
				  				 + "<br>"
				  				 + "Condição: " + Produtor.condicaosql
				  				 + "<br>"
				  				 + "<br>"
				  				 + "JSON:" + REST_Request.retorno
				  				 + "<br>"
				  				 + "<img src=\"https://i.ibb.co/1v72qSb/rodape.jpg\">"
				  				 + "<br>"
				  				 + "</html>";

		// define you base URL to resolve relative resource locations
		URL url = new URL("https://i.ibb.co");

		// create the email message
		ImageHtmlEmail email = new ImageHtmlEmail();
		//email.setDataSourceResolver(new DataSourceUrlResolver(url));
		String userName = "contatodw2tech@gmail.com";
		String password = "hesoyam123*";
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(userName,password));
		email.setSSLOnConnect(true);
		email.addTo("andryuscavalheiro@gmail.com", "Andryus Cavalheiro");
		email.setFrom("contato@dw2tech.com.br", "Sistema Dw2Tech");
		email.setSubject("Log Sistema Dw2Tech Integração");
		// set the html message
		email.setHtmlMsg(htmlEmailTemplate);

		// set the alternative message
		email.setTextMsg("Your email client does not support HTML messages");

		// send the email
		email.send();
	}
}