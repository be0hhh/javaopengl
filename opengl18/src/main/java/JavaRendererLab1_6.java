import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

/**
 * Lab 1.6 - Покрасить половину в желтый
 * Изображение не квадратное!
 * Левая половина экрана закрашивается в желтый цвет
 */
public class JavaRendererLab1_6 implements GLEventListener {
    private int width = 800;
    private int height = 600;
    private static final GLU glu = new GLU();

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        
        // Очищаем экран черным цветом
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        // Переключаемся на ортогональную проекцию для работы с пикселями
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, width, 0, height);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Рисуем левую половину в желтый цвет
        // Желтый = красный + зеленый (1.0, 1.0, 0.0)
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
        // Левый нижний угол
        gl.glVertex2i(0, 0);
        // Правый нижний угол (середина по ширине)
        gl.glVertex2i(width / 2, 0);
        // Правый верхний угол (середина по ширине)
        gl.glVertex2i(width / 2, height);
        // Левый верхний угол
        gl.glVertex2i(0, height);
        gl.glEnd();

        // Правую половину оставляем черной (фон)
        // Можно явно нарисовать черным, если нужно
        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2i(width / 2, 0);
        gl.glVertex2i(width, 0);
        gl.glVertex2i(width, height);
        gl.glVertex2i(width / 2, height);
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
        final float aspect = (float) w / (float) h;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, w, 0, h);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}
