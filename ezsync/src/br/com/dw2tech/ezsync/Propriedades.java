package br.com.dw2tech.ezsync;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Classe Propriedades criada para ler as informações dispostas no arquivo
 * Properties e assim alimentar as variáveis do sistema. É responsável pela
 * identificação e configuração do ambiente do cliente, como por exemplo o
 * caminho do banco de dados, porta, IP, usuário, senha, Token de autenticação
 * na API, etc. Estas informações irão ser utilizadas pelas classes de conexão
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
	/** Código referência para o sistema do cliente */
	public static String sistema = null;
	/** Código referência para o banco de dados do cliente */
	public static String banco = null;
	/** Caminho do banco de dados */
	public static String caminho = null;
	/** Codificação de caracteres do banco de dados */
	public static String charSet = "UTF8";
	/** IP do banco de dados */
	public static String ip = null;
	/** Porta do banco de dados */
	public static String porta = null;
	/** Usuário do banco de dados */
	public static String user = null;
	/** Senha do banco de dados */
	public static String pwd = null;
	/** Token para acesso ao REST API */
	public static String token;
	/** Timeout para consultas SQL */
	public static int timeperpost;
	/** Recebe os dados para conexão com banco de dados */
	public static String str_conexao;
	/** Driver do banco de dados */
	public static String driverjdbc;

	/**
	 * Método que lê o arquivo de propriedades que contém os dados para a conexão
	 * com a base de dados e demais informações do ambiente do cliente. As
	 * informações preenchidas no arquivo são carregadas nas variáveis e com isto
	 * alimenta a conexão na classe Conexao. Caso haja inclusão de informações no
	 * arquivo, será necessário ajustar esta classe com seus respectivos GETs e
	 * SETs.
	 * 
	 * @throws InvalidPropertiesFormatException Exceção de formato inválido para o
	 *                                          arquivo Properties.
	 * @throws FileNotFoundException            Exceção de arquivo não encontrado no
	 *                                          caminho informado.
	 * @throws IOException                      Exceção de leitura/escrita do
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
	 * Recupera o Token de autenticação, informado no arquivo Properties.
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
	 * Recupera o caminho onde está localizado o banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return String - Caminho do banco de dados.
	 */
	public static String getCaminho() {
		return caminho;
	}

	/**
	 * Aplica o caminho onde está localizado o banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @param caminho caminho do banco de dados informado.
	 */
	public static void setCaminho(String caminho) {
		Propriedades.caminho = caminho;
	}

	/**
	 * Recupera a codificação de caracteres do banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @return String - Codificação de caracteres.
	 */
	public String getCharSet() {
		return charSet;
	}

	/**
	 * Aplica a codificação de caracteres do banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @param charSet Codificação de caracteres informado.
	 */
	public static void setCharSet(String charSet) {
		Propriedades.charSet = charSet;
	}

	/**
	 * Recupera o IP onde está localizado o banco de dados do cliente, informado no
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
	 * Recupera a porta de conexão com o banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - Porta do banco de dados.
	 */
	public String getPorta() {
		return porta;
	}

	/**
	 * Aplica a porta de conexão do banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param porta Porta de conexão do banco de dados informado.
	 */
	public static void setPorta(String porta) {
		Propriedades.porta = porta;
	}

	/**
	 * Recupera o usuário de autenticação com banco de dados do cliente, informado
	 * no arquivo Properties.
	 * 
	 * @return String - Usuário do banco de dados.
	 */
	public static String getUser() {
		return user;
	}

	/**
	 * Aplica o usuário do banco de dados do cliente, informado no arquivo
	 * Properties.
	 * 
	 * @param user Usuário do banco de dados informado.
	 */
	public static void setUser(String user) {
		Propriedades.user = user;
	}

	/**
	 * Recupera a senha de autenticação com banco de dados do cliente, informado no
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
	 * Recupera os dados recebidos para conexão com banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @return String - Dados recebidos para conexão com o banco de dados.
	 */
	public static String getStr_conexao() {
		return str_conexao;
	}

	/**
	 * Aplica os dados recebidos para conexão com o banco de dados do cliente,
	 * informado no arquivo Properties.
	 * 
	 * @param str_conexao Dados recebidos para conexão com o banco de dados
	 *                    informado.
	 */
	public static void setStr_conexao(String str_conexao) {
		Propriedades.str_conexao = str_conexao;
	}

	/**
	 * Recupera o driver para conexão com banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @return String - Driver de conexão.
	 */
	public static String getDriverjdbc() {
		return driverjdbc;
	}

	/**
	 * Aplica o driver para conexão com o banco de dados do cliente, informado no
	 * arquivo Properties.
	 * 
	 * @param driverjdbc Driver para conexão com o banco de dados informado.
	 */
	public static void setDriverjdbc(String driverjdbc) {
		Propriedades.driverjdbc = driverjdbc;
	}
}