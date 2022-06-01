package files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import core.Algorithm;

public class CalculateVelocityPropagation {
	
	private static void calculateVelocity(String staticFile, String dynamicFile, Double zombieV) {
		//open static file
        InputStream staticStream = Algorithm.class.getClassLoader().getResourceAsStream(staticFile);
        assert staticStream != null;
        Scanner staticScanner = new Scanner(staticStream);
        int totalParticles= Integer.parseInt(staticScanner.next()) + 1; //+1 because static contains N for humans
        double spaceRadio= Double.parseDouble(staticScanner.next());
        staticScanner.close();
		
		//open dynamic file
        InputStream dynamicStream = Algorithm.class.getClassLoader().getResourceAsStream(dynamicFile);
        assert dynamicStream != null;
        Scanner dynamicScanner = new Scanner(dynamicStream);

        double lastTime= 0;
        int zombiesLastCount= 0;
        while (dynamicScanner.hasNext()) {
        	zombiesLastCount= 0;
        	dynamicScanner.next(); //skip N token
        	//Time
        	lastTime= Double.parseDouble(dynamicScanner.next());
        	for (int i = 0; i < totalParticles; i++) {
            	//X Y R Zombie Person
        		dynamicScanner.next(); //skip X token
        		dynamicScanner.next(); //skip Y token
        		dynamicScanner.next(); //skip R token
        		int zombie= Integer.parseInt(dynamicScanner.next()); //Zombie token
        		dynamicScanner.next(); //skip Person token
        		if (zombie > 0) {
        			zombiesLastCount ++;
				}
			}
		}
        dynamicScanner.close();
        
        lastTime= (lastTime == 0) ? 1 : lastTime;
        double velocity= zombiesLastCount / lastTime;
        
        String toPrint= "" + (totalParticles - 1) + "\t" + zombieV + "\t" + velocity + "\n";
        
        try {
            File file = new File("resources/velocities.txt");
            FileWriter myWriter = new FileWriter("resources/velocities.txt", true); //true to append in file
            myWriter.write(toPrint);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("IOException ocurred");
            e.printStackTrace();
        }
        System.out.println(toPrint);
	}
	
    static public void main(String[] args) throws IOException {
    	System.out.println("Static");
		BufferedReader readerStatic = new BufferedReader(new InputStreamReader(System.in));
		String staticInput = readerStatic.readLine();
    	
    	System.out.println("Dynamic");
		BufferedReader readerDynamic= new BufferedReader(new InputStreamReader(System.in));
		String dynamicInput = readerDynamic.readLine();
		
		System.out.println("Zombie max v (default 2)");
		BufferedReader readerV= new BufferedReader(new InputStreamReader(System.in));
		String vInput = readerV.readLine();
		
		String staticFile= (staticInput.length() == 0) ? "static.txt" : staticInput;
		String dynamicFile= (dynamicInput.length() == 0) ? "dynamicEnd.txt" : dynamicInput;
		Double zombieV= (vInput.length() == 0) ? 2 : Double.parseDouble(vInput);
		
		System.out.println("Starting with " + staticFile + ", " + dynamicFile + ", V_z= " + zombieV);

		calculateVelocity(staticFile, dynamicFile, zombieV);
		
		System.out.println("End");
    }
}
