package br.com.dwtech.jms.testes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFB {
	
	private static Connection conexao;
	
	public static Connection getConexao() {
		
		
		Consumidor_Teste conn = new Consumidor_Teste();
		
		try {
			if (conexao == null) {
				Class.forName("org.firebirdsql.jdbc.FBDriver");
				conexao = DriverManager.getConnection("jdbc:firebirdsql://" + conn.IP_do_banco + conn.porta_do_banco +
						conn.caminho_do_banco,conn.usuario,conn.senha);
			}
			System.out.println("deu certo");
			return conexao;
		}catch (ClassNotFoundException e) {
			System.out.println("Não foi possível encontrar o" + " driver de acesso ao banco de dados.");
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			System.out.println("Não foi possível conectar com "	+ " o banco de dados.");
			e.printStackTrace();
			return null;
		}
	}
}
