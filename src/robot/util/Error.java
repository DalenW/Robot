package robot.util;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class Error {
    public Error(String e){
        System.out.println(e);
        JOptionPane.showMessageDialog(null, e, "ERROR", ERROR_MESSAGE);
    }   
}
