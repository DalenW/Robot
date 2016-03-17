package test;



import com.ward.Console;
import javax.swing.JFrame;
import robot.*;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        //c.build(500, 500, "Robot Testing");
        
        Camera cam = new Camera("USB 2.0 PC Cam");
        cam.connect();
        
        JFrame gui = new JFrame();
        gui.setVisible(true);
        gui.setSize(320, 240);
        
        gui.add(cam.getCameraPanel());
    }
}
