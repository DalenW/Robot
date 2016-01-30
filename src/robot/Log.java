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
    
    /**
     * Create a new log. n is the name of the file. 
     * @param n 
     */
    public Log(String n){
        logFile = new File("Logs/" + n + ".txt");
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
        Error er = new Error(this, e);
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
}
