import java.awt.Graphics;
import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.desktop.ScreenSleepEvent;
import java.util.Random;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 20; //This is how big we want the items.
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){

        if(running) {
            //Guidelines for the snake
            /*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                //We draw the lines through the X unit
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                //We draw the lines through the Y unit
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }*/

            //Color of the apple
            g.setColor(new Color(0, 158, 133));
            //How big does the apple will be
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(new Color(231,84,128));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(new Color(255, 192, 203));
                    /*This line of code makes every square from tail a random color with every move.
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    */
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //Game over text when you lost
            g.setColor(Color.PINK);
            //This is the font for the text
            g.setFont(new Font("Calibri", Font.BOLD, 35));
            //Font metrics with Graphic g
            FontMetrics metrics = getFontMetrics(g.getFont());
            //This is gonna put it in the center of the screen
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }else{
            gameOver(g);
        }
    }
    public void newApple(){
        //We are setting where the apple should show on X
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        //We are setting where the apple should show on Y
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
        //Body parts of the snake
        for(int i= bodyParts;i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        //Using a switch were gonna setup direction of the snake
        switch(direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkApple(){
        if((x[0] == appleX)&&(y[0] == appleY)){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions(){
        //Check if head collides with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i])&&(y[0]==y[i])){
                running = false;
            }
        }
        //Check if head touches left border
        if(x[0]<0){
            running = false;
        }
        //Check if head touches right border
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        //Check if head touches top border
        if(y[0]<0){
            running = false;
        }
        //Check if head touches bottom border
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        //We are going to show the score at the game over screen
        g.setColor(Color.PINK);
        g.setFont(new Font("papyrus", Font.BOLD, 35));
        FontMetrics metricsend = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metricsend.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());

        //Game over text when you lost
        g.setColor(Color.PINK);
        //This is the font for the text
        g.setFont(new Font("papyrus", Font.BOLD, 50));
        //Font metrics with Graphic g
        FontMetrics metrics = getFontMetrics(g.getFont());
        //This is gonna put it in the center of the screen
        g.drawString("Game over my little", (SCREEN_WIDTH - metrics.stringWidth("Game over my little"))/2, SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        //TODO Auto-generated method sub
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
