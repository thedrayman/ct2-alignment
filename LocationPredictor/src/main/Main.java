package main;

import algorithms.LocalAlignment;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/Latitude_cv (2013_06_19 08_17_46 UTC).csv");
		localAlignment.findPrediction(5, 10);
		localAlignment.testSequences(5, 10, 0);
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}

}
