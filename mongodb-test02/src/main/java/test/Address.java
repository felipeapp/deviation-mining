package test;

import java.util.List;

public class Address {

	private String street;
	private List<Integer> numbers;

	public Address(String street, List<Integer> numbers) {
		this.street = street;
		this.numbers = numbers;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public List<Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(List<Integer> numbers) {
		this.numbers = numbers;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", numbers=" + numbers + "]";
	}

}
