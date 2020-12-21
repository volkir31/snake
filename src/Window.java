import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        setTitle("Snake");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(526, 551);
        setLocation(400, 400);
        add(new GameField());
        setVisible(true);
    }
    public static void main(String[] args) {
        Window win = new Window();
    }
}
