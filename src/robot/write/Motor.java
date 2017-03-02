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
     * The values of the motor range from 0 to 200. Anything above and below that get set to the respectable max or min. 
     * 0 is reverse and 200 is forward, 100 is neutral and anything between that is self explanetory. 
     * @param v 
     */
    public void setValue(int v){
        if(v < 0){
            v = 0;
        } else if(v > 200){
            v = 200;
        }
        
        value = v;
        
        //if you reversed the motor, this reverses the values. This is not to be used to go backwards, this actually flips all the values. 
        if(rev){
            int t = value;
            value = 200 - t;
        }
        v *= scale;
    }
    
    //set the value if it was a value from the joystick. 
    //the joystick returns values as -1f to 1f, so this adds 1 making it from 0 - 2, multiplies it by 100 and turns it into an int before sending it off to setValue()
    public void setValueAxisValue(float f){
        f++;
        f *= 100.0000;
        setValue((int)f);
    }
    
    //returns the value as an axis value, between -1f and 1f.
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
