/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
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
package info.ponciano.lab.jpc.algorithms;

import info.ponciano.lab.jpc.algorithms.segmentation.MinPatchesDistanceEstimation;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import lite.pointcloud.PointCloud;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 */
public class MinPatchesDistanceEstimationTest {

    public MinPatchesDistanceEstimationTest() {
    }

    /**
     * Test of run method, of class MinPatchesDistanceEstimation.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        PointCloudMap pcm1 = new PointCloudMap();
        PointCloudMap pcm2 = new PointCloudMap();
        PointCloudMap pcm3 = new PointCloudMap();

        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 1, 1);
        Point p3 = new Point(0.5, 0.5, 0.5);
        Point p4 = new Point(0.4, 0.4, 0.4);
        Point p5 = new Point(0.2, 0.2, 0.2);

        Point p6 = new Point(10, 10, 10);
        Point p7 = new Point(10, 10, 10);
        Point p8 = new Point(10.5, 10.5, 10.5);
        Point p9 = new Point(10.4, 10.4, 10.4);
        Point p10 = new Point(10.2, 10.2, 10.2);
        pcm1.add(p1);
        pcm1.add(p2);
        pcm1.add(p3);
        pcm1.add(p4);
        pcm1.add(p5);

        pcm2.add(p6);
        pcm2.add(p7);
        pcm2.add(p8);
        pcm2.add(p9);
        pcm2.add(p10);

        pcm3.add(p3);
        pcm3.add(p4);
        pcm3.add(p5);

        pcm3.add(p6);
        pcm3.add(p7);
        pcm3.add(p8);

        MinPatchesDistanceEstimation instance = new MinPatchesDistanceEstimation(pcm1, pcm2);
        instance.run();
        assertEquals(15.58, instance.getResults(), 0.01);
        
         MinPatchesDistanceEstimation instance2 = new MinPatchesDistanceEstimation(pcm3, pcm2);
        instance2.run();
        assertEquals(0, instance2.getResults(), 0.01);
         MinPatchesDistanceEstimation instance3 = new MinPatchesDistanceEstimation(pcm3, pcm1);
        instance3.run();
        assertEquals(0, instance3.getResults(), 0.01);
    }

    /**
     * Test of getResults method, of class MinPatchesDistanceEstimation.
     */
    @Test
    public void testGetResults() {
        System.out.println("getResults=run");
    }

}
