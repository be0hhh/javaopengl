import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.awt.GLCanvas;

public class MainLab1_2 implements Runnable, KeyListener {
    private static Thread displayT = new Thread(new MainLab1_2());
    private static float ROTATION_SPEED = 3f;
    private static float ZOOM_SPEED = 1.05f;
    private static boolean bQuit = false;
    public static float rotationX = 0f;
    public static float rotationY = 0f;
    public static float rotationZ = 0f;
    public static float zoomChange = 1.0f;
    public static boolean wireFrame = false;

    public static void main(String[] args) {
        displayT.start();
    }

    public void run() {
        Frame frame = new Frame("Lab 1.2 - Lighter 10%");
        frame.setLocation(0,0);
        GLCanvas canvas = new GLCanvas();
        int size = frame.getExtendedState();

        canvas.addGLEventListener(new JavaRendererLab1_2());
        frame.add(canvas);
        frame.setUndecorated(true);
        size |= Frame.MAXIMIZED_BOTH;
        frame.setExtendedState(size);
        canvas.addKeyListener(this);
        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                bQuit = true;
                System.exit(0);
            }
        });

        frame.setVisible(true);
        canvas.requestFocus();
        while( !bQuit ) {
            canvas.display();
        }
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            displayT = null;
            bQuit = true;
            System.exit(0);
        }
        if(e.getKeyCode() == KeyEvent.VK_W) {
            rotationX = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            rotationX = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_A) {
            rotationY = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_D) {
            rotationY = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            rotationZ = ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_E) {
            rotationZ = -ROTATION_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_MINUS) {
            zoomChange = 1/ZOOM_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
            zoomChange = ZOOM_SPEED;
        }
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            wireFrame = !wireFrame;
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_S) {
            rotationX = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D) {
            rotationY = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_Q || e.getKeyCode() == KeyEvent.VK_E) {
            rotationZ = 0f;
        }
        if(e.getKeyCode() == KeyEvent.VK_MINUS || e.getKeyCode() == KeyEvent.VK_EQUALS) {
            zoomChange = 1.0f;
        }
    }

    public void keyTyped(KeyEvent e) {
    }
}
