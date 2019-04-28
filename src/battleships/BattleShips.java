
package battleships;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class BattleShips extends JFrame {
    
    // BattleShipsApp Fields
    private ImageHolder imageHolder;    // Class containing all images.
    
    private TitlePanel titlePanel;   // JPanel containing title
    private PlayerBoard playerBoard;   // JPanel containing user game board.
    private OpponentBoard opponentBoard; // JPanel containing opponents game board.
    
    
    // Constructor
    public BattleShips() {    
        
        super("Battle Ships");      // Build JFrame titled Battle Ships
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        this.setLocationRelativeTo(null);   // Center frame on screen
        this.setResizable(false);       // Make frame size constant.
        this.setLayout(new GridBagLayout());  // Create GridBagLayout for the components
        this.addMouseListener(new MainMouseListener());   // Add a custom mouse listener for the game mouse events 
        imageHolder = new ImageHolder();        // Creating instances of the fields
        
        titlePanel = new TitlePanel();
        playerBoard = new PlayerBoard();
        opponentBoard = new OpponentBoard(playerBoard);
        
                
        // Add components to frame, and set their position with constraints. 
//      this.add(nameLabel);
        this.add(titlePanel, setConstraints(0, 0, 6, 1, GridBagConstraints.HORIZONTAL, 2));
        this.add(playerBoard, setConstraints(1, 1, 2, 5, GridBagConstraints.NONE, 30));
        this.add(opponentBoard, setConstraints(4, 1, 2, 5, GridBagConstraints.NONE, 30));
                
        
        // Pack the frame to its components, and make is visible
        this.pack();
        this.setVisible(true);
    }
    
    // Method for setting the component constraints needed by GridBagLayout more organized
    private GridBagConstraints setConstraints(int gx, int gy, int gw, int gh, int fill, int i) {
        GridBagConstraints out = new GridBagConstraints();
        out.fill = fill;
        out.gridx = gx;
        out.gridy = gy;
        out.gridwidth = gw;
        out.gridheight = gh;
        out.weightx = 0.5;
        out.insets = new Insets(i,i,i,i);
        return out;
    }
    
    // Inner class that implements MouseListener, with its methods.
    private class MainMouseListener implements MouseListener {
        
        public void mouseClicked(MouseEvent me) {}
        public void mousePressed(MouseEvent me) {}
        public void mouseEntered(MouseEvent me) {}
        public void mouseExited(MouseEvent me) {}
        
        // When right clicking, toggles the orientation of the ships when placing them
        public void mouseReleased(MouseEvent me) {
            if (SwingUtilities.isRightMouseButton(me)) {
                if (playerBoard.isPlacingShips()) {
                    playerBoard.toggleOrientation();
                    playerBoard.customCursor();
                }
            }
        }
        
    }
    
   
    public static void main(String[] args) {
        new BattleShips();   // Instance of our game
    }
}
