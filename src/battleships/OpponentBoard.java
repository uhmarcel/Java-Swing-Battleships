
package battleships;
import java.awt.Point;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class OpponentBoard extends GameBoard {
    
    private PlayerBoard playerBoard;  
    private boolean[][] shots; 
    
    public OpponentBoard(PlayerBoard playerBoard) {
        
        super();      
        
        this.playerBoard = playerBoard;  
        this.shots = new boolean[10][10];   
        addActionListener(new OpponentActionListener()); 
        
        generateRandomPositions();      
        
    }
    
    
    private class OpponentActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            JButton source = (JButton) ae.getSource();
            Point position = getButtonPosition(source);
            
            if (!playerBoard.isPlacingShips()){
                
                userShoot(position);
                opponentShoot(getNextShot());
                playerBoard.customCursor();
                
            }
        }    
    }
    

    private void userShoot(Point pos) {
        
        gridButtons[pos.y][pos.x].setEnabled(false);
        
        if (grid[pos.y][pos.x] != 0) {
            gridButtons[pos.y][pos.x].setBackground(GameBoard.SHOOT_COLOR);
            lives--;
            if(isShipFullyDestroyed(pos))
                showShip(pos);
            if (lives==0) {
                JOptionPane.showMessageDialog(null, "You win");
                System.exit(0);
            }
                
        }
        
    }
    
    
    // Method that returns a new shoot position that its not repeated
    private Point getNextShot() {
        Random r = new Random();
        while (true) {
            Point p = new Point(r.nextInt(10), r.nextInt(10));
            if (shots[p.y][p.x]==false) {
                shots[p.y][p.x]=true;
                return p;
            } 
        }
        
    }
    
    
    // Method that handles when pc shoots player
    private void opponentShoot(Point pos) {
        
        playerBoard.gridButtons[pos.y][pos.x].setEnabled(false);
        
        if (playerBoard.grid[pos.y][pos.x] != 0) {
            playerBoard.gridButtons[pos.y][pos.x].setBackground(GameBoard.SHOOT_COLOR);
            playerBoard.lives--;
            if (playerBoard.lives==0) {
                JOptionPane.showMessageDialog(null, "You Loose!");
                System.exit(0);
            }
        }
        
    }
    
    
    // Method used to show opponent ship when sinked 
    private void showShip(Point pos) {
        
        int shipId = grid[pos.y][pos.x];
        int orientation = shipId/100;
        int shipSize = (shipId%100)/10;
        int shipPart = (shipId%100)%10;
        
        Point p;
        if (orientation == 0) {
            p = new Point(pos.x-shipPart, pos.y);
            for (int i=0; i<shipSize; i++) {
                gridButtons[p.y][p.x+i].setIcon(ImageHolder.getImageByID(grid[p.y][p.x+i]));
            }
        }
        else if (orientation == 1) {
            p = new Point(pos.x, pos.y-shipPart);
            for (int i=0; i<shipSize; i++) {
                gridButtons[p.y+i][p.x].setIcon(ImageHolder.getImageByID(grid[p.y+i][p.x]));
            }
        }
    }
    
    
    // Method that places ships randomly on the board
    public void generateRandomPositions() {
        
        Random rand = new Random();
        
        while (isPlacingShips()) {
            
            int i = rand.nextInt(10);
            int n = rand.nextInt(6);
            this.orientation = rand.nextBoolean();

            if (this.orientation == HORIZONTAL)
                placeNextShip(new Point(n, i), false);
            else 
                placeNextShip(new Point(i, n), false);
        }
        this.orientation = HORIZONTAL;
    }
    
}
