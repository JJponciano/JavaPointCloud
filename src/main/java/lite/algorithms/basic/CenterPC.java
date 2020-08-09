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
 * CenterPC is an algorithm allowing for moving a point cloud at the origin
 * point of the scene.
 * <h3>Example</h3>
 * <code>
 * String pathfile = "table.txt";<br>
 * //read the point cloud.<br>
 * ReadPCfromTXT reader = new ReadPCfromTXT(pathfile);<br>
 * reader.run();<br>
 * //center the point cloud.<br>
 * CenterPC center = new CenterPC(reader.getCloud());<br>
 * center.run();
 * </code>
 *
 * @author Jean-Jacques Ponciano.
 */
public class CenterPC implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    public PointCloud getCloud() {
        return cloud;
    }

    /**
     * Creates a new instance of <code>CenterPC</code>.
     *
     * @param cloud cloud to be centered.
     */
    public CenterPC(PointCloud cloud) {
        this.isReady = true;
        this.cloud = cloud;
    }

    @Override
    public void run() {
        if (this.cloud.size() > 0) {
            float[] orig = this.cloud.get(0).getArray();
            for (int i = 0; i < this.cloud.size(); i++) {
                this.cloud.get(i).setX(this.cloud.get(i).getX() - orig[0]);
                this.cloud.get(i).setY(this.cloud.get(i).getY() - orig[1]);
                this.cloud.get(i).setZ(this.cloud.get(i).getZ() - orig[2]);

            }
        }
    }

}
