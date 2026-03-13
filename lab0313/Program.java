import javax.swing.JFrame;

public class Program extends JFrame {
    public Program() {
        this.setSize(400, 400);
        this.add(new Screen(30));
        this.setVisible(true);
    }
    public static void main(String[] args) {
        Program a = new Program();
    }
}