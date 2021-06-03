package br.com.dw2tech.ezsync;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InvalidPropertiesFormatException;

/**
 * Classe Conex�o criada para ler as vari�veis da classe Propriedades com as
 * configura��es do cliente e realizar a conex�o ao banco de dados. Para a
 * conex�o com o banco DwTech ser� necess�rio acessar a classe Dwtech.
 *
 * @see br.com.dw2tech.ezsync.Dwtech
 * @see br.com.dw2tech.ezsync.Propriedades
 * 
 * @author Dw2Tech
 *
 */
public class Conexao {

	/** Recebe a conex�o para o banco de dados */
	public static Connection conn;
	/** Interface de instru��es SQL */
	public static Statement statment;

	/**
	 * M�todo que realiza a conex�o com o banco de dados do cliente de acordo com os
	 * dados informados na classe Properties.
	 * 
	 * @throws IOException                      Exce��o de leitura/escrita do
	 *                                          arquivo.
	 * @throws FileNotFoundException            Exce��o de arquivo n�o encontrado no
	 *                                          caminho informado.
	 * @throws ClassNotFoundException           Exce��o de classe n�o encontrada.
	 * 
	 * @throws SQLException                     Exce��o de conex�o com o SQL.
	 * 
	 * @return conn - Retorna a conex�o com o banco de dados.
	 * 
	 * @see br.com.dw2tech.ezsync.Propriedades
	 */
	public static Connection conectar()
			throws IOException, ClassNotFoundException, SQLException {
		Propriedades.lerArquivo();
		Class.forName(Propriedades.getDriverjdbc());
		setC(DriverManager.getConnection(Propriedades.getStr_conexao(), Propriedades.getUser(), Propriedades.getPwd()));
		setStatment(getC().createStatement());
		return conn;
	}

	/**
	 * M�todo que realiza a desconex�o com o banco de dados do cliente.
	 * 
	 * @throws SQLException Exce��o de conex�o com o SQL.
	 */
	public static void desconectar() throws SQLException {
		getC().close();
	}

	/**
	 * Recupera a conex�o com banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @return Connection - Conex�o com o banco de dados.
	 */
	public static Connection getC() {
		return conn;
	}

	/**
	 * Aplica a conex�o com o banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param connection Conex�o com o banco de dados informado.
	 */
	public static void setC(Connection connection) {
		conn = connection;
	}

	/**
	 * Recupera a interface de instru��es SQL com banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return Statement - Interface de instru��es SQL.
	 */
	public Statement getStatment() {
		return statment;
	}

	/**
	 * Aplica a interface de instru��es SQL do banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @param statement Interface de instru��es SQL informado.
	 */
	public static void setStatment(Statement statement) {
		statment = statement;
	}
}