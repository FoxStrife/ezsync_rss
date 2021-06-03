package br.com.dw2tech.ezsync;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Classe Propriedades criada para ler as informa��es dispostas no arquivo
 * Properties e assim alimentar as vari�veis do sistema. � respons�vel pela
 * identifica��o e configura��o do ambiente do cliente, como por exemplo o
 * caminho do banco de dados, porta, IP, usu�rio, senha, Token de autentica��o
 * na API, etc. Estas informa��es ir�o ser utilizadas pelas classes de conex�o
 * com banco de dados do cliente.
 *
 * @see br.com.dw2tech.ezsync.Dwtech
 * @see br.com.dw2tech.ezsync.Conexao
 * 
 * @author Dw2Tech
 *
 */

public class Propriedades {

	/** CNPJ do cliente */
	public static String cnpj = null;
	/** C�digo refer�ncia para o sistema do cliente */
	public static String sistema = null;
	/** C�digo refer�ncia para o banco de dados do cliente */
	public static String banco = null;
	/** Caminho do banco de dados */
	public static String caminho = null;
	/** Codifica��o de caracteres do banco de dados */
	public static String charSet = "UTF8";
	/** IP do banco de dados */
	public static String ip = null;
	/** Porta do banco de dados */
	public static String porta = null;
	/** Usu�rio do banco de dados */
	public static String user = null;
	/** Senha do banco de dados */
	public static String pwd = null;
	/** Token para acesso ao REST API */
	public static String token;
	/** Timeout para consultas SQL */
	public static int timeperpost;
	/** Recebe os dados para conex�o com banco de dados */
	public static String str_conexao;
	/** Driver do banco de dados */
	public static String driverjdbc;

	/**
	 * M�todo que l� o arquivo de propriedades que cont�m os dados para a conex�o
	 * com a base de dados e demais informa��es do ambiente do cliente. As
	 * informa��es preenchidas no arquivo s�o carregadas nas vari�veis e com isto
	 * alimenta a conex�o na classe Conexao. Caso haja inclus�o de informa��es no
	 * arquivo, ser� necess�rio ajustar esta classe com seus respectivos GETs e
	 * SETs.
	 * 
	 * @throws InvalidPropertiesFormatException Exce��o de formato inv�lido para o
	 *                                          arquivo Properties.
	 * @throws FileNotFoundException            Exce��o de arquivo n�o encontrado no
	 *                                          caminho informado.
	 * @throws IOException                      Exce��o de leitura/escrita do
	 *                                          arquivo.
	 */
	public static void lerArquivo() throws InvalidPropertiesFormatException, FileNotFoundException, IOException {
		java.util.Properties arquivo = new Properties();

		arquivo.loadFromXML(new FileInputStream("C:\\\\ProgramData\\\\Dw2tech\\\\jms\\\\config.xml"));

		setCnpj(arquivo.getProperty("CLIENTE"));
		setSistema(arquivo.getProperty("SISTEMA"));
		setBanco(arquivo.getProperty("BANCO"));
		setCaminho(arquivo.getProperty("CAMINHO"));
		setIp(arquivo.getProperty("IP"));
		setPorta(arquivo.getProperty("PORTA"));
		setUser(arquivo.getProperty("USER"));
		setPwd(arquivo.getProperty("PWD"));
		setToken(arquivo.getProperty("TOKEN"));
		setTimeperpost(Integer.parseInt((arquivo.getProperty(("TIMEPERPOST")))));

		switch (arquivo.getProperty("BANCO")) {
		case "1":
			setCharSet("");
			setStr_conexao("jdbc:oracle:thin:" + ip + ":" + porta + ":XE" + "/" + caminho);
			setDriverjdbc("oracle.jdbc.driver.OracleDriver");
			break;
		case "2":
			setCharSet("");
			setStr_conexao("" + ip + ":" + porta + "/" + caminho);
			setDriverjdbc("");
			break;
		case "3":
			setCharSet("utf-8");
			setStr_conexao("jdbc:firebirdsql://" + ip + ":" + porta + "/" + caminho + "?lc_ctype=ISO8859_1" );
			setDriverjdbc("org.firebirdsql.jdbc.FBDriver");
			break;
		case "4":
			setCharSet("");
			setStr_conexao("jdbc:mysql://" + ip + ":" + porta + "/" + caminho);
			setDriverjdbc("com.mysql.jdbc.Driver");
			break;
		case "5":
			setCharSet("");
			setStr_conexao("jdbc:postgresql://" + ip + ":" + porta + "/" + caminho);
			setDriverjdbc("org.postgresql.Driver");
			break;
		default:
			break;
		}
	}

	/**
	 * Recupera o valor do CNPJ do cliente, informado no arquivo Properties.
	 * 
	 * @return String - CNPJ do cliente.
	 */
	public static String getCnpj() {
		return cnpj;
	}

	/**
	 * Aplica o CNPJ do cliente, informado no arquivo Properties.
	 * 
	 * @param cnpj CNPJ informado.
	 */
	public static void setCnpj(String cnpj) {
		Propriedades.cnpj = cnpj;
	}

