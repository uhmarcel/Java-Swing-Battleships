
package battleships;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

class GameBoard extends JPanel {
    
    // GUI fields
    protected JButton[][] gridButtons;  // Buttons that make the main grid
    private JLabel[] letterAxis;  // The vertical axis besides the grid
    private JLabel[] numberAxis;  // The horizontal axis on top of the grid
    
    // Game fields
    protected int[][] grid;    // Array of booleans, hold if there is a ship at position
    protected static boolean orientation;  // Ship orientation when placing them 
    
    protected int[] shipsToPlace = {5, 4, 4, 3, 3, 2, 2, 2};
    protected int lives;
                // Array which holds ships that still need to be placed,
                // the integer value corresponds to the size of the ship
                // Lives is the int holding total ships left, when 0 game overs
    
    // Constants
    public static final boolean HORIZONTAL = true;  // Values for orientation
    public static final boolean VERTICAL = false;
            
    public static final int LARGE_SHIP = 5;     // Values of ships corresponding
    public static final int MEDIUM_SHIP = 4;    // to their size
    public static final int SMALL_SHIP = 3;
    public static final int TINY_SHIP = 2;    
    
    public static final Color SHOOT_COLOR = new Color(180, 0, 0); // color when shoot;
    
    
    // Constructor
    public GameBoard() {
        
        super();    // Create panel, set layout and initiate fields.
        this.setLayout(new GridBagLayout());
        this.grid = new int[10][10];    
        this.orientation = HORIZONTAL;    
        this.lives = calculateLives();
        
        buildGrid(); // build GUI
        
    }
    
    
    // Method in charge of building the GUI
    private void buildGrid() {

        Dimension TILE_SIZE = new Dimension(ImageHolder.TILE_SIZE, ImageHolder.TILE_SIZE);
        
        letterAxis = new JLabel[10];        
        numberAxis = new JLabel[10];
        
        // creates the horizontal axis
        for (int i=0; i<10; i++) {
            letterAxis[i] = new JLabel(String.copyValueOf(Character.toChars(65+i)) + "  ");
            letterAxis[i].setMinimumSize(TILE_SIZE);        
            this.add(letterAxis[i], setConstraints(0, i+1, 1, 1, 0));
        }
        
        // creates vertical axis
        for (int i=0; i<10; i++) {
            numberAxis[i] = new JLabel(Integer.toString(i+1));
            numberAxis[i].setMinimumSize(TILE_SIZE);
            this.add(numberAxis[i], setConstraints(i+1, 0, 1, 1, 0));
        }
        
        // creates grid of Jbuttons and add mouse listener
        gridButtons = new JButton[10][10];
        for (int y=0; y<10; y++) {
            for (int x=0; x<10; x++) {
                gridButtons[y][x] = new JButton();
                gridButtons[y][x].setPreferredSize(TILE_SIZE);
                gridButtons[y][x].addMouseListener(new BoardMouseListener());
                this.add(gridButtons[y][x], setConstraints(x+1, y+1, 1, 1, 0));    
            }
        }
    }
    
    // Method to add action listener to all grid Buttons
    public void addActionListener(ActionListener al) {
        for (int y=0; y<10; y++) {
            for (int x=0; x<10; x++) {
                gridButtons[y][x].addActionListener(al);
            }
        }
    }
    
    // inner class in charge or changing orientation when right click
    private class BoardMouseListener implements MouseListener {
        public void mouseReleased(MouseEvent me) {
            if (SwingUtilities.isRightMouseButton(me) && isPlacingShips()) {
                toggleOrientation();
                customCursor();
            }
        }
        public void mouseClicked(MouseEvent me) {}
        public void mousePressed(MouseEvent me) {}
        public void mouseEntered(MouseEvent me) {}
        public void mouseExited(MouseEvent me) {}
        
    }
    
    //Method that places a ship in a position pos, depending on next ship in int[] shipsToPlace
    public void placeNextShip(Point pos, boolean showShip) {
        
            if (checkShipPlacingPosition(pos)) {

            if (orientation == HORIZONTAL) {    
                for (int i=0; i<shipsToPlace[0]; i++) {
                    grid[pos.y][pos.x+i] = shipsToPlace[0]*10 + i;
                    if (showShip)
                        gridButtons[pos.y][pos.x+i].setIcon(ImageHolder.getImageByID(grid[pos.y][pos.x+i]));
                }

            }
            else if (orientation == VERTICAL) {     
                for (int i=0; i<shipsToPlace[0]; i++) {
                    grid[pos.y+i][pos.x] = 100+shipsToPlace[0]*10 + i;
                    if (showShip)
                        gridButtons[pos.y+i][pos.x].setIcon(ImageHolder.getImageByID(grid[pos.y+i][pos.x]));
                }
            }
            shipsToPlace = Arrays.copyOfRange(shipsToPlace, 1, shipsToPlace.length);
        }
    }
    
