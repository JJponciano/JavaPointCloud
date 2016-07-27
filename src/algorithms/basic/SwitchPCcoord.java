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
package algorithms.basic;

import pointcloud.PointCloud;
import algorithms.IAlgorithm;

/**
 * A algorithm to switch both coordinates in a point cloud.
 *
 * @author Jean-Jacques Ponciano.
 */
public class SwitchPCcoord implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected int c1;
    protected int c2;

    /**
     * Creates a new instance of <code>SwitchPCcoord</code>.
     *
     * @param cloud cloud to be switch two coordinates.
     * @param c1 first coordinate to be switched with the second.
     * @param c2 second coordinate to be switched with the first.
     * <ol>
     * <li>0:x</li>
     * <li>1:y</li>
     * <li>2:z</li>
     * </ol>
     */
    public SwitchPCcoord(PointCloud cloud, int c1, int c2) {
        this.cloud = cloud;
        this.c1 = c1;
        this.c2 = c2;
    }

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    @Override
    public void run() {
        for (int i = 0; i < this.cloud.size(); i++) {
            float z = this.cloud.get(i).getArray()[c1];
            this.cloud.get(i).set(c1, this.cloud.get(i).getArray()[c2]);
            this.cloud.get(i).set(c2, z);

        }
    }

    public PointCloud getCloud() {
        return cloud;
    }

}
