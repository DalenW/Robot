package robot;

import java.util.ArrayList;

public class Robot {
    private static String version = "0.3.7";
    private static ArrayList<Motor> motors = new ArrayList();
    private static ArrayList<Servo> servos = new ArrayList();
    private static ArrayList<Direct> directs = new ArrayList();
    
    public static String getVersion(){
        return version;
    }
    
    public static void addMotor(Motor m){
        motors.add(m);
    }
    
    public static void removeMotor(Motor m){
        motors.remove(m);
    }
    
    public static ArrayList<Motor> getMotors(){
        return motors;
    }
    
    public static void addDirect(Direct d){
        directs.add(d);
    }
    
    public static void removeDirect(Direct d){
        directs.remove(d);
    }
    
    public static ArrayList<Direct> getAllWrites(){
        return directs;
    }
    
    public static void addServo(Servo s){
        servos.add(s);
    }
    
    public static void removeServo(Servo s){
        servos.remove(s);
    }
    
    public static ArrayList<Servo> getServos(){
        return servos;
    }
    
   
    
    

}