    // Method that checks if a position is suitable to place a ship, returns false if 
    // overlapping with another ship, or if the ship goes out of grid bounds
    private boolean checkShipPlacingPosition(Point position) {
        
        if (orientation == HORIZONTAL) {
            for (int i=0; i<shipsToPlace[0]; i++) {
                if ((position.x+i) >= 10)  //if out of bounds
                    return false;
                else if (grid[position.y][position.x+i] != 0) //if overlapping
                    return false;
            }       
        }
        else if (orientation == VERTICAL) {
            for (int i=0; i<shipsToPlace[0]; i++) { // if out of bounds
                if ((position.y+i) >= 10)
                    return false;
                else if (grid[position.y+i][position.x] != 0) // if overlapping
                    return false;
            }   
        }
        return true;
    }
    
    
    // Gets amount of lives according to ships placed
    private int calculateLives() {
        int out = 0;
        for (int shipLives: shipsToPlace)
            out += shipLives;
        return out;
    }
    
    
    // returns true if the ship was shoot in all positions.
    public boolean isShipFullyDestroyed(Point pos) {
        
        int shipId = grid[pos.y][pos.x];
        int orientation = shipId/100;
        int shipSize = (shipId%100)/10;
        int shipPart = (shipId%100)%10;
        
        Point p;
               
        if (shipId == 0) 
            return false;
        
        if (orientation == 0) {
            p = new Point(pos.x-shipPart, pos.y);
            for (int i=0; i<shipSize; i++) {
                if (!gridButtons[p.y][p.x+i].getBackground().equals(GameBoard.SHOOT_COLOR))
                    return false;
            }
        }
        else if (orientation == 1) {
            p = new Point(pos.x, pos.y-shipPart);
            for (int i=0; i<shipSize; i++) {
                if (!gridButtons[p.y+i][p.x].getBackground().equals(GameBoard.SHOOT_COLOR))
                    return false;
            }
        }
        return true;
    }
    
    
    // set constraints for the GridBagLayout
    private GridBagConstraints setConstraints(int gx, int gy, int gw, int gh, int fill) {
        GridBagConstraints out = new GridBagConstraints();
        out.fill = fill;
        out.gridx = gx;
        out.gridy = gy;
        out.gridwidth = gw;
        out.gridheight = gh;
        out.weightx = 0;
        return out;
    }
    
    // Accepts a Button and returns which is its position on the grid
    public Point getButtonPosition(JButton source) {
        int x = 0, y = 0;   
        for (int j=0; j<10; j++) {
            for (int i=0; i<10; i++) {
                if (source.equals(gridButtons[j][i])) {
                    x = i;      
                    y = j;
                }
            }
        }
        return new Point(x,y);
    }
    
    // switches the orientation
    public void toggleOrientation() {
        this.orientation = !orientation;
    }
    
    // true while placing ships
    public boolean isPlacingShips() {
        return (shipsToPlace.length != 0);
    }
    
   
    // Setting cursors
    public void customCursor() {
        
        if(isPlacingShips()) {
            ImageIcon selected;

            if (orientation == HORIZONTAL) {
                switch(shipsToPlace[0]) {
                    case 5:
                        selected = ImageHolder.largeShipFull;
                    break;    
                    case 4:
                        selected = ImageHolder.mediumShipFull;
                    break;   
                    case 3:
                        selected = ImageHolder.smallShipFull;
                    break;   
                    case 2:
                        selected = ImageHolder.tinyShipFull;
                    break;   
                    default:
                        selected = null;
                    break;   
                }
            }
            else {
                switch(shipsToPlace[0]) {
                    case 5:
                        selected = ImageHolder.largeShipFullV;
                    break;    
                    case 4:
                        selected = ImageHolder.mediumShipFullV;
                    break;   
                    case 3:
                        selected = ImageHolder.smallShipFullV;
                    break;   
                    case 2:
                        selected = ImageHolder.tinyShipFullV;
                    break;   
                    default:
                        selected = null;
                    break;   
                }
            }

            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Point hotspot = new Point(0,0);
            Cursor cursor = toolkit.createCustomCursor(selected.getImage(), hotspot, "ship");
            setCursor(cursor);     
        }
        else {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
