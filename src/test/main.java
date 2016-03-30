package test;



import com.ward.Console;
import robot.*;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        Joystick j = new Joystick("Logitech Extreme 3D");
        j.connect();
        
        
        
        Arduino a = new Arduino("Uno", 115200);
        a.connect();
        
        Servo s = new Servo("s1");
        Motor m1 = new Motor("Motor");
        Motor m2 = new Motor("Motor");
        Motor m3 = new Motor("Motor");
        
        while(true){
            if(j.getHatSwitch()[3]){ //right
                s.addDegree(10);

            }
            if(j.getHatSwitch()[7]){ //left
                s.subDegree(10);
            }
            //System.out.println(s.getValue());
            
            //a.write(m1.getValueHex() + m2.getValueHex() + m3.getValueHex() + s.getValueHex());
            a.write(s.getValueHex());
            System.out.println(a.getOutput());
            
            Thread.sleep(5);
        }
        
    }
}
