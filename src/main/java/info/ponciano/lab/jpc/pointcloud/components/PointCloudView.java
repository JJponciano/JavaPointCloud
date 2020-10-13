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
package info.ponciano.lab.jpc.pointcloud.components;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import info.ponciano.lab.jpc.math.*;
import info.ponciano.lab.jpc.math.vector.Normal;
import info.ponciano.lab.jpc.opengl.IObjectGL;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class PointCloudView implements IObjectGL{

    private DoubleBuffer vertex;
    private DoubleBuffer color;
    private IntBuffer indice;
    private int indiceCount;
    private int nbPoints;
    private int ptSize;
    /**
     * say if the cloud is display thanks to a label dictionary
     */
    //TODO remove point label consideration

    private boolean normalColor;

    /**
     * Creates a new instance of <code>PointCloudView</code>.
     */
    public PointCloudView() {
        this.init();
    }

    private void init() {
        this.ptSize = 1;
        this.nbPoints = 0;
        this.indiceCount = 0;

    }

    /**
     * Creates a new instance of <code>PointCloudView</code>.
     *
     * @param cloud update the view with the cloud given.
     * @param normalColor true to display normal instead of rgb color, false
     * otherwise.
     */
    public PointCloudView(APointCloud cloud, boolean normalColor) {
        this.init();
        this.normalColor = normalColor;
        this.updateCloud(cloud, normalColor);
    }

    protected final void updateCloud(APointCloud cloud, boolean normalColor) {
        if (cloud != null) {
            this.nbPoints = cloud.size();
            if (this.nbPoints > 0) {
                // init all arrays
                int[] indiceArray = new int[cloud.size()];
                double[] colorArray = new double[cloud.size() * 3];
                double[] vertexArray = new double[cloud.size() * 3];
                int i = 0;// indice index
                int j = 0;// color and vertex index
                // fill arrays
               for (Iterator<Point> iterator =cloud.iterator(); iterator.hasNext();) {
                Point point = iterator.next();
                    indiceArray[i] = i;
                    i++;
                    Coord3D coords = point.getCoords();
                    Color colorp;
                    if (normalColor) {
                        Normal normal = point.getNormal();
                        colorp = normal.toColor();
                    } //else if (this.labelUsed) {
                    // set the color thanks to the disctionary of random color
                    // test if the color is unknown
                    //  if (!this.dico.containsKey(point.getLabel())) {
                    // create new color and add to the dico
                    //    this.dico.put(point.getLabel().get(0), this.rc.getColor());
                    //}
                    // colorp = this.dico.get(point.getLabel());
                    // } 
                    else {
                        colorp = point.getColor();
                    }
                    if (colorp == null) {
                        colorp = Color.WHITE;
                    }
                    vertexArray[j] = coords.getX();
                    colorArray[j] = colorp.getRedf();
                    j++;
                    vertexArray[j] = coords.getY();
                    colorArray[j] = colorp.getGreenf();
                    j++;
                    vertexArray[j] = coords.getZ();
                    colorArray[j] = colorp.getBluef();
                    j++;
                }
                // fill buffer
                this.vertex = Buffers.newDirectDoubleBuffer(vertexArray);
                this.color = Buffers.newDirectDoubleBuffer(colorArray);
                this.indice = Buffers.newDirectIntBuffer(indiceArray);
                this.indiceCount = indiceArray.length;
            }
        }
    }

    /**
     * Draw the point cloud in a GL2
     *
     * @param gl GL to draw the the point cloud.
     */
    @Override
    public void displayGL(GL2 gl) {
        gl.glPointSize(ptSize);
        if (this.nbPoints > 0) {
            try {
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

                gl.glVertexPointer(3, GL2.GL_DOUBLE, 0, vertex);
                gl.glColorPointer(3, GL2.GL_DOUBLE, 0, color);

                gl.glDrawElements(GL2.GL_POINTS, this.indiceCount, GL2.GL_UNSIGNED_INT, indice);

                gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
                gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
            } catch (java.lang.RuntimeException e) {
                System.err.println("Empty cloud");
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.vertex);
        hash = 37 * hash + Objects.hashCode(this.color);
        hash = 37 * hash + Objects.hashCode(this.indice);
        hash = 37 * hash + this.indiceCount;
        hash = 37 * hash + this.nbPoints;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PointCloudView other = (PointCloudView) obj;
        if (this.indiceCount != other.indiceCount) {
            return false;
        }
        if (this.nbPoints != other.nbPoints) {
            return false;
        }
        if (!Objects.equals(this.vertex, other.vertex)) {
            return false;
        }
        if (!Objects.equals(this.color, other.color)) {
            return false;
        }
        return Objects.equals(this.indice, other.indice);
    }

    public void decreasePointSize() {
        if (this.ptSize > 1) {
            this.ptSize--;
        }
    }

    public void increasePointSize() {
        this.ptSize++;

    }

    public void setPointSize(int size) {
        this.ptSize = size;
    }

    public boolean isNormalColor() {
        return normalColor;
    }

    public void setNormalColor(boolean normalColor) {
        this.normalColor = normalColor;
    }

}
