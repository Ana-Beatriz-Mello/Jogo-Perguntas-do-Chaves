package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class CriarPerguntas {

	static String URL = "jdbc:mysql://localhost"; // ENDEREÇO
	public Connection conexao;
	private String command;
	ResultSet result;
	private PreparedStatement execute;
	private int id;
	private boolean flag = false;
	
	
	public CriarPerguntas(ArrayList perguntasUsadas, ArrayList perguntaDaVez) {
		
		perguntaDaVez.clear();
		queryPergunta(perguntasUsadas, perguntaDaVez);
		
	}
	
	//private static ArrayList usadas = new ArrayList();
	//private static ArrayList question;

	public void queryPergunta(ArrayList usadas, ArrayList question) { // Vai escolher a pergunta a ser usada na rodada
		// Retorna um vetor 
		//ArrayList question = new ArrayList();
		int id = 0;
		ResultSet result;
		URL = "jdbc:mysql://localhost/quizchaves"; // ENDEREÇO
		
		try {
				conexao = DriverManager.getConnection(URL, "root", "");
		} catch (SQLException e) {
				e.printStackTrace();
		}
		command = "SELECT id FROM pergunta ORDER BY RAND()";
		
		try {

			execute = conexao.prepareStatement(command);
			result = execute.executeQuery();
			
			while (result.next()) {
				result.next();
				id = result.getInt(1);
				
				System.out.println("\nPróximo...\n" + usadas);
				
				if (!usadas.contains(id)) {
					
					usadas.add(id);
					prepararPergunta(question, id);
					flag = true;
					break;
					
				}
				
			}

		} catch (SQLException e) {


			e.printStackTrace();

		}
		
		if (!flag) {
			
			usadas.clear();
			queryPergunta(usadas, question);
	
		}
		
	}
	
	public ArrayList prepararPergunta (ArrayList question, int id) {
		
		
		try {
			
			question.clear();
			command = "SELECT op1, op2, op3, op4, gabarito, enunciado FROM pergunta WHERE id = "+id;
			execute = conexao.prepareStatement(command);
			result = execute.executeQuery();
			result.next();

			question.add(result.getString("op1"));
			question.add(result.getString("op2"));
			question.add(result.getString("op3"));
			question.add(result.getString("op4"));
			question.add(result.getString("gabarito"));

			Collections.shuffle(question);
			question.add(result.getString("enunciado"));
			question.add(id);
			question.add(result.getString("gabarito"));
			
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return question;
		
	}

}
