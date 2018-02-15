package tests;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import algorithms.LocalAlignment;
import algorithms.Node;
import algorithms.SmithWaterman;

public class AlignmentTests {

	@Test
	public void testLocalAlignmentWithFirstDataSet() {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test.csv");
		localAlignment.findPrediction(5, 10);
		localAlignment.testSequences(5, 10, 0);
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}
	
	@Test
	public void testLocalAlignmentWithSecondDataSet() {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test2.csv");
		localAlignment.findPrediction(5, 10);
		localAlignment.testSequences(5, 10, 0);
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}
	
	@Test
	public void testLocalAlignmentWithThirdDataSet() {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test3.csv");
		localAlignment.findPrediction(5, 10);
		localAlignment.testSequences(5, 10, 0);
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}
	
	@Test
	public void testShift() {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test2.csv");
		localAlignment.findPrediction(5, 10);
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i = 0; i < 20; i++) {
			list.add(localAlignment.testSequences(5, 10, i));		
			
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
		
		System.out.println("Levenstein distances: " + list);
	}
	
	@Test
	public void testSearchLength() {
		long start = System.currentTimeMillis();
		LocalAlignment localAlignment = new LocalAlignment("google location data/test.csv");
		//localAlignment.findPrediction(5, 10);
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for(int i = 1; i < 50; i++) {
			list.add(localAlignment.testSequences(i, 1, 0));		
			
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
		
		System.out.println("Levenstein distances: " + list);
	}
	
	@Test
	public void testLocalAlignment() {
		SmithWaterman algorithm = new SmithWaterman();
		
		String testSequence = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACCCCCCCCCCCCCCCCBBBCCCCCCCCCCCCCCCCCCCCCCCCCCCCBBBBDDDDBBBBBBFFFFAAAAAAAAAAAAAAAAAAAACCCCCCCCCCCCCCCCCCCCCCCCCCCBBBBBBBBBBBBBBBBBBBBBBBBBBAAAAAAAAAAAAAAAABBBBBBBBCCCCCCBCCCCCCCCBBBCCCCCCCCCCBBCCCBCCCCCCBBBBBBBBBBBBBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBCCCBCCC";
		String searchSequence = "BCBBCBCCCBCCCCCCBCBCC";
		
		algorithm.initialize(testSequence, searchSequence);
		algorithm.fillInAll();
		
		String[] traceback = algorithm.getTraceback();
		
		System.out.println("Traceback:");
		
		for(String t : traceback) {
			System.out.println(t);			
		}
		
		Node highScoreNode = algorithm.getHighScoreNode();
		
		algorithm.predict(highScoreNode.getCol(), 30);
		
	}
}
