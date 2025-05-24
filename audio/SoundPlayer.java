package audio;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.*;


public class SoundPlayer implements LineListener {
	
	private final static String openingAudio = "src/audio/opening.wav";
	private final static String wrongAnswerAudio = "src/audio/wrongAnswer.wav";
	private final static String rightAnswerAudio = "src/audio/rightAnswer.wav";
	private final static String muitoFacilAudio = "src/audio/muitoFacil.wav";
	private final static String seuMadrugaAudio = "src/audio/seuMadruga.wav";
	private final static String chavesAudio = "src/audio/chaves.wav";
	private final static String chiquinhaAudio = "src/audio/chiquinha.wav";
	private final static String kikoAudio = "src/audio/kiko.wav";
	private final static String burroDaZero = "src/audio/burroDaZero.wav";
	Clip clip;
	
	public void playAudioBurroDaZero () {
		
		try {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(burroDaZero).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
			
		}
		
	}
	
	public void playWinnerName(int personagem) {
		
		File file;
		
		if (personagem == 1) {
			file = new File (seuMadrugaAudio).getAbsoluteFile();
		}
		else if (personagem == 2) {
			file = new File (chavesAudio).getAbsoluteFile();
		}
		else if (personagem == 3) {
			file = new File (chiquinhaAudio).getAbsoluteFile();
		}
		else {
			file = new File (kikoAudio).getAbsoluteFile();
		}
		
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
		
	}
	
	public void playAudioRight () {
		
		try {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(rightAnswerAudio).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
			
		}
		
	}

	public void playAudioWrong () {
		
		try {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(wrongAnswerAudio).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
			
		}
		
	}
	
	public void playAudioOpening() {
		
		try {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(openingAudio).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
			
		}
		
		
		
	}

	public void playAudioMuitoFacil() {
		
		try {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(muitoFacilAudio).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();

		} catch (Exception e) {

			e.getMessage();
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	public void update(LineEvent event) {}
		
}	
