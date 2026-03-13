import java.awt.Graphics;
import java.awt.Color;

public class Circle {
    int radius;
    int x, y;
    Color color;
    public Circle(int x, int y, int radius) { 
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = new Color((float)(Math.random() * 0.5 + 0.5), 
                               (float)(Math.random() * 0.5 + 0.5),
                               (float)(Math.random() * 0.5 + 0.5)); 
    }
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(this.x - this.radius, this.y - this.radius, 2 * this.radius, 2 * this.radius);
        g.setColor(Color.BLACK);
        g.drawOval(this.x - this.radius, this.y - this.radius, 2 * this.radius, 2 * this.radius);
    }
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean inside(int x, int y) {
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) <= this.radius;
    }
}