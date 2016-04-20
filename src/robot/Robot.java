package robot;

import java.util.ArrayList;
import robot.devices.Arduino;
import robot.devices.Camera;
import robot.devices.Joystick;
import robot.read.Sensor;
import robot.util.Log;
import robot.write.Direct;
import robot.write.Motor;
import robot.write.Servo;

public class Robot {
    private static String version = "0.3.7";
    private static ArrayList<Motor> motors = new ArrayList();
    private static ArrayList<Servo> servos = new ArrayList();
    private static ArrayList<Direct> directs = new ArrayList();
    private static ArrayList<Camera> cameras = new ArrayList();
    private static ArrayList<Arduino> arduinos = new ArrayList();
    private static ArrayList<Joystick> joysticks = new ArrayList();
    private static ArrayList<Log> logs = new ArrayList();
    private static ArrayList<Sensor> sensors = new ArrayList();
    private static ArrayList all = new ArrayList();
    
    public static String getVersion(){
        return version;
    }
    
    //motors
    public static void add(Motor m){
        motors.add(m);
        all.add(m);
    }
    
    public static void remove(Motor m){
        motors.remove(m);
        all.remove(m);
    }
    
    public static ArrayList<Motor> getMotors(){
        return motors;
    }
    
    //directs
    public static void add(Direct d){
        directs.add(d);
        all.add(d);
    }
    
    public static void remove(Direct d){
        directs.remove(d);
        all.remove(d);
    }
    
    public static ArrayList<Direct> getAllWrites(){
        return directs;
    }
    
    //servos
    public static void add(Servo s){
        servos.add(s);
        all.add(s);
    }
    
    public static void remove(Servo s){
        servos.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Servo> getServos(){
        return servos;
    }
    
    //cameras
    public static void add(Camera s){
        cameras.add(s);
        all.add(s);
    }
    
    public static void remove(Camera s){
        cameras.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Camera> getCameras(){
        return cameras;
    }
    
    //arduinos
    public static void add(Arduino s){
        arduinos.add(s);
        all.add(s);
    }
    
    public static void remove(Arduino s){
        arduinos.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Arduino> getArduinos(){
        return arduinos;
    }
    
    //joysticks
    public static void add(Joystick s){
        joysticks.add(s);
        all.add(s);
    }
    
    public static void remove(Joystick s){
        joysticks.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Joystick> getJoysticks(){
        return joysticks;
    }
    
    public static void add(Log s){
        logs.add(s);
        all.add(s);
    }
    
    public static void remove(Log s){
        logs.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Log> getLogs(){
        return logs;
    }
    
    public static void add(Sensor s){
        sensors.add(s);
        all.add(s);
    }
    
    public static void remove(Sensor s){
        sensors.remove(s);
        all.remove(s);
    }
    
    public static ArrayList<Sensor> getSensors(){
        return sensors;
    }
    
    //all
    public static ArrayList getAll(){
        return all;
    }
}
