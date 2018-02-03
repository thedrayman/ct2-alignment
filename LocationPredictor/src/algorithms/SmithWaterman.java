package algorithms;



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
		
		scoreTable = new Node[searchSequence.length()][testSequence.length()];
		
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
	 
	   String[] alignments = new String[] { "Test sequence: " + align1Buf.toString(),
	         "Search sequence: " + align2Buf.toString() };
	 
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
