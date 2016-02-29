package sima.sync.server.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Main entrypoint for the app
 */
public class Main {
    public static final Main instance = new Main();
    public JFrame mainFrame;
    /**
    * Must be called from the event dispatching thread
     */
    private void initiateSwing(){
        //First of all create the window.
        mainFrame = new JFrame("Hello Swing!!!");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Hello everybody stuff
        JLabel label = new JLabel("Hello World");
        mainFrame.getContentPane().add(label);
        //Display the window
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(instance::initiateSwing);
    }
}
