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
package algorithms.spatial;

import algorithms.IAlgorithm;
import pointcloud.PointCloud;
import pointcloud.PointColor;

/**
 * Calculating of the area of the point cloud given.
 *
 * @author Jean-Jacques Ponciano
 */
public class AreaPC implements IAlgorithm {

    /**
     *Unit of spatial measure.
     */
    public enum Unit {
        m3, dm3, cm3, mm3
    };
    protected PointCloud cloud;
    protected boolean isReady;
    private double area;

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    public PointCloud getCloud() {
        return cloud;
    }

    /**
     * Creates a new instance of <code>AreaPC</code>.
     *
     * @param cloud cloud to be used.
     */
    public AreaPC(PointCloud cloud) {
        this.cloud = cloud;
        this.isReady = true;
        this.area = 0;
    }

    private double area() {
        //calcule the size of the cloud
        float[] min = {this.cloud.get(0).getX(), this.cloud.get(0).getY(), this.cloud.get(0).getZ()};
        float[] max = {this.cloud.get(0).getX(), this.cloud.get(0).getY(), this.cloud.get(0).getZ()};
        //test each point of the point cloud to seen if a point coordinates is a extremum of the cloud.
        for (PointColor p : this.cloud.getPoints()) {
            if (p.getX() < min[0]) {
                min[0] = p.getX();
            } else if (p.getX() > max[0]) {
                max[0] = p.getX();
            }

            if (p.getY() < min[1]) {
                min[1] = p.getY();
            } else if (p.getY() > max[1]) {
                max[1] = p.getY();
            }
            if (p.getZ() < min[2]) {
                min[2] = p.getZ();
            } else if (p.getZ() > max[2]) {
                max[2] = p.getZ();
            }
        }
        //Calculate the area of the cloud 
        double area = (max[0] - min[0]) * (max[1] - min[1]) * (max[1] - min[1]);
        return area;
    }

    @Override
    public void run() {
        this.isReady = false;
        this.area = this.area();
        this.isReady = true;
    }

    /**
     * Get the area of the cloud in a specific unit of measure.
     * @param unit Unit of measure to be used (m3,dm3,cm3,mm3)
     * @return The area in a specific unit of measure.
     */
    public double getArea(Unit unit) {
        switch (unit) {
            case m3:
                return area;
            case dm3:
                return area * 1000;
            case cm3:
                return area * 1000 * 1000;
            case mm3:
                return area * 1000 * 1000 * 1000;
            default:
                return area;
        }
    }

}
