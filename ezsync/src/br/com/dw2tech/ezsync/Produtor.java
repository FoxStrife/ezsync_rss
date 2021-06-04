package br.com.dw2tech.ezsync;

import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.filter.function.replaceFunction;
import org.apache.camel.json.simple.JsonObject;
import org.apache.commons.mail.EmailException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class Produtor {

	public static javax.jms.Connection connection = null;
	public static javax.jms.Connection conn = null;
	public static javax.jms.Connection conn1 = null;
	public static String urlactivemq = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static String retornoactivemq = null;
	public static Session session = null;
	public static String urlapi = null;
	public static String urlparamapi = null;
	public static String sql = null;
	public static String condicao = null;
	public static String condicaosql = null;
	public static String correlationid = null;
	public static int colunaid_estrutura_json = 0;
	public static int colunaqtdobj = 0;
	public static int colunaqtdobjsql = 0;
	public static String objporvez = null;
	public static String pkdwsync = null;
	public static String fila = null;
	public static String idfila = null;
	public static String diowson = null;
	public static String Jsonmontado = null;
	public static String filainicio = null;
	public static Integer qtdReg = 0;
	public static Integer qtdRegjsonsql = 0;
	public static java.sql.Connection con = null;
	public static java.sql.Connection con1 = null;
	public static java.sql.Connection con2 = null;
	public static java.sql.Connection con3 = null;
	public static java.sql.Connection con4 = null;
	public static java.sql.Connection condw5 = null;
	public static java.sql.Connection condw = null;
	public static Statement stmt = null;
	public static Statement stmt1 = null;
	public static Statement stmt2 = null;
	public static Statement stmt3 = null;
	public static Statement stmt4 = null;
	public static Statement stmtdw5 = null;
	public static Statement stmtdw = null;
	private static ResultSet rs;
	private static ResultSet rs1;
	private static ResultSet rs2;
	private static ResultSet rs4;
	private static ResultSet rsdw;
	public static java.sql.Connection condw1 = null;
	public static Statement stmtdw1 = null;
	public static ResultSet rsdw1 = null;
	public static ResultSet rsdw5 = null;
	public static String Json = null;
	public static int ultobj = 0;
	
	public static JsonObject jsonfinal = new JsonObject();

	public static Produtor enviaFila() throws JMSException, EmailException, MalformedURLException {

		do {
			try {

				/*ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(urlactivemq); // inicio conexão com
																									// ActiveMQ
				ActiveMQConnection connection = (ActiveMQConnection) connectionFactory.createConnection();
				connection.start();
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
				DestinationSource ds = connection.getDestinationSource();
				Set<ActiveMQQueue> queues = ds.getQueues();
				if (!queues.isEmpty()) {
					for (ActiveMQQueue activeMQQueue : queues) {
						try {
							filainicio = activeMQQueue.getQueueName();
							Destination destination = session.createQueue(filainicio);
							QueueBrowser browser = session.createBrowser((Queue) destination);
							Enumeration enu = browser.getEnumeration();
							List list = new ArrayList();

							while (enu.hasMoreElements()) {
								TextMessage messagex = (TextMessage) enu.nextElement();
								list.add(messagex.getText());
							}

							System.out.println("Size " + list.size());

							for (int i = 0; list.size() > i; i++)
								SendReceive.SendReceive();

							System.out.println(activeMQQueue.getQueueName());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
				connection.close(); // fim conexão com ActiveMQ
*/
				pkdwsync = null;
				correlationid = null;
				colunaqtdobjsql = 0;
				
				TimeUnit.MILLISECONDS.sleep(Propriedades.timeperpost);
				

				con = Conexao.conectar(); // ---inicio conexão com banco de dados Cliente---//
				con.setAutoCommit(false);
				stmt = con.createStatement();

				if (Propriedades.banco.equals("3")) {
					rs = stmt.executeQuery(
							"SELECT FIRST 1 DISTINCT A.IDFILA, (SELECT LIST(ID) FROM RHPROTINTEGRATERC WHERE SYNC = 0 AND IDFILA = A.IDFILA), F.DESCRICAO FROM RHPROTINTEGRATERC A LEFT JOIN RHSISTEMAINTEGRATERCFILA F ON F.IDSISTEMA = A.IDSISTEMA WHERE  SYNC = 0");
				}

				if (rs.isBeforeFirst()) {

					while (rs.next()) {

						fila = null;
						fila = rs.getString(3);
						int filars = rs.getInt(1);
						System.out.println("Fila" + filars);
						pkdwsync = rs.getString(2);

						con4 = Conexao.conectar(); // ---inicio conexão com banco de dados Dw2Tech---//
						con4.setAutoCommit(false);
						stmt4 = con4.createStatement();
						rs4 = stmt4.executeQuery("SELECT NEXT VALUE FOR GEN_DWSYNC_ID_ACTIVEMQ  FROM RDB$DATABASE;");

						while (rs4.next()) {
							correlationid = rs4.getString(1);
						}

						con4.rollback(); // ---rollback conexão com banco de dados Dw2tech---//

						con4 = Conexao.conectar(); // ---inicio conexão com banco de dados Cliente---//
						con4.setAutoCommit(false);
						stmt4 = con4.createStatement();
						stmt4.executeUpdate("UPDATE RHPROTINTEGRATERC SET IDFILA_ACTIVEMQ = " + correlationid + " WHERE ID IN ("
								+ pkdwsync + ")");
						con4.commit();
						con4.close(); // ---fim conexão com banco de dados Cliente---//

						// TimeUnit.MILLISECONDS.sleep(Conexao.timeperpost);

					/*	condw = Dwtech.conectar(); // ---inicio conexão com banco de dados Dw2Tech---//
						condw.setAutoCommit(false);
						stmtdw = condw.createStatement();
						rsdw = stmtdw.executeQuery(
								"SELECT DESCRICAO FROM  , url, URLPARAM,OBJPORVEZ,COLUNAID_ESTRUTURA_JSON,COLUNAQTDOBJ FROM FILAS WHERE ID_FILA = "
										+ filars);

						while (rsdw.next()) {
							fila = null;
							fila = rsdw.getString(1);
							urlapi = rsdw.getString(2);
							urlparamapi = rsdw.getString(3);
							objporvez = rsdw.getString(4);
							colunaid_estrutura_json = rsdw.getInt(5);
							colunaqtdobj = rsdw.getInt(6);
						}

						condw.rollback(); // ---rollback conexão com banco de dados Dw2Tech---//

*/
						sql = null;
						condw = Dwtech.conectar(); // ---inicio conexão com banco de dados Dw2Tech---//
						condw.setAutoCommit(false);
						stmtdw = condw.createStatement();
						rsdw = stmtdw.executeQuery("SELECT CAST(SQL AS VARCHAR(MAX)) AS SQL "
								+ "FROM DW_SQLS WHERE ID_FILA = " + filars + "" + " AND ID_SISTEMA_ORIGEM = "
								+ " 4 AND ID_SISTEMA_DESTINO = 1 AND ID_BANCO_DE_DADOS = " + Propriedades.banco + "");

						while (rsdw.next()) {
							sql = rsdw.getString(1);
						}
						//System.out.println(sql);
						
						condw.rollback(); // ---rollback conexão com banco de dados Dw2Tech---//

						condicao = null;
						rsdw = stmtdw.executeQuery(
								"SELECT CAST(CONDICAO AS VARCHAR(MAX)) AS CONDICAO FROM DW_SQLS WHERE ID_FILA = " + filars + "" + " AND ID_SISTEMA_ORIGEM = 4"
								+ " AND ID_SISTEMA_DESTINO = 1 AND ID_BANCO_DE_DADOS = " + Propriedades.banco + "");
						while (rsdw.next()) {
							condicao = rsdw.getString(1);
						}

						condw.rollback();
						// TimeUnit.MILLISECONDS.sleep(Conexao.timeperpost);
						//System.out.println(condicao);
						
						con1 = Conexao.conectar(); // ---inicio conexão com banco de dados Cliente---//
						con1.setAutoCommit(false);
						stmt1 = con1.createStatement(rs1.TYPE_SCROLL_INSENSITIVE, rs1.CONCUR_READ_ONLY);
						rs1 = stmt1.executeQuery(condicao + filars);

						
						//System.out.println(condicao+ filars); 
						rs1.last();
						int qtdregsql = rs1.getRow();
						rs1.beforeFirst();

						while (rs1.next()) {
							if ((rs1.getRow() == 1) && (rs1.getRow() == qtdregsql)) {
								condicaosql = rs1.getString(1);
							}
							if ((rs1.getRow() == 1) && (rs1.getRow() < qtdregsql)) {
								condicaosql = rs1.getString(1) + " OR ";
							} else if ((rs1.getRow() != 1) && (rs1.getRow() < qtdregsql)) {
								condicaosql = condicaosql + rs1.getString(1) + " OR ";
							} else if ((qtdregsql > 1) && (rs1.getRow() == qtdregsql)) {
								condicaosql = condicaosql + rs1.getString(1);
							}
						}
						
						
						//System.out.println(sql + " " + condicaosql);

						con1.rollback(); // ---rollback conexão com banco de dados Cliente---//

						con2 = Conexao.conectar(); // ---inicio conexão com banco de dados Cliente---//
						con2.setAutoCommit(false);
						stmt2 = con2.createStatement(rs2.TYPE_SCROLL_INSENSITIVE, rs2.CONCUR_READ_ONLY);
						rs2 = stmt2.executeQuery(sql + " " + condicaosql);
						
						System.out.println(sql + " " + condicaosql);
			

					
							while (rs2.next()) {
								  JSONArray jsonArray = new JSONArray();
								/*  jsonArray = jsonobj.convert(rs2);
								
								Gson gson = new Gson();
								String diowson = (gson.toJson(jsonArray));
								*/
								  
								  int columns = rs2.getMetaData().getColumnCount();
							        JSONObject obj = new JSONObject();
							 
							        for (int i = 0; i < columns; i++)
							            obj.put(rs2.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs2.getObject(i + 1));
							 
							        jsonArray.put(obj);
			
							       String diowson = jsonArray.toString();
							        
							       
							        ConnectionFactory connectionFactoryconsumidor1 = new ActiveMQConnectionFactory(
											urlactivemq); // ---inicio conexão com ActiveMQ---//
									conn1 = connectionFactoryconsumidor1.createConnection();
									
									conn1.start();
									session = conn1.createSession(false, Session.AUTO_ACKNOWLEDGE);

									Destination destinationprod = session.createQueue(fila);
									MessageProducer producer = session.createProducer(destinationprod);
									TextMessage message = session.createTextMessage(diowson);
									message.setJMSCorrelationID(correlationid);
									producer.send(message);
									
									diowson = null;
									
									conn1.close();

									ConnectionFactory connectionFactoryconsumidor = new ActiveMQConnectionFactory(
											urlactivemq); // ---inicio conexão com ActiveMQ---//
									conn = connectionFactoryconsumidor.createConnection();
									conn.start();
									session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

									Destination destinationconsumidor = session.createQueue(fila);
									MessageConsumer consumer = session.createConsumer(destinationconsumidor);
									Message messagerec = consumer.receive();

									if (message instanceof TextMessage) {
										TextMessage textMessage = (TextMessage) messagerec;
										retornoactivemq = textMessage.getText();
									}
									// System.out.println(rs2.getInt(colunaid_estrutura_json));

									conn.close(); // ---fim conexão com ActiveMQ---//
									//REST_Request.json = retornoactivemq;
									
							        
							        //System.out.println( " EXEC DW_XML_RSDATA_000 " + filars + ", 'integracao.db0745apresenta@rsdata.com.br'"
									//+ ", '21753ef24af23bd6cc64435a1a4b4137','" + jsonArray +"'");


							        condw = Dwtech.conectar(); // ---inicio conexão com banco de dados Dw2Tech---//
									condw.setAutoCommit(false);
									stmtdw = condw.createStatement();
									rsdw = stmtdw.executeQuery(" EXEC DW_XML_RSDATA_000 " + filars + ", 'integracao.db0745apresenta@rsdata.com.br'"
									+ ", '21753ef24af23bd6cc64435a1a4b4137','" + jsonArray +"'");

									while (rsdw.next()) {
										if (rsdw.getInt(1) == 0 ){
											con4 = Conexao.conectar(); // ---inicio conexão com banco de dados Cliente---//
											con4.setAutoCommit(false);
											stmt4 = con4.createStatement();
											
											System.out.println("UPDATE RHPROTINTEGRATERC SET SYNC = 1"
													+ ", DTHORASYNC = CURRENT_TIMESTAMP "
													+ ", CODRETORNO = " + rsdw.getInt(1)  
													+ ", DESCRETORNO = '" + rsdw.getString(2) 
													+ "', XML = '" + rsdw.getString(3) 
													+ "', CONDICAO = '" + condicao + "' WHERE IDFILA_ACTIVEMQ = " + correlationid );
											
											stmt4.executeUpdate("UPDATE RHPROTINTEGRATERC SET SYNC = 1"
													+ ", DTHORASYNC = CURRENT_TIMESTAMP "
													+ ", CODRETORNO = " + rsdw.getInt(1)  
													+ ", DESCRETORNO = '" + rsdw.getString(2) 
													+ "', XML = '" + rsdw.getString(3) 
													+ "', CONDICAO = '" + condicao.replace("'", "^") + "' WHERE IDFILA_ACTIVEMQ = " + correlationid );
											con4.commit();
											con4.close();
										};
								
										System.out.println(rsdw.getString(2));
									}
									//System.out.println(sql);
									
									condw.rollback(); // ---rollback conexão com banco de dados Dw2Tech---//
									
									
								System.out.println(jsonArray);
								
								
								
								
								
								System.gc();
							}
							//condw1.rollback(); 
							//condw1.close();
						}
					}
				

				con.rollback(); // ---rollback conexão com banco de dados Cliente---//
				

				

			} catch (Exception e) {
				String erro = e.getClass().getName() + ": " + e.getMessage();
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				//EnviarEmail.enviar(erro);
				return null;
			}
		}while(true);
}}