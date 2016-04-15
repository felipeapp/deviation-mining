package br.ufrn.ase.util;

public abstract class StringUtil {

	public static String getFirstLine(String text) {
		return text == null ? null : text.substring(0, text.indexOf('\n'));
	}

}
