package test;



import com.ward.Console;
import robot.Arduino;
import robot.Joystick;
import robot.Motor;
import robot.Servo;

public class main {
    public static void main(String[] args) throws InterruptedException {
        Console c = new Console();
        c.build(500, 500, "Robot Testing");
        
        System.out.println("Starting");
        
        Joystick j = new Joystick("Logitech Extreme 3D");
        j.connect();
        while(true){
            System.out.println("X: " + j.getX() + " Y: " + j.getY() + " R: " + j.getRotation());
            Thread.sleep(50);
        }
    }
}
