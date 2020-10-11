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
package info.ponciano.lab.jpc.examples;

import info.ponciano.lab.jpc.algorithms.IoPointcloud;
import info.ponciano.lab.jpc.algorithms.ShowPointcloud;
import info.ponciano.lab.jpc.pointcloud.Pointcloud;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dr Jean-Jacques Ponciano (Contact: jean-jacques@ponciano.info)
 */
public class CenterExample {

    public static void main(String[] args) {
        try {
            System.out.println("Loading");
            // load a classic xyz point cloud "testSaveASCII.xyz"
            Pointcloud instance = IoPointcloud.loadASCII("D:\\2020\\2020_data_IPM\\test\\test_part.txt");
            System.out.println("End loading: ");
            instance.getPoints().center();
            instance.saveASCII("centered.xyz");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pointcloud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Pointcloud typePatches(Pointcloud instance) {
        int size = instance.getPatches().size();
        System.out.println("Points: " + instance.size());
        System.out.println("Patches: " + size);
        //refactor the pointcloud to creates patches
        if (size == 1) {
            System.out.println("Refactoring");
            instance.refactor();
            System.out.println("End: ");
        }
        System.out.println("Colorizing");
        ///random colorizes every patch
        instance.typeColorizesPatches();
        return instance;
    }

    public static Pointcloud colorPatches(Pointcloud instance) {
        int size = instance.getPatches().size();
        System.out.println("Points: " + instance.size());
        System.out.println("Patches: " + size);
        //refactor the pointcloud to creates patches
        if (size == 1) {
            System.out.println("Refactoring");
            instance.refactor();
        }
        System.out.println("Colorizing");
        ///random colorizes every patch
        instance.randomColorizesPatches();
        return instance;

    }
}
