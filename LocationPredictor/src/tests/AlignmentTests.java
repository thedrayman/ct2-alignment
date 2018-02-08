package tests;

import org.junit.jupiter.api.Test;

import algorithms.LocalAlignment;
import algorithms.Node;
import algorithms.SmithWaterman;

public class AlignmentTests {

	@Test
	public void testLocalAlignmentWithFirstDataSet() {
		long start = System.currentTimeMillis();
		new LocalAlignment("google location data/test.csv");
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}
	
	@Test
	public void testLocalAlignmentWithSecondDataSet() {
		long start = System.currentTimeMillis();
		new LocalAlignment("google location data/test2.csv");
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
	}
	
	@Test
	public void testLocalAlignmentWithThirdDataSet() {
		long start = System.currentTimeMillis();
		new LocalAlignment("google location data/test3.csv");
		long end = System.currentTimeMillis();
		
		System.out.println("Time needed for algorithm: " + (end - start) + "ms");
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