	/**
	 * Recupera o valor do Timeout, informado no arquivo Properties.
	 * 
	 * @return int - Valor do Timeout.
	 */
	public int getTimeperpost() {
		return timeperpost;
	}

	/**
	 * Aplica o tempo de timeout, informado no arquivo Properties.
	 * 
	 * @param timeperpost Timeout informado.
	 */
	public static void setTimeperpost(int timeperpost) {
		Propriedades.timeperpost = timeperpost;
	}

	/**
	 * Recupera o Token de autentica��o, informado no arquivo Properties.
	 * 
	 * @return String - Token da API.
	 */
	public static String getToken() {
		return token;
	}

	/**
	 * Aplica o Token da API, informado no arquivo Properties.
	 * 
	 * @param token Token informado.
	 */
	public static void setToken(String token) {
		Propriedades.token = token;
	}

	/**
	 * Recupera o tipo de sistema do cliente, informado no arquivo Properties.
	 * 
	 * @return String - Sistema do Cliente.
	 */
	public static String getSistema() {
		return sistema;
	}

	/**
	 * Aplica o tipo de sistema do cliente, informado no arquivo Properties.
	 * 
	 * @param sistema Sistema informado.
	 */
	public static void setSistema(String sistema) {
		Propriedades.sistema = sistema;
	}

	/**
	 * Recupera qual banco de dados o cliente utiliza, informado no arquivo
	 * Properties.
	 * 
	 * @return String - Banco de dados do Cliente.
	 */
	public static String getBanco() {
		return banco;
	}

	/**
	 * Aplica o tipo de banco de dados do cliente, informado no arquivo Properties.
	 * 
	 * @param banco Banco de dados informado.
	 */
	public static void setBanco(String banco) {
		Propriedades.banco = banco;
	}

	/**
	 * Recupera o caminho onde est� localizado o banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return String - Caminho do banco de dados.
	 */
	public static String getCaminho() {
		return caminho;
	}

	/**
	 * Aplica o caminho onde est� localizado o banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @param caminho caminho do banco de dados informado.
	 */
	public static void setCaminho(String caminho) {
		Propriedades.caminho = caminho;
	}

	/**
	 * Recupera a codifica��o de caracteres do banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @return String - Codifica��o de caracteres.
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * Aplica a codifica��o de caracteres do banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @param charSet Codifica��o de caracteres informado.
	 */
	public static void setCharSet(String charSet) {
		Propriedades.charSet = charSet;
	}

	/**
	 * Recupera o IP onde est� localizado o banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - IP do banco de dados.
	 */
	public static String getIp() {
		return ip;
	}

	/**
	 * Aplica o IP do banco de dados do cliente, informado no arquivo Properties.
	 * 
	 * @param ip IP do banco de dados informado.
	 */
	public static void setIp(String ip) {
		Propriedades.ip = ip;
	}

	/**
	 * Recupera a porta de conex�o com o banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - Porta do banco de dados.
	 */
	public String getPorta() {
		return porta;
	}

	/**
	 * Aplica a porta de conex�o do banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param porta Porta de conex�o do banco de dados informado.
	 */
	public static void setPorta(String porta) {
		Propriedades.porta = porta;
	}

	/**
	 * Recupera o usu�rio de autentica��o com banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @return String - Usu�rio do banco de dados.
	 */
	public static String getUser() {
		return user;
	}

	/**
	 * Aplica o usu�rio do banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param user Usu�rio do banco de dados informado.
	 */
	public static void setUser(String user) {
		Propriedades.user = user;
	}

	/**
	 * Recupera a senha de autentica��o com banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - Senha do banco de dados.
	 */
	public static String getPwd() {
		return pwd;
	}

	/**
	 * Aplica a senha do banco de dados do cliente, informado no arquivo Properties.
	 * 
	 * @param pwd Senha do banco de dados informado.
	 */
	public static void setPwd(String pwd) {
		Propriedades.pwd = pwd;
	}

	/**
	 * Recupera os dados recebidos para conex�o com banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return String - Dados recebidos para conex�o com o banco de dados.
	 */
	public static String getStr_conexao() {
		return str_conexao;
	}

	/**
	 * Aplica os dados recebidos para conex�o com o banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @param str_conexao Dados recebidos para conex�o com o banco de dados
	 *                    informado.
	 */
	public static void setStr_conexao(String str_conexao) {
		Propriedades.str_conexao = str_conexao;
	}

	/**
	 * Recupera o driver para conex�o com banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - Driver de conex�o.
	 */
	public static String getDriverjdbc() {
		return driverjdbc;
	}

	/**
	 * Aplica o driver para conex�o com o banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @param driverjdbc Driver para conex�o com o banco de dados informado.
	 */
	public static void setDriverjdbc(String driverjdbc) {
		Propriedades.driverjdbc = driverjdbc;
	}
}