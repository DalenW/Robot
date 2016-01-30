package test;

import com.ward.Console;
import robot.Arduino;
import robot.Joystick;

public class main{
    public static void main(String[] args){
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        //Joystick j = new Joystick("Logitech 3D Pro");
        Arduino a = new Arduino("Arduino Uno", "COM3");
    }
}