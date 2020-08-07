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

import com.jogamp.opengl.awt.GLCanvas;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JDialog;

/**
 *
 * @author jean-jacques
 */
public class Display extends JDialog implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = -4899520103671545232L;
    protected int width;
    protected int height;
    private DrawingScene scene;
    protected double deepview;

    /**
     * Creates a new instance of <code>Display</code>.
     *
     * @param width    width of the windows.
     * @param height   height of the windows.
     * @param deepview deepview of the Glut windows.
     */
    public Display(final java.awt.Frame parent, final boolean modal, final int width, final int height,
            final double deepview) {
        super(parent, modal);
        this.width = width;
        this.height = height;
        this.deepview = deepview;
        initScene();
    }

    public Display(final java.awt.Frame parent, final boolean modal, final int width, final int height) {
        super(parent, modal);
        this.deepview = -1;
        this.width = width;
        this.height = height;
        initScene();
    }

    public Display(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        this.deepview = -1;
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maximumWindowBounds = ge.getMaximumWindowBounds();
        this.height = (int) maximumWindowBounds.getHeight();
        this.width = (int) maximumWindowBounds.getWidth();
      
        initScene();
    }

    @Override
    public void run() {
        this.setVisible(true);
    }

    private void initScene() {
        if (this.deepview > 0) {
            this.scene = new DrawingScene(deepview);
        } else {
            this.scene = new DrawingScene();
        }
        this.setSize(this.width, this.height);
        final GLCanvas canvas = scene.getCanvas(this.width, this.height);
        this.add(canvas);
    }

    /**
     * Get the canvas contained every objects in the scene.
     *
     * @return the scene in OpenGL format.
     */
    public GLCanvas getCanvas() {
        final GLCanvas canvas = scene.getCanvas(this.width, this.height);
        return canvas;
    }

    /**
     * Adds a object
     *
     * @param ob object to be added at the scene
     */
    public void addObject(final IObjectGL ob) {
        if (scene == null) {
            initScene();
        }
        scene.addObject(ob);
    }

    /**
     * Close the windows
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    public DrawingScene getScene() {
        return this.scene;
    }

    public void setScene(final DrawingScene scene) {
        this.scene = scene;
    }

    public void setCamera(final double x, final double y, final double z, final double lx, final double ly,
            final double lz) {
        this.scene.setCamera(x, y, z, lx, ly, lz);
    }
}
