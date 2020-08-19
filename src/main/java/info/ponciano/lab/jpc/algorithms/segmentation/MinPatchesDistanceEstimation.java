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
package info.ponciano.lab.jpc.algorithms.segmentation;

import info.ponciano.lab.jpc.algorithms.Algorithm;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;

/**
 * {@code MinPatchesDistanceEstimation } aims to calculate the minimum distance
 * between two point clouds according to their oriented bounding box.
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public class MinPatchesDistanceEstimation implements Algorithm<Double> {

    private APointCloud patch1, patch2;
    private double output;

    /**
     * Creates new instance of {@code MinPatchesDistanceEstimation } to compute
     * the distance between two point cloud
     *
     * @param patch1 first point cloud.
     * @param patch2 second point cloud.
     */
    public MinPatchesDistanceEstimation(APointCloud patch1, APointCloud patch2) {
        this.patch1 = patch1;
        this.patch2 = patch2;
    }

    @Override
    public void run() {
        this.output = this.patch1.getOBB().distance(this.patch2.getOBB());
    }

    @Override
    public Double getResults() {
        return output;
    }

    public APointCloud getPatch2() {
        return patch2;
    }

    public APointCloud getPatch1() {
        return patch1;
    }

}
