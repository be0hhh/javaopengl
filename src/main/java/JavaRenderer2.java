import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;

public class JavaRenderer2 implements GLEventListener {
    private float angleX = 0.0f;
    private float angleY = 0.0f;
    private float angleZ = 0.0f;
    private float zoom = 1.0f;
    private static final GLU glu = new GLU();

    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);

        if (Main2.wireFrame)
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_LINE);
        else
            gl.glPolygonMode(gl.GL_FRONT_AND_BACK, gl.GL_FILL);

        angleX += Main2.rotationX;
        angleY += Main2.rotationY;
        angleZ += Main2.rotationZ;
        zoom *= Main2.zoomChange;
        gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(angleY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
        gl.glScalef(zoom, zoom, zoom);

        float s = 0.1f; // половина ребра маленького кубика

        // 8 позиций для маленьких кубиков
        for (int ix = -1; ix <= 1; ix += 2) {
            for (int iy = -1; iy <= 1; iy += 2) {
                for (int iz = -1; iz <= 1; iz += 2) {
                    float x = ix * 0.5f;
                    float y = iy * 0.5f;
                    float z = iz * 0.5f;

                    // цвет на основе позиции
                    float r = (ix + 1) / 2.0f;
                    float g = (iy + 1) / 2.0f;
                    float b = (iz + 1) / 2.0f;
                    gl.glColor3f(r, g, b);

                    // 6 граней кубика
                    float[][] normals = {{0,0,1}, {0,0,-1}, {0,1,0}, {0,-1,0}, {1,0,0}, {-1,0,0}};

                    for (int face = 0; face < 6; face++) {
                        gl.glBegin(GL2.GL_QUADS);

                        float nx = normals[face][0];
                        float ny = normals[face][1];
                        float nz = normals[face][2];

                        for (int i = 0; i < 4; i++) {
                            float vx = 0, vy = 0, vz = 0;

                            if (nz != 0) { // перед/зад
                                vx = (i == 0 || i == 3) ? -s : s;
                                vy = (i < 2) ? -s : s;
                                vz = nz * s;
                            } else if (ny != 0) { // верх/низ
                                vx = (i == 0 || i == 3) ? -s : s;
                                vy = ny * s;
                                vz = (i < 2) ? -s : s;
                            } else if (nx != 0) { // право/лево
                                vx = nx * s;
                                vy = (i < 2) ? -s : s;
                                vz = (i == 0 || i == 3) ? -s : s;
                            }

                            gl.glVertex3f(x + vx, y + vy, z + vz);
                        }
                        gl.glEnd();
                    }
                }
            }
        }
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
        if(height <= 0) height = 1;
        final float h = (float)width / (float)height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(50.0f, h, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public void dispose(GLAutoDrawable arg0) {}
}