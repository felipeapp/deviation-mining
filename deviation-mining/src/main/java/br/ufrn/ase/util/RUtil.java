/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.util.List;


/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class RUtil {
	
	/**
	 * Receive a list of values and return them in the R vector format c(1,2,3,4,5) 
	 * @param values
	 * @return
	 */
	public static String formatRVectorLabels(List<String> labels){
		final int MAX_LABEL_SIZE = 100;
		
		StringBuilder buffer = new StringBuilder();
		boolean first = true;
		for (String label : labels) {
			if(label.length() > MAX_LABEL_SIZE)
				label = label.substring(label.length()-MAX_LABEL_SIZE, label.length()); // limit the size of information to put in graphic
			if(first)
				buffer.append("'"+label+"'");
			else
				buffer.append(",'"+label+"'");
			first = false;
		}
		
		return "c("+buffer.toString()+")";
	}

	/**
	 * @param values
	 * @return
	 */
	public static String formatRVector(List<Double> values) {
		
		StringBuilder buffer = new StringBuilder();
		boolean first = true;
		for (Double value : values) {
			if(first)
				buffer.append(value);
			else
				buffer.append(","+value);
			first = false;
		}
		
		return "c("+buffer.toString()+")";
		
	}

}
