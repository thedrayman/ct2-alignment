package algorithms;

import reader.CSVReader;

public class LocalAlignment {
	
	private String path;
	private String locationSequence;

	public LocalAlignment(String path) {
		
		if(path == null || "".equals(path)) {
			throw new IllegalArgumentException("Path is not set!");
		}
		
		this.path = path;

		locationSequence = readFile(this.path);
		
		System.out.println("File path: " + this.path);
		System.out.println();
	}
	
	public void findPrediction(int searchLength, int predictedLength) {
		String historySequence;
		String searchSequence;
		
		System.out.println("==========SEQUENCE PREDICTION==========");
		
		int historyStart = 0;
		int historyEnd = locationSequence.length();
		int searchPatternStart = historyEnd - searchLength;
		int searchPatternEnd = historyEnd;
		
		historySequence = locationSequence.substring(historyStart, historyEnd);
		searchSequence = locationSequence.substring(searchPatternStart, searchPatternEnd);
		
		System.out.println("Length of Data sequence: " + historyEnd);
		
		System.out.println("Search sequence: " + searchSequence);
		System.out.println("In History sequence: " + historySequence);

		SmithWaterman algorithm = new SmithWaterman();
		
		algorithm.initialize(historySequence, searchSequence);
		algorithm.fillInAll();
		
		printScoreTable(historySequence, searchSequence, algorithm.getScoreTable());
		
		String[] traceback = algorithm.getTraceback();
		
		System.out.println();
		
		System.out.println("Traceback:");
		
		for(String t : traceback) {
			System.out.println(t);			
		}
		
		System.out.println();
		
		Node highScoreNode = algorithm.getHighScoreNode();
		
		System.out.println("Highest Score: " + highScoreNode.getScore() + " in row " + highScoreNode.getRow() + " and col " + highScoreNode.getCol());
		
		System.out.println();
		
		algorithm.predict(highScoreNode.getCol(), predictedLength);
		
		System.out.println();
	}
	
	public int testSequences(int searchLength, int predictedLength, int shift) {
		String historySequence;
		String searchSequence;
		String testSequence;
		
		System.out.println("==========SEQUENCE TESTING==========");
		
		int historyStart = 0;
		int historyEnd = locationSequence.length()/2 + shift;
		int searchPatternStart = historyEnd - searchLength;
		int searchPatternEnd = historyEnd;
		
		int testStart = historyEnd;
		int testEnd = locationSequence.length();
		
		historySequence = locationSequence.substring(historyStart, historyEnd);
		searchSequence = locationSequence.substring(searchPatternStart, searchPatternEnd);
		
		System.out.println("Length of Data sequence: " + testEnd);
		testSequence = locationSequence.substring(testStart, testEnd);
		
		System.out.println("History sequence: " + historySequence);
		System.out.println("Search sequence: " + searchSequence);
		System.out.println("In Test sequence: " + testSequence);
		
		SmithWaterman algorithm = new SmithWaterman();
		
		algorithm.initialize(testSequence, searchSequence);
		algorithm.fillInAll();
		
		printScoreTable(testSequence, searchSequence, algorithm.getScoreTable());
		
		String[] traceback = algorithm.getTraceback();
		
		System.out.println();
		
		System.out.println("Traceback:");
		
		for(String t : traceback) {
			System.out.println(t);			
		}
		
		System.out.println();
		
		Node highScoreNode = algorithm.getHighScoreNode();
		
		System.out.println("Highest Score: " + highScoreNode.getScore() + " in row " + highScoreNode.getRow() + " and col " + highScoreNode.getCol());
		
		System.out.println();
		
		return algorithm.predictWithExpected(highScoreNode.getCol(), predictedLength);
	}

	private String readFile(String path) {
		CSVReader reader = new CSVReader();
		reader.readCSV(path);
		return reader.getLocationSequence().toString();
	}
	
	public void printScoreTable(String testSequence, String searchSequence, Node[][] scoreTable) {
		System.out.println("Score Table: ");
		
		System.out.print("    ");
		
		for(int i = 0; i < testSequence.length(); i++) {
			System.out.print(testSequence.charAt(i) + " ");				
		}
		
		System.out.println();
		
		for(int i = 0; i < scoreTable.length; i++) {
			
			if(i != 0) {
				System.out.print(searchSequence.charAt(i - 1) + " ");				
			} else {
				System.out.print("  ");
			}
			
			for(int j = 0; j < scoreTable[i].length; j++) {			
				System.out.print(scoreTable[i][j].getScore() + " ");					
			}
			System.out.println();
		}
	}

}
