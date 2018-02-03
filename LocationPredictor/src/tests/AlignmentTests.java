package tests;

import org.junit.jupiter.api.Test;

import algorithms.LocalAlignment;
import algorithms.Node;
import algorithms.SmithWaterman;

public class AlignmentTests {

	@Test
	public void testLocalAlignmentWithFirstDataSet() {
		new LocalAlignment("google location data/test.csv");
	}
	
	@Test
	public void testLocalAlignmentWithSecondDataSet() {
		new LocalAlignment("google location data/test2.csv");
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
