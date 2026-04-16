import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jogamp.opengl.awt.GLCanvas;

/**
 * Lab 1.7 - Покрасить "бабочкой" в фиолетовый
 * Изображение не квадратное!
 * Рисуем узор "бабочка" из четырех треугольников в фиолетовом цвете
 */
public class MainLab1_7 implements Runnable, KeyListener {
    private static Thread displayT = new Thread(new MainLab1_7());
    private static boolean bQuit = false;

    public static void main(String[] args) {
        displayT.start();
    }

    public void run() {
        Frame frame = new Frame("Lab 1.7 - Purple Butterfly");
        frame.setLocation(0, 0);
        GLCanvas canvas = new GLCanvas();
        int size = frame.getExtendedState();

        canvas.addGLEventListener(new JavaRendererLab1_7());
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
        while (!bQuit) {
            canvas.display();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            displayT = null;
            bQuit = true;
            System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
