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

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class PointCloudControler {

    protected PointCloud cloud;
    protected PointCloudView view;

    public PointCloudControler(PointCloud cloud) {
        this.cloud = cloud;
        this.view = new PointCloudView(this.cloud);
    }

    /**
     * Returns the view of the point cloud usually for drawing the point cloud.
     *
     * @return View of the point cloud.
     */
    public PointCloudView getView() {
        return this.view;
    }

    /**
     * Returns the point cloud
     *
     * @return The point cloud.
     */
    public PointCloud getCloud() {
        return cloud;
    }

    /**
     * Update the view of the point cloud.
     */
    public void updateView() {
        this.view.updateCloud(cloud);
    }

}
