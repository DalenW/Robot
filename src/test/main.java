package test;



import com.ward.Console;
import robot.*;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        Joystick j = new Joystick("Logitech Extreme 3D");
        j.connect();
        
        Arduino a = new Arduino("Uno", 115200);
        a.connect();
        
        Servo s = new Servo("s1");
        
        int deg = 90;
        
        while(true){
            
            
            if(j.getHatSwitch()[3]){ //right
                deg++;
            }
            if(j.getHatSwitch()[7]){ //left
                deg--;
            }
            
            if(deg < 0){
                deg = 0;
            } 
            
            if(deg > 180){
                deg = 180;
            }
            
            s.setValue(deg);
            System.out.println(deg);
            a.write(s.getValueHex());
            
            Thread.sleep(2);
        }
    }
}
