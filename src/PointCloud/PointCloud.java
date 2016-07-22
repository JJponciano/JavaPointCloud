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
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jean-Jacques Ponciano.
 */
public class PointCloud implements IPointCloud {

    protected ArrayList<Point> points;

    @Override
    public void loadTXT(String filepath) throws FileNotFoundException {
        this.fireStartLoading();
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
                    this.points.add(p);
                }
            }
            //send signal
            this.fireEndLoading();
            this.fireCloudChange();

        } catch (IOException ex) {
            Logger.getLogger(PointCloud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadBin(String filepath) throws FileNotFoundException {
            this.fireStartLoading();
        //create the file
        File file = new File(filepath);
        // test if the file exists
        if (!file.exists()) // if it does not exists throws a exception.
        {
            throw new java.io.FileNotFoundException("the file with the path " + filepath + "does not found");
        }
        DataInputStream ois = null;
        try {
            ois = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
            //read number of point
            int nbPoint = ois.readInt();
            this.points.clear();
            //read and build each point
            for (int i = 0; i < nbPoint; i++) {
                float x = ois.readFloat();
                float y = ois.readFloat();
                float z = ois.readFloat();
                this.points.add(new Point(x, y, z));
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
        //send signal
        this.fireCloudChange();
        //send signal
        this.fireEndLoading();
    }

    @Override
    public void saveBin(String filepath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveTXT(String filepath, boolean color) throws FileNotFoundException {
        String txt = this.toString();
        File fileio = new File(filepath);
        Charset charset = Charset.forName("UTF8");
        try (BufferedWriter writer = Files.newBufferedWriter(fileio.toPath(), charset)) {
            writer.write(txt, 0, txt.length());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void center() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void scale(float x, float y, float z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void switchYZ() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void switchZX() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setColor(Color color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getEXT() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fireStartLoading() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fireEndLoading() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void fireCloudChange() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
