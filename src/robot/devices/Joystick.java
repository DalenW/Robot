package robot.devices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import robot.Robot;
import robot.util.Log;

public class Joystick {
    private String name;
    private boolean connected = false, wasConnected = false, polling = false;
    private Log log;
    private Controller[] device;
    private Controller controller;
    private Component[] components;
    private Component joystick;
    private Identifier ident;
    private float x, y, z, slider, rotation, hatswitch;
    private int deviceIndex = -1;
    private HashMap<Component.Identifier, Float> customValues;
    private ArrayList<Component.Identifier> customValueNames;
    private boolean[] button = new boolean[32], hatSwitchArr = new boolean[9];
    
    /**
     * Connect to the joystick with name n.
     * @param n 
     */
    public Joystick(String n){
        name = n;
        log = new Log(name);
        customValues = new HashMap();
        customValueNames = new ArrayList();
        Robot.add(this);
        log.write("Created joystick " + name);
        connect();
    }
    
    /**
     * Connect to the joystick. 
     */
    private void connect(){
        log.write("Connecting to the joystick.");
        device  = ControllerEnvironment.getDefaultEnvironment().getControllers();
        ArrayList<Integer> indexes = new ArrayList();
        
        for(int i = 0; i < device.length; i++){
            log.write("Found a controller called " + device[i].getName() + ".");
            if(device[i].getName().equals(name)){
                indexes.add(i);
            }
        }
        int sameNames = 0;
        for(Joystick j : Robot.getJoysticks()) if(j.getName().equals(name) && j.connected) sameNames++;
        
        if(sameNames == indexes.size()) log.crtError("No joysticks with this name left to connect to!");
        else if(indexes.size() == 1) connectJoystick(indexes.get(0));
        else if(indexes.isEmpty()) log.crtError("Couldn't find joystick!");
        else {
            int i = 0;
            for(Joystick j : Robot.getJoysticks()){
                if(j.getName().equals(name) && j.connected){
                    if(indexes.get(i) > j.getDeviceIndex()) connectJoystick(indexes.get(i));
                    else i++;
                }
            }
        }
        
        if(wasConnected && !connected){
            try { Thread.sleep(100); }
            catch (InterruptedException ex){ Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);}
            reconnect();
        }
        loop();
    }
    
    private void connectJoystick(int i){
        controller = device[i];
        components = controller.getComponents();
        deviceIndex = i;
        connected = true;
        wasConnected = true;
        log.write("Connected to the joystick.");
    }
    
    /**
     * Reconnect to the joystick.
     */
    public void reconnect(){
        connected = false;
        log.write("Reconnecting.");
        connect();
    }
    
    private void loop(){
        log.write("Beginning poll loop.");
        new Thread(){
            public void run(){
                while(true){
                    if(!controller.poll()){
                        polling = false;
                        log.crtError("POLL: Disconnected from " + name + ".");
                        reconnect();
                        break;
                    } else {
                        polling = true;
                        bind();
                    }
                    try { Thread.sleep(50); }
                    catch (InterruptedException ex){ Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);}
                }
            }
        }.start();
        log.write("Succesfully created poll loop.");
    }
    
    private void bind(){
        for(int i = 0; i < components.length; i++){
            joystick = components[i];
            ident = joystick.getIdentifier();
            
            if(joystick.getName().contains("Button")) fetchButtons(!(joystick.getPollData() == 0f));
            else {
                fetchHatSwitch();
                //fetchAxis();
            }
            fetchAxis();
            fetchCustom();
        }
    }
    
    private void fetchButtons(boolean pressed){
        if(ident == Component.Identifier.Button._0) button[0] = pressed;
        if(ident == Component.Identifier.Button._1) button[1] = pressed;
        if(ident == Component.Identifier.Button._2) button[2] = pressed;
        if(ident == Component.Identifier.Button._3) button[3] = pressed;
        if(ident == Component.Identifier.Button._4) button[4] = pressed;
        if(ident == Component.Identifier.Button._5) button[5] = pressed;
        if(ident == Component.Identifier.Button._6) button[6] = pressed;
        if(ident == Component.Identifier.Button._7) button[7] = pressed;
        if(ident == Component.Identifier.Button._8) button[8] = pressed;
        if(ident == Component.Identifier.Button._9) button[9] = pressed;
        if(ident == Component.Identifier.Button._12)button[12] = pressed;
        if(ident == Component.Identifier.Button._13)button[13] = pressed;
        if(ident == Component.Identifier.Button._14)button[14] = pressed;
        if(ident == Component.Identifier.Button._15)button[15] = pressed;
        if(ident == Component.Identifier.Button._16)button[16] = pressed;
        if(ident == Component.Identifier.Button._17)button[17] = pressed;
        if(ident == Component.Identifier.Button._18)button[18] = pressed;
        if(ident == Component.Identifier.Button._19)button[19] = pressed;
        if(ident == Component.Identifier.Button._20)button[20] = pressed;
        if(ident == Component.Identifier.Button._21)button[21] = pressed;
        if(ident == Component.Identifier.Button._22)button[22] = pressed;
        if(ident == Component.Identifier.Button._23)button[23] = pressed;
        if(ident == Component.Identifier.Button._24)button[24] = pressed;
        if(ident == Component.Identifier.Button._25)button[25] = pressed;
        if(ident == Component.Identifier.Button._26)button[26] = pressed;
        if(ident == Component.Identifier.Button._27)button[27] = pressed;
        if(ident == Component.Identifier.Button._28)button[28] = pressed;
        if(ident == Component.Identifier.Button._29)button[29] = pressed;
        if(ident == Component.Identifier.Button._30)button[30] = pressed;
        if(ident == Component.Identifier.Button._31)button[31] = pressed;  
    }
    
    private void fetchHatSwitch(){
        if(ident == Component.Identifier.Axis.POV) hatswitch = joystick.getPollData();
        
        for(int i = 0; i < hatSwitchArr.length; i++) hatSwitchArr[i] = false;
        
        if(hatswitch == Component.POV.CENTER) hatSwitchArr[0] = true; //neutral
        if(hatswitch == Component.POV.UP) hatSwitchArr[1] = true; //north
        if(hatswitch == Component.POV.UP_RIGHT) hatSwitchArr[2] = true; //northeast
        if(hatswitch == Component.POV.RIGHT) hatSwitchArr[3] = true; //east
        if(hatswitch == Component.POV.DOWN_RIGHT) hatSwitchArr[4] = true; //southeast
        if(hatswitch == Component.POV.DOWN) hatSwitchArr[5] = true; //south
        if(hatswitch == Component.POV.DOWN_LEFT) hatSwitchArr[6] = true; //southwest
        if(hatswitch == Component.POV.LEFT) hatSwitchArr[7] = true; //west
        if(hatswitch == Component.POV.UP_LEFT) hatSwitchArr[8] = true; //northwest        
    }
    
    private void fetchAxis(){
        if(joystick.isAnalog()){
            float axis = joystick.getPollData();
            
            if(ident == Component.Identifier.Axis.X) x = axis;
            if(ident == Component.Identifier.Axis.Y) y = axis;
            if(ident == Component.Identifier.Axis.Z) z = axis;
            if(ident == Component.Identifier.Axis.SLIDER) slider = axis;
            if(joystick.getName().equals("Z Rotation")) rotation = axis;
        }
    }
    
    private void fetchCustom(){
        for(int i = 0; i < customValueNames.size(); i++) if(ident == customValueNames.get(i)) customValues.replace(customValueNames.get(i), joystick.getPollData());
    }
    
    public float getValue(Component.Identifier c){
        if(customValues.containsKey(c)){
            return customValues.get(c);
        } else {
            customValues.put(c, 0f);
            customValueNames.add(c);
            return getValue(c);
        }
    }
    
    /**
     * Returns the array of button values.
     * @return 
     */
    public boolean[] getButtonArray(){return button;}
    /**
     * Returns the button value of index i
     * @param i
     * @return 
     */
    public boolean getButton(int i){return button[i];}
    /**
     * Returns x.
     * @return 
     */
    public float getX(){return x;}
    /**
     * Returns y.
     * @return 
     */
    public float getY(){return -y;}
    /**
     * Returns z.
     * @return 
     */
    public float getZ(){return z;}
    /**
     * Returns rotation.
     * @return 
     */
    public float getRotation(){return rotation;}
    /**
     * Returns slider.
     * @return 
     */
    public float getSlider(){return slider;}
    /**
     * Returns the hat switch.
     * @return 
     */
    public float getHatSwitchValues(){return hatswitch;}
    /**
     * Returns a boolean array of the hatswitch. This is what each array index means. 
     * 0 = neutral
     * 1 = north
     * 2 = northeast
     * 3 = east
     * 4 = southeast
     * 5 = south
     * 6 = southwest
     * 7 = west
     * 8 = northwest
     * @return 
     */
    public boolean[] getHatSwitch(){return hatSwitchArr;}
    public boolean isConnected(){return connected;}
    public boolean wasConnected(){return wasConnected;}
    public String getName(){return name;}
    public int getDeviceIndex(){return deviceIndex;}
}