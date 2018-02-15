package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CSVReader {
	
	public static final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	ArrayList<String> longitudeSequence = new ArrayList<String>();
	ArrayList<String> latitudeSequence = new ArrayList<String>();
	StringBuilder locationSequence = new StringBuilder();
	
	HashMap<String, Character> wordMap = new HashMap<String, Character>();
	
	public void readCSV(String localPath) {
		
		//read csv and sort by timestamp (titles are removed because of lambda commands)
		try {
			Map<Long, List<String>> collect = Files.lines(Paths.get(localPath))
				    .collect(
				    		Collectors.groupingBy(
				    				l -> Long.valueOf(l.split(";", 6)[0]), TreeMap::new, Collectors.toList()));
			
			for (Entry<Long, List<String>> entry : collect.entrySet()) {
				String dataLine = entry.getValue().get(0);
				String[] split = dataLine.split(";");
				
				longitudeSequence.add(split[2]);
				latitudeSequence.add(split[3]);
				
				String wholeWord = split[5];
				
				if(!wordMap.containsKey(wholeWord)) {					
					wordMap.put(wholeWord, nextLetter());
				} else {
					
				}
				
				char letter = wordMap.get(wholeWord);
//				locationSequence.append(letter);	
				if(locationSequence.length() == 0 || locationSequence.charAt(locationSequence.length() - 1) != letter) {
					locationSequence.append(letter);					
				}
			}
			
			System.out.println("This is the following word mapping: ");
			System.out.println();
			
			for(Entry<String, Character> entry : wordMap.entrySet()) {
				System.out.println(entry.getKey() + " --> " + entry.getValue());
			}
			
			System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	int letterCounter = 0;

	private Character nextLetter() {
		char letter = alphabet[letterCounter];
		letterCounter++;
		
		if(alphabet.length == letterCounter) {
			throw new IllegalArgumentException("Out of letters.");
		}
		
		return letter;
	}

	public ArrayList<String> getLongitudeSequence() {
		return longitudeSequence;
	}

	public ArrayList<String> getLatitudeSequence() {
		return latitudeSequence;
	}

	public StringBuilder getLocationSequence() {
		return locationSequence;
	}
	
	
}
