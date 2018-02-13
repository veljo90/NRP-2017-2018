package main;

import java.util.Scanner;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationBiPolar;
import org.encog.engine.network.activation.ActivationClippedLinear;
import org.encog.engine.network.activation.ActivationCompetitive;
import org.encog.engine.network.activation.ActivationElliott;
import org.encog.engine.network.activation.ActivationElliottSymmetric;
import org.encog.engine.network.activation.ActivationGaussian;
import org.encog.engine.network.activation.ActivationLOG;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationRamp;
import org.encog.engine.network.activation.ActivationSIN;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.engine.network.activation.ActivationSteepenedSigmoid;
import org.encog.engine.network.activation.ActivationStep;
import org.encog.engine.network.activation.ActivationTANH;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class main {

	static String fileName = "ulaz.csv";
	public static String meals[][] = {{"francuski","the","èaj"},
									  {"spanjolski","te","èaj"},
									  {"portugalski","cha","èaj"},
									  {"talijanski","te","èaj"},
									  {"rumunjski","ceai","èaj"},
									  {"francuski","pieu","odrezak"},
									  {"spanjolski","estaca","odrezak"},
									  {"portugalski","estaca","odrezak"},
									  {"talijanski","palo","odrezak"},
									  {"rumunjski","miza","odrezak"},
									  {"francuski","riz","riža"},
									  {"spanjolski","arroz","riža"},
									  {"portugalski","arroz","riža"},
									  {"talijanski","riso","riža"},
									  {"rumunjski","orez","riža"},
									  {"francuski","cafe","kava"},
									  {"spanjolski","cafe","kava"},
									  {"portugalski","cafe","kava"},
									  {"talijanski","caffe","kava"},
									  {"rumunjski","cafea","kava"},
									  {"francuski","jus","sok"},
									  {"spanjolski","jugo","sok"},
									  {"portugalski","suco","sok"},
									  {"talijanski","succo","sok"},
									  {"rumunjski","suc","sok"}};

	public static double MEAL_OUTPUT[][] = { { -1.0 }, { -1.0 }, { -1.0 }, { -1.0 }, { -1.0 },
											 { -0.5 }, { -0.5 }, { -0.5 }, { -0.5 }, { -0.5 },
											 { -0.0 }, { -0.0 }, { -0.0 }, { -0.0 }, { -0.0 },
											 { 0.5 }, { 0.5 }, { 0.5 }, { 0.5 }, { 0.5 },
											 { 1 }, { 1 }, { 1 }, { 1 }, { 1 } };

	/**
	 * The main method.
	 * @param args No arguments are used.
	 */
	public static void main(final String args[]) {

		double[][] normalizedInput = normalizeInput(meals);

		// create a neural network, without using a factory
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null,false,3));
		network.addLayer(new BasicLayer(new ActivationSIN(),true,6));
		network.addLayer(new BasicLayer(new ActivationSIN(),false,5));
		network.addLayer(new BasicLayer(new ActivationSIN(),false,3));
		network.addLayer(new BasicLayer(new ActivationSIN(),false,1));
		network.getStructure().finalizeStructure();
		network.reset();

		// create training data
		MLDataSet trainingSet = new BasicMLDataSet(normalizedInput, MEAL_OUTPUT);

		// train the neural network
		final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
		//final Backpropagation train = new Backpropagation(network, trainingSet);

		int epoch = 1;

		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
		} while(train.getError() > 0.000005);
		train.finishTraining();

		String[] ulaz = new String[1];
		while (true){
			Scanner reader = new Scanner(System.in);
			System.out.println("Unesite rijeè: ");
			String rijec = reader.nextLine();
			if (rijec.equals("stop")){
				reader.close();
				break;
			}

			ulaz[0] = rijec;
			double[] normalizedUlaz = normalizeUlaz(rijec);
			MLData input = new BasicMLData(normalizedUlaz);
			final MLData output = network.compute(input);
			String jelo;
			double normaliziraniIzlaz = output.getData(0);
			if (normaliziraniIzlaz < -0.75)
				jelo = "Èaj";
			else if (normaliziraniIzlaz < -0.25)
				jelo = "Odrezak";
			else if (normaliziraniIzlaz < 0.25)
				jelo = "Riža";
			else if (normaliziraniIzlaz < 0.75)
				jelo = "Kava";
			else
				jelo = "Sok";

			System.out.println("Normalizirani izlaz: " + output.getData(0) + ", jelo: " + jelo);
		}

		Encog.getInstance().shutdown();
	}

//	private static double[][] normalizeInput(String[][] input){
//		double[][] normalizedInput = new double[meals.length][6];
//
//		for(int i = 0; i<meals.length; i++){
//			String meal = meals[i][1].toLowerCase();
//			for(int j = 0; j<meal.length();j++){
//				int letter = (int) meal.charAt(j);
//				double normalizedLetter = ((letter - 97)/12.5 - 1);
//				normalizedInput[i][j] = normalizedLetter;
//			}
//		}
//		return normalizedInput;
//	}
//
//	private static double[] normalizeUlaz(String ulaz){
//		double[] normalizedUlaz = new double[6];
//		for(int i = 0; i<ulaz.length(); i++){
//			int letter = ulaz.charAt(i);
//			double normalizedLetter = ((letter - 97)/12.5 - 1);
//			normalizedUlaz[i] = normalizedLetter;
//		}
//		return normalizedUlaz;
//	}
	
	
	private static double[][] normalizeInput(String[][] input){
		double[][] normalizedInput = new double[meals.length][3];

		for(int i = 0; i<meals.length; i++){
			String meal = meals[i][1].toLowerCase();
			
			double samoglasnik = 10.0;
			double suglasnik = 10.0;
			
			for(int j = 0; j<meal.length(); j++){
				char letter = meal.charAt(j);
				if (samoglasnik != 10.0 && suglasnik != 10.0)
					break;
				
				if (samoglasnik == 10.0 && (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u')){
					samoglasnik = ((letter - 97)/12.5 - 1);
				}
				else if (suglasnik == 10.0 && letter != 'a' && letter != 'e' && letter != 'i' && letter != 'o' && letter != 'u'){
					suglasnik = ((letter - 97)/12.5 - 1);
				}
			}
			
			normalizedInput[i][0] = 6.0 / meal.length();
			normalizedInput[i][1] = samoglasnik;
			normalizedInput[i][2] = suglasnik;
		}
		return normalizedInput;
	}
	
	
	private static double[] normalizeUlaz(String ulaz){
		double[] normalizedUlaz = new double[3];
		normalizedUlaz[0] = 6.0 / ulaz.length();
		double samoglasnik = 10.0;
		double suglasnik = 10.0;
		
		for(int i = 0; i<ulaz.length(); i++){
			char letter = ulaz.charAt(i);
			if (samoglasnik != 10.0 && suglasnik != 10.0)
				break;
			
			if (samoglasnik == 10.0 && (letter == 'a' || letter == 'e' || letter == 'i' || letter == 'o' || letter == 'u')){
				samoglasnik = ((letter - 97)/12.5 - 1);
			}
			else if (suglasnik == 10.0 && letter != 'a' && letter != 'e' && letter != 'i' && letter != 'o' && letter != 'u'){
				suglasnik = ((letter - 97)/12.5 - 1);
			}
			
		}
		normalizedUlaz[1] = samoglasnik;
		normalizedUlaz[2] = suglasnik;
		
		return normalizedUlaz;
	}
}
