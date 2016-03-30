package robot;

import gnu.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Arduino {

    private String com;
    private String name;
    private boolean connected = false;
    private Log log;
    private int rate;
    private String os;
    private int comChoice = -1;
    private String output;
    
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
    
    public Arduino(String n, int r, String c){
        this(n, r);
        com = c;
    }

    /**
     * Connect to the Arduino.
     */
    public void connect(){
        log.write("Connecting to the Arduino.");
        portEnum = CommPortIdentifier.getPortIdentifiers();
        
        try {
            if(com == null){
                log.write("Listing port options");
                ArrayList<CommPortIdentifier> coms = new ArrayList();

                if(portEnum.hasMoreElements()){
                    while(portEnum.hasMoreElements()){
                        CommPortIdentifier cp = portEnum.nextElement();
                        log.write(cp.getName() + " - " + getPortTypeName(cp.getPortType()));

                        if(cp.getPortType() == CommPortIdentifier.PORT_SERIAL){
                            coms.add(cp);
                        }
                    }
                } else {
                    log.crtError("Couldn't find an Arduino.");
                }

                if(coms.size() > 1){
                    log.crtError("More than one serial interface detected.");
                    //log.write("Make sure that itunes is completly shutdown. ");

                    String message = "Select the com port that you want. If your not sure check devices and printers.";
                    for(int i = 1; i < coms.size() + 1; i++){
                        message += "\n " + i + ": " + coms.get(i-1).getName();
                    }

                    if(comChoice == -1){
                        comChoice = Integer.parseInt(JOptionPane.showInputDialog(message));
                        log.write("\n\n" + message);
                        log.write("Selected " + comChoice);
                        portID = CommPortIdentifier.getPortIdentifier(coms.get(comChoice - 1).getName());
                        com = portID.getName();
                    } else { 
                    }

                } else {
                    portID = coms.get(0);
                    com = portID.getName();
                }
            } else {
                portID = CommPortIdentifier.getPortIdentifier(com);
            }
            
            port = (SerialPort) portID.open(this.getClass().getName(), rate);
            openOutStream();
            openInStream();
            port.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            
            Thread.sleep(100);

            input = new BufferedReader(new InputStreamReader(port.getInputStream()));

            connected = true;
            log.write("Connected.");
            log.write("\n\n" + this.toString());
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
        } catch (NoSuchPortException ex) {
            log.crtError("Couldn't connect to the " + name + " on port " + com + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*
    /**
     * Connect with a specified com choice. 
     * @param c 
     *
    public void connect(int c){
        comChoice = c;
        connect();
    }
    */
    /**
     * Write to the Arduino.
     * @param s
     */
    public void write(String s) {
        String w = parseOutput(s).toUpperCase();
        
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
    public String parseOutput(String s) {
        int r = 24 - s.length();
        String write = 'T' + s;

        if (r > 0 && r < 25) {
            for (int i = 0; i < r; i++) {
                write += "0";
            }
        } else {
            //log.crtError("To many motors and servos.");
        }
        output = write;
        return write;
    }
    
    public String getOutput(){
        return output;
    }

    @Override
    public String toString() {
        return "Arduino "
                + "\n Name: " + name
                + "\n COM Port: " + com
                + "\n Rate: " + rate;
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
