package robot;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Joystick {
    private String name;
    private boolean connected = false, wasConnected = false;
    private Log log;
    
    private Controller[] device;
    private Controller controller;
    private Component[] components;
    private Component joystick;
    private Identifier ident;
    
    private float x, y, z, slider, rotation, hatswitch;
    
    private boolean[] button = new boolean[32];
    private boolean[] hatSwitchArr = new boolean[9];
    
    /**
     * Connect to the joystick with name n.
     * @param n 
     */
    public Joystick(String n){
        name = n;
        log = new Log(name);
    }
    
    /**
     * Connect to the joystick. 
     */
    public void connect(){
        //System.out.println("Connecting to joystick");
        device  = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        log.write("Connecting to the joystick.");
        
        for(int i = 0; i < device.length; i++){
            log.write("Found a controller called " + device[i].getName() + ".");
            if(device[i].getName().equals(name)){
                log.write("Found the joystick " + device[i].getName() + ".");
                controller = device[i];
                components = controller.getComponents();
                connected = true;
                i = device.length;
            }
            
        }
        
        if(connected){
            log.write("Found the joystick.");
            wasConnected = true;
            loop();
        } else if(wasConnected){
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);
            }
            reconnect();
            
        } else {
           log.crtError("Couldn't find the joystick " + name + ".");
        }
    }
    
    private void loop(){
        log.write("Beginning poll loop.");
        new Thread(){
            public void run(){
                while(true){
                    if(!controller.poll()){
                        log.crtError("POLL: Disconnected from " + name + ".");
                        reconnect();
                        break;
                    }
                    bind();
                    
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Joystick.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        log.write("Succesfully created poll loop.");
    }
    
    private void bind(){
        for(int i = 0; i < components.length; i++){
            joystick = components[i];
            ident = joystick.getIdentifier();
            
            //buttons
            if(joystick.getName().contains("Button")){
                boolean pressed;
                
                if(joystick.getPollData() == 0.0f){
                    pressed = false;
                    fetchButtons(pressed);
                } else {
                    pressed = true;
                    fetchButtons(pressed);
                }  
            }
            
            //hat switch
            fetchHatSwitch();
            
            //axis
            fetchAxis();
        }
    }
    
    private void fetchButtons(boolean pressed){
        if(ident == Component.Identifier.Button._0){
            button[0] = pressed;
        }
        
        if(ident == Component.Identifier.Button._1){
            button[1] = pressed;
        }

        if(ident == Component.Identifier.Button._2){
            button[2] = pressed;
        }

        if(ident == Component.Identifier.Button._3){
            button[3] = pressed;
        }

        if(ident == Component.Identifier.Button._4){
            button[4] = pressed;
        }

        if(ident == Component.Identifier.Button._5){
            button[5] = pressed;
        }

        if(ident == Component.Identifier.Button._6){
            button[6] = pressed;
        }

        if(ident == Component.Identifier.Button._7){
            button[7] = pressed;
        }

        if(ident == Component.Identifier.Button._8){
            button[8] = pressed;
        }

        if(ident == Component.Identifier.Button._9){
            button[9] = pressed;
        }

        if(ident == Component.Identifier.Button._10){
            button[10] = pressed;
        }

        if(ident == Component.Identifier.Button._11){
            button[11] = pressed;
        }

        if(ident == Component.Identifier.Button._12){
            button[12] = pressed;
        }

        if(ident == Component.Identifier.Button._13){
            button[13] = pressed;
        }

        if(ident == Component.Identifier.Button._14){
            button[14] = pressed;
        }

        if(ident == Component.Identifier.Button._15){
            button[15] = pressed;
        }

        if(ident == Component.Identifier.Button._16){
            button[16] = pressed;
        }

        if(ident == Component.Identifier.Button._17){
            button[17] = pressed;
        }

        if(ident == Component.Identifier.Button._18){
            button[18] = pressed;
        }

        if(ident == Component.Identifier.Button._19){
            button[19] = pressed;
        }

        if(ident == Component.Identifier.Button._20){
            button[20] = pressed;
        }

        if(ident == Component.Identifier.Button._21){
            button[21] = pressed;
        }

        if(ident == Component.Identifier.Button._22){
            button[22] = pressed;
        }

        if(ident == Component.Identifier.Button._23){
            button[23] = pressed;
        }

        if(ident == Component.Identifier.Button._24){
            button[24] = pressed;
        }

        if(ident == Component.Identifier.Button._25){
            button[25] = pressed;
        }

        if(ident == Component.Identifier.Button._26){
            button[26] = pressed;
        }

        if(ident == Component.Identifier.Button._27){
            button[27] = pressed;
        }
        if(ident == Component.Identifier.Button._28){
            button[28] = pressed;
        }

        if(ident == Component.Identifier.Button._29){
            button[29] = pressed;
        }

        if(ident == Component.Identifier.Button._30){
            button[30] = pressed;
        }

        if(ident == Component.Identifier.Button._31){
            button[31] = pressed;
        }
    }
    
    private void fetchHatSwitch(){
        if(ident == Component.Identifier.Axis.POV){
            hatswitch = joystick.getPollData();
        }
        
        for(int i = 0; i < hatSwitchArr.length; i++){
            hatSwitchArr[i] = false;
        }
        
        //neutral
        if(hatswitch == 0.0){
            hatSwitchArr[0] = true;
        }
        //north
        if(hatswitch == 0.25){
            hatSwitchArr[1] = true;
        }
        //northeast
        if(hatswitch == 0.375){
            hatSwitchArr[2] = true;
        }
        //east
        if(hatswitch == 0.5){
            hatSwitchArr[3] = true;
        }
        //southeast
        if(hatswitch == 0.625){
            hatSwitchArr[4] = true;
        }
        //south
        if(hatswitch == 0.75){
            hatSwitchArr[5] = true;
        }
        //southwest
        if(hatswitch == 0.875){
            hatSwitchArr[6] = true;
        }
        //west
        if(hatswitch == 1.0){
            hatSwitchArr[7] = true;
        }
        //northwest
        if(hatswitch == 0.125){
            hatSwitchArr[8] = true;
        }
                
    }
    
    private void fetchAxis(){
        if(joystick.isAnalog()){
            float axis = joystick.getPollData();
            
            if(ident == Component.Identifier.Axis.X){
                x = axis;
            }
            
            if(ident == Component.Identifier.Axis.Y){
                y = axis;
            }
            
            if(ident == Component.Identifier.Axis.Z){
                z = axis;
            }
            
            if(ident == Component.Identifier.Axis.SLIDER){
                slider = axis;
            }
            
            if(joystick.getName().equals("Z Rotation")){
                rotation = axis;
            }
        }
    }
    
    /**
     * Returns the array of button values.
     * @return 
     */
    public boolean[] getButtonArray(){
        return button;
    }
    
    /**
     * Returns the button value of index i
     * @param i
     * @return 
     */
    public boolean getButton(int i){
        return button[i];
    }
    
    /**
     * Returns x.
     * @return 
     */
    public float getX(){
        return x;
    }
    
    /**
     * Returns y.
     * @return 
     */
    public float getY(){
        return -y;
    }
    
    /**
     * Returns z.
     * @return 
     */
    public float getZ(){
        return z;
    }
    
    /**
     * Returns rotation.
     * @return 
     */
    public float getRotation(){
        return rotation;
    }
    
    /**
     * Returns slider.
     * @return 
     */
    public float getSlider(){
        return slider;
    }
    
    /**
     * Returns the hat switch.
     * @return 
     */
    public float getHatSwitchValues(){
        return hatswitch;
    }
    
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
    public boolean[] getHatSwitch(){
        return hatSwitchArr;
    }
    
    /**
     * Reconnect to the joystick.
     */
    public void reconnect(){
        connected = false;
        log.write("Reconnecting.");
        connect();
    }
    
    @Override
    public String toString(){
        return "Joystick" +
                "\n Name: " + name +
                "\n Connected: " + connected;
        
        
    }
}
