package robot.write;

import robot.Robot;
import robot.devices.Arduino;
import robot.util.Log;

public class Direct {
    String name;
    int value;
    Log log;
    int port;
    Arduino a;
    public final int MAX_VALUE = 255, MIN_VALUE = 0;
    double scale = 1.0;
    
    /*
    Create a Direct object. The direct object is the parent object of motor and servo, and it's purpose is to store values, manage ports, return the value as hexadecimal and scale. 
    Basically repeat code that the child objects would have. 
    */
    public Direct(String n, int p, Arduino a){
        name = n;
        setPort(p);
        value = 0;
        
        log = new Log(name);
        log.write("Created a Direct Write object");
        
        Robot.add(this);
        //This adds the direct object to the arduino. The arduino object stores an arry of 12 direct objects to read from. That's why I fill it with blank direct objects on initialization.
        a.setDirect(this);
        log.write("Added this to the arduino " + a.getName() + ".");
    }
    
    //creates an empty direct object, the ### is just a filler
    public Direct(int p, Arduino a){
        this("###", p, a);
    }
    
    //The max and min of a value is 250 and 0. This is purley for hexadecimal purposes.
    public void setValue(int v){
        if(v > 255){
            v = 255;
        }
        if(v < 0){
            v = 0;
        }
        value = v;
        log.write("value = " + value);
    }
    
    //returns the value as a hexadecimal to pass onto the arduino. It's a 2 character string
    public String getValueHex(){
        log.write(Integer.toString(value));
        
        String h = Integer.toHexString(value);
        
        //if the value was one digit instead of 2, it adds a 0 to the beginning.
        if(h.length() < 2){
            h = "0" + h;
        } else if(h.length() > 2){
            log.Error("Hexidecimal value is too high.");
        }
        return h.toUpperCase();
    }
    
    public int getValue(){
        return value;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public String getName(){
        return name;
    }
    
    public int getPort(){
        return port;
    }
    
    //set the port of the object on the arduino. the port has to be 2 or greater. 
    public boolean setPort(int p){
        if(p < 2){
            log.crtError("Tried to set " + name + " to port " + p + ". This needs to be 2 or greater.");
            return false;
        } else if(p > 14){
            return false;
        } else {
            port = p;
            return true;
        }
    }
    //MAGIC, HOPES AND DREAMS MAKE ROBOTS WORK
    //dont know when ^^^ got there but ok. this scales the value. if you set the sensitivity to 50% this gets changed. 
    public void scaleValue(double d){
        if(d > 0 && d < 1) scale = d;
    }

}
