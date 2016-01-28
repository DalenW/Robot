package robot;

import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

public class Error {
    public Error(String e){
        JOptionPane.showMessageDialog(null, e, "ERROR", ERROR_MESSAGE);
    }
}
