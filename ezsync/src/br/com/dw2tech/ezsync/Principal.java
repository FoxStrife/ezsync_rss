package br.com.dw2tech.ezsync;

/**
 * Dw2Tech Integração - 2020 -
 * 
 * Sistema de integração entre sistemas utilizando conceito de mensageria, com o
 * auxílio da ferramenta Apache ActiveMQ.
 * 
 * @version 1.0
 * 
 * @author Andryus Cavalheiro
 * @author Daniel Carlos da Silva
 *
 * @see <a href="http://www.dw2tech.com.br/">Dw2Tech Homepage</a>
 *
 */

public class Principal {
	
	

	public static void main(String[] args) throws Exception {

		Produtor.enviaFila();
	}
}
