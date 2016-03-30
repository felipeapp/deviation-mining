/*
 * Copyright (C) Automation Software Engineering Group
 *
 * This software is distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND
 */
package br.ufrn.ase.r;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rosuda.JRI.Rengine;

/**
 * Draw a boxplot graphics
 * 
 * @author jadson - jadsonjs@gmail.com
 * 
 * Run > Run Configurations > Environment. Create a new variable with the name R_HOME
 *  /Library/Frameworks/R.framework/Resources
 *
 *
 *  Run > Run Configurations > JVM Arguments. Add
 * -Djava.library.path=.:/Library/Frameworks/R.framework/Resources/library/rJava/jri
 *
 */
public class BoxPlotGraphic {
	
	
	public void drawBoxPlot() throws IOException {

		String graphName = "graph.png";
		Rengine re = new Rengine (new String [] {"--vanilla"}, false, null);
		re.eval("x <- c(0.02, 0.02, 0.06, 0.06, 0.11, 0.11, 0.22, 0.22, 0.56, 0.56,1.10, 1.10)");
		re.eval("y <- c(76, 47, 97, 107, 123, 139, 159, 152, 191, 201, 207, 200)");
		
		re.eval("png(file=\""+graphName+"\",width=1600,height=1600,res=400)");
		re.eval("plot(x,y)");
		re.eval("dev.off()");
		
		re.end();
		
		//get the image and create a new imagepanel
		File file = new File(graphName);
		Image image = ImageIO.read(file);
		LineImagePanel myPanel = new LineImagePanel(image);
		//Create a new frame and add the imagepanel
		JFrame aFrame = new JFrame();
		aFrame.setTitle("LineChart");
		aFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); aFrame.getContentPane().add(myPanel, BorderLayout.CENTER);
		aFrame.pack();
		aFrame.setVisible(true);
		aFrame.setSize(new Dimension(600, 600));
		
    }

	public static void main(String[] args) throws IOException {
		new BoxPlotGraphic().drawBoxPlot();
	}
	
	/* A panel to draw the picture */
	static class LineImagePanel extends JPanel {
        Image image = null;
        public LineImagePanel(Image image) {
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics g) {
        	super.paintComponent(g); //there is a picture: draw it if (image != null)
    		int height = this.getSize().height;
    		int width = this.getSize().width; 
    		g.drawImage(image, 0, 0, width, height, this);
        }
	}

}
