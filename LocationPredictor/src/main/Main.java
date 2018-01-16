package main;

import reader.CSVReader;

public class Main {

	public static void main(String[] args) {
		CSVReader reader = new CSVReader();
		reader.readCSV("google location data/test.csv");
		
		System.out.println(reader.getLocationSequence());
		
		//TODO now use alignment

	}

}
