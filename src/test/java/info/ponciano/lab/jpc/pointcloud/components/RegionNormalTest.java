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
package info.ponciano.lab.jpc.pointcloud.components;

import info.ponciano.lab.jpc.algorithms.segmentation.RegionNormal;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.vector.Normal;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public class RegionNormalTest {

    public RegionNormalTest() {
    }

 

    /**
     * Test of canMerge method, of class RegionNormal.
     */
    @Test
    public void testCanMerge() {
        System.out.println("canMerge");
        Point p1 = new Point(new Coord3D(0, 0, 0), Color.coral, new Normal(-0.73, 0.66, 0.14));
        Point p2 = new Point(new Coord3D(0, 0, 0), Color.coral, new Normal(0.05, 0.02, 0.9));
 Point p3 = new Point(new Coord3D(0, 0, 0), Color.coral, new Normal(0.04, -0.03, 0.9));
        PointCloudMap rpc = new PointCloudMap();
        rpc.add(p1);
         PointCloudMap rpc2 = new PointCloudMap();
        rpc2.add(p2);
         PointCloudMap rpc3 = new PointCloudMap();
        rpc3.add(p3);
        RegionNormal reg2 = new RegionNormal(rpc, 0.1);
        RegionNormal instance =  new RegionNormal(rpc2, 0.1);
         RegionNormal instance2 =  new RegionNormal(rpc3, 0.1);
        boolean expResult = false;
        boolean result = instance.canMerge(reg2);
        assertEquals(expResult, result);
          expResult = true;
         result = instance2.canMerge(instance);
        assertEquals(expResult, result);

        
    }

}
