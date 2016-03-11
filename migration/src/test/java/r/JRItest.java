/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package r;

import org.rosuda.JRI.Rengine;

/**
 * This class test if Java/R integration are correctly. Afterwards you’ll see a random 
 * number from an uniform distribution. 
 * 
 * @author jadson - jadsonjs@gmail.com
 *
 * @see http://binfalse.de/2011/02/20/talking-r-through-java/
 * 
 * To run sucessifuly this class we need to install R on our machine and make some configurations. 
 * 
 *  R is ‘GNU S’, a freely available language and environment for statistical computing and graphics which provides a wide variety of statistical and graphical 
 *  techniques: linear and nonlinear modelling, statistical tests, time series analysis, classification, clustering, etc. Please consult the R project homepage for further information.
 *  
 *  CRAN is a network of ftp and web servers around the world that store identical, up-to-date, versions of code and documentation for R. Please use the CRAN mirror nearest to you to minimize network load. 
 * 
 * The follow steps need to be made:
 * 
 * === Installation ===
 * 
 * Install R on you operation system:  
 * 			Windows: https://cran.r-project.org/bin/windows/base/
 *          MacOS: https://cran.r-project.org/bin/macosx/
 *          Ubuntu: https://cran.r-project.org/bin/linux/ubuntu/README
 * 
 * JRI is now part of rJava, so if you simply want to use JRI, install rJava binary and your'e all set! 
 * 
 * Open the R terminal.
 *  The best way is to simply install rJava. It is available from CRAN, so you can use "install.packages("rJava")"
 * 
 * 
 * === Shell environment ===
 * 
 * To talk to R through Java you have to specify three more environmental variables. First of all you need to publish you R installation path, my R is 
 * found in /usr/lib64/R : export R_HOME=/usr/lib64/R
 * In windows export C:\Program Files\R\R-3.2.4
 *
 * Second the $CLASSPATH needs to get an update. Precisely you have to add the archives JRIEngine.jar , JRI.jar and REngine.jar . In my case all of 
 * them can be found in /usr/lib/R/site-library/rJava/jri/ , so the $CLASSPATH should be set like that:
 *
 * export CLASSPATH=.:/usr/lib/R/site-library/rJava/jri/
 * If the $CLASSPATH isn’t defined correctly you won’t be able to compile your Java code.  (this part is configured in gradle build file)
 *
 * Last but not least you have to add the native JRI-library to your $LD_LIBRARY_PATH , by default this lib is located in the same directory like the jar’s:
 * export LD_LIBRARY_PATH=/usr/lib/R/site-library/rJava/jri/
 *
 *
 * === Eclipse setup ===
 * 
 * Instead of the $LD_LIBRARY_PATH you can set the java.library.path in Run > Run Configurations > Arguments. 
 * Add -Djava.library.path=.:/usr/lib/R/site-library/rJava/jri/ to the VM arguments (modify the path to match your criteria). The R_HOME can be published in Run > 
 * Run Configurations > Environment. Create a new variable with the name R_HOME and the value /usr/lib64/R (or an equivalent path). That’s it, see the section 
 * above to identify what went wrong if something fails.
 */
public class JRItest {

	
	public static void main(String[] args) {
		// new R-engine
	    Rengine re=new Rengine (new String [] {"--vanilla"}, false, null);
	    if (!re.waitForR())
	    {
	      System.out.println ("Cannot load R");
	      return;
	    }
	    
	    // print a random number from uniform distribution
	    System.out.println (re.eval ("runif(1)").asDouble ());

	    // done...
	    re.end();
	}
}
