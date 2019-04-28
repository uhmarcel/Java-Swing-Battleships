
package battleships;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

class TitlePanel extends JPanel {
    
    //  Fields
    private JLabel titleLabel;
    private JLabel subtitleLabel;
    
    public TitlePanel() {
        
        super();    // Create panel
        this.setLayout(new BorderLayout()); // set a borderLayout for the components
        this.setBorder(BorderFactory.createEtchedBorder());  // create a border for the panel
        
        titleLabel = new JLabel("BattleShips");     // create title label
        titleLabel.setFont(new Font("Calibri", 20,20));     // change the font to calibri size 20
        titleLabel.setHorizontalAlignment(JLabel.CENTER);   // center the title
        
        subtitleLabel = new JLabel("Place your ships on the field, once selected left click to place, and right click to rotate."); 
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER); // create and center subtitle mesagge
        
        this.add(titleLabel, BorderLayout.NORTH);   // add the title to the north of panel
        this.add(subtitleLabel, BorderLayout.CENTER);   // add subtitle to center of panel
        
    }
}
