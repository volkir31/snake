import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
    private final int SIZE = 480;
    private final int DOT_SIZE = 16;
    private final int COUNT_DOT = 900;
    private Image dot;
    private Image apple;
    private int apple_x;
    private int apple_y;
    private int[] x = new int[COUNT_DOT];
    private int[] y = new int[COUNT_DOT];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;


    public GameField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new PressKeyListener());
        setFocusable(true);

    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(100, this);
        timer.start();
        createApple();
    }

    public void createApple(){
        apple_x = new Random().nextInt(30) * DOT_SIZE;
        apple_y = new Random().nextInt(30) * DOT_SIZE;

    }

    public void loadImages(){
        ImageIcon img_apple = new ImageIcon("apple.png");
        apple = img_apple.getImage();
        ImageIcon img_dot = new ImageIcon("dot.png");
        dot = img_dot.getImage();

    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        }
        if(up){
            y[0] -= DOT_SIZE;
        }
        if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple(){
        if(x[0] == apple_x && y[0] == apple_y){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
        if (x[0] > SIZE){
            inGame = false;
        }
        if (y[0] > SIZE){
            inGame = false;
        }
        if (x[0] < 0){
            inGame = false;
        }
        if (y[0] < 0){
            inGame = false;
        }

    }

    class PressKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) && !right){
                left = true;
                up = false;
                down = false;
            }
            if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && !left){
                right = true;
                up = false;
                down = false;
            }
            if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && !up){
                left = false;
                right = false;
                down = true;
            }
            if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_W) && !down){
                left = false;
                right = false;
                up = true;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(apple, apple_x, apple_y,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        else{
            String str = "Game Over!";
            g.setColor(Color.white);
            g.drawString(str,SIZE/2 - 10, SIZE/2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
}
