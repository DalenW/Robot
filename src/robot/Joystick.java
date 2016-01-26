package robot;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Joystick {
    private String name;
    
    private Controller[] device;
    private Controller controller;
    private Component[] components;
    
    public Joystick(String n){
        name = n;
        device  = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        for(int i = 0; i < device.length; i++){
            if(device[i].getName().equals(name)){
                controller = device[i];
                components = controller.getComponents();
                break;
            }
        }
        
    }
}
