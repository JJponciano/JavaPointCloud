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
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;


/**
 *
 * @author claire.prudhomme
 */
public class PointGL extends Point implements IObjectGL {

    /**
     *
     */
    private static final long serialVersionUID = 2082542802933657469L;
    private final float size;
    protected Float texCoordY = null;
    protected Float texCoordX = null;

    public PointGL(final float x, final float y, final float z) {
        super(x, y, z);
        this.size = 1;
    }

    /**
     * Return texture coordinates of the point or null if there do not exist.
     *
     * @return array of texture coordinates (0:x and 1:y)
     */
    public float[] getTexCoord() {
        if (texCoordX == null) {
            return null;
        } else {
            return new float[] { texCoordX, texCoordY };
        }
    }

    /**
     * Set the texture coordinate of the point between 0.0f and 1.0f
     *
     * @param texCoordX x coordinate
     * @param texCoordY y coordinate
     */
    public void setTexCoord(final float texCoordX, final float texCoordY) {
        if ((texCoordX >= 0f && texCoordX <= 1f) && (texCoordY >= 0f && texCoordY <= 1f)) {
            this.texCoordY = texCoordY;
            this.texCoordX = texCoordX;
        }

    }

    public PointGL(final float size, final float x, final float y, final float z) {
        super(x, y, z);
        this.size = size;
    }

    public PointGL(final float size, final float x, final float y, final float z, final Color color) {
        super(new Coord3D(x, y, z), color);
        this.size = size;
    }

    public PointGL(final float size, final Coord3D coord) {
        super(coord);
        this.size = size;
    }

    @Override
    public void displayGL(final GL2 gl) {
        gl.glPointSize(this.size);
        gl.glBegin(GL2.GL_POINTS);
        gl.glColor3f(this.color.getRedf(), this.color.getGreenf(), this.color.getBluef());
        gl.glVertex3d(this.coords.getX(), this.coords.getY(), this.coords.getZ());
        gl.glEnd();
        gl.glPointSize(1);
    }
}
