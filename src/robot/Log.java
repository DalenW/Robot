package robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private PrintWriter log;
    private File logFile;
    private String name;
    
    /**
     * Create a new log. n is the name of the file. 
     * @param n 
     */
    public Log(String n){
        name = n;
        
        ClearLogs.clearLogs(new File("Logs"));
        
        int i = 1;
        logFile = new File("Logs/" + name + " " + i + ".txt");
        
        while(logFile.exists()){
            name = name + " " + i;
            logFile = new File("Logs/" + name + ".txt");
            i++;
        }
        
        logFile.getParentFile().mkdirs();
        try {
            log = new PrintWriter(logFile, "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Write to the log
     * @param s 
     */
    public void write(String s){
        log.println(date() + " " + time() + "::" + millis() + " --> " + s);
        log.flush();
    }
    
    public void Error(String e){
        write(logError(e));
    }
    
    public void crtError(String e){
        Error(e);
        Error er = new Error(e);
    }
    
    private String logError(String s){
        return "ERROR: " + s;
    }
    private String date(){
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    private String time(){
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public String dateTime(){
        return date() + " " + time();
    }
    
    private long millis(){
        long millis = System.currentTimeMillis() % 1000;
        return millis;
    }
    
    /**
     * Returns the log file
     * @return 
     */
    public File getFile(){
        return logFile;
    }
    
    @Override
    public String toString(){
        return "Log" +
                "\n Name: " + name +
                "\n Path: " + logFile.getPath();
                
    }
}
