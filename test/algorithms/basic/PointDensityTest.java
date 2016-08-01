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

import algorithms.io.ReadPCfromTXT;
import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pointcloud.PointCloud;

/**
 *
 * @author jean-jacques.poncian
 */
public class PointDensityTest {
    
    public PointDensityTest() {
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
     * Test of run method, of class PointDensity.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        String path=getClass().getResource("../../resources/table.txt").getPath();
        assertTrue(new File(path).exists());
        //read point cloud 
        ReadPCfromTXT reader=new ReadPCfromTXT(path);
        reader.run();
        PointCloud cloud = reader.getCloud();
        //calculating density
        PointDensity instance = new PointDensity(cloud,AreaPC.Unit.cm3);
        
        instance.run(); 
        System.out.println("Point density:"+instance.getDensity());
        double expected=6.706579471028027;
        assertEquals(expected, instance.getDensity(), 0.000001);
       
    }

    
}
