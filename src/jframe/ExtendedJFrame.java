/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Tyler
 */
public abstract class ExtendedJFrame extends JFrame implements Runnable{
    
    public static final int DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 500;
    public static final String DEFAULT_TITLE = "Window";
    
    
    
        //Frame Stuff
        protected final int INITAL_WIDTH, INITAL_HEIGHT;
        protected final ExtendedJFrame INSTANCE = this;
        protected final String TITLE;

        //Panel Stuff
        private ArrayList<Container> containers = new ArrayList<Container>();
        private JPanel panel = new JPanel();
        
        public ExtendedJFrame()
        {
            this.TITLE = ExtendedJFrame.DEFAULT_TITLE;
            this.INITAL_HEIGHT = ExtendedJFrame.DEFAULT_HEIGHT;
            this.INITAL_WIDTH = ExtendedJFrame.DEFAULT_WIDTH;
        }
        public ExtendedJFrame(String title)
        {
            this.TITLE = title;
            this.INITAL_HEIGHT = ExtendedJFrame.DEFAULT_HEIGHT;
            this.INITAL_WIDTH = ExtendedJFrame.DEFAULT_WIDTH;
        }
        
        public ExtendedJFrame(String title, int width, int height)
        {
            this.TITLE = title;
            this.INITAL_HEIGHT = height;
            this.INITAL_WIDTH = width;
        }
        
    @Override
        public abstract void run();
        
        protected abstract void buildContainers();
        
        protected void center()
        {
            //Used to center the Window to the center of the screen no matter what computer you are using
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            this.setMaximizedBounds(null);
            this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        }
        
        
        
}
