package algorithms;

import reader.CSVReader;

public class LocalAlignment {

	public static final int HISTORY_START = 0;
	public static final int HISTORY_END = 800;
	
	public static final int TEST_START = HISTORY_END;
	public static int TEST_END; 
	
	public static final int SEARCH_PATTERN_START = HISTORY_END - 11;
	public static final int SEARCH_PATTERN_END = HISTORY_END;
	
	public static final int PREDICTED_LENGTH = 50;
	
	private String path;

	public LocalAlignment(String path) {
		
		if(path == null || "".equals(path)) {
			throw new IllegalArgumentException("Path is not set!");
		}
		
		this.path = path;
		
		String historySequence;
		String searchSequence;
		String testSequence;
		
		String locationSequence = readFile(this.path);
		
		System.out.println("File path: " + this.path);
		
		if(HISTORY_START < 0 || HISTORY_END < 0 || SEARCH_PATTERN_START < 0 || HISTORY_END - HISTORY_START < 0) {
			throw new IllegalArgumentException("Wrong bounds error occured! Please check the constants.");
		}
		
		historySequence = locationSequence.substring(HISTORY_START, HISTORY_END);
		searchSequence = locationSequence.substring(SEARCH_PATTERN_START, SEARCH_PATTERN_END);
		
		TEST_END = locationSequence.length();
		System.out.println("Length of Data sequence: " + TEST_END);
		testSequence = locationSequence.substring(TEST_START, TEST_END);
		
		System.out.println("History sequence: " + historySequence);
		System.out.println("Search sequence: " + searchSequence);
		System.out.println("In Test sequence: " + testSequence);

		SmithWaterman algorithm = new SmithWaterman();
		
		algorithm.initialize(testSequence, searchSequence);
		algorithm.fillInAll();
		
		//printScoreTable(testSequence, searchSequence, algorithm.getScoreTable());
		
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
		
		algorithm.predict(highScoreNode.getCol(), PREDICTED_LENGTH);
	}

	private String readFile(String path) {
		CSVReader reader = new CSVReader();
		reader.readCSV(path);
		return reader.getLocationSequence().toString();
	}
	
	public void printScoreTable(String testSequence, String searchSequence, Node[][] scoreTable) {
		System.out.println("Score Table: ");
		
		System.out.print("  ");
		
		for(int i = 0; i < testSequence.length(); i++) {
			System.out.print(testSequence.charAt(i) + " ");
		}
		
		System.out.println();
		
		for(int i = 0; i < scoreTable.length; i++) {
			
			System.out.print(searchSequence.charAt(i) + " ");
			
			for(int j = 0; j < scoreTable[i].length; j++) {				
				System.out.print(scoreTable[i][j].getScore() + " ");
			}
			System.out.println();
		}
	}

}
