package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dao.CriarPerguntas;
import audio.SoundPlayer;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Perguntar extends JFrame {

	private static final long serialVersionUID = 1L;
	private static int qmComeca; // Quem responde primeiro
	private int respondendo; // Atualiza indicador de quem é que está respondendo
	private int tentativas = 0; // Caso ninguém acerte
	private SoundPlayer soundPlayer = new SoundPlayer(); // Objeto SoundPlayer responsável por tocar os áudios	

	// Medidas de segurança para ninguém tentar acertar a mesma resposta duas vezes
	private boolean disableOp1;
	private boolean disableOp2;
	private boolean disableOp3;
	private boolean disableOp4;
	private boolean disableOp5;
	
	CriarPerguntas criarPerguntas;
	
	private JPanel contentPane;
	private JButton btnJogando;
	private JButton tfOp1 = new JButton();
	private JButton tfOp2 = new JButton();
	private JButton tfOp3 = new JButton();
	private JButton tfOp4 = new JButton();
	private JButton tfOp5 = new JButton();
	private JLabel tfPergunta = new JLabel();
	private JLabel tfJogando = new JLabel();
	private ArrayList pergunta = new ArrayList(); /*
								O ArrayList tem três posições fixas:
								5 - Enunciado da pergunta
								6 - ID da pergunta
								7 - resposta certa, para comparar com as opções
								
								De 0 a 4 são as opções da pergunta, e elas
								estão sempre randomizadas, com a resposta
								correta estando entre elas em alguma posição	
								*/

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Perguntar frame = new Perguntar(qmComeca);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public Perguntar(int vezDeResponder, ArrayList perguntasUsadas) {
		qmComeca = vezDeResponder;
		this.respondendo = qmComeca;
		
		disableOp1 = false;
		disableOp2 = false;
		disableOp3 = false;
		disableOp4 = false;
		disableOp5 = false;
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 680, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		tfOp1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfOp1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!disableOp1)
					checarResposta(tfOp1);
				disableOp1 = true;
			}
		});
		tfOp1.setBackground(Color.WHITE);
		
		tfOp1.setBounds(25, 50, 388, 34);
		contentPane.add(tfOp1);
		tfOp2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfOp2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!disableOp2)
					checarResposta(tfOp2);
				disableOp2 = true;
			}
		});
		tfOp2.setBackground(Color.WHITE);
		
		tfOp2.setBounds(25, 95, 388, 34);
		contentPane.add(tfOp2);
		tfOp3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfOp3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!disableOp3)
					checarResposta(tfOp3);
				disableOp3 = true;
			}
		});
		tfOp3.setBackground(Color.WHITE);
		
		tfOp3.setBounds(25, 140, 388, 34);
		contentPane.add(tfOp3);
		tfOp4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfOp4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!disableOp4)
					checarResposta(tfOp4);
				disableOp4 = true;
			}
		});
		tfOp4.setBackground(Color.WHITE);
		
		tfOp4.setBounds(25, 185, 388, 34);
		contentPane.add(tfOp4);
		tfOp5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		tfOp5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!disableOp5)
					checarResposta(tfOp5);
				disableOp5 = true;
			}
		});
		tfOp5.setBackground(Color.WHITE);
		
		tfOp5.setBounds(25, 230, 388, 34);
		contentPane.add(tfOp5);
		tfPergunta.setHorizontalAlignment(SwingConstants.CENTER);
		tfPergunta.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tfPergunta.setBackground(Color.WHITE);
		
		tfPergunta.setBounds(25, 19, 629, 20);
		contentPane.add(tfPergunta);
		tfPergunta.setOpaque(true);
		
		btnJogando = new JButton("");
		tfJogando.setHorizontalAlignment(SwingConstants.CENTER);
		
		tfJogando.setOpaque(true);
		
		preencherQmEstaRespondendo(); // Atualiza indicador de quem está respondendo a pergunta
		criarPerguntas = new CriarPerguntas(perguntasUsadas, pergunta); // Pede uma pergunta da base de dados
		
		preencherPerguntas(pergunta); // coloca a pergunta e as opções na tela;
	
		
		
		btnJogando.setBounds(483, 105, 88, 97);
		contentPane.add(btnJogando);
		tfJogando.setBackground(Color.WHITE);
		
		
		tfJogando.setBounds(450, 74, 150, 20);
		contentPane.add(tfJogando);
	}
	
	private void preencherPerguntas(ArrayList pergunta) {
		
		tfPergunta.setText((String)pergunta.get(5));
		tfOp1.setText((String)pergunta.get(0));
		tfOp2.setText((String)pergunta.get(1));
		tfOp3.setText((String)pergunta.get(2));
		tfOp4.setText((String)pergunta.get(3));
		tfOp5.setText((String)pergunta.get(4));
		soundPlayer.playAudioMuitoFacil();
	}
	
	private void checarResposta(JButton bt) { // Verifica se a pessoa acertou ou errou
		
		if (bt.getText().compareTo((String)pergunta.get(7)) == 0) {
			
			bt.setBackground(Color.GREEN);
			soundPlayer.playAudioRight();
			terminarRodada(this.respondendo, 1);
			
		}
		
		else {
			
			bt.setBackground(Color.RED);
			soundPlayer.playAudioWrong();
			this.respondendo = this.respondendo + 1;
			tentativas++;
			if (tentativas == 4)
				terminarRodada(0, 0);
			preencherQmEstaRespondendo();
		
		}
		
	}
	
	private void terminarRodada(int vencedor, int operacao) {
		// operacao 1 = alguém acertou
		// operacao 0 = ninguém acertou
		
		javax.swing.JFrame optionFrame = new javax.swing.JFrame();
		
		if (operacao == 1) {
			String nome;
		
			if (vencedor == 1)
				nome = "Seu Madruga";
			else if (vencedor == 2)
				nome = "Chaves";
			else if (vencedor == 3)
				nome = "Chiquinha";
			else
				nome = "Kiko";
			
			JOptionPane.showMessageDialog(optionFrame, "Parabéns, " + nome + "! Marque qual casa deseja ocupar.");
			optionFrame.toFront();
			dispose();
			Mapa.proximaRodada(vencedor);
			//CriarPerguntas.queryPergunta();
			
		}
		else {
			
			soundPlayer.playAudioBurroDaZero();
			JOptionPane.showMessageDialog(optionFrame, "Oops! Parece que ninguém acertou nessa rodada! Nenhuma casa deve ser ocupada.");
			optionFrame.toFront();
			dispose();
			Mapa.proximaRodada(0);
			
		}
		
		//CriarPerguntas = new CriarPerguntas();
		
	}
	
	private void preencherQmEstaRespondendo () {
		
		if (this.respondendo > 4) {
			this.respondendo = 1;
		}
		
		
		if (respondendo == 1) {
			btnJogando.setIcon(new ImageIcon(getClass().getResource(Mapa.madruga)));
			tfJogando.setText("Vez do Seu Madruga");
		}
		else if (respondendo == 2) {
			btnJogando.setIcon(new ImageIcon(getClass().getResource(Mapa.chaves)));
			tfJogando.setText("Vez do Chaves");
		}
		else if (respondendo == 3) {
			btnJogando.setIcon(new ImageIcon(getClass().getResource(Mapa.chiquinha)));
			tfJogando.setText("Vez da Chiquinha");
		}
		else {
			btnJogando.setIcon(new ImageIcon(getClass().getResource(Mapa.kiko)));
			tfJogando.setText("Vez do Kiko");
		}
		
	}
	
}
