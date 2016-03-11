/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package r;

import org.rosuda.JRI.Rengine;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 * @see http://www.codophile.com/how-to-integrate-r-with-java-using-rjava/
 * 
 * Step-1 (Installing rJava package)
 * 
 * Open you R workbench and type following command in R console install.packages('rJava')
 * 
 * The console will display a small window with CRAN mirror header. Choose a mirror from which rJava package will be installed 
 * (for the purpose of this tutorial we have used mirror USA (KS)). After the package is successfully installed you will see a screen something 
 * similar to below figure.
 * 
 * 
 * Step-2 (Configuring PATH variable for rJava)
 * 
 * In order to use rJava package inside Java code we need to add few location to the PATH environment variable. Follow following steps to configure PATH:
 * 
 * Right click on My Computer and select Properties.
 * In the System window that appears, select Advanced system settings.
 * System Properties dialog will appear, on this dialog select Environment Variables.
 * Under System variables section search for variable PATH. Select PATH variable and click on Edit button.
 * In the  Variable value section append ;D:\ProgramFiles\R\R-3.1.3\bin\i386;D:\ProgramFiles\R\R-3.1.3\library\rJava\jri; 
 * (see the ; at the start is necessary to separate current paths from existing paths). In this step we have added two paths, 
 * one corresponding to R dlls and other corresponding to rJava dlls. Note: As I have installed R in D:\ProgramFiles\R all my 
 * paths contain this location for your location the generic path structure should be like ;<YOUR_R_HOME>\bin\i386;<YOUR_R_HOME>\library\rJava\jri\i386;
 * Click Ok, Ok, Ok.
 * 
 * 
 * Step-3 (Creating JAVA Program)
 * 
 * Now that we have configured R, we need to proceed with creating Java code that will be using R functionality.
 * Open eclipse LUNA.
 * Create a Java Project in eclipse named RwithJAVA.
 * In the Package Explorer section right click on the project and select Build Path > Configure Build Path.
 * Select Add External JARs button on the right. Browse to location D:\ProgramFiles\R\R-3.1.3\library\rJava\jri or in your 
 * case <YOUR_R_HOME>\library\rJava\jri and select all 3 JAR files i.e. JRI.jar, JRIEngine.jar, REngine.jar and click Open > Ok.
 */
public class JRItest2 {

	public static void main(String a[]) {

        // Create an R vector in the form of a string.
        String javaVector = "c(1,2,3,4,5)";

        // Start Rengine.
        Rengine engine = new Rengine(new String[] { "--no-save" }, false, null);

        // The vector that was created in JAVA context is stored in 'rVector' which is a variable in R context.
        engine.eval("rVector=" + javaVector);
        
        //Calculate MEAN of vector using R syntax.
        engine.eval("meanVal=mean(rVector)");
        
        //Retrieve MEAN value
        double mean = engine.eval("meanVal").asDouble();
        
        //Print output values
        System.out.println("Mean of given vector is=" + mean);
    }
}
