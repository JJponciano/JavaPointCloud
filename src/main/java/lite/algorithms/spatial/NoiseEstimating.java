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
package lite.algorithms.spatial;

import lite.algorithms.IAlgorithm;
import java.util.ArrayList;
import lite.pointcloud.PointCloud;
import lite.pointcloud.PointColor;

/**
 * Estimate the noise in a point cloud.
 *
 * @author Jean-Jacques Ponciano
 */
public class NoiseEstimating implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected Unit unit;
    private int noise;
    protected ArrayList<PointColor> noisePoints;
/**
 * Creates a new instance of <code>NoiseEstimating</code>.
 * @param cloud  cloud to be used.
 * @param unit  Unit of spatial measure
 */
    public NoiseEstimating(PointCloud cloud, Unit unit) {
        this.cloud = cloud;
        this.unit = unit;
        this.isReady = true;
        this.noise = 0;
        noisePoints = new ArrayList<>();
    }

    @Override
    public boolean isReady() {
        return isReady;
    }

    @Override
    public void run() {
        //get the density of the cloud
        PointDensity densityPC = new PointDensity(cloud, unit);
        densityPC.run();
        double density = densityPC.getDensity();
        //distance min
        double min = 1.0 / density;
        double noise = 0;
        noisePoints = new ArrayList<>();
        boolean noNeigthbor = true;
        //look for each point if it has a other point close to this point.
        for (int i = 0; i < this.cloud.size(); i++) {
            PointColor get = this.cloud.get(i);
            //test with all previous point
            for (int j = 0; j < noisePoints.size(); j++) {
                PointColor get2 = noisePoints.get(j);
                //test the distance
                if (get.distance(get2) <= min) {
                    //remove the point in the temporary array.
                    noisePoints.remove(j);
                    noNeigthbor = false;
                    j--;
                }
            }
            //if the point has no neightbor it is added to the temporary array
            if (noNeigthbor) {
                noisePoints.add(get);
            }
        }
        this.noise = noisePoints.size();
    }

    public int getNoise() {
        return noise;
    }

    public ArrayList<PointColor> getNoisePoints() {
        return noisePoints;
    }

    public PointCloud getCloud() {
        return cloud;
    }
    
}
