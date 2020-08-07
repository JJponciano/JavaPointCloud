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
public interface IObjectGL {
    /**
     * displayGL Display all points of the cloud with the openGL code. Example:
     * @param gl gl componant to draw the cloud
     * <code>
     * glPointSize(1);<br>
     * glBegin(GL_POINTS);{<br>
     *  glColor3f(1,0,0);//red<br>
     *  glVertex3f(0.5,1,0.7);//point coordinate<br>
     * }glEnd();
     * </code>
     */
    public void displayGL(GL2 gl);
}
