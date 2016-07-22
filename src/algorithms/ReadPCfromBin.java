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
package algorithms;

import PointCloud.PointCloud;
import PointCloud.PointColor;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jean-Jacques Ponciano
 */
public class ReadPCfromBin {

    public PointCloud loadTXT(String filepath) throws FileNotFoundException {
        PointCloud cloud = new PointCloud();
        //read the file
        File fileio = new File(filepath);
        try (BufferedReader reader = Files.newBufferedReader(fileio.toPath(),
                StandardCharsets.UTF_8)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //test if the line is not a comment
                if (line.charAt(0) != '#') {
                    //if it not a comment, split the line in different value
                    String[] split = line.split("\\s");
                    PointColor p = new PointColor(Float.parseFloat(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
                    if (split.length == 6) {
                        p.setColor(new Color(Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5])));
                    }
                    cloud.add(p);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PointCloud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cloud;
    }
}
