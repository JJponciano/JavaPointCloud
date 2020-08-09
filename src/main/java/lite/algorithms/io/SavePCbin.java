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
package lite.algorithms.io;

import lite.pointcloud.Point;
import lite.pointcloud.PointCloud;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class SavePCbin extends IoPointCloud {

    protected PointCloud cloud;

    /**
     * Creates a new instance of <code>SavePCbin</code>.
     *
     * @param cloud cloud to be saved.
     * @param filepath The path of the file containing the point cloud.
     */
    public SavePCbin(PointCloud cloud, String filepath) {
        super(filepath);
        this.cloud = cloud;
    }

    @Override
    public void run() {
        this.isready = false;
        // initialization of the stream.
        DataOutputStream oos = null;
        try {
            // open the stream from the file
            oos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));

            //write the number of point
            oos.writeInt(this.cloud.size());
            for (Point point : this.cloud.getPoints()) {
                // write points coordinates
                oos.writeFloat(point.getX());
                oos.writeFloat(point.getY());
                oos.writeFloat(point.getZ());
            }

        } catch (final java.io.IOException e) {
            System.err.println(e);
        } finally {
            try {
                if (oos != null) {
                    // empty the buffer
                    oos.flush();
                    // close the file
                    oos.close();
                }
            } catch (final IOException ex) {
                System.err.println(ex);
            }
        }
        this.isready = true;
    }
}
