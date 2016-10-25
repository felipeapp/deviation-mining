/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class CalendarUtil {

	/**
	 * 
	 * This method set the time of date
	 *
	 * @param data ??/??/???? xx:xx:xx:xxx
	 * @param hora HH
	 * @param minuto mm
	 * @param segundos ss
	 * @param milisegundos yyy
	 * 
	 * @return ??/??/???? HH:mm:ss:yyy
	 */
	public static Date configDataTime(Date data, int hora, int minuto, int segundos, int milisegundos){
		
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		c.set(Calendar.HOUR_OF_DAY, hora);
		c.set(Calendar.MINUTE, minuto);
		c.set(Calendar.SECOND, segundos);
		c.set(Calendar.MILLISECOND, milisegundos);
		
		return c.getTime();
	}
	
}
