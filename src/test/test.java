package test;

import com.ward.Console;
import robot.Arduino;

public class test{
    public static void main(String[] args){
        Console c = new Console();
        c.build(500, 500, "ROBOT Library Testing");
        
        Arduino a = new Arduino("Arduino Uno", "COM3");
        a.setCOM("COM5");
    }
}