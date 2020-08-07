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
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to drawing all objects contained. Just add a
 * <code>IObjectGL</code> with the method <code>addObject</code> to draw it.
 *
 * @author ponciano
 */
public class DrawingScene extends BasicScene {

    protected List<IObjectGL> objects;

    public DrawingScene() {
        super();
        this.objects = new ArrayList<>();
    }

    public DrawingScene(double depthView) {
        super(depthView);
        this.objects = new ArrayList<>();
    }

    /**
     * Add a object will be drawing.
     *
     * @param o oject will be added.
     */
    public void addObject(IObjectGL o) {
        this.objects.add(o);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        super.display(drawable);
        final GL2 gl = drawable.getGL().getGL2();
        //display each object
        objects.forEach((object) -> {
            this.displayObject(gl, object);
        });
        gl.glFlush();
    }

    /**
     * Removes all of the elements from this list. The list will be empty after
     * this call returns.
     */
    public void clear() {
        this.objects.clear();
    }

    public void setCamera(double x, double y, double z, double lx, double ly, double lz) {
        this.camera.setPosition(x, y, z, lx, ly, lz);
    }

    public void remove(IObjectGL message) {
        this.objects.remove(message);
    }

    /**
     * Display the given object
     * @param gl gl used to display the object
     * @param object object displayed
     */
    protected void displayObject(GL2 gl, IObjectGL object) {
        object.displayGL(gl);
    }

}
