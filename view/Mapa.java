package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import audio.SoundPlayer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.Font;

public class Mapa extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int vezDeResponder; // Indica quem começa respondendo na próxima pergunta
	private static boolean perguntaAtiva = false;
	private JPanel contentPane;
	private int personagem = 0;
	private JButton btnFonte1;
	private JButton btnFonte2;
	private JButton btnFonte3;
	private JButton btnFonte4;
	private JButton btnCompetir;
	private ArrayList usadas = new ArrayList(); // Guarda as perguntas que já foram usadas no jogo

	public SoundPlayer soundPlayer = new SoundPlayer();
	
	// Só quem acerta uma pergunta pode preencher uma casa
	private static boolean madrugaAtivo = false;
	private static boolean chavesAtivo = false;
	private static boolean chiquinhaAtivo = false;
	private static boolean kikoAtivo = false;
	
	private JButton winBarriga;
	private JButton winFonte;
	private JButton winMadruga;
	private JButton winChaves;
	private JButton winEscola;
	private JButton winFlorinda;
	private JButton winClotilde;
	private JButton winner;
	
	private boolean alterarAtivo = false; //Controla se há um personagem ou borracha selecionado
	private int empateAtivo = 0; //Controlador para que o programa saiba se, ao ter todas as casas preenchidas, há um
										//empate em alguma das áreas (anuncia vencedor ou empate não finalizado?)
	
	private final String mapa = "/imagens/MapaPersonagens.png";
	public static final String chiquinha = "/imagens/PersonagemChiquinha.png";
	public static final String chaves = "/imagens/PersonagemChaves.png";
	public static final String kiko = "/imagens/PersonagemKiko.png";
	public static final String madruga = "/imagens/PersonagemSeuMadruga.png";
	private final String coroa = "/imagens/coroa.png";
	private final String tie = "";
	
	private static int controladorFimDeJogo;

	static int[][] frequencyArray = new int[7][6];
	// A posição [x][5] indica quem ganhou aquele lugar
	// [ Lugar ][ Personagem ]
	/*
	 *Locais:
	 *Casa do Barriga: 0
	 *Casa da Clotilde: 1
	 *Casa da Dona Florinda: 2
	 *Escola: 3
	 *Barril do Chaves: 4
	 *Casa do Seu Madruga: 5
	 *Fonte: 6
	 */
	
	/* 
	 * Personagens:
		0 -> nenhum
		1 -> Seu Madruga
		2 -> Chaves
		3 -> Chiquinha
		4 -> Kiko
		-> A posição 0 no vetor representa quantas casas estão ocupadas.
	*/
	
	// Anuncia um local como "vencido" ou em rodada de "empate"
	public void finalizarLocal(int local, int operacao) {
		//Se operação = 0 -> Um local vencido anteriormente apagou um dos personagens
		//Se operação = 1 -> Um local foi vencido
		
		JButton auxiliar = null;
		
		switch (local) {
		
		case 0:
			auxiliar = winBarriga;
			break;
		case 1:
			auxiliar = winClotilde;
			break;
		case 2:
			auxiliar = winFlorinda;
			break;
		case 3:
			auxiliar = winEscola;
			break;
		case 4:
			auxiliar = winChaves;
			break;
		case 5:
			auxiliar = winMadruga;
			break;
		case 6:
			auxiliar = winFonte;
			break;
		default:
			System.out.println("Local não encontrado!!");
			break;
		}
		
		
		if (operacao == 0) {
			
			auxiliar.setVisible(false);
			frequencyArray[local][5] = 0;
			controladorFimDeJogo--;
			
		}
		else if (operacao == 1) {
		
			auxiliar.setVisible(true);
			boolean empate = false;
			int maior = -1;
			
			for (int i = 1; i < 5; i++) {
				
				if (frequencyArray[local][i] > maior) {
					empate = false;
					maior = frequencyArray[local][i];
					frequencyArray[local][5] = i;
					
				}
				else if (frequencyArray[local][i] == maior) {
					empate = true;
					frequencyArray[local][5] = 0;
				}
				
			}
			if (empate) {
				auxiliar.setText("EMPATE!");
				empateAtivo++;
			}
			else {
				if (frequencyArray[local][5] == 1) {
					if (auxiliar.getText().toString().equals("EMPATE!")) {
						
						empateAtivo--;
					}
					auxiliar.setText("MADRUGA");
					soundPlayer.playWinnerName(1);
				}
				else if (frequencyArray[local][5] == 2) {
					if (auxiliar.getText().toString().equals("EMPATE!")) {
						empateAtivo--;
					}
					auxiliar.setText("CHAVES");
					soundPlayer.playWinnerName(2);
					
				}
				else if (frequencyArray[local][5] == 3) {
					if (auxiliar.getText().toString().equals("EMPATE!")) {
						empateAtivo--;
					}
					auxiliar.setText("CHIQUINHA");
					soundPlayer.playWinnerName(3);
					
				}
				else {
					if (auxiliar.getText().toString().equals("EMPATE!")) {
						empateAtivo--;
					}
					auxiliar.setText("KIKO");
					soundPlayer.playWinnerName(4);
					
				}
			}

			controladorFimDeJogo++;
			
		}

		acabarJogo();
		
	}
	
	public void vencedor() {

		int[] aux = new int[5];
		int pos = -1;
		int contMadruga = 0;
		int contChaves = 0;
		int contChiquinha = 0;
		int contKiko = 0;
		
		for (int i = 0; i < 7; i++) {
			
			switch (frequencyArray[i][5]) {
			
				case 1:
					contMadruga++;
					break;
				case 2:
					contChaves++;
					break;
				case 3:
					contChiquinha++;
					break;
				case 4: 
					contKiko++;
					break;
			}
			
		}
		aux[0] = contMadruga;
		aux[1] = contChaves;
		aux[2] = contChiquinha;
		aux[3] = contKiko;
		
		boolean empatado = false;
		int maior = -1;
		for (int i = 0; i < 4; i++) {
			if (aux[i] > maior) {
				maior = aux[i];
				pos = i;
				empatado = false;
				empateAtivo--;
			}
			else if (aux[i] == maior) {
				empatado = true;
			}
		}
		
		if (empatado) {
			winner.setText("EMPATADO!");
			winner.setVisible(true);	
		}
		else {
			switch (pos+1) {
			
				case 1:
					winner.setText("Parabéns, Madruga!");
					soundPlayer.playWinnerName(1);
					break;
				case 2:
					winner.setText("Parabéns, Chaves!");
					soundPlayer.playWinnerName(2);
					break;
				case 3:
					winner.setText("Parabéns, Chiquinha!");
					soundPlayer.playWinnerName(3);
					break;
				case 4:
					winner.setText("Parabéns, Kiko!");
					soundPlayer.playWinnerName(4);
					break;
			}
		}
		
		winner.setVisible(true);
		
	}
	
	public static void proximaRodada(int qmAcertou) { // Recebe aviso da classe Perguntar e passa a vez de começar a responder para a próxima pessoa
		
		if (qmAcertou == 1)
			madrugaAtivo = true;
		if (qmAcertou == 2)
			chavesAtivo = true;
		if (qmAcertou == 3)
			chiquinhaAtivo = true;
		if (qmAcertou == 4)
			kikoAtivo = true;
		
		vezDeResponder++;
		if (vezDeResponder > 4)
			vezDeResponder = 1;
		
	}
	
	public void acabarJogo() {
		
		if (controladorFimDeJogo == 7) {
			if (empateAtivo != 0) {
				return;
			}
			vencedor();
			
		}
		
		else {
			
			winner.setVisible(false);
			
		}
		
	}
	
	// Atualizar o array de frequencia sempre que um personagem for alocado
	public void updateFrequencyArray(int local, int personagem, int operacao) {
		
		//operação = 1 -> personagem colocado em casa
		//operação = 0 -> apagando um personagem
		if (operacao == 1) {
			if(frequencyArray[local][0] + 1 > 4) {
				
				return;
			}
			frequencyArray[local][0]++;
			frequencyArray[local][personagem]++;
			
			if (frequencyArray[local][0] == 4) {
				
				finalizarLocal(local,1);
				
			}
			
		}
		else if (operacao == 0) {
			frequencyArray[local][personagem]--;
			
			if (frequencyArray[local][0] == 4) {
				finalizarLocal(local,0);
			}
			frequencyArray[local][0]--;
			
		}
		
	}	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mapa frame = new Mapa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		// Inicializa o array de frequências
		for (int s = 0; s < 7; s++) {
			
			for (int j = 0; j < 6; j++) {
				
				frequencyArray[s][j] = 0;
				
			}
			
		}
		
		// Inicializa flag de quantos locais estao finalizados
		controladorFimDeJogo = 0;
		
	}

	
	public void atualizarCasa (JButton bt, int local) {
		// Coloca o personagem na casa selecionada
		// Ou apaga ele, caso a borracha esteja selecionada
		// recebe a casa que está sendo atualizada
		
		if (alterarAtivo) {
			if(personagem == 1 && bt.getIcon() == null && madrugaAtivo == true) {
				bt.setIcon(new ImageIcon(getClass().getResource(madruga)));
				updateFrequencyArray(local,1,1);
				madrugaAtivo = false;
			}
			else if(personagem == 2 && bt.getIcon() == null && chavesAtivo == true) {
				bt.setIcon(new ImageIcon(getClass().getResource(chaves)));
				updateFrequencyArray(local,2,1);
				chavesAtivo = false;
			}
			else if(personagem == 3 && bt.getIcon() == null && chiquinhaAtivo == true) {
				bt.setIcon(new ImageIcon(getClass().getResource(chiquinha)));
				updateFrequencyArray(local,3,1);
				chiquinhaAtivo = false;
			}
			else if(personagem == 4 && bt.getIcon() == null && kikoAtivo == true) {
				bt.setIcon(new ImageIcon(getClass().getResource(kiko)));
				updateFrequencyArray(local,4,1);
				kikoAtivo = false;
			}
			else if(personagem == 0) {
			
				if (bt.getIcon().toString().equals(this.getClass().getResource(madruga).toString())) {
					updateFrequencyArray(local,1,0);
				}
				else if(bt.getIcon().toString().equals(this.getClass().getResource(chaves).toString())) {
					updateFrequencyArray(local,2,0);
				}
				else if(bt.getIcon().toString().equals(this.getClass().getResource(chiquinha).toString())) {
					updateFrequencyArray(local,3,0);
				}
				else if (bt.getIcon().toString().equals(this.getClass().getResource(kiko).toString())) {
					updateFrequencyArray(local,4,0);
				}
			
				bt.setIcon(null);
			}
			
		}
		
		alterarAtivo = false;
		
	}
	/**
	 * Create the frame.
	 */
	public Mapa() {
		soundPlayer.playAudioOpening();
		vezDeResponder = 1;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 726, 464);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(new Color(128, 128, 255));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				personagem = 1;
				alterarAtivo = true;
			}
		});
		
		btnCompetir = new JButton("Competir");
		btnCompetir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Se o vencedor não marcou sua casa
				// Perde a chance de marcar
				madrugaAtivo = false;
				chavesAtivo = false;
				chiquinhaAtivo = false;
				kikoAtivo = false;
				
				Perguntar perguntar = new Perguntar(vezDeResponder, usadas);
				perguntaAtiva = true;
				perguntar.setVisible(true);
				
			}
		});
		btnCompetir.setBounds(611, 386, 89, 23);
		contentPane.add(btnCompetir);
		
		
		JLabel lblNewLabel_1 = new JLabel("Casa Dona Florinda");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_1.setBackground(new Color(255, 128, 255));
		lblNewLabel_1.setBounds(172, 167, 131, 14);
		contentPane.add(lblNewLabel_1);
		
		winner = new JButton("");
		winner.setForeground(Color.WHITE);
		winner.setBackground(Color.BLACK);
		winner.setBounds(245, 180, 222, 91);
		contentPane.add(winner);
		winner.setVisible(false);
		
		winBarriga = new JButton("");
		winBarriga.setBounds(447, 46, 120, 17);
		contentPane.add(winBarriga);
		winBarriga.setVisible(false);
		
		winClotilde = new JButton("");
		winClotilde.setBounds(302, 46, 120, 17);
		contentPane.add(winClotilde);
		winClotilde.setVisible(false);
		
		winChaves = new JButton("");
		winChaves.setBounds(123, 386, 126, 16);
		contentPane.add(winChaves);
		winChaves.setVisible(false);
		
		winEscola = new JButton("");
		winEscola.setBounds(38, 275, 103, 17);
		contentPane.add(winEscola);
		winEscola.setVisible(false);
		
		winFonte = new JButton("");
		winFonte.setBounds(474, 386, 136, 16);
		contentPane.add(winFonte);
		winFonte.setVisible(false);
		
		winMadruga = new JButton("");
		winMadruga.setBounds(286, 386, 146, 16);
		contentPane.add(winMadruga);
		winMadruga.setVisible(false);
		
		winFlorinda = new JButton("");
		winFlorinda.setBounds(172, 46, 120, 17);
		contentPane.add(winFlorinda);
		winFlorinda.setVisible(false);

		btnNewButton_1.setIcon(new ImageIcon(getClass().getResource(madruga)));
		btnNewButton_1.setBounds(629, 66, 55, 60);
		contentPane.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				personagem = 2;
				alterarAtivo = true;
			}
		});
		
		btnNewButton_2.setIcon(new ImageIcon(getClass().getResource(chaves)));
		btnNewButton_2.setBackground(new Color(128, 255, 128));
		btnNewButton_2.setBounds(629, 155, 55, 60);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				personagem = 3;
				alterarAtivo = true;
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(getClass().getResource(chiquinha)));
		btnNewButton_3.setBackground(new Color(255, 128, 255));
		btnNewButton_3.setBounds(629, 239, 55, 59);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				personagem = 4;
				alterarAtivo = true;
			}
		});
		btnNewButton_4.setBackground(new Color(255, 255, 128));
		btnNewButton_4.setIcon(new ImageIcon(getClass().getResource(kiko)));
		btnNewButton_4.setBounds(629, 326, 55, 60);
		contentPane.add(btnNewButton_4);
		
		
		
