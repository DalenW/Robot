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
    
    public Direct(String n, int p, Arduino a){
        name = n;
        setPort(p);
        value = 0;
        
        log = new Log(name);
        log.write("Created a Direct Write object");
        
        Robot.add(this);
        a.setDirect(this, port);
        log.write("Added this to the arduino " + a.getName() + ".");
    }
    
    public Direct(int p, Arduino a){
        this("###", p, a);
    }
    
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
    
    public String getValueHex(){
        log.write(Integer.toString(value));
        
        String h = Integer.toHexString(value);
        
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
    
    public boolean setPort(int p){
        if(p < 2){
            return false;
        } else if(p > 14){
            return false;
        } else {
            port = p;
            return true;
        }
    }
    
    public void scaleValue(double d){
        this.setValue((int) (value * d));
    }

}
