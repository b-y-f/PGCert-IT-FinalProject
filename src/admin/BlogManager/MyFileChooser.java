package admin.BlogManager;

import javax.swing.*;
import java.io.File;

public class MyFileChooser {
    private final JFileChooser fc;

    MyFileChooser() {
        fc= new JFileChooser();

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("Open command cancelled by user.");
        }
    }

    public File getFile(){
        return fc.getSelectedFile();
    }
}
