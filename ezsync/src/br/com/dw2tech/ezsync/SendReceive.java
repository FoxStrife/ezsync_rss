package br.com.dw2tech.ezsync;

import javax.jms.MessageConsumer;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Message;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class SendReceive {

	public static javax.jms.Connection connectionx = null;
	public static String urlactivemq = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static String retornoactivemq = null;
	public static Session sessionx = null;

	public static void SendReceive() throws JMSException, Exception {

		ConnectionFactory connectionFactoryconsumidor = new ActiveMQConnectionFactory(Produtor.urlactivemq); // inicio conexão com ActiveMQ
		connectionx = connectionFactoryconsumidor.createConnection();
		connectionx.start();
		sessionx = connectionx.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destinationconsumidor = sessionx.createQueue(Produtor.filainicio);
		MessageConsumer consumer = sessionx.createConsumer(destinationconsumidor);
		Message messagerec = consumer.receive();

		TextMessage textMessage = (TextMessage) messagerec;
		Produtor.retornoactivemq = textMessage.getText();

		connectionx.close(); // fim conexão com ActiveMQ

		REST_Request.json = Produtor.retornoactivemq;
		REST_Request.sendPost();
	}
}