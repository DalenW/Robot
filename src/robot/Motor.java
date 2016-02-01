package robot;

public class Motor{
    private String name;
    private float value;
    private Log log;
    
    /**
     * Create a new motor with the name n.
     * @param n 
     */
    public Motor(String n){
        name = n;
        log = new Log(name);
    }
    
    /**
     * Set the value of the motor.
     * @param v 
     */
    public void setValue(float v){
        value = v;
        //log.write("Set the value to " + getValueInt() + ".");
    }
    
    /**
     * Returns the value of the motor between 0-1.
     * @return 
     */
    public float getValue(){
        return value;
    }
    
    /**
     * Returns the value as an int between 100 - 100.
     * @return 
     */
    public int getValueInt(){
        return (int)(value*100);
    }
    
    /**
     * Returns the value as a double between -100 - 100.
     * @return 
     */
    public double getValueDouble(){
        return (double)(value*100);
    }
    
    /**
     * Return the pwm value as a Hexadecimal.
     * @return 
     */
    public String getValueHex(){
        log.write(Integer.toString(getValueInt()));
        
        int i = getValueInt();
        i += 100;
        String h = Integer.toHexString(i);
        
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
}
