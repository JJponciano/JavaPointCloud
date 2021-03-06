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
package lite.algorithms.basic;

import lite.pointcloud.PointCloud;
import lite.algorithms.IAlgorithm;

/**
 * ScalePC is an algorithm allowing for scaling a point cloud.
 *<h3>Example</h3>
 * <code>
 * String pathfile = "table.txt";<br>
 * //read the point cloud.<br>
 * ReadPCfromTXT reader = new ReadPCfromTXT(pathfile);<br>
 * reader.run();<br>
 * //center the point cloud.<br>
 * ScalePC scale = new ScalePC(reader.getCloud(),0.1f,2,1);<br>
 * scale.run();
 * </code>
 * @author Jean-Jacques Ponciano.
 */
public class ScalePC implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected float x, y, z;

    /**
     * Creates a new instance of <code>ScalePC</code>.
     *
     *
     * @param cloud cloud to be scaled.
     * @param x Scale in x.
     * @param y Scale in y.
     * @param z Scale in z.
     */
    public ScalePC(PointCloud cloud, float x, float y, float z) {
        this.isReady = true;
        this.cloud = cloud;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.cloud.size(); i++) {
            this.cloud.get(i).setX(this.cloud.get(i).getX() * x);
            this.cloud.get(i).setY(this.cloud.get(i).getY() * y);
            this.cloud.get(i).setZ(this.cloud.get(i).getZ() * z);
        }
    }

    public PointCloud getCloud() {
        return cloud;
    }

}
