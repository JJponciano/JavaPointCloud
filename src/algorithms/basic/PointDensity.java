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

import algorithms.IAlgorithm;
import pointcloud.PointCloud;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class PointDensity implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected double density;
    protected AreaPC.Unit unit;
    public PointDensity(PointCloud cloud,AreaPC.Unit unit) {
        this.cloud = cloud;
        this.isReady = true;
        this.density = 0;
        this.unit=unit;
    }

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    public PointCloud getCloud() {
        return cloud;
    }

    @Override
    public void run() {
        this.isReady = false;
        //Density calculating
        //calculate the area
        AreaPC areaPC = new AreaPC(cloud);
        areaPC.run();
        double area = areaPC.getArea(this.unit);
        //divide the area by the number of points
        this.density = (double) (area / (double) this.cloud.size());;
        this.isReady = true;

    }

    public double getDensity() {
        return density;
    }

}
