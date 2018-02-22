package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CSVReader {
	
	public static final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	ArrayList<String> longitudeSequence = new ArrayList<String>();
	ArrayList<String> latitudeSequence = new ArrayList<String>();
	ArrayList<LocalDateTime> timestampSequence = new ArrayList<LocalDateTime>();
	StringBuilder locationSequence = new StringBuilder();
	
	HashMap<String, Character> wordMap = new HashMap<String, Character>();
	
	public void readCSV(String localPath) {
		
		//read csv and sort by timestamp (titles are removed because of lambda commands)
		try {
			Map<Long, List<String>> collect = Files.lines(Paths.get(localPath))
					.skip(1)
				    .collect(
				    		Collectors.groupingBy(
				    				l -> Long.valueOf(l.split(";", 6)[0]), TreeMap::new, Collectors.toList()));

			int index = 0;
			for (Entry<Long, List<String>> entry : collect.entrySet()) {
				String dataLine = entry.getValue().get(0);
				String[] split = dataLine.split(";");
				
				long milliSecondTimestamp = Long.parseLong(split[0]);
				LocalDateTime timestamp =
				        LocalDateTime.ofInstant(Instant.ofEpochSecond(milliSecondTimestamp), 
				                                TimeZone.getDefault().toZoneId()); 
				
				timestampSequence.add(timestamp);
				longitudeSequence.add(split[2]);
				latitudeSequence.add(split[3]);
				
				String wholeWord = split[5];
				
				if(!wordMap.containsKey(wholeWord)) {					
					wordMap.put(wholeWord, nextLetter());
				}
				
				char letter = wordMap.get(wholeWord);
				
//				locationSequence.append(letter);
				
				long  minutes = 0;
				
				if (index != 0)
				{
					minutes = ChronoUnit.MINUTES.between(timestampSequence.get(index -1), timestampSequence.get(index));
				}
				
				if(locationSequence.length() == 0 || locationSequence.charAt(locationSequence.length() - 1) != letter || minutes >= 30) {
					locationSequence.append(letter);					
				}

				index++;
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
