package robot;

public class DirectWrite {
    private String name;
    private int value;
    private Log log;
    public final int MAX_VALUE = 255, MIN_VALUE = 0;    
    
    public DirectWrite(String n){
        name = n;
        log = new Log(name);
        
        log.write("Created a Direct Write object");
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

}
