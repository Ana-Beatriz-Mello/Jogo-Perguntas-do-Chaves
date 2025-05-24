package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CriarTabela {
	static final String URL = "jdbc:mysql://localhost/QuizChaves";
	
	public CriarTabela() {
		String sql = "CREATE TABLE pergunta(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, enunciado VARCHAR(100),"
				+ " op1 VARCHAR(100), op2 VARCHAR(100),op3 VARCHAR(100), op4 VARCHAR(100), gabarito VARCHAR (100))";
		
		try {
			Connection conexao = DriverManager.getConnection(URL, "root", "");
			PreparedStatement operacao = conexao.prepareStatement(sql);
			operacao.execute();
			
			System.out.println("Tabela criada com sucesso!");
			conexao.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}