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
import info.ponciano.lab.jpc.math.Color;

/**
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public class RegionColor extends RegionEuclidean {

    public static final int ACCURACY = 69;

    /**
     *
     * @param points points of the region
     * @param distance the minimum distance between segments to consider them as
     * contiguous.
     */
    public RegionColor(final APointCloud points, double distance) {
        super(points, distance);
    }


    /**
     * Test if the regions can merge with another
     *
     * @param reg2 second region to test
     * @return true if both region can be merge in one, false otherwise.
     */
    @Override
    public boolean canMerge(Region reg2) {
        if (!super.canMerge(reg2)) {
            return false;
        }
//        double normalD = this.getMeanNormal().distance(reg2.getMeanNormal());
//        return normalD <= this.normalDistance;
        Color c1 = this.getMeanColor().asHumanPerception(ACCURACY);
        Color c2 = reg2.getMeanColor().asHumanPerception(ACCURACY);
        return c1.equals(c2);
    }


}
