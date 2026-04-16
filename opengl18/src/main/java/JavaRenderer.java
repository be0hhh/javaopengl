import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

public class JavaRenderer implements GLEventListener {
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

        if (Main.wireFrame)
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);
        else
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);

        angleX += Main.rotationX;
        angleY += Main.rotationY;
        angleZ += Main.rotationZ;
        zoom *= Main.zoomChange;
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
        gl.glScalef(zoom, zoom, zoom);

        // Рисуем куб с центром в (0,0,0) и ребром 1
        float r = 0.5f;

        // Передняя грань (+Z) - Красный
        gl.glColor3f(1,0,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r,  r);
        gl.glVertex3f( r, -r,  r);
        gl.glVertex3f( r,  r,  r);
        gl.glVertex3f(-r,  r,  r);
        gl.glEnd();

        // Задняя грань (-Z) - Зеленый
        gl.glColor3f(0,1,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f(-r,  r, -r);
        gl.glVertex3f( r,  r, -r);
        gl.glVertex3f( r, -r, -r);
        gl.glEnd();

        // Верхняя грань (+Y) - Синий
        gl.glColor3f(0,0,1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r,  r, -r);
        gl.glVertex3f(-r,  r,  r);
        gl.glVertex3f( r,  r,  r);
        gl.glVertex3f( r,  r, -r);
        gl.glEnd();

        // Нижняя грань (-Y) - Желтый
        gl.glColor3f(1,1,0);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f( r, -r, -r);
        gl.glVertex3f( r, -r,  r);
        gl.glVertex3f(-r, -r,  r);
        gl.glEnd();

        // Правая грань (+X) - Голубой
        gl.glColor3f(0,1,1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f( r, -r, -r);
        gl.glVertex3f( r,  r, -r);
        gl.glVertex3f( r,  r,  r);
        gl.glVertex3f( r, -r,  r);
        gl.glEnd();

        // Левая грань (-X) - Пурпурный
        gl.glColor3f(1,0,1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-r, -r, -r);
        gl.glVertex3f(-r, -r,  r);
        gl.glVertex3f(-r,  r,  r);
        gl.glVertex3f(-r,  r, -r);
        gl.glEnd();
    }

    public void init(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);
    }

    public void reshape(GLAutoDrawable gLDrawable, int x,
                        int y, int width, int height) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        if(height <= 0) {
            height = 1;
        }
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void dispose(GLAutoDrawable arg0) {

    }

}