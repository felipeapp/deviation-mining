package br.ufrn.ase.util;

import java.util.Collections;
import java.util.Comparator;
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
 * @see How to sort a Map in Java:  http://www.mkyong.com/java/how-to-sort-a-map-in-java/
 *
 */
public abstract class MapUtil {

	
	/**
	 * The overall idea is, converts the Map into a List, sorts the List by Comparator and put the sorted list back to a Map.
	 * 
	 * Map ---> List ---> Sort --> SortedList ---> Map
	 * 
	 * @param unsortMap
	 * @return
	 */
	public static Map<String, Double> sortByComparator(Map<String, Double> unsortMap) {

        List<Entry<String, Double>> list = new LinkedList<Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Double>>()
        {
            public int compare(Entry<String, Double> o1, Entry<String, Double> o2){
                // sorting descending order
            	return o2.getValue().compareTo(o1.getValue());                
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Entry<String, Double> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
