package robot;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class Joystick {
    private String name;
    private boolean connected = false;
    
    private Controller[] device;
    private Controller controller;
    private Component[] components;
    private Component joystick;
    private Identifier ident;
    
    private boolean[] button = new boolean[32];
    
    /**
     * Connect to the joystick with name n.
     * @param n 
     */
    public Joystick(String n){
        name = n;
        device  = ControllerEnvironment.getDefaultEnvironment().getControllers();
        
        for(int i = 0; i < device.length; i++){
            if(device[i].getName().equals(name)){
                controller = device[i];
                components = controller.getComponents();
                connected = true;
                break;
            }
        }
        
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
        }
    }
    
    private void fetchButtons(boolean pressed){
        if(ident == Component.Identifier.Button._0){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._1){
            button[1] = pressed;
        } else {
            button[1] = false;
        }

        if(ident == Component.Identifier.Button._2){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._3){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._4){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._5){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._6){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._7){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._8){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._9){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._10){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._11){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._12){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._13){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._14){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._15){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._16){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._17){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._18){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._19){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._20){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._21){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._22){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._23){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._24){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._25){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._26){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._27){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._28){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._29){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._30){
            button[0] = pressed;
        } else {
            button[0] = false;
        }

        if(ident == Component.Identifier.Button._31){
            button[0] = pressed;
        } else {
            button[0] = false;
        }
    }
}
