package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageDivider {
	
	private static int rows, columns;
	private File folder = new File("CutPictures/");

	ImageDivider(final int rows, final int columns){
		ImageDivider.rows = rows;
		ImageDivider.columns = columns;
	}

	public void divide(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		
		// Pozivanje metode za èitanje slike
		BufferedImage image = ImageIO.read(fis);

		int chunks = rows * columns;

		// odreðivanje velièine jednog dijela
		int chunkWidth = image.getWidth() / columns;
		int chunkHeight = image.getHeight() / rows;

		// Definiranje polja koje sadrži dijelove slika
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {

				// Inicijalizacija polja s dijelovima slike
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// "Crtanje" pojedinih dijelova slike
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, 
							 chunkWidth, 
							 chunkHeight, 
							 chunkWidth * y, 
							 chunkHeight * x,
							 chunkWidth * y + chunkWidth, 
							 chunkHeight * x + chunkHeight, 
							 null);
				gr.dispose();
			}
		}
		
		System.out.println("Rezanje slike " + file.getName() + " je završeno.");
		
		// Zapisivanje dijelova slika u zasebne datoteke
		
		String outputFolderName = this.folder + "/" + file.getName().replace(".jpg", "");
		(new File(outputFolderName)).mkdirs();
		for (int i = 0; i < imgs.length; i++) {
			//ImageIO.write(imgs[i], "jpg", new File(outputFolderName + "/slika" + i + ".jpg"));
			ImageIO.write(imgs[i], "jpg", new File(outputFolderName + "/" + file.getName().replace(".jpg", String.valueOf(i)) + ".jpg"));
		}
		
		System.out.println("Kreiranje slika je završeno.");
	}
	
	public void divide(File file, File outputFolder) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		
		// Pozivanje metode za èitanje slike
		BufferedImage image = ImageIO.read(fis);

		int chunks = rows * columns;

		// odreðivanje velièine jednog dijela
		int chunkWidth = image.getWidth() / columns;
		int chunkHeight = image.getHeight() / rows;

		// Definiranje polja koje sadrži dijelove slika
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];

		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < columns; y++) {

				// Inicijalizacija polja s dijelovima slike
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				// "Crtanje" pojedinih dijelova slike
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0, 
							 chunkWidth, 
							 chunkHeight, 
							 chunkWidth * y, 
							 chunkHeight * x,
							 chunkWidth * y + chunkWidth, 
							 chunkHeight * x + chunkHeight, 
							 null);
				gr.dispose();
			}
		}
		
		// Zapisivanje dijelova slika u zasebne datoteke
		(new File(outputFolder.toString())).mkdirs();
		for (int i = 0; i < imgs.length; i++) {
			ImageIO.write(imgs[i], "jpg", new File(outputFolder + "/" + file.getName().replace(".jpg", String.valueOf(i)) + ".jpg"));
		}
	}
}
