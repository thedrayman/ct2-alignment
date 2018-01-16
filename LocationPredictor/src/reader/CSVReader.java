package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CSVReader {
	ArrayList<String> longitudeSequence = new ArrayList<String>();
	ArrayList<String> latitudeSequence = new ArrayList<String>();
	ArrayList<String> locationSequence = new ArrayList<String>();
	
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
				locationSequence.add(split[5]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<String> getLongitudeSequence() {
		return longitudeSequence;
	}

	public ArrayList<String> getLatitudeSequence() {
		return latitudeSequence;
	}

	public ArrayList<String> getLocationSequence() {
		return locationSequence;
	}
	
	
}
