/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jframe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

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
        private JMenu debugMenu = new JMenu("Debug");
        private JMenuItem exitItem = new JMenuItem("Exit");
        private JMenuItem debugConsole = new JMenuItem("Debug Console");
        
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
            this.menuBar.add(this.debugMenu);
            
            //Make MenuItem Mnemonic
            this.exitItem.setMnemonic('X');
            
            //Register Listeners to MenuItems
            this.exitItem.addActionListener(new ExitListener());
            this.debugConsole.addActionListener(new DebugConsoleListener());
            
            //Add MenuItems to menuBar
            this.mainMenu.add(this.exitItem);
            this.debugMenu.add(this.debugConsole);
        }
        
        protected class ExitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            INSTANCE.dispose();
        }
            
        }
        
        protected class DebugConsoleListener implements ActionListener
        {

        @Override
        public void actionPerformed(ActionEvent ae) {
            ExternalizedConsole con = new ExternalizedConsole();
            PrintStream ps = new PrintStream(con);
            System.setOut(ps);
            System.setErr(ps);
        }
            
        }
        
        
        
}
class ExternalizedConsole extends OutputStream{
    protected final ExternalizedConsole CONSOLE = this;
    
    protected final JTextArea area = new JTextArea();
    private final JFrame frame;
    private final JConsole jcon = new JConsole();
    protected Thread thread;
    
    public ExternalizedConsole()
    {
        this.frame = jcon.INSTANCE;
        this.area.setEditable(false);
        this.thread = new Thread(jcon);
        this.thread.start();
    }

    @Override
    public void write(int i) throws IOException {
        // redirects data to the text area
        this.area.append(String.valueOf((char)i));
        // scrolls the text area to the end of data
        this.area.setCaretPosition(this.area.getDocument().getLength());
    }
    
    private final class JConsole extends ExtendedJFrame{
        
        
        private final JScrollPane scroll = new JScrollPane(area);
        
        private JMenuBar menuBar = new JMenuBar();
        private JMenu mainMain = new JMenu("File");
        private JMenuItem clearItem = new JMenuItem("Clear");
        private JMenuItem closeItem = new JMenuItem("Close");
        private JMenuItem saveItem = new JMenuItem("Save to File");
        
        
        public JConsole()
        {
            super("Console");
        }

        @Override
        public void run() {
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            super.center();
            this.buildContainers();
            this.setVisible(true);
        }

        @Override
        protected void buildContainers() {
            this.setLayout(new GridLayout(1,1));
            this.add(this.scroll);
            this.scroll.setAutoscrolls(true);
            this.scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            this.scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            this.addWindowListener(new WindowListener(){

                @Override
                public void windowOpened(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowClosing(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    System.setOut(System.out);
                    System.setErr(System.err);
                    Thread.currentThread().interrupt();
                }

                @Override
                public void windowClosed(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowIconified(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowDeiconified(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowActivated(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void windowDeactivated(WindowEvent we) {
                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            this.setJMenuBar(menuBar);
            this.menuBar.add(this.mainMain);
            this.mainMain.add(this.clearItem);
            this.clearItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    CONSOLE.area.setText("");
                }
            });
            this.mainMain.add(this.closeItem);
            this.closeItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.setOut(System.out);
                    System.setErr(System.err);
                    INSTANCE.dispose();
                    Thread.currentThread().interrupt();
                }
            });
            
            this.mainMain.add(this.saveItem);
            this.saveItem.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                    File file =null;
                    PrintStream ps = null;
                    FileChooser fc = new FileChooser();
                    try{
                        //file = fc.showSaveDialog(null);
                        //ps = new PrintStream(file);
                        ps = new PrintStream(new Date().toString() + "_console_Log.txt");
                        
                        ps.println(CONSOLE.area.getText().toCharArray());
                        ps.flush();
                        ps.close();
                        
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            
            
            
        }
    
}
        
    }