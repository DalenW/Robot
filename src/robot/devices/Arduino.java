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
    private String rawInput = "";
    private int loopRate = 20;
    
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
        
        //create 12 empty direct objects on this arduino. They'll return 0 when called for value. 
        //The arduino object stores an arry of 12 direct objects to read from. That's why I fill it with blank direct objects on initialization.
        for(int i = 0; i < 12; i++){
            Direct d = new Direct(i + 2, this);
        }
        
        Robot.add(this);
    }
    
    //not really a use for this. I never finished adding a manual com port. Or maybe I did IDK. 
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
                //an array list of all the com port options
                ArrayList<CommPortIdentifier> coms = new ArrayList();
                
                //if there are ports avaliable
                if(portEnum.hasMoreElements()){
                    //go through the list of ports
                    while(portEnum.hasMoreElements()){
                        //create a com port object or something
                        CommPortIdentifier cp = portEnum.nextElement();
                        log.write(cp.getName() + " - " + getPortTypeName(cp.getPortType()));
                        
                        //if the port is type serial, add it to the possible com port list. 
                        if(cp.getPortType() == CommPortIdentifier.PORT_SERIAL){
                            coms.add(cp);
                        }
                    }
                } else { //if theres no ports avaliable
                    log.crtError("Couldn't find an Arduino.");
                }
                /*
                if theres more then one com port in the list. bascially multiple arduinos or devices connected.
                for use on macs when itunes creates like 6 comm port devices for some reason.
                This code makes a popup come up and ask the user what com port to use for the arduino. 
                */
                if(coms.size() > 1){
                    log.crtError("More than one serial interface detected.");
                    //log.write("Make sure that itunes is completly shutdown. ");

                    String message = "Select the com port that you want. If your not sure check devices and printers.";
                    for(int i = 1; i < coms.size() + 1; i++){
                        message += "\n " + i + ": " + coms.get(i-1).getName();
                    }

                    if(comChoice == -1){
                        //the popup
                        comChoice = Integer.parseInt(JOptionPane.showInputDialog(message));
                        log.write("\n\n" + message);
                        log.write("Selected " + comChoice);
                        portID = CommPortIdentifier.getPortIdentifier(coms.get(comChoice - 1).getName());
                        com = portID.getName();
                    }

                } else if(coms.size() == 1){
                    portID = coms.get(0);
                    com = portID.getName();
                }
            } else {
                portID = CommPortIdentifier.getPortIdentifier(com);
            }
            
            //if connected
            if(portID != null){
                //open the port
                port = (SerialPort) portID.open(this.getClass().getName(), rate);
                //open the data streams
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
        
        //start the write and read loop
        if(connected){
            startLoop();
        }
    }
    
    /**
     * Write to the Arduino.
     * Pretty simple, write the output stream in the form of a byte array. 
     * @param s
     */
    public void write() {
        String w = getOutput();
        
        byte[] b = w.getBytes();
        
        if(connected && b.length == 25) {
            try {
                //write the bytes
                portOutStream.write(b);
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
        //start the string with T to verify its mine on the ardiuno
        String write = "T";
        
        //get the hex values from the direct write array
        for(int i = 0; i < 12; i++){
            write += writes[i].getValueHex();
        }
        output = write;
        return write;
    }
    
    /*
    I hate this code it took me so long to write this function. 
    basically this code gets the raw input from the arduino, splits it up into the respectable sensor values, and set the values. 
    
    Dont worry about this unless you need sensors on the robot. 
    */
    public void parseRead(){
        String line = readRaw().trim();
        if(line.length() > 1){
            for(int i = 1; i < line.length(); i++){
                if(line.substring(i, i+1).equals("$")){ //start of value
                    i++;
                    String sensName = "";
                    String sensValue = "";
                    
                    String sect = line.substring(i);
                    
                    if(sect.contains("$")){
                        sect = sect.substring(0, sect.indexOf("&"));
                        
                        //random crap gets added on to the message so using string == didn't work. this fixes that. 
                        try{
                            sensName = sect.substring(0, sect.indexOf("/")).trim().replaceAll("[\\W]|_", ""); //remove all non alphanumeric characters
                            sensValue = sect.substring(sect.indexOf("/") + 1).trim().replaceAll("[^\\d.]", ""); //remove all non numeric characters
                        } catch(Exception e){

                        }

                        if(sensName.length() > 0 && sensValue.length() > 0){
                            sensors.get(sensName).setValue(Double.parseDouble(sensValue));
                        }
                    }
                    
                    i += sect.length();
                }
            }
        }
    }
    
    //reads the bytes from the arduino input stream and converts it to a string
    public String readRaw(){
        String line = "didn't read :(";
        try {
            line = "";
            
            boolean done = false;
            while(!done){
                if(portInStream.available() > 0){
                    byte[] b = new byte[portInStream.available()];

                    for(int x = 0; x < portInStream.available() && x < b.length; x++){
                        if(x < portInStream.available())
                            b[x] = (byte) portInStream.read();
                    }

                    line += new String(b);
                    
                    /*
                    The string from the arduino isn't clean at all. Because it reads at a rate, sometimes you'll get half a message,
                    or a quarter message and a full message. Basically its messy. This basically checks that a message has a beginning and then a end. 
                    */
                    done = line.substring(0, line.length()/2).contains("`") && line.substring(line.length()/2 + 1, line.length()).contains("*");
                }
            }
            //if a message has a beginning and an end, cut the string there. 
            if(done){
                line = line.substring(line.indexOf("`"), line.indexOf("*", line.indexOf("`")));
            }
            rawInput = line;
            log.write("Read: " + line);
            return line;
        } catch (IOException ex) {
            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
        }
        //allways has to return something  ¯\_(ツ)_/¯ because this will fail often due to incomplete messages. 
        return line;
    }
    
    //sets the direct object to the port, replacing the one previously there. 
    public void setDirect(Direct d){
        writes[d.getPort() - 2] = d;
        log.write("Added a " + d.getName() + " to port " + d.getPort());
    }
    
    public void addSensor(Sensor s){
        sensors.put(s.getName(), s);
        log.write("Added sensor " + s.getName());
    }
    
    public void removeSensor(Sensor s){
        sensors.remove(s.getName());
    }
    
    //close the streams. closeInStream() used to be here but its not and I don't want to break it so...
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
    
    //the rate to loop the read and write at
    public void setLoopRate(int i){
        if(i >= 20){
            loopRate = i;
            log.write("Changed the loop rate to " + loopRate);
        }
    }
    
    public int getLoopRate(){
        return loopRate;
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
    
    public String getRawInput(){
        return rawInput.trim();
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
    
    //this starts the read and write loop. A different thread is created for each loop so you can multitask. 
    public void startLoop(){
        if(connected){
            new Thread(){
                public void run(){
                    while(true){
                        write();
                        try {
                            Thread.sleep(loopRate);

                        } catch (InterruptedException ex) {
                            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
            
            new Thread(){
                public void run(){
                    while(true){
                        parseRead();
                        try {
                            Thread.sleep(loopRate);

                        } catch (InterruptedException ex) {
                            Logger.getLogger(Arduino.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
        }
    }
}


