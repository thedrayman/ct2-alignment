package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		try {
			//read csv and sort by timestamp (titles are removed because of lambda commands)
			Map<Long, List<String>> collect = Files.lines(Paths.get("google location data/test.csv"))
				    .collect(
				    		Collectors.groupingBy(
				    				l -> Long.valueOf(l.split(";", 6)[0]), TreeMap::new, Collectors.toList()));

			//then split each sorted line into lists
			ArrayList<String> longitudeSequence = new ArrayList<String>();
			ArrayList<String> latitudeSequence = new ArrayList<String>();
			ArrayList<String> locationSequence = new ArrayList<String>();
			
			for (Entry<Long, List<String>> entry : collect.entrySet()) {
				String dataLine = entry.getValue().get(0);
				String[] split = dataLine.split(";");
				longitudeSequence.add(split[2]);
				latitudeSequence.add(split[3]);
				locationSequence.add(split[5]);
			}
			
			System.out.println(locationSequence);
			
			//TODO now use alignment
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
