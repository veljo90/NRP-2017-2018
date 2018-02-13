package main;

import java.io.File;
import java.io.IOException;

import org.encog.Encog;

public class Main {
	
	public static final int ROWS = 1;
	public static final int COLUMNS = 10;
	public static final String CONFIGURATION_FILE = "Configuration.txt";
	
	public static void main(String[] args) {
		
		File trainingFolder = new File("trainingPictures/");
		
		File[] trainingPictures = trainingFolder.listFiles();

		ImageDivider divider = new ImageDivider(ROWS, COLUMNS);
		
		for (File picture : trainingPictures){
			try {
				divider.divide(picture);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			final ImageNeuralNetwork program = new ImageNeuralNetwork();
			program.execute(CONFIGURATION_FILE);
			
			System.out.println("Broj iteracija: " + program.getNbrOfIterations());
			System.out.println("Greška: " + program.getError());
			System.out.println("Vrijeme treniranja: " + program.getTrainingTime() + " s");
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		Encog.getInstance().shutdown();
	}

}
