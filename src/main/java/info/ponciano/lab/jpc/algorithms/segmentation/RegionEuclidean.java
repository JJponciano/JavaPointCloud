
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

import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.math.PiMath;
import info.ponciano.lab.jpc.math.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public class RegionEuclidean extends Region {

    protected double distance;

    @Override
    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param points points of the region
     * @param distance the minimum distance between segments to consider them as
     * contiguous.
     */
    public RegionEuclidean(final APointCloud points, double distance) {
        super(points);
        this.distance = distance;
    }

    @Override
    protected boolean isCloseX(Region reg2) {
        double distance = Math.abs(this.getCentroid().getX() - reg2.getCentroid().getX());// fast way
        return distance <= this.distance;
    }

    @Override
    protected boolean isClose(Region reg2) {
        double distance = this.getCentroid().distance(reg2.getCentroid());// fast way
        return distance <= this.distance;
    }

    @Override
    protected boolean isSimilar(Region reg2) {
        double distance = this.getCentroid().distance(reg2.getCentroid());// fast way
        return distance <= this.distance;
    }

    @Override
    protected Region createRegion(APointCloud points) {
        return new RegionEuclidean(points, distance);
    }

}
