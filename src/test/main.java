package test;

import com.ward.Console;
import robot.Arduino;
import robot.Motor;
import robot.Servo;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        Arduino a = new Arduino("Arduino Uno", "COM3");
        a.connect();
        System.out.println("Connected: " + a.isConnected());
        
        Motor m1 = new Motor("Motor 1");
        Motor m2 = new Motor("Motor 2");
        Servo s1 = new Servo("Servo 1");
        
        s1.setValue(180);
        
        System.out.println(s1.getValueHex());
        System.out.println(a.getOutput(s1.getValueHex()));
        System.out.println(a.getOutput(s1.getValueHex()).getBytes());
        
        a.write(s1.getValueHex());
        
        while(true){
            a.write(s1.getValueHex());
            //System.out.println(a.readLine());
            Thread.sleep(10);
        }
        
    }
}
