package courseman2;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nguyen manh tien 3c14 on .
 */


public class LogoHelper extends JLabel{
    public LogoHelper(String label){
        setText(label);
        setFont(new Font("Serif", Font.BOLD, 18));
        setBackground(Color.orange);
        setForeground(Color.blue);
        setOpaque(true);
        setFocusable(false);
    }
}
