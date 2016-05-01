/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.domain.degradation;

import java.time.LocalDateTime;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 */
public class Interval {
	
	private LocalDateTime initialTime;
	private LocalDateTime finalTime;
	
	
	/**
	 * @param initialTime
	 * @param finalTime
	 */
	public Interval(LocalDateTime initialTime, LocalDateTime finalTime) {
		if(initialTime == null || finalTime == null)
			throw new IllegalArgumentException();
		this.initialTime = initialTime;
		this.finalTime = finalTime;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((finalTime == null) ? 0 : finalTime.hashCode());
		result = prime * result + ((initialTime == null) ? 0 : initialTime.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Interval other = (Interval) obj;
		if (finalTime == null) {
			if (other.finalTime != null)
				return false;
		} else if (!finalTime.equals(other.finalTime))
			return false;
		if (initialTime == null) {
			if (other.initialTime != null)
				return false;
		} else if (!initialTime.equals(other.initialTime))
			return false;
		return true;
	}



	public LocalDateTime getInitialTime() {
		return initialTime;
	}
	public LocalDateTime getFinalTime() {
		return finalTime;
	}



	@Override
	public String toString() {
		return "Interval [initialTime=" + initialTime + ", finalTime=" + finalTime + "]";
	}
	

}
