/*
 * Copyright (C) 2016 Jean-Jacques Ponciano.
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
package PointCloud;

import java.awt.Color;
import java.io.FileNotFoundException;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public interface IPointCloud {

    /**
     * Load a point cloud from a text file
     *
     * @param filepath The path of the file containing the point cloud.
     * @throws java.io.FileNotFoundException When the file is not found.
     */
    public void loadTXT(String filepath) throws FileNotFoundException;

    /**
     * Load a cloud from a PIC file
     *
     * @param filepath The path of the file containing the point cloud.
     * @throws java.io.FileNotFoundException When the file is not found.
     */
    public void loadBin(String filepath) throws FileNotFoundException;

    /**
     * Save the cloud in a binary file
     *
     * @param filepath The path of the file where to save the point cloud.
     *
     */
    public void saveBin(String filepath);

    /**
     * Save a point cloud in a text file
     *
     * @param filepath The path of the file where to save the point cloud.
     * @param color True to save the point cloud with its color, false otherwise.
     * @throws java.io.FileNotFoundException When the file is not found.
     *
     */
    public void saveTXT(String filepath, boolean color) throws FileNotFoundException;


}
