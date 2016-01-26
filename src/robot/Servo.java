package robot;

public class Servo {
    private String name;
    private float value;
    
    /**
     * Create a new motor with the name n.
     * @param n 
     */
    public Servo(String n){
        name = n;
    }
    
    /**
     * Create a new motor with the name "Motor".
     */
    public Servo(){
        this("Servo");
    }
    
    /**
     * Set the value of the motor.
     * @param v 
     */
    public void setValue(float v){
        value = v;
    }
    
    /**
     * Returns the value of the motor.
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
        return (int)value;
    }
    
    /**
     * Returns the value as a double.
     * @return 
     */
    public double getValueDouble(){
        return (double)value;
    }
    
    /**
     * Return the value as a Hexadecimal.
     * @return 
     */
    public String getValueHex(){
        return Integer.toHexString(this.getValueInt());
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
