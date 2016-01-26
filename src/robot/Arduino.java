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
            
        } catch (NoSuchPortException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            port.close();
        }
    }
    
    public void write(){
        
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
