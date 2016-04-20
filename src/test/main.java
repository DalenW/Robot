package test;



import com.ward.Console;
import robot.devices.Arduino;
import robot.devices.Joystick;
import robot.read.Sensor;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        Joystick j = new Joystick("Logitech Extreme 3D");
        //j.connect();
        
        Arduino a = new Arduino("Uno", 115200);
        a.connect();
        
        Sensor t = new Sensor("temperature", 1, a);
        a.startWrite();
        
        while(true){
            //System.out.println(t.getValue());
            Thread.sleep(50);
        }
    }
}
