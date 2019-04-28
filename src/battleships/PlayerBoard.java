
package battleships;
import static battleships.GameBoard.HORIZONTAL;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;


public class PlayerBoard extends GameBoard {
    
    public PlayerBoard() {
        
        super();        // Create GameBoard object
        addActionListener(new PlayerActionListener());  // Add own player ActionListener
        customCursor();     // Set cursor to ship type
        
    }
    
    
    // Own inner class ActionListener for PlayerBoard class
    private class PlayerActionListener implements ActionListener {
        
        public void actionPerformed(ActionEvent ae) {        
            JButton source = (JButton) ae.getSource();
            Point position = getButtonPosition(source);
            
            if (isPlacingShips())              
                placeNextShip(position, true);    
        }  
    } 
}
