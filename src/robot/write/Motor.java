package robot.write;

import robot.Robot;
import robot.devices.Arduino;

public class Motor extends Direct{
    private boolean rev = false;
    
    /**
     * Create a new motor with the name n.
     * @param n 
     */
    public Motor(String n, int p, Arduino a){
        super(n, p, a);
        Robot.add(this);
        log.write("This is a motor.");
    }
    
    /**
     * Set the value of the motor.
     * @param v 
     */
    public void setValue(int v){
        if(v < 0){
            v = 0;
        } else if(v > 200){
            v = 200;
        }
        
        value = v;
        
        if(rev){
            int t = value;
            value = 200 - t;
        }
        v *= scale;
    }
    
    public void setValueAxisValue(float f){
        f++;
        f *= 100.0000;
        setValue((int)f);
    }
    
    public float getValueAsAxis(){
        float f;
        f = value/100;
        f--;
        return f;
    }
    
    /**
     * Check if the motor is being reversed automatically
     * @return 
     */
    public boolean getReversed(){
        return rev;
    }
    
    /**
     * Set whether or not the motor is being reversed. Default false.
     * @param b 
     */
    public void setReverse(boolean b){
        rev = b;
    }
}
