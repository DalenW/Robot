package robot;

public class Servo {
    private String name;
    private int value;
    private Log log;
    //public final float max_value = 180; min_value = 0;
    
    /**
     * Create a new servo with the name n.
     * @param n 
     */
    public Servo(String n){
        name = n;
        log = new Log(name);
        value = 90;
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
        //log.write("Set the value to " + value + ".");
    }
    
    /**
     * Returns the value of the servo.
     * @return 
     */
    public int getValue(){
        return value;
    }
    
    /**
     * Returns the value as a double.
     * @return 
     */
    public double getValueDouble(){
        return (double)(value);
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
    
    /**
     * Return the value as a Hexadecimal.
     * @return 
     */
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
    
    /**
     * Set the name for the servo.
     * @param n 
     */
    public void setName(String n){
        name = n;
    }
    
    /**
     * Get the name of the motor.
     * @return 
     */
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return "Servo" + 
                "\n Name: " + name;
    }
}
