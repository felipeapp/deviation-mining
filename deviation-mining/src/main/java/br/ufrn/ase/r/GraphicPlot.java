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
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.rosuda.JRI.Rengine;

import br.ufrn.ase.util.RUtil;

/**
 * This class draw some kinds of graphics using R statistics.
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
public class GraphicPlot {
	
	/** The engine of JRI */
	private Rengine re;
	
	public GraphicPlot(){
		re = new Rengine (new String [] {"--vanilla"}, false, null);
	}
	
	/** Draw a simple dotted graphic, just for tests */
	public void drawPlotTest()  {

		String graphName = "graph.png";
		re.eval("x <- c(0.02, 0.02, 0.06, 0.06, 0.11, 0.11, 0.22, 0.22, 0.56, 0.56,1.10, 1.10)");
		re.eval("y <- c(76, 47, 97, 107, 123, 139, 159, 152, 191, 201, 207, 200)");
		
		re.eval("png(file=\""+graphName+"\",width=1600,height=1600,res=400)");
		re.eval("plot(x,y)");
		re.eval("dev.off()");
		
		re.end();
		
		//get the image and create a new imagepanel
		File file = new File(graphName);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawGraphicWindow(image);
		
    }
	
	/** Draw a ColumnBar graphic */
	public void drawColumnChart(Map<String, Double> mapRange) {

		if(mapRange == null) throw new IllegalArgumentException("Information to plot wasn't passed");
		
		String graphName = "columnchart.png";
		
		String nameVector = RUtil.formatRVector( new ArrayList<String>( mapRange.keySet()));
		String valuesVector = RUtil.formatRVectorDoubleList( new ArrayList<Double>( mapRange.values()));
		
		System.out.println(nameVector);
		System.out.println(valuesVector);
		
		re.eval("x <- "+valuesVector);
		re.eval("name <- "+nameVector);
		
		re.eval("png(file=\""+graphName+"\",width=2600,height=1600,res=200)");
		re.eval("barplot(x, main=\"Teste\", xlab=\"scenario\", ylab=\"time\", names.arg = names ) ");
		re.eval("dev.off()");
		
		re.end();
		
		//get the image and create a new imagepanel
		File file = new File(graphName);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawGraphicWindow(image);
		
    }

	/** Draw a BoxPlot graphic */
	public void drawBoxPlotChart(Map<String, Double> mapRange) {

		if(mapRange == null) throw new IllegalArgumentException("Information to plot wasn't passed");
		
		String graphName = "boxplot.png";
		
		String nameVector = RUtil.formatRVector( new ArrayList<String>( mapRange.keySet()));
		String valuesVector = RUtil.formatRVectorDoubleList( new ArrayList<Double>( mapRange.values()));
		
		System.out.println(nameVector);
		System.out.println(valuesVector);
		
		re.eval("x <- "+valuesVector);
		re.eval("name <- "+nameVector);
		
		re.eval("png(file=\""+graphName+"\",width=2600,height=1600,res=200)");
		re.eval("boxplot(x, horizontal=TRUE) ");
		re.eval("dev.off()");
		
		re.end();
		
		//get the image and create a new imagepanel
		File file = new File(graphName);
		Image image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawGraphicWindow(image);
		
    }

	/* Draw the image inside a JFrame */
	private void drawGraphicWindow(Image image) {
		LineImagePanel myPanel = new LineImagePanel(image);
		//Create a new frame and add the imagepanel
		JFrame aFrame = new JFrame();
		aFrame.setTitle("Chart");
		aFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
		aFrame.getContentPane().add(myPanel, BorderLayout.CENTER);
		aFrame.pack();
		aFrame.setVisible(true);
		aFrame.setSize(new Dimension(1200, 600));
	}
	
	
	
	/* A panel to draw the picture */
	static class LineImagePanel extends JPanel {
        Image image = null;
        public LineImagePanel(Image image) {
            this.image = image;
        }
        @Override
        public void paintComponent(Graphics g) {
        	super.paintComponent(g);
    		int height = this.getSize().height;
    		int width = this.getSize().width; 
    		g.drawImage(image, 0, 0, width, height, this);
        }
	}
	

}
