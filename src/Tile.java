import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Tile {

    private int id;
    private JLabel label;
    MemoryGameView parent;

    public Tile(MemoryGameView parent, int id) {
        this.parent = parent;
        this.id = id;
        //Someone else uses this tile, ~~
        label = new JLabel();
        label.setPreferredSize(new Dimension(100,100));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEtchedBorder());
        label.setBackground(Color.black);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

                parent.tileClicked(id);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    public int getId() {
        return id;
    }

    public JLabel getLabel() {
        return label;
    }
}
