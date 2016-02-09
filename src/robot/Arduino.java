package robot;

import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arduino {

    private String com = "";
    private String name;
    private boolean connected = false;
    private Log log;

    private CommPortIdentifier portID;
    private SerialPort port;
    private OutputStream portOutStream;
    private InputStream portInStream;
    private BufferedReader input;

    /**
     * Add an Arduino
     *
     * @param n = Arduino name
     * @param c = COM port
     * @param m = # of motors
     * @param s = # of servos
     */
    public Arduino(String n, String c) {
        name = n;
        com = c;
        log = new Log(name);
    }

    /**
     * Connect to the Arduino.
     */
    public void connect() {
        log.write("Connecting to the Arduino.");
        try {
            portID = CommPortIdentifier.getPortIdentifier(com);
            port = (SerialPort) portID.open(name, 9600);

            portOutStream = port.getOutputStream();
            portInStream = port.getInputStream();
            
            input = new BufferedReader(new InputStreamReader(port.getInputStream()));

            connected = true;
            log.write("Connected.");
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
     *
     * @param m array of motors.
     * @param s array of servos.
     */
    public void write(String s) {
        String w = getOutput(s);
        w = w.toUpperCase();
        byte[] b = w.getBytes();
        
        if (connected && b.length == 25) {
            try {
                //System.out.println(b);
                portOutStream.write(b);
                portOutStream.flush();
            } catch (IOException ex) {
                log.crtError("IO Exception when writing to Arduino " + name + ".");
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Set the COM port. Runs connect() after.
     *
     * @param c
     */
    public void setCOM(String c) {
        com = c;
        log.write("Changed the COM port to " + com + ".");
        connect();
    }

    /**
     * Returns the COM port.
     *
     * @return
     */
    public String getCOM() {
        return com;
    }

    /**
     * Returns the name of the Arduino.
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the hex output being sent to the arduino.
     *
     * @param s
     * @return
     */
    public String getOutput(String s) {
        int r = 24 - s.length();
        String write = 'T' + s;

        if (r > 0 && r < 25) {
            for (int i = 0; i < r; i++) {
                write += "0";
            }
        } else {
            log.crtError("To many motors and servos.");
        }
        return write;
    }

    @Override
    public String toString() {
        return "Arduino "
                + "\n Name: " + name
                + "\n COM Port: " + com;
    }

    public void reconnect() {
        connect();
    }
    
    public boolean isConnected(){
        return connected;
    }
    
    public String readLine(){
        try {
            return input.readLine();
        } catch (IOException ex) {
            log.Error("Failed to read line from Arduino.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
