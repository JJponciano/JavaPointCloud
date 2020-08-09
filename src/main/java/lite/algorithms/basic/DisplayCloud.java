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
package lite.algorithms.basic;

import lite.pointcloud.PointCloud;
import lite.pointcloud.PointCloudView;
import lite.algorithms.IAlgorithm;
import com.jogamp.opengl.awt.GLCanvas;
import info.ponciano.lab.jpc.opengl.DrawingScene;
import info.ponciano.lab.jpc.opengl.IObjectGL;
import javax.swing.JDialog;

/**
 * A algorithm to display a Point cloud in no modal windows.
 *
 * @author Jean-Jacques Ponciano.
 */
public class DisplayCloud implements IAlgorithm {

    protected PointCloud cloud;
    protected boolean isReady;
    protected int width;
    protected int height;
    private JDialog jd;
    private DrawingScene scene;
    private PointCloudView view;

    /**
     * Creates a new instance of <code>DisplayCloud</code>.
     *
     * @param cloud cloud to be centered.
     * @param width width of the windows.
     * @param height height of the windows.
     */
    public DisplayCloud(PointCloud cloud, int width, int height) {

        this.isReady = true;
        this.cloud = cloud;
        this.width = width;
        this.height = height;
        this.view = new PointCloudView(this.cloud);
        this.scene = new DrawingScene();
        this.scene.addObject((IObjectGL) view);
    }

    /**
     * Creates a new instance of <code>DisplayCloud</code>.
     *
     * @param width width of the windows.
     * @param height height of the windows.
     */
    public DisplayCloud(int width, int height) {
        this.width = width;
        this.height = height;
        this.scene = new DrawingScene();
    }

    @Override
    public boolean isReady() {
        return this.isReady;
    }

    @Override
    public void run() {
        this.isReady = false;
        this.jd = new JDialog();
        jd.setSize(this.width, this.height);
        GLCanvas canvas = scene.getCanvas(this.width, this.height);
        jd.add(canvas);
        jd.setModal(false);
        jd.setVisible(true);
        this.isReady = true;
    }

    /**
     * Adds a object
     *
     * @param ob object to be added at the scene
     */
    public void addObject(IObjectGL ob) {
        scene.addObject(ob);
    }

    /**
     * Close the windows
     */
    public void close() {
        jd.dispose();
        this.isReady = true;
    }

    public PointCloud getCloud() {
        return cloud;
    }

    public void setCloud(PointCloud cloud) {
        this.cloud = cloud;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
