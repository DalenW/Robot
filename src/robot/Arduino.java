package robot;

import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arduino {
    private String com = "";
    private String name;
    private boolean connected = false;
    
    private static CommPortIdentifier portID;
    private static SerialPort port;
    private static OutputStream portOutStream;
    private static InputStream portInStream;
    
    /**
     * Add the arduino.
     * @param n = Arduino name.
     * @param c = COM port to connect to. 
     */
    public Arduino(String n, String c){
        name = n;
        com = c;
        connect();
    }
    
    /**
     * Connect to the Arduino.
     */
    public void connect(){
        try {
            portID = CommPortIdentifier.getPortIdentifier(com);
            port = (SerialPort)portID.open(name, 9600);
            
            portOutStream = port.getOutputStream();
            portInStream = port.getInputStream();
            
            connected = true;
            
        } catch (NoSuchPortException ex) {
            connected = false;
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            connected = false;
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            connected = false;
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            port.close();
        }
    }
    
    /**
     * Write to the Arduino. 
     * @param m array of motors.
     * @param s array of servos.
     */
    public void write(Motor[] m, Servo[] s){
        if(connected){
            String write = "T";
            
            for(int i = 0; i < m.length; i++){
                write += m[i].getValueHex();
            }
            
            for(int i = 0; i < s.length; i++){
                write += s[i].getValueHex();
            }
            
            try {
                portOutStream.write(write.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Set the COM port. Runs connect() after. 
     * @param c 
     */
    public void setCOM(String c){
        com = c;
        connect();
    }
    
    /**
     * Returns the COM port.
     * @return 
     */
    public String getCOM(){
        return com;
    }
    
    /**
     * Returns the name of the Arduino.
     * @return 
     */
    public String getName(){
        return name;
    } 
}
