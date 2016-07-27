/*
 * Copyright (C) 2016 jean-jacques.poncian.
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

import pointcloud.PointCloud;
import pointcloud.PointColor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jean-jacques.poncian
 */
public class CenterPCTest {

    public CenterPCTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of run method, of class CenterPC.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        PointCloud pc = new PointCloud();

        pc.add(new PointColor(10, 10, 10));
        pc.add(new PointColor(100, 8, 10));
        pc.add(new PointColor(10, 10, 100));
        CenterPC instance = new CenterPC(pc);
        instance.run();
        PointCloud cloud = instance.getCloud();
        PointColor get = cloud.get(0);
        PointColor get1 = cloud.get(1);
        PointColor get2 = cloud.get(2);
        assertEquals(get, new PointColor(0, 0, 0));
        assertEquals(get1, new PointColor(90, -2, 0));
        assertEquals(get2, new PointColor(0, 0, 90));
    }

}
