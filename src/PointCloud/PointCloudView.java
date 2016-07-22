/*
 * Copyright (C) 2016 Jean-Jacques Ponciano.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package PointCloud;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import opengl.objects.IObjectGL;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class PointCloudView implements IPointCloudListener, IObjectGL {

    private FloatBuffer vertex;
    private FloatBuffer color;
    private IntBuffer indice;
    private int indiceCount;
    private int nbPoints;

    /**
     * Creates a new instance of <code>PointCloudView</code>.
     */
    public PointCloudView() {
        this.nbPoints = 0;
        this.indiceCount = 0;
    }

    /**
     * Creates a new instance of <code>PointCloudView</code>.
     *
     * @param cloud update the view with the cloud given.
     */
    public PointCloudView(PointCloud cloud) {
        this.nbPoints = 0;
        this.indiceCount = 0;
        this.updateCloud(cloud);
    }

    @Override
    public void updateCloud(PointCloud cloud) {
        if (cloud != null) {
            this.nbPoints = cloud.size();
            if (this.nbPoints > 0) {
                //init all arrays
                int[] indiceArray = new int[cloud.size()];
                float[] colorArray = new float[cloud.size() * 3];
                float[] vertexArray = new float[cloud.size() * 3];
                int i = 0;//indice index
                int j = 0;//color and vertex index
                //fill arrays
                for (PointColor point : cloud.getPoints()) {
                    indiceArray[i] = i;
                    i++;
                    vertexArray[j] = point.getX();
                    colorArray[j] = (float) (point.getColor().getRed() / 255.0f);
                    j++;
                    vertexArray[j] = point.getY();
                    colorArray[j] = (float) (point.getColor().getGreen() / 255.0f);
                    j++;
                    vertexArray[j] = point.getZ();
                    colorArray[j] = (float) (point.getColor().getBlue() / 255.0f);
                    j++;
                }
                //fill buffer
                this.vertex = Buffers.newDirectFloatBuffer(vertexArray);
                this.color = Buffers.newDirectFloatBuffer(colorArray);
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
        if (this.nbPoints > 0) {
            try {
                gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
                gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

                gl.glVertexPointer(3, GL2.GL_FLOAT, 0, vertex);
                gl.glColorPointer(3, GL2.GL_FLOAT, 0, color);

                gl.glDrawElements(GL2.GL_POINTS, this.indiceCount, GL2.GL_UNSIGNED_INT, indice);

                gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
                gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
            } catch (java.lang.RuntimeException e) {
                System.err.println("Empty cloud");
            }
        }
    }
}
