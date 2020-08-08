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

/**
 *
 * @author ponciano
 */
public class PiMire implements IObjectGL {

    protected float x;
    protected float y;
    protected float z;

    public PiMire(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PiMire() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public void displayGL(GL2 gl) {
        //axe x en majenta
        gl.glPointSize(3);
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3d(0, 0.6, 0.0);
        gl.glVertex3d(x, y, z);
        gl.glEnd();
        gl.glPointSize(1);
    }
}
