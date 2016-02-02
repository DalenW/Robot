package robot;

public class Servo {
    private String name;
    private int value;
    private Log log;
    
    /**
     * Create a new motor with the name n.
     * @param n 
     */
    public Servo(String n){
        name = n;
        log = new Log(name);
    }
    
    /**
     * Set the value of the motor.
     * @param v 
     */
    public void setValue(int v){
        if(v > 180){
            v = 180;
        }
        value = v;
        //log.write("Set the value to " + value + ".");
    }
    
    /**
     * Returns the value of the servo.
     * @return 
     */
    public float getValue(){
        return value;
    }
    
    /**
     * Returns the value as an int.
     * @return 
     */
    public int getValueInt(){
        return value;
    }
    
    /**
     * Returns the value as a double.
     * @return 
     */
    public double getValueDouble(){
        return (double)(value);
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
     * Set the name for the motor.
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
