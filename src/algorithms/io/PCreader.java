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
package algorithms.io;

import pointcloud.PointCloud;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public abstract class PCreader extends IoPointCloud {


    protected PointCloud cloud;

    /**
     * Creates a new instance of <code>PCreader</code>.
     *
     * @param filepath The path of the file containing the point cloud.
     */
    public PCreader(String filepath) {
        super(filepath);
        this.cloud = new PointCloud();
    }

    /**
     * Get the point cloud read.
     *
     * @return The point cloud read.
     */
    public PointCloud getCloud() {
        return cloud;
    }
}
