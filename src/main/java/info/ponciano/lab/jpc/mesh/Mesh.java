
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
package info.ponciano.lab.jpc.mesh;

import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.Point;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import info.ponciano.lab.jpc.mesh.ply.PLY;
import info.ponciano.lab.jpc.mesh.ply.PLYElementList;
import info.ponciano.lab.jpc.mesh.ply.PLYType;
import static info.ponciano.lab.jpc.mesh.ply.PLYType.FLOAT32;
import static info.ponciano.lab.jpc.mesh.ply.PLYType.LIST;
import static info.ponciano.lab.jpc.mesh.ply.PLYType.UINT32;
import static info.ponciano.lab.jpc.mesh.ply.PLYType.UINT8;
import info.ponciano.lab.jpc.mesh.ply.PLY_Plotly;

/**
 *
 * @author Dr Jean-Jacques Ponciano Contact: jean-jacques@ponciano.info
 */
public class Mesh {

    protected List<Polygon> polygons;
    protected Color color;

    public Mesh() {
        this.polygons = new ArrayList<>();
        this.color = Color.WHITE;
    }

    /**
     * Set the color of every polygons
     *
     * @param color new color to be applied.
     */
    public final void setColor(Color color) {
        this.color = color;
        polygons.forEach((point) -> {
            point.setColor(color);
        });
    }

    public Mesh(Coord3D[] vertices, int[][] faceIndices) {
        this.polygons = new ArrayList<>();
        this.build(vertices, faceIndices);
        this.color = Color.WHITE;
    }

    private void build(Coord3D[] vertices, int[][] faceIndices) {
        for (int[] face : faceIndices) {
            Polygon pol = this.getNewPolygon();
            for (int i : face) {
                pol.add(new Point(vertices[i]));
            }
            this.polygons.add(pol);
        }
    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    protected Polygon getNewPolygon() {
        return new Polygon();
    }

    /**
     * Exports the mesh in PLY ASCII file
     *
     * @param path path of the file with the PLY extension.
     * @param comments comments to add or empty for no comment
     */
    public void exportPly(String path, String comments) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