//		JButton btnNewButton_0 = new JButton("");
//		btnNewButton_0.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				personagem = 0;
//			}
//		});
//		btnNewButton_0.setBackground(new Color(255, 255, 128));
//		btnNewButton_0.setIcon(new ImageIcon("C:\\Users\\aluno.unilasalle\\eclipse-workspace\\RPG_Chaves\\Imagens\\PersonagemKiko.png"));
//		btnNewButton_0.setBounds(609, 326, 55, 60);
//		contentPane.add(btnNewButton_0);
//		
		
		JButton btnVazio = new JButton("");
		btnVazio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				personagem = 0;
				alterarAtivo = true;
			}
		});
		btnVazio.setBounds(629, 34, 55, 26);
		contentPane.add(btnVazio);
		
		
		////////////////////// SEU BARRIGA///////////////////////////
		JButton btnCasaSeuBarriga1 = new JButton("");
		btnCasaSeuBarriga1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuBarriga1, 0);
			}
		});
		btnCasaSeuBarriga1.setBounds(513, 66, 39, 40);
		
		contentPane.add(btnCasaSeuBarriga1);
		JButton btnCasaSeuBarriga2 = new JButton("");
		btnCasaSeuBarriga2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuBarriga2, 0);
			}
		});
		btnCasaSeuBarriga2.setBounds(513, 116, 39, 40);
		contentPane.add(btnCasaSeuBarriga2);
		
		JButton btnCasaSeuBarriga3 = new JButton("");
		btnCasaSeuBarriga3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuBarriga3, 0);
			}
		});
		btnCasaSeuBarriga3.setBounds(464, 116, 39, 40);
		contentPane.add(btnCasaSeuBarriga3);
		
		JButton btnCasaSeuBarriga4 = new JButton("");
		btnCasaSeuBarriga4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuBarriga4, 0);
			}
		});
		btnCasaSeuBarriga4.setBounds(464, 66, 39, 40);
		contentPane.add(btnCasaSeuBarriga4);
		
		
		///////////////////////// DONA FLORINDA///////////////////////////
		JButton btnCasaDonaFlorinda1 = new JButton("");
		btnCasaDonaFlorinda1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaFlorinda1, 2);
			}
		});
		btnCasaDonaFlorinda1.setBounds(245, 66, 39, 40);
		contentPane.add(btnCasaDonaFlorinda1);
		
		JButton btnCasaDonaFlorinda2 = new JButton("");
		btnCasaDonaFlorinda2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaFlorinda2, 2);
			}
		});
		btnCasaDonaFlorinda2.setBounds(196, 66, 39, 40);
		contentPane.add(btnCasaDonaFlorinda2);
		
		JButton btnCasaDonaFlorinda3 = new JButton("");
		btnCasaDonaFlorinda3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaFlorinda3, 2);
			}});
		btnCasaDonaFlorinda3.setBounds(245, 116, 39, 40);
		contentPane.add(btnCasaDonaFlorinda3);
		
		JButton btnCasaDonaFlorinda4 = new JButton("");
		btnCasaDonaFlorinda4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaFlorinda4, 2);
			}});
		btnCasaDonaFlorinda4.setBounds(196, 116, 39, 40);
		contentPane.add(btnCasaDonaFlorinda4);
		
		
		//////////////////// SEU MADRUGADÃO////////////////////
		JButton btnCasaSeuMadruga1 = new JButton("");
		btnCasaSeuMadruga1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuMadruga1, 5);
			}});
		btnCasaSeuMadruga1.setBounds(315, 295, 39, 40);
		contentPane.add(btnCasaSeuMadruga1);
		
		JButton btnCasaSeuMadruga2 = new JButton("");
		btnCasaSeuMadruga2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuMadruga2, 5);
			}});
		btnCasaSeuMadruga2.setBounds(364, 346, 39, 40);
		contentPane.add(btnCasaSeuMadruga2);
		
		JButton btnCasaSeuMadruga3 = new JButton("");
		btnCasaSeuMadruga3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuMadruga3, 5);
			}});
		btnCasaSeuMadruga3.setBounds(364, 295, 39, 40);
		contentPane.add(btnCasaSeuMadruga3);
		
		JButton btnCasaSeuMadruga4 = new JButton("");
		btnCasaSeuMadruga4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaSeuMadruga4, 5);
			}});
		btnCasaSeuMadruga4.setBounds(315, 346, 39, 40);
		contentPane.add(btnCasaSeuMadruga4);
		
		//////////////////////BRUXA DO 71 ///////////////////////
		
		JButton btnCasaDonaClotilde1 = new JButton("");
		btnCasaDonaClotilde1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaClotilde1, 1);
			}});
		btnCasaDonaClotilde1.setBounds(315, 116, 39, 40);
		contentPane.add(btnCasaDonaClotilde1);
		
		JButton btnCasaDonaClotilde2 = new JButton("");
		btnCasaDonaClotilde2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaClotilde2, 1);
			}
		});
		btnCasaDonaClotilde2.setBounds(364, 66, 39, 40);
		contentPane.add(btnCasaDonaClotilde2);
		

		JButton btnCasaDonaClotilde3 = new JButton("");
		btnCasaDonaClotilde3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaClotilde3, 1);
			}
		});
		btnCasaDonaClotilde3.setBounds(364, 116, 39, 40);
		contentPane.add(btnCasaDonaClotilde3);
		
		JButton btnCasaDonaClotilde4 = new JButton("");
		btnCasaDonaClotilde4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnCasaDonaClotilde4, 1);
			}
		});
		btnCasaDonaClotilde4.setBounds(315, 66, 39, 40);
		contentPane.add(btnCasaDonaClotilde4);
		
		
		
		////////////////// BARRIL DO CHAVES ///////////	
		JButton btnBarrilChaves1 = new JButton("");
		btnBarrilChaves1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnBarrilChaves1, 4);
			}
		});
		btnBarrilChaves1.setBounds(147, 298, 39, 40);
		contentPane.add(btnBarrilChaves1);
		
		JButton btnBarrilChaves2 = new JButton("");
		btnBarrilChaves2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnBarrilChaves2, 4);
			}
		});
		btnBarrilChaves2.setBounds(196, 346, 39, 40);
		contentPane.add(btnBarrilChaves2);

		JButton btnBarrilChaves3 = new JButton("");
		btnBarrilChaves3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnBarrilChaves3, 4);
			}
		});
		btnBarrilChaves3.setBounds(196, 299, 39, 40);
		contentPane.add(btnBarrilChaves3);
		
		JButton btnBarrilChaves4 = new JButton("");
		btnBarrilChaves4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnBarrilChaves4, 4);
			}
		});
		btnBarrilChaves4.setBounds(147, 346, 39, 40);
		contentPane.add(btnBarrilChaves4);
		
		
		////////////////ESCOLA/////////////////
		JButton btnEscola1;
		btnEscola1 = new JButton("");
		btnEscola1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnEscola1, 3);
			}
		});
		btnEscola1.setBounds(98, 180, 39, 40);
		contentPane.add(btnEscola1);
		
		JButton btnEscola2 = new JButton("");
		btnEscola2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnEscola2, 3);
			}
		});
		btnEscola2.setBounds(49, 180, 39, 40);
		contentPane.add(btnEscola2);

		JButton btnEscola3 = new JButton("");
		btnEscola3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnEscola3, 3);
			}
		});
		btnEscola3.setBounds(98, 231, 39, 40);
		contentPane.add(btnEscola3);
		
		JButton btnEscola4 = new JButton("");
		btnEscola4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				atualizarCasa(btnEscola4, 3);
			}
		});
		btnEscola4.setBounds(49, 231, 39, 40);
		contentPane.add(btnEscola4);
		
		
		
		//////////////////// FONTE ///////////
		JButton btnFonte1;
		btnFonte1 = new JButton("");
		btnFonte1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				atualizarCasa(btnFonte1, 6);
			}
		});
		btnFonte1.setBounds(545, 293, 39, 40);
		contentPane.add(btnFonte1);
		
		JButton btnFonte2;
		btnFonte2 = new JButton("");
		btnFonte2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				atualizarCasa(btnFonte2, 6);
			}
		});
		btnFonte2.setBounds(496, 346, 39, 40);
		contentPane.add(btnFonte2);

		JButton btnFonte3 = new JButton("");
		btnFonte3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				atualizarCasa(btnFonte3, 6);
			}
		});
		btnFonte3.setBounds(496, 295, 39, 40);
		contentPane.add(btnFonte3);
		
		JButton btnFonte4;
		btnFonte4 = new JButton("");
		btnFonte4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				atualizarCasa(btnFonte4, 6);
			}
		});
		btnFonte4.setBounds(545, 346, 39, 40);
		contentPane.add(btnFonte4);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Casa Seu Barriga");
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBackground(new Color(255, 128, 255));
		lblNewLabel.setBounds(474, 167, 120, 14);
		contentPane.add(lblNewLabel);
		

		JLabel lblNewLabel_3 = new JLabel("Casa Seu Madruga");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_3.setBackground(new Color(255, 255, 255));
		lblNewLabel_3.setBounds(302, 275, 120, 14);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Casa Dona Clotilde");
		lblNewLabel_3_1.setForeground(Color.WHITE);
		lblNewLabel_3_1.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_3_1.setBackground(Color.MAGENTA);
		lblNewLabel_3_1.setBounds(312, 167, 142, 14);
		contentPane.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_3_2 = new JLabel("Barril do Chaves");
		lblNewLabel_3_2.setForeground(Color.WHITE);
		lblNewLabel_3_2.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_3_2.setBackground(Color.WHITE);
		lblNewLabel_3_2.setBounds(145, 278, 120, 14);
		contentPane.add(lblNewLabel_3_2);
		
		
		JLabel lblNewLabel_3_2_1 = new JLabel("Escola");
		lblNewLabel_3_2_1.setForeground(Color.WHITE);
		lblNewLabel_3_2_1.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_3_2_1.setBackground(Color.WHITE);
		lblNewLabel_3_2_1.setBounds(66, 155, 55, 14);
		contentPane.add(lblNewLabel_3_2_1);
		
		JLabel lblNewLabel_3_2_2 = new JLabel("Fonte");
		lblNewLabel_3_2_2.setForeground(Color.WHITE);
		lblNewLabel_3_2_2.setFont(new Font("Courier New", Font.BOLD, 12));
		lblNewLabel_3_2_2.setBackground(Color.WHITE);
		lblNewLabel_3_2_2.setBounds(513, 275, 46, 14);
		contentPane.add(lblNewLabel_3_2_2);
		
		JLabel Background = new JLabel(new ImageIcon(getClass().getResource(mapa)));
		Background.setBounds(10, 11, 700, 415);
		contentPane.add(Background);
	}
}
