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

/**
 *
 * @author Jean-Jacques Ponciano
 */
public abstract class IoPointCloud implements IAlgorithm{
    protected String filepath;
    protected boolean isready;

    /**
     * Creates a new instance of <code>IoPointCloud</code>.
     *
     * @param filepath The path of the file containing the point cloud.
     */
    public IoPointCloud(String filepath) {
        this.filepath = filepath;
        this.isready = true;
    }

    @Override
    public boolean isReady() {
        return this.isready;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }
    
}
