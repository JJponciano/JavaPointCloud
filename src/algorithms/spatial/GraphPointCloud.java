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
 * Create  graph of  a point cloud according to a distance between two points.
 *
 * @author Jean-Jacques Ponciano
 */
public class GraphPointCloud implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected double[][]graph;

    public GraphPointCloud(PointCloud cloud) {
        this.cloud = cloud;
        //init the graph
        this.graph=new double[this.cloud.size()][this.cloud.size()];
    }

    
    @Override
    public boolean isReady() {
        return this.isReady;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
