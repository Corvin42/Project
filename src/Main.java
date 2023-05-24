import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        MyFrame TheGreatFrame = new MyFrame();
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(TheGreatFrame);


        while (true) {
            TheGreatFrame.repaint();

            TheGreatFrame.updateWorldPhysics();
            Thread.sleep(20);
        }

    }
}