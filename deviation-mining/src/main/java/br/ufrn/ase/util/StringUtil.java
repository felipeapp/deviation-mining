package br.ufrn.ase.util;

/**
 * Specific manipulation of strings
 * 
 * @author felipe -
 *
 */
public abstract class StringUtil {

	public static String getFirstLine(String text) {
		return text == null ? null : text.substring(0, text.indexOf('\n'));
	}
	
	/**
	 * Format the name of the scenario if It is too long.
	 * 
	 * @param text   http://localhost:8080/sigaa/graduacao/menu/portal_docente.jsp
	 * @return       portal_docente.jsp
	 */
	public static String formatScenarioName(String scenarioName) {
		if(scenarioName == null) return null;
		return scenarioName.contains("/") ? scenarioName.substring(scenarioName.lastIndexOf("/")+1, scenarioName.length()) : scenarioName ;
	}

	/**
	 * @param systemVersion
	 * @return
	 */
	public static String getSystemName(String systemVersion) {
		return  systemVersion!= null ? systemVersion.substring(0, systemVersion.indexOf('-')).trim().toUpperCase() : "";
	}

}
