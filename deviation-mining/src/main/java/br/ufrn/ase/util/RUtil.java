/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

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
	public static String formatRVector(List<String> values){
		StringBuilder buffer = new StringBuilder();
		boolean first = true;
		for (String value : values) {
			if(first)
				buffer.append(value);
			else
				buffer.append(","+value);
			first = false;
		}
		
		return "c("+buffer.toString()+")";
	}

	/**
	 * @param values
	 * @return
	 */
	public static String formatRVectorDoubleList(List<Double> values) {
		List<String> strings = Lists.transform(values, new Function<Double, String>() {
	        @Override
	        public String apply(Double from) {
	            return from.toString();
	        }
	    });
		return formatRVector(strings);
	}

}
