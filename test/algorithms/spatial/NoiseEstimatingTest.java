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
package algorithms.spatial;

import algorithms.basic.DisplayCloud;
import algorithms.io.ReadPCfromTXT;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pointcloud.PointCloud;
import pointcloud.PointColor;

/**
 *
 * @author jean-jacques.poncian
 */
public class NoiseEstimatingTest {
    
    public NoiseEstimatingTest() {
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
     * Test of run method, of class NoiseEstimating.
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
        NoiseEstimating noise=new NoiseEstimating(cloud, Unit.cm3);
        noise.run();
    }

    /**
     * Test of isReady method, of class NoiseEstimating.
     */
    @Test
    public void testIsReady() {
        System.out.println("isReady");
    }

    /**
     * Test of getNoise method, of class NoiseEstimating.
     */
    @Test
    public void testGetNoise() {
        System.out.println("getNoise");
    }
    /**
     * Test of getNoisePoints method, of class NoiseEstimating.
     */
    @Test
    public void testGetNoisePoints() {
        System.out.println("getNoisePoints");
        String path=getClass().getResource("../../resources/table.txt").getPath();
          assertTrue(new File(path).exists());
        //read point cloud 
        ReadPCfromTXT reader=new ReadPCfromTXT(path);
        reader.run();
        PointCloud cloud = reader.getCloud();
        NoiseEstimating noise=new NoiseEstimating(cloud, Unit.mm3);
        noise.run();
        //get noised point
        ArrayList<PointColor> noisePoints = noise.getNoisePoints();
        for (PointColor noisePoint : noisePoints) {
            noisePoint.setColor(Color.red);
        }
        DisplayCloud display=new DisplayCloud(noise.getCloud(), 1000, 1000);
        display.run();
         //----------------------------------------------------------------------
            System.out.println("Exit:press key");
        
    }
    
}
