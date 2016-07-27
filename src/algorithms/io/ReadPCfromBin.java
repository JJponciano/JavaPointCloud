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
package algorithms.io;

import pointcloud.PointColor;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class ReadPCfromBin extends PCreader {

    /**
     * Creates a new instance of <code>ReadPCfromBin</code>.
     *
     * @param filepath The path of the file containing the point cloud.
     */
    public ReadPCfromBin(String filepath) {
        super(filepath);
    }

    @Override
    public void run() {
        this.isready = false;
        //create the file
        File file = new File(filepath);
        // test if the file exists
        if (file.exists()) // if it does not exists throws a exception.
        {
            DataInputStream ois = null;
            try {
                ois = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
                //read number of point
                int nbPoint = ois.readInt();
                //read and build each point
                for (int i = 0; i < nbPoint; i++) {
                    float x = ois.readFloat();
                    float y = ois.readFloat();
                    float z = ois.readFloat();
                    this.cloud.add(new PointColor(x, y, z));
                }
                ois.close();
            } catch (final java.io.IOException e) {
                System.err.println(e);
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (final IOException ex) {
                    System.err.println(ex);
                }
            }
        }
        this.isready = true;
    }

}
