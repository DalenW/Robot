package robot;

public class Motor {
    private String name;
    private float value;
    
    /**
     * Create a new motor with the name n.
     * @param n 
     */
    public Motor(String n){
        name = n;
    }
    
    /**
     * Create a new motor with the name "Motor".
     */
    public Motor(){
        this("Motor");
    }
    
    /**
     * Set the value of the motor.
     * @param v 
     */
    public void setValue(float v){
        value = v;
    }
    
    /**
     * Returns the value of the motor between 0-1.
     * @return 
     */
    public float getValue(){
        return value;
    }
    
    /**
     * Returns the value as an int between 0 - 100.
     * @return 
     */
    public int getValueInt(){
        return (int)(value*100);
    }
    
    /**
     * Returns the value as a double between 0 - 100.
     * @return 
     */
    public double getValueDouble(){
        return (double)(value*100);
    }
    
    /**
     * Return the value as a Hexadecimal.
     * @return 
     */
    public String getValueHex(){
        int i = (int) (value*250);
        String h = Integer.toHexString(this.getValueInt());
        
        if(h.length() < 2){
            h = "0" + h;
        } else if(h.length() > 2){
            
        }
        return h;
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
