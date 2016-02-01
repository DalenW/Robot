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
    private Log log;
    private Motor[] motor;
    private Servo[] servo;
    private String hexRest = "";
    
    private static CommPortIdentifier portID;
    private static SerialPort port;
    private static OutputStream portOutStream;
    private static InputStream portInStream;
    
    /**
     * Add an Arduino
     * @param n = Arduino name
     * @param c = COM port
     * @param m = # of motors
     * @param s = # of servos
     */
    public Arduino(String n, String c, int m, int s){
        name = n;
        com = c;
        log = new Log(name);
        motor = new Motor[m];
        servo = new Servo[s];
        
        int ms = m + s;
        if(ms > 12){
            log.crtError("More than 12 motors/servos.");
        } else {
            for(int i = ms; i < 12; i++){
                hexRest += "00";
            }
        }
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
            log.crtError("Couldn't find the Arduino " + name + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            connected = false;
            log.crtError("Something else is already using the Arduino " + name + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            connected = false;
            log.crtError("Io Exception when connecting to the Arduino " + name + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            port.close();
        }
    }
    
    /**
     * Write to the Arduino. 
     * @param m array of motors.
     * @param s array of servos.
     */
    public void write(){
        if(connected){
            try {
                portOutStream.write(getWrite().getBytes());
            } catch (IOException ex) {
                log.crtError("IO Exception when writing to Arduino " + name + ".");
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
    
    /**
     * Set the motor array.
     * @param m 
     */
    public void setMotors(Motor[] m){
        motor = m;
    }
    
    /**
     * Set the servo array. 
     * @param s 
     */
    public void setServos(Servo[] s){
        servo = s;
    }
    
    public String getWrite(){
        String write = "";
            
        for(int i = 0; i < motor.length; i++){
            write += motor[i].getValueHex();
        }

        for(int i = 0; i < servo.length; i++){
            write += servo[i].getValueHex();
        }

        write += hexRest;
        
        return write;
    }
}
