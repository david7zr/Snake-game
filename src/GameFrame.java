import javax.swing.JFrame;
public class GameFrame extends JFrame{
    GameFrame(){

        //Add specific element into a set collection
        this.add(new GamePanel());
        //Method/constructor
        this.setTitle("Snake");
        //Control the behavior of a window when the user tries to close it
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //The window can be resized while its running
        this.setResizable(false);
        //Causes window to be sized to fit size and layouts subcomponents
        this.pack();
        //Makes the frame appear on the screen
        this.setVisible(true);
        //Center the window on the screen
        this.setLocationRelativeTo(null);

    }
}
