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
package info.ponciano.lab.jpc.algorithms;

import info.ponciano.lab.jpc.pointcloud.Pointcloud;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
 */
public class IoPointcloudTest {

    final String path;

    public IoPointcloudTest() {
        this.path = "src/test/resources/test_part_small.txt";
    }


    /**
     * Test of saveASCII method, of class IoPointcloud.
     */
    @Test
    public void testSaveASCII() {
        System.out.println("saveASCII");
        try {
            // load a classic xyz point cloud
            Pointcloud instance = IoPointcloud.loadASCII(path);
            //refactor the pointcloud to creates patches
            instance.refactor();
            assertTrue(instance.getPatches().size() > 1);

            String testSaveASCIIxyz = "testSaveASCII.xyz";
            IoPointcloud.saveASCII(testSaveASCIIxyz, instance);
            Pointcloud result = IoPointcloud.loadASCII(testSaveASCIIxyz);
//             instance = IoPointcloud.loadASCII(testSaveASCIIxyz);
            assertEquals(instance.getPoints().size(), result.getPoints().size());
            assertEquals(instance, result);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pointcloud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of loadASCII method, of class IoPointcloud.
     */
    @Test
    public void testLoadASCII() {
        System.out.println("loadASCII");
//        int expResult = 469341;
//        expResult = 468815;
//        try {
//            Pointcloud result = null;
//
//            result = IoPointcloud.loadASCII(path);
//
//            System.out.println(result.size());
//            assertEquals(expResult, result.size());
//        } catch (FileNotFoundException ex) {
//            fail(ex.getMessage());
//        }
    }

}
