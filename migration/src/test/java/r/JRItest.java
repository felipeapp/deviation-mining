/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package r;

import org.junit.Assert;
import org.junit.Test;
import org.rosuda.JRI.Rengine;

/**
 * @author jadson - jadsonjs@gmail.com
 *
 * @see http://www.codophile.com/how-to-integrate-r-with-java-using-rjava/
 * @see http://binfalse.de/2011/02/20/talking-r-through-java/
 * 
 * ===== Step-1 (Download and Install R workbench)  ===== 
 * 
 * https://cran.r-project.org/bin/windows/base/
 * https://cran.r-project.org/bin/macosx/
 * 
 * 
 * 
 * ===== Step-2 (Installing rJava package) ===== 
 * 
 * Open you R workbench and type following command in R console: "install.packages('rJava')"
 * 
 * The console will display a small window with CRAN mirror header. Choose a mirror from which rJava package will be installed 
 * (for the purpose of this tutorial we have used mirror USA (KS)). After the package is successfully installed you will see a screen something 
 * similar to below figure.
 * 
 * 
 * 
 * =====  Step-3 (Configuring PATH variable for rJava) ===== 
 * 
 *  Add JRI.jar, JRIEngine.jar, REngine.jar to the classe path (GRADLE)
 * 
 * In order to use rJava package inside Java code we need to add few location to the PATH environment variable. Follow following steps to configure PATH:
 * 
 * On Windows:
 * 
 * Right click on My Computer and select Properties.
 * In the System window that appears, select Advanced system settings.
 * System Properties dialog will appear, on this dialog select Environment Variables.
 * Under System variables section search for variable PATH. Select PATH variable and click on Edit button.
 * In the  Variable value section append ;D:\ProgramFiles\R\R-3.1.3\bin\i386;D:\ProgramFiles\R\R-3.1.3\library\rJava\jri; 
 * (see the ; at the start is necessary to separate current paths from existing paths). In this step we have added two paths, 
 * one corresponding to R dlls and other corresponding to rJava dlls. Note: As I have installed R in D:\ProgramFiles\R all my 
 * paths contain this location for your location the generic path structure should be like ;<YOUR_R_HOME>\bin\i386;<YOUR_R_HOME>\library\rJava\jri\i386;
 * 
 * 
 * On mac os:
 * 
 * vim .bash_profile
 * R_HOME=/Library/Frameworks/R.framework/Resources
 * LD_LIBRARY_PATH=/Library/Frameworks/R.framework/Resources/library/rJava/jri
 * export :$R_HOME:$LD_LIBRARY_PATH
 * 
 *
 * 
 *  Run > Run Configurations > Environment. Create a new variable with the name R_HOME
 *  /Library/Frameworks/R.framework/Resources
 *
 *
 *  Run > Run Configurations > JVM Arguments. Add
 * -Djava.library.path=.:/Library/Frameworks/R.framework/Resources/library/rJava/jri
 * 
 */
public class JRItest {

	@Test
	public void testConnection() {

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
        Assert.assertEquals(3.0, mean, 0.0);
    }
}
