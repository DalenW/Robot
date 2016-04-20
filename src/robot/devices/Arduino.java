package robot.devices;

import gnu.io.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import robot.Robot;
import robot.read.Sensor;
import robot.util.Log;
import robot.write.Direct;

public class Arduino {

    private String com;
    private String name;
    private boolean connected = false;
    private Log log;
    private int rate;
    private String os;
    private int comChoice = -1;
    private String output;
    
    private Direct[] writes = new Direct[12];
    private HashMap<String, Sensor> sensors = new HashMap();
    
    private CommPortIdentifier portID;
    private SerialPort port;
    private java.util.Enumeration<CommPortIdentifier> portEnum;
    private OutputStream portOutStream;
    private InputStream portInStream;

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
        
        for(int i = 0; i < 12; i++){
            Direct d = new Direct(i + 2, this);
        }
        
        Robot.add(this);
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

                } else if(coms.size() == 1){
                    portID = coms.get(0);
                    com = portID.getName();
                }
            } else {
                portID = CommPortIdentifier.getPortIdentifier(com);
            }
            
            if(portID != null){
                port = (SerialPort) portID.open(this.getClass().getName(), rate);
                openOutStream();
                openInStream();
                port.setSerialPortParams(rate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                Thread.sleep(100);

                connected = true;
                log.write("Connected.");
                log.write("\n\n" + this.toString());
            } else {
                connected = false;
                
            }
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCommOperationException ex) {
            log.crtError("Couldn't set port parameters.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PortInUseException ex) {
            log.crtError("Something else is using " + name + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPortException ex) {
            log.crtError("Couldn't connect to the " + name + " on port " + com + ".");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Write to the Arduino.
     * @param s
     */
    public void write() {
        String w = getOutput();
        
        byte[] b = w.getBytes();
        
        if(connected && b.length == 25) {
            try {
                portOutStream.write(b);
                parseRead();
            } catch (IOException ex) {
                log.crtError("IO Exception when writing to Arduino " + name + ".");
                Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        log.write("Writing: " + w);
    }
    
    /**
     * Returns the hex output being sent to the arduino.
     *
     * @param s
     * @return
     */
    public String getOutput() {
        String write = "T";
        
        for(int i = 0; i < 12; i++){
            write += writes[i].getValueHex();
        }
        output = write;
        return write;
    }
    
    public void parseRead(){
        String line = readRaw();
        line += readRaw();
        if(line.indexOf("`") != -1){
            if(line.indexOf("`", 1) == -1){
                line += readRaw();
            } else {
                line = line.substring(line.indexOf("`"), line.indexOf("`", line.indexOf("`")+1));
                ArrayList<String> outputs = new ArrayList();

                for(int i = 1; i < line.length(); i++){
                    if(line.substring(i, i+1).equals("$")){ //start of value
                        i++;

                        String name = "";
                        String value = "";

                        name = line.substring(i, line.indexOf("/"));
                        value = line.substring(line.indexOf("/") + 1, line.length());

                        sensors.get(name).setValue(Float.parseFloat(value));

                        i += line.length() - 1;
                        System.out.println("here");
                    }
                }
            }
            
        } else {
            System.out.println("can't read");
        }
    }
    
    public String readRaw(){
        String line = "didn't read :(";
        try {
            if(portInStream.available() > 0){
                byte[] b = new byte[portInStream.available()];

                for(int i = 0; i < portInStream.available(); i++){
                    b[i] = (byte) portInStream.read();
                }

                line = new String(b);
                //System.out.println(line);
                return line;
            }
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return line;
    }
    
    public void setDirect(Direct d, int p){
        writes[p - 2] = d;
    }
    
    public void addSensor(Sensor s){
        sensors.put(s.getName(), s);
    }
    
    public void removeSensor(Sensor s){
        sensors.remove(s.getName());
    }
    
    public void close(){
        closeOutStream();
        port.close();
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
        try {
            log.write("Opening the input stream.");
            portInStream = port.getInputStream();
        } catch (IOException ex) {
            log.crtError("Couldnt open the input stream.");
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    public void reconnect() {
        connect();
    }
    
    public boolean isConnected(){
        return connected;
    }
    
    public int getRate(){
        return port.getBaudRate();
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
    
    public void startWrite(){
        new Thread(){
            public void run(){
                while(true){
                    write();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
    }
    
    @Override
    public String toString() {
        return "Arduino "
                + "\n Name: " + name
                + "\n COM Port: " + com
                + "\n Rate: " + rate;
    }
}


