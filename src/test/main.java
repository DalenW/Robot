package test;

import com.ward.Console;
import robot.Arduino;
import robot.Motor;
import robot.Servo;

public class main {
    public static void main(String[] args) {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        //Arduino a = new Arduino("Arduino Uno", "COM3");
        
        Motor m1 = new Motor("Motor 1");
        Motor m2 = new Motor("Motor 2");
        Servo s1 = new Servo("Servo 1");
        
        s1.setValue(180);
        System.out.println(s1.getValueHex());
        System.out.println(getOutput(s1.getValueHex()));
        
        while(true){
            //a.write(s1.getValueHex() + m1.getValueHex());
        }
    }
    
    public static String getOutput(String s){
        int r = 24 - s.length();
        String write = s;
        
        if(r > 0 && r < 25){
            for(int i = 0; i < r; i++){
                write += "0";
            }
        } else { 
            //log.crtError("To many motors and servos.");
        }
        return write;
    }

}
