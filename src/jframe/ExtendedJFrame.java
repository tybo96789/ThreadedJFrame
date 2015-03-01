/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Tyler Atiburcio
 */
public abstract class ExtendedJFrame extends JFrame implements Runnable{

    public static final int DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 500;
    public static final String DEFAULT_TITLE = "Window";
    
    
    
        //Frame Stuff
        protected final int INITAL_WIDTH, INITAL_HEIGHT;
        protected final ExtendedJFrame INSTANCE = this;
        protected final String TITLE;

        //Panel Stuff
        protected ArrayList<Container> containers = new ArrayList<Container>();
        protected JPanel panel = new JPanel();
        
        //Menu Bar
        private JMenuBar menuBar = new JMenuBar();
        private JMenu mainMenu = new JMenu("File");
        private JMenuItem exitItem = new JMenuItem("Exit");
        
        public ExtendedJFrame()
        {
            this(ExtendedJFrame.DEFAULT_TITLE,ExtendedJFrame.DEFAULT_HEIGHT,ExtendedJFrame.DEFAULT_WIDTH);
        }
        public ExtendedJFrame(String title)
        {
            this(title,ExtendedJFrame.DEFAULT_HEIGHT,ExtendedJFrame.DEFAULT_WIDTH);
        }
        
        public ExtendedJFrame(String title, int width, int height)
        {
            this.TITLE = title;
            this.INITAL_HEIGHT = height;
            this.INITAL_WIDTH = width;
            
            this.setTitle(this.TITLE);
            this.setSize(INITAL_HEIGHT,INITAL_WIDTH);
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
        
        protected void buildJMenu()
        {
            //Attach JMenubar to Frame
            this.setJMenuBar(menuBar);
            this.menuBar.add(this.mainMenu);
            
            //Make MenuItem Mnemonic
            this.exitItem.setMnemonic('X');
            
            //Register Listeners to MenuItems
            this.exitItem.addActionListener(new ExitListener());
            
            //Add MenuItems to menuBar
            this.mainMenu.add(this.exitItem);
        }
        
        protected class ExitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            INSTANCE.dispose();
        }
            
        }
        
        
        
}
