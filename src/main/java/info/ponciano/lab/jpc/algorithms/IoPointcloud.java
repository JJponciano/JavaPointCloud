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
package info.ponciano.lab.jpc.algorithms;

import info.ponciano.lab.jpc.pointcloud.Pointcloud;
import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.vector.Normal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class IoPointcloud {

    public static boolean saveASCII(String path, Pointcloud cloud) {
        Boolean result = true;
        BufferedWriter writer = null;
        try {
            File fileio = new File(path);
            Charset charset = Charset.forName("UTF8");
            writer = Files.newBufferedWriter(fileio.toPath(), charset);
            // -------------------------------------------------
            /* write cloud */
            Map<String, APointCloud> patches = cloud.getPatches();
            Set<String> keySet = patches.keySet();
            for (String k : keySet) {
                APointCloud patch = patches.get(k);
                Iterator<Point> points = patch.iterator();

                while (points.hasNext()) {
                    Point next = points.next();
                    StringBuilder txt = new StringBuilder();
                    txt.append(next.getCoords().getX()).append("\t")
                            .append(next.getCoords().getY()).append("\t")
                            .append(next.getCoords().getZ()).append("\t")
                            .append(next.getColor().getRed()).append("\t")
                            .append(next.getColor().getGreen()).append("\t")
                            .append(next.getColor().getBlue()).append("\t")
                            .append(next.getNormal().getX()).append("\t")
                            .append(next.getNormal().getY()).append("\t")
                            .append(next.getNormal().getZ()).append("\t")
                            .append(k).append("\n");
                    writer.write(txt.toString());
                }

            }//end patches saving
            writer.write(saveSet(cloud.getIrregular().iterator(), Pointcloud.IRREGULAR_K));
            writer.write(saveSet(cloud.getRegular().iterator(), Pointcloud.REGULAR_K));
            writer.write(saveSet(cloud.getIsolated().iterator(), Pointcloud.ISOLATED_K));
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            result = false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                result = false;
            }
        }
        return result;
    }

    public static String saveSet(Iterator<String> irregular, char key) {

        StringBuilder txt = new StringBuilder();
        if (irregular.hasNext()) {

            txt.append(SET_K).append("\t").append(key).append("\t");
            while (irregular.hasNext()) {
                String next = irregular.next();
                txt.append(next);
                if (irregular.hasNext()) {
                    txt.append("\t");
                }
            }
            txt.append("\n");
        }
        return txt.toString();

        // -------------------------------------------------
    }
    public static final char SET_K = '%';

    public static Pointcloud loadASCII(String path) throws FileNotFoundException {
        Pointcloud cloud = new Pointcloud();
        if (loadASCII(path, cloud)) return null;
        return cloud;
    }

    public static boolean loadASCII(String path, Pointcloud cloud) throws FileNotFoundException, NumberFormatException {
        BufferedReader reader = null;
        IOException error = null;
        // read the file
        File fileio = new File(path);
        if (!fileio.exists()) {
            throw new FileNotFoundException(path + " not found");
        }
        try {
            reader = Files.newBufferedReader(fileio.toPath(), StandardCharsets.UTF_8);
            // read each other line; TODO 2.0 use the point count.
            String line;
            double dx, dy, dz, dnx = 0, dny = 0, dnz = 0;
            String id;
            short r = 255;
            short g = 255;
            short b = 255;

            Set<String> regular = new LinkedHashSet<>();
            Set<String> irregular = new LinkedHashSet<>();
            Set<String> isolated = new LinkedHashSet<>();
            // FOR EACH LINE
            while ((line = reader.readLine()) != null) {
                // get regex with white character
                String[] split = line.split("\\s");
                if (split.length > 1 && split[0].charAt(0) == SET_K) {
                    switch (split[1].charAt(0)) {
                        case Pointcloud.ISOLATED_K:
                            for (int i = 2; i < split.length; i++) {
                                isolated.add(split[i]);
                            }
                            break;
                        case Pointcloud.IRREGULAR_K:
                            for (int i = 2; i < split.length; i++) {
                                irregular.add(split[i]);
                            }
                            break;
                        case Pointcloud.REGULAR_K:
                            for (int i = 2; i < split.length; i++) {
                                regular.add(split[i]);
                            }
                            break;
                        default:
                            System.err.println("Unknow key for the point cloud loading:" + split[0] + " info.ponciano.lab.jpc.algorithms.IoPointcloud.loadASCII()");
                    }
                } else // if the line is not a comment
                    if (line.charAt(0) != '#' && line.charAt(0) != '/') {
                        
                        id = "0";
                        try {
                            dx = Double.parseDouble(split[0]);
                        } catch (NumberFormatException e) {
                            dx = 0;
                        }
                        try {
                            dy = Double.parseDouble(split[1]);
                        } catch (NumberFormatException e) {
                            dy = 0;
                        }
                        try {
                            dz = Double.parseDouble(split[2]);
                        } catch (NumberFormatException e) {
                            dz = 0;
                        }
                        
                        if (split.length >= 6) {
                            r = Short.parseShort(split[3]);
                            g = Short.parseShort(split[4]);
                            b = Short.parseShort(split[5]);
                        }
                        if (split.length >= 9) {
                            try {
                                dnx = Double.parseDouble(split[6]);
                            } catch (NumberFormatException e) {
                                dnx = 0;
                            }
                            try {
                                dny = Double.parseDouble(split[7]);
                            } catch (NumberFormatException e) {
                                dny = 0;
                            }
                            try {
                                dnz = Double.parseDouble(split[8]);
                            } catch (NumberFormatException e) {
                                dnz = 0;
                            }
                        }
                        if (split.length >= 10) {
                            try {
                                id = split[9];
                            } catch (NumberFormatException e) {
                                id = "0";
                            }
                            
                        }
                        
                        Point point3D = new Point(new Coord3D(dx, dy, dz), new Color(r, g, b), new Normal(dnx, dny, dnz));
                        if (cloud.contains(id)) {
                            cloud.get(id).add(point3D);
                        } else {
                            PointCloudMap patch = new PointCloudMap();
                            patch.add(point3D);
                            cloud.add(id, patch);
                        }
                    }
            }//end read line
            cloud.setIrregular(irregular);
            cloud.setRegular(regular);
            cloud.setIsolated(isolated);
        } catch (IOException ex) {
            error = ex;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                error = ex;
            }
        }
        if (error != null) {
            System.err.println(error.getMessage());
            return true;
        }
        return false;
    }

}
