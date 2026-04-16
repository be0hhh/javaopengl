import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

/**
 * Lab 1.7 - Покрасить "бабочкой" в фиолетовый
 * Изображение не квадратное!
 * Рисуем узор "бабочка" из четырех треугольников, сходящихся в центре
 * Фиолетовый цвет = красный + синий (1.0, 0.0, 1.0)
 */
public class JavaRendererLab1_7 implements GLEventListener {
    private int width = 800;
    private int height = 600;
    private static final GLU glu = new GLU();

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        
        // Находим центр экрана
        int centerX = width / 2;
        int centerY = height / 2;
        
        // Очищаем экран черным цветом
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Переключаемся на ортогональную проекцию для работы с пикселями
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, width, 0, height);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Фиолетовый цвет
        gl.glColor3f(1.0f, 0.0f, 1.0f);

        // Рисуем "бабочку" - четыре треугольника, сходящихся в центре
        // Верхний левый треугольник
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(0, height);           // Левый верхний угол
        gl.glVertex2i(centerX, height);     // Центр сверху
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();

        // Верхний правый треугольник
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(centerX, height);     // Центр сверху
        gl.glVertex2i(width, height);       // Правый верхний угол
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();

        // Нижний правый треугольник
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glVertex2i(width, centerY);      // Центр справа
        gl.glVertex2i(width, 0);            // Правый нижний угол
        gl.glEnd();

        // Нижний левый треугольник
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(0, 0);                // Левый нижний угол
        gl.glVertex2i(centerX, 0);          // Центр снизу
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();

        // Левый треугольник (дополнительный для полной бабочки)
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(0, height);           // Левый верхний угол
        gl.glVertex2i(0, centerY);          // Центр слева
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();

        // Правый треугольник (дополнительный)
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(width, height);       // Правый верхний угол
        gl.glVertex2i(width, centerY);      // Центр справа
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();

        // Нижний центральный треугольник
        gl.glBegin(GL2.GL_TRIANGLES);
        gl.glVertex2i(0, 0);                // Левый нижний угол
        gl.glVertex2i(0, centerY);          // Центр слева
        gl.glVertex2i(centerX, centerY);    // Центр экрана
        gl.glEnd();
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int w, int h) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if (h <= 0) {
            h = 1;
        }
        this.width = w;
        this.height = h;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, w, 0, h);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}
