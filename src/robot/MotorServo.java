package robot;

public interface MotorServo {
    public String getName();
    
    public void setValue(float v);
    
    public float getValue();
    public double getValueDouble();
    public int getValueInt();
    public String getValueHex();
    
}
