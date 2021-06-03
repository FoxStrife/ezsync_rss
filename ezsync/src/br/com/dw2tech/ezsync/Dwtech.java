package br.com.dw2tech.ezsync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe Dwtech criada para se conectar � base de dados da Dw2tech e recuperar
 * SQLs de controle. Esta base de dados em Firebird estar� no ambiente do
 * cliente devido a preocupa��o de poss�vel interrup��o de conex�o com a
 * Internet. Como a l�gica do sistema necessita desses controles a
 * disponibilidade do banco de dados � essencial. Para a conex�o com o banco do
 * cliente ser� necess�rio acessar a classe Conexao.
 *
 * @see br.com.dw2tech.ezsync.Conexao
 * @see br.com.dw2tech.ezsync.Propriedades
 * 
 * @author Dw2Tech
 *
 */

public class Dwtech {
	
public static Connection conectar() throws SQLException {
    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://127.0.0.1:1433;databaseName=DW2TECH;user=sa;password=hesoyam123";
    
    Connection con = DriverManager.getConnection(connectionUrl); Statement stmt = con.createStatement();  
    return con;

    
}
}


