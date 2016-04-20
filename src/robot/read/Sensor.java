package robot.read;

import robot.devices.Arduino;
import robot.util.Log;

public class Sensor {
    private String name;
    private int type = -1;
    private float value;
    
    private Log log;
    private Arduino arduino;
    
    public Sensor(String n, int t, Arduino a){
        name = n;
        type = t;
        arduino = a;
        
        log = new Log(name);
        log.write("Created a new sensor with type " + t + ".");
        
        robot.Robot.add(this);
        a.addSensor(this);
        log.write("Added this to the arduino " + a.getName() + ".");
    }
    
    public Sensor(String n, Arduino a){
        this(n, -1, a);
    }
    
    public void setValue(float v){
        value = v;
    }
    
    public float getValue(){
        return value;
    }
    
    public int getType(){
        return type;
    }
    
    public String getName(){
        return name;
    }

}
