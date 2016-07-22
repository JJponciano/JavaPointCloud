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

import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DisplayCloudTest {
    
    public DisplayCloudTest() {
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
     * Test of close method, of class DisplayCloud.
     */
    @Test
    public void testRun() {
        System.out.println("close");
        try {
           
            DisplayCloud instance = new DisplayCloud(null, 1000, 1000);
            assertTrue(instance.isReady());
            instance.run();
            Thread.sleep(1000);
            instance.close();
             assertTrue(instance.isReady());
             

        } catch (InterruptedException ex) {
            Logger.getLogger(DisplayCloudTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
