package test;



import com.ward.Console;
import java.awt.Dimension;
import javax.swing.JFrame;
import robot.*;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        Camera cam1 = new Camera("USB 2.0 PC Cam"); 
        cam1.setSize(new Dimension(640, 480));
        cam1.connect();
       
        
        
        /*
        Camera cam2 = new Camera("USB 2.0 PC Cam"); 
        cam2.connect();
        cam2.setSize(new Dimension(640, 480));
        */
        System.out.println("1: " + cam1.getCamName());
        //System.out.println("2: " + cam2.getCamName());
        
        //cam1.displayFPS(true);
        //cam1.debugMode(true);
        cam1.displaySize(true);
        
        JFrame gui1 = new JFrame();
        gui1.setVisible(true);
        gui1.setSize(320, 240);
        gui1.add(cam1.getCameraPanel());
        
        /*
        JFrame gui2 = new JFrame();
        gui2.setVisible(true);
        gui2.setSize(320, 240);
        gui2.add(cam2.getCameraPanel());
        */
        
        
    }
}
