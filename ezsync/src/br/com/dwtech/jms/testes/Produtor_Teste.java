package br.com.dwtech.jms.testes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;


public class Produtor_Teste {
	
	public static String IP_do_banco = "172.18.1.12";
	public static String porta_do_banco = ":3050";
	public static String caminho_do_banco = "/C:\\\\Viasoft\\\\Dados\\\\RH\\\\RHUNIFICADO.FDB?charSet=utf-8";
	public static String usuario = "SYSDBA";
	public static String senha = "masterkey";	

    public static void ProducerDB(String[] args){
    	
        ConnectionFactory factory = null;
        javax.jms.Connection connection = null;
        Session session = null;
        Destination destination = null;
        MessageProducer producer = null;
        Connection c = null;
        Statement stmt = null;
        
        try {
        Class.forName("org.firebirdsql.jdbc.FBDriver");
        c = DriverManager.getConnection("jdbc:firebirdsql://" + IP_do_banco + porta_do_banco + caminho_do_banco,usuario,senha);
        c.setAutoCommit(false);
        System.out.println("----------------------------");

        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT NOME, 'DANIEL' AS DIOW  FROM RHPESSOA WHERE IDPESS = '434'");
        
        while (rs.next()) {
            String  message = rs.getString("NOME");
            System.out.println("Mensagem = " + message);
            
                try {
                    factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
                    connection = factory.createConnection();
                    connection.start();
                    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                    destination = session.createQueue("TestQueue");
                    producer = session.createProducer(destination);
                    TextMessage mssg = session.createTextMessage(message);
                    System.out.println("Enviado: " + mssg.getText());
                    producer.send(mssg);
                }
                catch (JMSException e) {
                    e.printStackTrace();
                }
        }
        
        rs.close();
        stmt.close();
        c.close();
        }
        catch (Exception e) {
            System.err.println(e.getClass().getName()+": "+ e.getMessage());
        }
        System.out.println("----------------------------");
        System.out.println("Mensagem enviada com sucesso");
    }
    
    public static void main(String[] args) throws Exception {
    	Produtor_Teste.ProducerDB(args);
    }
}