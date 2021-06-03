package br.com.dwtech.jms.testes;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Consumidor_Teste {
	
	public static String IP_do_banco = "172.18.1.12";
	public static String porta_do_banco = ":3050";
	public static String caminho_do_banco = "/C:\\\\Viasoft\\\\Dados\\\\RH\\\\RHUNIFICADO.FDB?charSet=utf-8";
	public static String usuario = "SYSDBA";
	public static String senha = "masterkey";	
	public static Connection conexao;
	
	public static ResultSet RetornarDados() throws Exception {
		Class.forName("org.firebirdsql.jdbc.FBDriver");
		conexao = DriverManager.getConnection("jdbc:firebirdsql://" + IP_do_banco + porta_do_banco +
				caminho_do_banco,usuario,senha);
	     
	      Statement stmt = conexao.createStatement();
	    
	      ResultSet rs = stmt.executeQuery("SELECT NOME, 'DANIEL' AS DIOW  FROM RHPESSOA WHERE IDPESS = '434'");
	      return rs;
	   }

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
	      JSONObject jsonObject = new JSONObject();

	      JSONArray array = new JSONArray();
	      ResultSet rs = (ResultSet) RetornarDados();
	      
	      while(rs.next()) {
	         JSONObject record = new JSONObject();
	        
	         record.put("NOME", rs.getString("NOME"));
	         record.put("DIOW", rs.getString("DIOW"));
	         array.add(record);
	      }
	      jsonObject.put("Dados", array);
	      try {
	         FileWriter file = new FileWriter("D:/saida.json");
	         file.write(jsonObject.toJSONString());
	         file.close();
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      System.out.println("Arquivo JSON criado com sucesso!");
		
		
		
		
		/*InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("financeiro");		
		MessageConsumer consumer = session.createConsumer(fila);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				
				TextMessage textMessage = (TextMessage)message;
				
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
		*/
	}

}
