package main;

import algorithms.LocalAlignment;

public class Main {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		new LocalAlignment("google location data/test.csv");
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}

}
