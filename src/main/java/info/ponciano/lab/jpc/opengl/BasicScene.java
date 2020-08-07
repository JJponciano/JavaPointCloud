/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package info.ponciano.lab.jpc.opengl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author ponciano
 */
public class BasicScene implements GLEventListener, KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    protected final GLU glu;
    protected final LandMark landmark;
    protected PiCameraFPS camera;
    protected double depthView;
    protected boolean landmarkDisable;
    protected boolean cameraDisable;
    private float h;
    
    //singleton
//    private static BasicScene scene = null;
//    public static BasicScene get() {
//        if (scene == null) {
//            scene = new BasicScene();
//        }
//
//        return scene;
//    }

    public BasicScene() {
        this.cameraDisable = false;
        this.landmarkDisable = false;
        this.glu = new GLU();
        this.landmark = new LandMark(100, 100, 100);
        this.camera = new PiCameraFPS(new PiMire());
        System.out.println(this.toString());
        this.depthView = 1000.0;
    }

    public BasicScene(double depthView) {
        this.cameraDisable = false;
        this.landmarkDisable = false;
        this.glu = new GLU();
        this.landmark = new LandMark(100, 100, 100);
        this.camera = new PiCameraFPS(new PiMire());
        System.out.println(this.toString());
        this.depthView = depthView;
    }

    public boolean isLandmarkDisable() {
        return landmarkDisable;
    }

    public void setLandmarkDisable(boolean landmarkDisable) {
        this.landmarkDisable = landmarkDisable;
    }

    public boolean isCameraDisable() {
        return cameraDisable;
    }

    public void setCameraDisable(boolean cameraDisable) {
        this.cameraDisable = cameraDisable;
    }

    public GLCanvas getCanvas(int height, int width) {
        //getting the capabilities object of GL2 profile        
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        GLCanvas glcanvas = new GLCanvas(capabilities);

        glcanvas.addGLEventListener(this);
        glcanvas.setSize(height, width);
        glcanvas.addKeyListener(this);
        glcanvas.addMouseListener(this);
        glcanvas.addMouseMotionListener(this);
        glcanvas.requestFocus();
        FPSAnimator animator = new FPSAnimator(glcanvas, 26, true);
        animator.start();
        return glcanvas;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
            final GL2 gl = drawable.getGL().getGL2();
            gl.glShadeModel(GL2.GL_SMOOTH);
            gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            gl.glClearDepth(1.0f);
            gl.glEnable(GL2.GL_DEPTH_TEST);
            gl.glDepthFunc(GL2.GL_LEQUAL);
            gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
            //for transparency
            gl.glEnable(GL2.GL_BLEND);
            gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
//    gl.glEnable(GL2.GL_LIGHTING);
//    gl.glEnable(GL2.GL_TEXTURE_2D);
//initialisation of the camera.
         
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();

        // Clear The Screen And The Depth Buffer
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Reset The View
        if (!cameraDisable) {
            this.camera.init(glu);
            this.camera.displayGL(gl);
        }
        if (!landmarkDisable) {
            //show landmark
            this.landmark.displayGL(gl);
        }

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        if (height < 1) {
            height = 1;
        }
        this.h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45.0f, h, 1.0, this.depthView);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public PiCamera getCamera() {
        return camera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("camera:");
        System.out.println(this.camera.positionToString());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.camera.move(e.getKeyCode());
        //show or hide the mire
        if (e.getKeyCode() == KeyEvent.VK_M) {
            this.camera.setShowMire(!this.camera.isShowMire());
        } else//show or hide the LandMark
        if (e.getKeyCode() == KeyEvent.VK_L) {
            this.landmark.setShow(!this.landmark.isShow());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("opengl");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //if the left button is pressed
        switch (e.getButton()) {
            case MouseEvent.BUTTON1:
                System.out.println("BUTTON1: " + e.getX() + " " + e.getY() + "\n on screen: " + e.getXOnScreen() + " " + e.getYOnScreen());
                this.camera.zoom();
                System.out.println("Zoom: " + this.camera.getZoom());
                break;
            //if the scroll button is pressed
            case MouseEvent.BUTTON2:
                this.camera.switchOnOff(e.getX(), e.getY());
                break;
            //if the right button is pressed
            case MouseEvent.BUTTON3:
                this.camera.unzoom();
                System.out.println("Zoom: " + this.camera.getZoom());
                break;
            default:
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //  System.out.println("mousePressed: "+e.getX()+" "+e.getY()+"\n on screen: "+e.getXOnScreen()+" "+e.getYOnScreen());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // System.out.println("mouseReleased: "+e.getX()+" "+e.getY()+"\n on screen: "+e.getXOnScreen()+" "+e.getYOnScreen());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouseEntered: "+e.getX()+" "+e.getY()+"\n on screen: "+e.getXOnScreen()+" "+e.getYOnScreen());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //  System.out.println("mouseExited: "+e.getX()+" "+e.getY()+"\n on screen: "+e.getXOnScreen()+" "+e.getYOnScreen());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //  System.out.println("mouseDragged: "+e.getX()+" "+e.getY()+"\n on screen: "+e.getXOnScreen()+" "+e.getYOnScreen());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.camera.taget(e.getX(), e.getY());

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        System.out.println("mouseWheelMoved");

    }

    public double getDepthView() {
        return depthView;
    }

    public void setDepthView(double depthView) {
        this.depthView = depthView;
    }

    @Override
    public String toString() {
        String s = "Commandes:\n";
        s += "L: show or hide the landmark\n";
        s += "M: show or hide the camera mire\n";
        s += this.camera.toString();
        return s;
    }

}
