import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

/**
 * Lab 1.5 - Гистограмма по трем цветам и по яркости
 * Каждая грань куба окрашена в цвета, представляющие разные компоненты:
 * - Передняя: красная компонента (градации красного)
 * - Задняя: зеленая компонента (градации зеленого)
 * - Верхняя: синяя компонента (градации синего)
 * - Нижняя: яркость (градации серого)
 * - Правая: комбинация RGB (разные сочетания)
 * - Левая: инверсия (дополнительные цвета)
 */
public class JavaRendererLab1_5 implements GLEventListener {
    private float angleX = 0.0f;
    private float angleY = 0.0f;
    private float angleZ = 0.0f;
    private float zoom = 1.0f;
    private static final GLU glu = new GLU();

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        // Режим отображения: каркас или заполнение
        if (MainLab1_5.wireFrame)
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);
        else
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);

        // Обновление углов поворота и зума
        angleX += MainLab1_5.rotationX;
        angleY += MainLab1_5.rotationY;
        angleZ += MainLab1_5.rotationZ;
        zoom *= MainLab1_5.zoomChange;
        
        // Применяем трансформации
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
        gl.glScalef(zoom, zoom, zoom);

        // Рисуем куб с центром в (0,0,0) и ребром 1
        float r = 0.5f;

        // Передняя грань (+Z) - Красная компонента (градации красного)
        // От темно-красного до ярко-красного
        gl.glColor3f(0.8f, 0.0f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, r);
        gl.glVertex3f(r, -r, r);
        gl.glVertex3f(r, r, r);
        gl.glVertex3f(-r, r, r);
        gl.glEnd();

        // Задняя грань (-Z) - Зеленая компонента (градации зеленого)
        // От темно-зеленого до ярко-зеленого
        gl.glColor3f(0.0f, 0.8f, 0.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f(-r, r, -r);
        gl.glVertex3f(r, r, -r);
        gl.glVertex3f(r, -r, -r);
        gl.glEnd();

        // Верхняя грань (+Y) - Синяя компонента (градации синего)
        // От темно-синего до ярко-синего
        gl.glColor3f(0.0f, 0.0f, 0.8f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, r, -r);
        gl.glVertex3f(-r, r, r);
        gl.glVertex3f(r, r, r);
        gl.glVertex3f(r, r, -r);
        gl.glEnd();

        // Нижняя грань (-Y) - Яркость (градации серого)
        // От темно-серого до светло-серого
        gl.glColor3f(0.6f, 0.6f, 0.6f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f(r, -r, -r);
        gl.glVertex3f(r, -r, r);
        gl.glVertex3f(-r, -r, r);
        gl.glEnd();

        // Правая грань (+X) - Комбинация RGB (разные сочетания)
        // Смешанный цвет с преобладанием всех компонент
        gl.glColor3f(0.7f, 0.5f, 0.9f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(r, -r, -r);
        gl.glVertex3f(r, r, -r);
        gl.glVertex3f(r, r, r);
        gl.glVertex3f(r, -r, r);
        gl.glEnd();

        // Левая грань (-X) - Инверсия (дополнительные цвета)
        // Инвертированный цвет (циан как инверсия красного)
        gl.glColor3f(0.2f, 1.0f, 1.0f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f(-r, -r, r);
        gl.glVertex3f(-r, r, r);
        gl.glVertex3f(-r, r, -r);
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

    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if (height <= 0) {
            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {
    }
}
