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
    private boolean main = false;
    
    /**
     * Create a new log. n is the name of the file. 
     * @param n 
     */
    public Log(String n){
        name = n;
        
        ClearLogs.clearLogs(new File("Logs"));
        
        if(!name.equals("###")){
            int x = 2;
            for(int i = 0; i < Robot.getLogs().size(); i++){
                if(Robot.getLogs().get(i).getName().equals(name)){
                    name = n + " " + x;
                    x++;
                }
            }
            Robot.add(this);

            logFile = new File("Logs/" + name + ".txt");

            logFile.getParentFile().mkdirs();
            
            try {
                log = new PrintWriter(logFile, "UTF-8");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }
    
    public Log(String n, Boolean m){
        this(n);
        main = m;
    }
    
    /**
     * Write to the log
     * @param s 
     */
    public void write(String s){
        if(!name.equals("###")){
            String w = date() + " " + time() + "::" + millis() + " --> " + s;
            log.println(w);
            log.flush();
            if(main){
                System.out.println(w);
            }
        }
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
    
    public String getName(){
        return name;
    }
    
    @Override
    public String toString(){
        return "Log" +
                "\n Name: " + name +
                "\n Path: " + logFile.getPath();
                
    }
}
