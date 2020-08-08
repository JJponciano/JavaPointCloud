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
package info.ponciano.lab.jpc.pointcloud.stucture.octree;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL2;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.opengl.IObjectGL;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

/**
 *
 * @author jean-jacques.ponciano
 */
 public class VoxelView implements IObjectGL {

    private DoubleBuffer vertex;
    private DoubleBuffer color;
    private IntBuffer indice;
    private int indiceCount = 0;
    private double alpha = 1;

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public VoxelView(Voxel vox) {
        this.update(vox);
    }

    /**
     * Update the view
     *
     * @param vox voxel to be drawn.
     */
    public final void update(Voxel vox) {
        Point[] pointFace = vox.getPointFace();
        //init all arrays
        int[] indiceArray = new int[pointFace.length];
        double[] colorArray = new double[pointFace.length * 4];
        double[] vertexArray = new double[pointFace.length * 3];
        int i = 0;//indice index
        int j = 0;// vertex index          
        int k = 0;//color index          
        //fill arrays
        for (Point point : pointFace) {
            indiceArray[i] = i;
            i++;
            vertexArray[j] = point.getCoords().getX();
            j++;
            vertexArray[j] = point.getCoords().getY();
            j++;
            vertexArray[j] = point.getCoords().getZ();
            j++;
            colorArray[k] = (double) (vox.getColor().getRed() / 255.0f);
            k++;
            colorArray[k] = (double) (vox.getColor().getGreen() / 255.0f);
            k++;
            colorArray[k] = (double) (vox.getColor().getBlue() / 255.0f);
            k++;
            colorArray[k] = this.alpha;
            k++;

        }
        //fill buffer
        this.vertex = Buffers.newDirectDoubleBuffer(vertexArray);
        this.color = Buffers.newDirectDoubleBuffer(colorArray);
        this.indice = Buffers.newDirectIntBuffer(indiceArray);
        this.indiceCount = indiceArray.length;
    }

    @Override
    public void displayGL(GL2 gl) {
        try {
            gl.glEnableClientState(GL2.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL2.GL_COLOR_ARRAY);

            gl.glVertexPointer(3, GL2.GL_DOUBLE, 0, vertex);
            gl.glColorPointer(4, GL2.GL_DOUBLE, 0, color);

            gl.glDrawElements(GL2.GL_QUADS, this.indiceCount, GL2.GL_UNSIGNED_INT, indice);

            gl.glDisableClientState(GL2.GL_COLOR_ARRAY);
            gl.glDisableClientState(GL2.GL_VERTEX_ARRAY);
        } catch (java.lang.RuntimeException e) {
            System.err.println("Empty element");
        }

    }

}
