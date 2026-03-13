import javax.swing.JComponent;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Screen extends JComponent implements MouseMotionListener, MouseListener {
    public void mouseMoved(MouseEvent e) {
    
    }
    public void mouseDragged(MouseEvent e) { 
        int x = e.getX(), y = e.getY();
        if (chosenOne != null) {
            this.chosenOne.move(x, y);
            this.repaint(); 
        }
    }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    Circle chosenOne;
    public void mousePressed(MouseEvent e) { 
        int x = e.getX(), y = e.getY();
        chosenOne = null;
        for (Circle c : this.circles) {
            if (c.inside(x, y)) {
                chosenOne = c;
            }
        }
    }
    public void mouseReleased(MouseEvent e) { }
    public void mouseClicked(MouseEvent e) { }
    ArrayList<Circle> circles;
    public Screen(int howMany) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.circles = new ArrayList<Circle>();
        for (int i = 0; i < howMany; i++) {
            this.circles.add(new Circle((int) (Math.random() * 300 + 50), 
                                        (int) (Math.random() * 300 + 50), 
                                        (int) (Math.random() *  40 + 10))); 
        }       
    }
    public void paintComponent(Graphics g) {
        for (Circle c : this.circles) {
            c.draw(g);
        }
    }
}