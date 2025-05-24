package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectaBanco {
	static final String URL = "jdbc:mysql://localhost";
	
	public ConectaBanco() {
		try {
			Connection conexao = DriverManager.getConnection(URL, "root", "");
			System.out.println("Conexão com Banco de dados realizada com sucesso!");
			conexao.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
