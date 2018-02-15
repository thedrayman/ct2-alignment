package algorithms;

import java.util.logging.Level;

public class SmithWaterman {
	private String testSequence;
	private String searchSequence;
	
	private int match = 2;
	private int mismatch = -1;
	private int gap = -1;
	
	private Node highScoreNode;
	
	private Node[][] scoreTable;
	
	public void initialize(String testSequence, String searchSequence) {
		this.testSequence = testSequence;
		this.searchSequence = searchSequence;
		
		scoreTable = new Node[searchSequence.length() + 1][testSequence.length() + 1];
		
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {
				scoreTable[i][j] = new Node(i, j);
			}
		}
		
		//initialize scores
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {
				scoreTable[i][j].setScore(0);
			}
		}
		
		highScoreNode = new Node(0, 0);
		highScoreNode.setScore(0);
		
		//initialize pointers
		for (int i = 0; i < scoreTable.length; i++) {
			for (int j = 0; j < scoreTable[i].length; j++) {
				scoreTable[i][j].setPreviousNode(null);
			}
		}
	}
	
	public void fillInAll() {
		for (int row = 1; row < scoreTable.length; row++) {
			for (int col = 1; col < scoreTable[row].length; col++) {
				Node currentNode = scoreTable[row][col];
				Node nodeAbove = scoreTable[row - 1][col];
				Node nodeToLeft = scoreTable[row][col - 1];
				Node nodeAboveLeft = scoreTable[row - 1][col - 1];
				fillInNodeWithScore(currentNode, nodeAbove, nodeToLeft, nodeAboveLeft);
			}
		}
	}
	
	private void fillInNodeWithScore(Node currentNode, Node nodeAbove, Node nodeToLeft, Node nodeAboveLeft) {
	   int rowSpaceScore = nodeAbove.getScore() + gap;
	   int colSpaceScore = nodeToLeft.getScore() + gap;
	   int matchOrMismatchScore = nodeAboveLeft.getScore();
	   
	   //apply scores for match and mismatch
	   if (searchSequence.charAt(currentNode.getRow() - 1) == testSequence.charAt(currentNode.getCol() - 1)) {
	      matchOrMismatchScore += match;
	   } else {
	      matchOrMismatchScore += mismatch;
	   }
	   
	   //apply rules
	   if (rowSpaceScore >= colSpaceScore) {
	      if (matchOrMismatchScore >= rowSpaceScore) {
	         if (matchOrMismatchScore > 0) {
	            currentNode.setScore(matchOrMismatchScore);
	            currentNode.setPreviousNode(nodeAboveLeft);
	         }
	      } else {
	         if (rowSpaceScore > 0) {
	            currentNode.setScore(rowSpaceScore);
	            currentNode.setPreviousNode(nodeAbove);
	         }
	      }
	   } else {
	      if (matchOrMismatchScore >= colSpaceScore) {
	         if (matchOrMismatchScore > 0) {
	            currentNode.setScore(matchOrMismatchScore);
	            currentNode.setPreviousNode(nodeAboveLeft);
	         }
	      } else {
	         if (colSpaceScore > 0) {
	            currentNode.setScore(colSpaceScore);
	            currentNode.setPreviousNode(nodeToLeft);
	         }
	      }
	   }
	   
	   //save new high score
	   if (currentNode.getScore() > highScoreNode.getScore()) {
	      highScoreNode = currentNode;
	   }
	}
	
	public String[] getTraceback() {
	   StringBuffer align1Buf = new StringBuffer();
	   StringBuffer align2Buf = new StringBuffer();
	   Node currentNode = highScoreNode;
	   while (currentNode.getScore() != 0) {
	      if (currentNode.getRow() - currentNode.getPreviousNode().getRow() == 1) {
	         align2Buf.insert(0, searchSequence.charAt(currentNode.getRow() - 1));
	      } else {
	         align2Buf.insert(0, '-');
	      }
	      if (currentNode.getCol() - currentNode.getPreviousNode().getCol() == 1) {
	         align1Buf.insert(0, testSequence.charAt(currentNode.getCol() - 1));
	      } else {
	         align1Buf.insert(0, '-');
	      }
	      
	      currentNode = currentNode.getPreviousNode();
	   }
	 
	   String[] alignments = new String[] { "In test sequence: " + align1Buf.toString(),
	         "In search sequence: " + align2Buf.toString() };
	 
	   return alignments;
	}
	
	public String predict(int endIndex, int length) {
		String predictedSequence = "";
		
		if(endIndex + length > testSequence.length()) {
			predictedSequence = testSequence.substring(endIndex, testSequence.length());
		} else {
			predictedSequence = testSequence.substring(endIndex, endIndex + length);			
		}
		
		System.out.println("Predicted sequence of max length " + length + ": " + predictedSequence);
		
		return predictedSequence;
		
	}
	
	public int predictWithExpected(int endIndex, int length) {
		String predictedSequence = predict(endIndex, length);
		System.out.println("Expected sequence of max length " + length + ": " + testSequence.substring(0, length));
		
		int similarity = computeLevenshteinDistance(predictedSequence, testSequence.substring(0, length));
		
		System.out.println("Similarity: " + similarity);
		
		return similarity;
	}
	
	public int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
                                                                                 
        for (int i = 0; i <= lhs.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= rhs.length(); j++)                                 
            distance[0][j] = j;                                                  
                                                                                 
        for (int i = 1; i <= lhs.length(); i++)                                 
            for (int j = 1; j <= rhs.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
                                                                                 
        return distance[lhs.length()][rhs.length()];                           
    }   
	
	private int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }

	public Node[][] getScoreTable() {
		return scoreTable;
	}

	public void setScoreTable(Node[][] scoreTable) {
		this.scoreTable = scoreTable;
	}

	public Node getHighScoreNode() {
		return highScoreNode;
	}

	public void setHighScoreNode(Node highScoreNode) {
		this.highScoreNode = highScoreNode;
	}

	public String getTestSequence() {
		return testSequence;
	}

	public void setTestSequence(String testSequence) {
		this.testSequence = testSequence;
	}

	public String getSearchSequence() {
		return searchSequence;
	}

	public void setSearchSequence(String searchSequence) {
		this.searchSequence = searchSequence;
	}
}
