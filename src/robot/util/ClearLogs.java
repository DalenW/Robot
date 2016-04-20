package robot.util;

import java.io.File;

public class ClearLogs {
    private static boolean cleared = false;
    
    public static void clearLogs(File dir){
        if(!cleared){
            if(dir.exists()){
                if(dir.listFiles() != null){
                    File[] files = dir.listFiles();
                    
                    for(int i = 0; i < files.length; i++){
                        if(files[i].isDirectory()){
                            clearLogs(files[i]);
                        } else {
                            files[i].delete();
                        }
                    }
                }
                cleared = true;
            }
        }
    }

}
