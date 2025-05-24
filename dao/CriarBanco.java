package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CriarBanco {
	static final String URL = "jdbc:mysql://localhost";
	
	public CriarBanco(String descricao) {
		String sql = "CREATE DATABASE " + descricao;
		
		try {
			Connection conexao = DriverManager.getConnection(URL, "root", "");
			PreparedStatement operacao = conexao.prepareStatement(sql);
			operacao.execute();
			
			System.out.println("Banco de dados criado com sucesso!");
			conexao.close();
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}