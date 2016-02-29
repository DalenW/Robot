package robot;

import gnu.io.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arduino {

    private String com = "";
    private String name;
    private boolean connected = false;
    private Log log;
    private int rate;
    private String os;

    private CommPortIdentifier portID;
    private SerialPort port;
    private java.util.Enumeration<CommPortIdentifier> portEnum;
    private OutputStream portOutStream;
    private InputStream portInStream;
    private BufferedReader input;

    /**
     * Add an Arduino
     *
     * @param n = Arduino name
     * @param c = COM port
     * @param r = communication rate
     */
    public Arduino(String n, int r) {
        name = n;
        rate = r;
        log = new Log(name);
    }

    /**
     * Connect to the Arduino.
     */
    public void connect(){
        os = System.getProperty("os.name").toLowerCase();
        
        //windows
        if(os.substring(0, 1).equals("w")){
            connectW();
        } else if(os.substring(0, 1).equals("m")){
            connectM();
        } else {
            log.crtError("Unsupported OS: " + os);
        }
        
        
        try {
            port = (SerialPort) portID.open(this.getClass().getName(), rate);
            openOutStream();
            openInStream();
            port.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            Thread.sleep(100);

            input = new BufferedReader(new InputStreamReader(port.getInputStream()));

            connected = true;
            log.write("Connected.");
        } catch (InterruptedException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCommOperationException ex) {
            log.crtError("Couldn't set port parameters.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            log.crtError("Something else is using " + name + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Connect to the Arduino on windows
     */
    private void connectW(){
        log.write("Connecting to the Arduino.");
        portEnum = CommPortIdentifier.getPortIdentifiers();
        
        log.write("Listing port options");
        int x = 0;
        if(portEnum.hasMoreElements()){
            while(portEnum.hasMoreElements()){
                CommPortIdentifier cp = portEnum.nextElement();
                log.write(cp.getName() + " - " + getPortTypeName(cp.getPortType()));
                
                if(cp.getPortType() == CommPortIdentifier.PORT_SERIAL){
                    portID = cp;
                    com = portID.getName();
                    x++;
                }
            }
        } else {
            log.crtError("Couldn't find an Arduino.");
        }
        
        if(x > 1){
            log.crtError("More than one serial interface detected.");
        }
    }
    
    /**
     * Connect to the Arduino on mac
     */
    private void connectM(){
        //log.write("Connecting to the Arduino.");
        connectW();
    }

    /**
     * Write to the Arduino.
     * @param s
     */
    public void write(String s) {
        String w = getOutput(s).toUpperCase();
        
        byte[] b = w.getBytes();
        
        if(connected && b.length == 25) {
            try {
                //System.out.println(b);
                portOutStream.write(b);
                //portOutStream.flush();
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
            //log.crtError("To many motors and servos.");
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
        return null;
    }
    
    public int getRate(){
        return port.getBaudRate();
    }
    
    public void closeOutStream(){
        try {
            log.write("Closing the output stream.");
            portOutStream.close();
        } catch (IOException ex) {
            log.crtError("Couldn't close the output stream.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openOutStream(){
        try {
            log.write("Opening the output stream.");
            portOutStream = port.getOutputStream();
        } catch (IOException ex) {
            log.crtError("Couldn't open output stream.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void openInStream(){
        
    }
    
    public void closeInStream(){
        try {
            log.write("Closing the input stream.");
            portInStream.close();
        } catch (IOException ex) {
            log.crtError("Couldn't close the input stream.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        closeOutStream();
        port.close();
    }
    
    private String getPortTypeName(int p){
        switch(p){
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }
    
    /**
     * Returns the OS you connected to the arduino on
     * @return 
     */
    public String getOS(){
        return os;
    }
}
