package br.com.dw2tech.ezsync;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InvalidPropertiesFormatException;

/**
 * Classe Conexão criada para ler as variáveis da classe Propriedades com as
 * configurações do cliente e realizar a conexão ao banco de dados. Para a
 * conexão com o banco DwTech será necessário acessar a classe Dwtech.
 *
 * @see br.com.dw2tech.ezsync.Dwtech
 * @see br.com.dw2tech.ezsync.Propriedades
 * 
 * @author Dw2Tech
 *
 */
public class Conexao {

	/** Recebe a conexão para o banco de dados */
	public static Connection conn;
	/** Interface de instruções SQL */
	public static Statement statment;

	/**
	 * Método que realiza a conexão com o banco de dados do cliente de acordo com os
	 * dados informados na classe Properties.
	 * 
	 * @throws IOException                      Exceção de leitura/escrita do
	 *                                          arquivo.
	 * @throws FileNotFoundException            Exceção de arquivo não encontrado no
	 *                                          caminho informado.
	 * @throws ClassNotFoundException           Exceção de classe não encontrada.
	 * 
	 * @throws SQLException                     Exceção de conexão com o SQL.
	 * 
	 * @return conn - Retorna a conexão com o banco de dados.
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
	 * Método que realiza a desconexão com o banco de dados do cliente.
	 * 
	 * @throws SQLException Exceção de conexão com o SQL.
	 */
	public static void desconectar() throws SQLException {
		getC().close();
	}

	/**
	 * Recupera a conexão com banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @return Connection - Conexão com o banco de dados.
	 */
	public static Connection getC() {
		return conn;
	}

	/**
	 * Aplica a conexão com o banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param connection Conexão com o banco de dados informado.
	 */
	public static void setC(Connection connection) {
		conn = connection;
	}

	/**
	 * Recupera a interface de instruções SQL com banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return Statement - Interface de instruções SQL.
	 */
	public Statement getStatment() {
		return statment;
	}

	/**
	 * Aplica a interface de instruções SQL do banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @param statement Interface de instruções SQL informado.
	 */
	public static void setStatment(Statement statement) {
		statment = statement;
	}
}