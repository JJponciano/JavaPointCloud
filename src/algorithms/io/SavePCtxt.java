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

import PointCloud.PointCloud;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 *
 * @author Jean-Jacques Ponciano.
 */
public class SavePCtxt extends IoPointCloud {
       protected PointCloud cloud;

    /**
     * Creates a new instance of <code>SavePCbin</code>.
     *
     * @param cloud cloud to be saved.
     * @param filepath The path of the file containing the point cloud.
     */
    public SavePCtxt(PointCloud cloud, String filepath) {
        super(filepath);
        this.cloud = cloud;
    }

    @Override
    public void run() {
        this.isready = false;
       String txt = this.cloud.toString();
        File fileio = new File(filepath);
        Charset charset = Charset.forName("UTF8");
        try (BufferedWriter writer = Files.newBufferedWriter(fileio.toPath(), charset)) {
            writer.write(txt, 0, txt.length());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.isready = true;
    }
    
}
