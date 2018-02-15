package main;

import algorithms.LocalAlignment;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test.csv");
		localAlignment.findPrediction(5, 10);
		localAlignment.testSequences(5, 10);
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}

}
