/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
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
import com.jogamp.opengl.util.awt.TextRenderer;
import info.ponciano.lab.jpc.math.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ponciano
 */
public class LandMark implements IObjectGL {

    private double length;
    private double width;
    private double height;
    private final TextRenderer renderer;
    private boolean show;
    private final List<PointGL> point;
    private final float sizePoint;

    /**
     * Constructor
     *
     * @param length length in X.
     * @param width width in Y.
     * @param height height in Z.
     */
    public LandMark(double length, double width, double height) {
        this.sizePoint = 10;
        this.point = new ArrayList<>();
        this.length = length;
        this.width = width;
        this.height = height;

        this.show = true;
        renderer = new TextRenderer(new Font("SansSerif", Font.PLAIN, 10));
        for (int i = 0; i < this.width; i++) {
            this.point.add(new PointGL(this.sizePoint, i, 0, 0, Color.RED));
            this.point.add(new PointGL(this.sizePoint, -i, 0, 0, Color.MAGENTA));
        }
        for (int i = 0; i < this.height; i++) {
            this.point.add(new PointGL(this.sizePoint, 0, i, 0, Color.GREEN));
            this.point.add(new PointGL(this.sizePoint, 0, -i, 0, Color.YELLOW));

        }
        for (int i = 0; i < this.height; i++) {
            this.point.add(new PointGL(this.sizePoint, 0, 0, i, Color.BLUE));
            this.point.add(new PointGL(this.sizePoint, 0, 0, -i, Color.CYAN));
        }

    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void displayGL(GL2 gl) {
        if (this.show) {
            this.drawLetter();
            //Repère
            //axe x en rouge
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(1.0, 0.0, 0.0);
            gl.glVertex3d(0, 0, 0.0);
            gl.glVertex3d(width, 0, 0.0);
            gl.glEnd();
            //draw points

            //axe des y en vert
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.0, 1.0, 0.0);
            gl.glVertex3d(0, 0, 0.0);
            gl.glVertex3d(0, height, 0.0);
            gl.glEnd();
            //axe des z en bleu
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.0, 0.0, 1.0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, 0, length);
            gl.glEnd();

            //axe x en majenta
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(1.0, 0.0, 1.0);
            gl.glVertex3d(0, 0, 0.0);
            gl.glVertex3d(-width, 0, 0.0);
            gl.glEnd();
            //axe des y en jaune
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(1.0, 1.0, 0.0);
            gl.glVertex3d(0, 0, 0.0);
            gl.glVertex3d(0, -height, 0.0);
            gl.glEnd();
            //axe des z en bleu
            gl.glBegin(GL2.GL_LINES);
            gl.glColor3d(0.0, 1.0, 1.0);
            gl.glVertex3d(0, 0, 0);
            gl.glVertex3d(0, 0, -length);
            gl.glEnd();

            this.drawPoints(gl);
        }
    }

    private void drawLetter() {

        this.renderer.begin3DRendering();
        this.renderer.setColor(java.awt.Color.red);
        this.renderer.draw3D("X", 1, -0.1f, 0, 0.03f);
        this.renderer.setColor(java.awt.Color.blue);
        this.renderer.draw3D("Z", 0, -0.1f, 1, 0.03f);
        this.renderer.setColor(java.awt.Color.green);
        this.renderer.draw3D("Y", 0, 1, 0, 0.03f);
        this.renderer.end3DRendering();
    }

    private void drawPoints(GL2 gl) {
        point.forEach((pointGL) -> {
            pointGL.displayGL(gl);
        });
    }
}
