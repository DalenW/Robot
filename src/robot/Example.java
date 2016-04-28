package robot;

import robot.devices.Arduino;
import robot.devices.Joystick;
import robot.read.Sensor;
import robot.util.Log;
import robot.write.Motor;
import robot.write.Servo;

class Example {
    public void run(){
        Log log = new Log("Example Log", true); //seconed argument makes it a main log. this makes it println all writes. if you didn't need that just do //Log log = new Log("Example Log");
        log.write("Starting");
        
        log.write("Adding joystick");
        Joystick j = new Joystick("Logitech Extreme 3D"); //name of the joystick as it appears in devices and printers
        
        log.write("Adding Arduino");
        Arduino a = new Arduino("Uno", 115200); //name doesn't matter, just for log and reference. number is the baud rate of the arduino
        a.connect(); //connect to the arduino
        
        log.write("Creating motors");
        Motor left = new Motor("Left", 2, a);//name of the motor for log purposus, port on the arduino, arduino
        Motor right = new Motor("Right", 3, a);
        
        log.write("Creating servos");
        Servo arm = new Servo("Arm", 4, a);//same as motor
        
        log.write("Creating sensors");
        Sensor temp = new Sensor("Temperature", a);//name of the sensor, exact same as name in arduino code, arduino
        
        while(true){ //code to take the joystick values and apply them to motor/servo values
            left.setValue((int)(100 * j.getX()));
            //or
            left.setValueAxisValue(j.getX());
            
            arm.setValueAxisValue(j.getSlider());
            
            if(j.getButton(0)){
                right.setValue(200); //full forward
            } else if(j.getButton(1)){
                right.setValue(0);//full reverse
            } else {
                right.setValue(100);//neutral
            }
            System.out.println(temp.getName() + ": " + temp.getValue());//will print 0.0 until the reading starts
        }
    }

}
