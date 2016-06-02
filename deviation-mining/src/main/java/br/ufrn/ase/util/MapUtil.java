package br.ufrn.ase.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map util class
 * 
 * @author felipe
 * @author jadson - jadsonjs@gmail.com
 * 
 * @see How to sort a Map in Java:
 *      http://www.mkyong.com/java/how-to-sort-a-map-in-java/
 */
public class MapUtil {

	/**
	 * The overall idea is, converts the Map into a List, sorts the List by
	 * Comparator and put the sorted list back to a Map.
	 * 
	 * Map ---> List ---> Sort --> SortedList ---> Map
	 * 
	 * @param unsortMap
	 * @return sortedMap
	 */
	public static Map<String, Double> sortByValue(Map<String, Double> unsortMap) {
		List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

		// Sorting the list based on values
		Collections.sort(list, new Comparator<Entry<String, Double>>() {
			public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
				// Sorting descending order
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		// Maintaining insertion order with the help of LinkedList
		Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
		for (Entry<String, Double> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	/**
	 * Get the top RANGE elements for a map
	 * 
	 * @param mapRange_3_21
	 * @param rANGE
	 * @return
	 */
	public static Map<String, Double> cutOff(Map<String, Double> map, final int RANGE) {
		
		map = MapUtil.sortByValue(map);
		
		Map<String, Double> mapTemp = new HashMap<>();
		
		List<String> keys = new ArrayList<String>(map.keySet());
		List<Double> values = new ArrayList<Double>(map.values());
		
		for(int i = 0 ;  i < (map.size() > RANGE ? RANGE : map.size() );  i++){
			mapTemp.put( keys.get(i), values.get(i) );
	    }
		
		return MapUtil.sortByValue(mapTemp);
	}
	
	/**
	 * This method print the qtd of bytes of a map
	 * @param map
	 */
	public static void printMapSize(@SuppressWarnings("rawtypes") Map map) {
	    try{
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(map);
	        oos.close();
	        System.out.println(">>>>> Map Qtd of elements: " + map.size());
	        System.out.println(">>>>> Map Size in Bytes: " + baos.size());
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}

}
