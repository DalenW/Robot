package robot.write;

import robot.Robot;
import robot.devices.Arduino;

public class Servo extends Direct{
   
    //public final float max_value = 180; min_value = 0;
    
    /**
     * Create a new servo with the name n.
     * @param n 
     */
    public Servo(String n, int p, Arduino a){
        super(n, p, a);
        value = 90;
        Robot.add(this);
        log.write("This is a servo.");
    }
    
    /**
     * Set the value of the servo.
     * @param v 
     */
    public void setValue(int v){
        if(v > 180){
            v = 180;
        }
        
        if(v < 0){
            v = 0;
        }
        value = v;
    }
    
    public void setValueAxisValue(float f){
        f++;
        f *= 90;
        setValue((int) f);
    }
    
    
    
    public void addOneDegree(){
        addDegree(1);
    }
    
    public void subOneDegree(){
        subDegree(1);
    }
    
    public void addDegree(int v){
        setValue(value + v);
    }
    
    public void subDegree(int v){
        setValue(value - v);
    }
    
    @Override
    public String toString(){
        return "Servo" + 
                "\n Name: " + name;
    }
}
