package br.ufrn.ase.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

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

	public static final String TEMP_FILE_PATH = "temp.properties";;


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
	
	/**
	 * This method return the qtd of bytes of a map
	 * @param map
	 */
	public static int getMapSize(@SuppressWarnings("rawtypes") Map map) {
	    try{
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(baos);
	        oos.writeObject(map);
	        oos.close();
	        return baos.size();
	    }catch(IOException e){
	        e.printStackTrace();
	        return 0;
	    }
	}
	
	
	/**
	 * This method get the first results common in the two maps.
	 * 
	 * ie this method make the intersection between the two maps returning the first elements of the MAP1 
	 * which are connected between the first elements of MAP2 up to the limit of the amount of desired elements.
	 * 
	 * @param highestAverage
	 * @param mostAccesss
	 * @return
	 */
	public static Map<String, Double> crossMaps(Map<String, Double> firstMap, Map<String, Double> secondMap, final int QTD) {

		Map<String, Double> _return = new HashMap<>();
		
		// First we take the 10 times first results of the second map
		Map<String, Double> secondMapTop10Times = MapUtil.cutOff(secondMap, QTD*10);
		
		List<String> firstMapKeys = new ArrayList<String>(firstMap.keySet());
		
		for (int i = 0; i < firstMapKeys.size() ; i++) {
			String key = firstMapKeys.get(i);
			
			if(secondMapTop10Times.containsKey(key)){ // this key is in the both maps, in the fisrt and in the 10 times first elements of the second
				_return.put(key, firstMap.get(key));
			}
		}
		
		return MapUtil.cutOff(_return, QTD);
	}

	/**
	 * Store the entire map in a properties file.
	 * @param retorno
	 */
	public static void storeMapInFile(Map<String, List<Double>> retorno) {
		for (String key : retorno.keySet()) {
			appendPropertie(key, retorno.get(key));
		}
      
	}
	
	/** write the values of the key in a propertie file */
	public static void appendPropertie(String key, List<Double> values) {
		
		List<Double> newValues = new ArrayList<>();
		newValues.addAll(readPropertiesValues(key));
		newValues.addAll(values);
		
		try {
			PropertiesConfiguration  props = new PropertiesConfiguration(new File(TEMP_FILE_PATH));
			props.setProperty(key, newValues.toString().replace("[", "").replace("]", "") );
			props.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
      
	}
	
	/**
	 * Read all key form a propertie file
	 * @return
	 */
	public static List<String> readAllPropertiesKeys() {
		
		List<String> keys = new ArrayList<>();
		
		try {
				Properties p = new Properties();
			    p.load(new FileInputStream(TEMP_FILE_PATH));
			    Enumeration<?> e = p.propertyNames();
			
			    for (; e.hasMoreElements();) {
			    	keys.add( (String) e.nextElement() );
			
			    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return keys;
	}
	
	
	/** read the values of the key from a propertie file */
	public static List<Double> readPropertiesValues(String key) {
		
		List<Double> list = new ArrayList<>();
		File f = new File(TEMP_FILE_PATH);
		
		try (  InputStream is = new FileInputStream( f ); ) {  // The try-with-resources Statement
			
			Properties props = new Properties();
		
			
			if ( is != null ) {
	            props.load( is );
	            String values = props.getProperty(key);
	            
	            if(values != null && values.length() > 0 ){
	            	
		            String[] valuesArray = values.split(",");
		           
		            for (String value : valuesArray) {
						list.add(Double.valueOf(value));
					}
	            }
	        }
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		return list;
      
	}
	
	
	
	
}
