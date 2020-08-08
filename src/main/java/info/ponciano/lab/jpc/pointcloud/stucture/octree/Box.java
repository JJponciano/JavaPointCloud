/*
 * Copyright (C) 2020 Dr Jean-Jacques Ponciano <jean-jacques@ponciano.info>
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
package info.ponciano.lab.jpc.pointcloud.stucture.octree;

import info.ponciano.lab.jpc.math.Splitable;
import info.ponciano.lab.jpc.math.Color;
import info.ponciano.lab.jpc.math.Coord3D;
import info.ponciano.lab.jpc.math.PiMath;
import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Dr. Jean-Jacques Ponciano
 */
public class Box implements Splitable{

    protected Point[] points;

    /**
     * Creates a new instance of <code>Box</code>.
     *
     * @param p1 first point of the diagonal.
     * @param p2 second point of the diagonal.
     */
    public Box(Point p1, Point p2) {

        this.points = new Point[8];
        this.points[0] = new Point(p1);
        this.points[this.points.length - 1] = new Point(p2);
        this.initBox();
    }

    /**
     * Creates the box composed of 8 points
     *
     * @param p 8 points in a list
     */
    public Box(List<Point> p) {
        Collections.sort(p);
        this.points = new Point[8];
        for (int i = 0; i < 8; i++) {
            this.points[i] = new Point(p.get(i));
        }
    }

    /**
     * Split the box according to the dimension (4 box in 2D, 8 box in 3D).
     *
     * @return Sub box created after the split.
     */
    @Override
    public ArrayList split() {
        // get the center point of the cube of box.
        Point centre = new Point(this.points[0].getCoords().medium(this.points[this.points.length - 1].getCoords()));
        // create the number of box corresponding to the number of points
        ArrayList<Box> boxes = new ArrayList<>();
        for (Point point : this.points) {
            boxes.add(new Box(point, centre));
        }
        return boxes;
    }

    private void initBox() {

        this.points[1] = new Point(this.points[0].getCoords().getX(),
                this.points[this.points.length - 1].getCoords().getY(), this.points[0].getCoords().getZ());
        this.points[2] = new Point(this.points[this.points.length - 1].getCoords().getX(),
                this.points[0].getCoords().getY(), this.points[0].getCoords().getZ());
        // if the box is in 3D
        this.points[3] = new Point(this.points[this.points.length - 1].getCoords().getX(),
                this.points[this.points.length - 1].getCoords().getY(), this.points[0].getCoords().getZ());
        this.points[4] = new Point(this.points[0].getCoords().getX(), this.points[0].getCoords().getY(),
                this.points[this.points.length - 1].getCoords().getZ());
        this.points[5] = new Point(this.points[0].getCoords().getX(),
                this.points[this.points.length - 1].getCoords().getY(),
                this.points[this.points.length - 1].getCoords().getZ());
        this.points[6] = new Point(this.points[this.points.length - 1].getCoords().getX(),
                this.points[0].getCoords().getY(), this.points[this.points.length - 1].getCoords().getZ());

    }

    /**
     * Gets the length of the box
     *
     * @return The length of the box diagonal.
     */
    public double length() {
        return this.points[0].getCoords().distance(this.points[this.points.length - 1].getCoords());
    }

    /**
     * Gets the area of the box.
     *
     * @return the area of the box.
     */
    public double getVolume() {
        // get the length in X
        double xl = Math
                .abs(this.points[0].getCoords().getX() - this.points[this.points.length - 1].getCoords().getX());
        // get the length in Y
        double yl = Math
                .abs(this.points[0].getCoords().getY() - this.points[this.points.length - 1].getCoords().getY());
        // If the box is in 3D
        if (this.points.length > 4) {
            // get the length in Z
            double zl = Math
                    .abs(this.points[0].getCoords().getZ() - this.points[this.points.length - 1].getCoords().getZ());
            return xl * yl * zl;
        } else {
            return xl * yl;
        }
    }

    public Point[] getPoints() {
        return points;
    }

    /**
     * Get all faces of the box with a sorted array of point.
     * <p>
     * index 0 to 3 represent the first face,index 4 to 7 represent the second
     * face ...
     * </p>
     *
     * @return array of point representing the face
     */
    public Point[] getPointFace() {
        Point[] faces;
        if (points.length > 4) {
            faces = new Point[6 * 4];
            faces[0] = this.points[0];
            faces[1] = this.points[2];
            faces[2] = this.points[3];
            faces[3] = this.points[1];

            faces[4] = this.points[2];
            faces[5] = this.points[6];
            faces[6] = this.points[7];
            faces[7] = this.points[3];

            faces[8] = this.points[6];
            faces[9] = this.points[4];
            faces[10] = this.points[5];
            faces[11] = this.points[7];

            faces[12] = this.points[4];
            faces[13] = this.points[0];
            faces[14] = this.points[1];
            faces[15] = this.points[5];

            faces[16] = this.points[1];
            faces[17] = this.points[3];
            faces[18] = this.points[7];
            faces[19] = this.points[5];

            faces[20] = this.points[0];
            faces[21] = this.points[4];
            faces[22] = this.points[6];
            faces[23] = this.points[2];
        } else {
            faces = new Point[4];
            faces[0] = this.points[0];
            faces[1] = this.points[2];
            faces[2] = this.points[3];
            faces[3] = this.points[1];
        }
        return faces;
    }

    public List<Point[]> getPointFaces() {
        List<Point[]> pf = new ArrayList<>();
        Point[] faces = new Point[4];
        faces[0] = this.points[0];
        faces[1] = this.points[2];
        faces[2] = this.points[3];
        faces[3] = this.points[1];
        pf.add(faces);

        faces = new Point[4];
        faces[0] = this.points[2];
        faces[1] = this.points[6];
        faces[2] = this.points[7];
        faces[3] = this.points[3];
        pf.add(faces);
        faces = new Point[4];
        faces[0] = this.points[6];
        faces[1] = this.points[4];
        faces[2] = this.points[5];
        faces[3] = this.points[7];
        pf.add(faces);
        faces = new Point[4];
        faces[0] = this.points[4];
        faces[1] = this.points[0];
        faces[2] = this.points[1];
        faces[3] = this.points[5];
        pf.add(faces);
        faces = new Point[4];
        faces[0] = this.points[1];
        faces[1] = this.points[3];
        faces[2] = this.points[7];
        faces[3] = this.points[5];
        pf.add(faces);
        faces = new Point[4];
        faces[0] = this.points[0];
        faces[1] = this.points[4];
        faces[2] = this.points[6];
        faces[3] = this.points[2];
        pf.add(faces);
        return pf;
    }

    /**
     * get the shortest distance between both boxes Execution times:
     * <p>
     * <ul>
     * <li>8ms:10</li>
     * <li>20ms:100</li>
     * <li>123ms:1000 502ms:10 000</li>
     * <li>4s, 625ms:100 000</li>
     * <li>46s, 73ms:1 000 000
     * <li>7m, 51s, 58ms:10 000 000</li>
     * <li>2h, 3m, 15s, 204ms:100 000 000</li>
     * </ul>
     * </p>
     *
     * @param b box for comparison
     * @return the shortest distance between both boxes
     */
    public double distance(Box b) {
        List<Point[]> pointFaces = this.getPointFaces();
        List<Point[]> pointFaces2 = b.getPointFaces();

        List<Double> distances = new ArrayList<>();
        pointFaces.forEach(s1 -> {
            pointFaces2.forEach(s2 -> {
                distances.add(distance2Faces(s1, s2));
            });
        });
        return PiMath.min(distances);
    }

    /**
     * Computes shortest distance between 2 faces each composed of 4 points
     *
     * @param f1 first face
     * @param f2 second face
     * @return shortest distance between two face
     */
    public static double distance2Faces(Point[] f1, Point[] f2) {
        // get each seg
        Coord3D[] c1 = {f1[0].getCoords(), f1[1].getCoords()};
        Coord3D[] c2 = {f1[1].getCoords(), f1[2].getCoords()};
        Coord3D[] c3 = {f1[2].getCoords(), f1[3].getCoords()};
        Coord3D[] c4 = {f1[3].getCoords(), f1[0].getCoords()};
        Coord3D[] c5 = {f1[0].getCoords(), f1[2].getCoords()};
        Coord3D[] c6 = {f1[1].getCoords(), f1[3].getCoords()};
        List<Coord3D[]> seg1 = List.of(c1, c2, c3, c4, c5, c6);

        Coord3D[] c21 = {f2[0].getCoords(), f2[1].getCoords()};
        Coord3D[] c22 = {f2[1].getCoords(), f2[2].getCoords()};
        Coord3D[] c23 = {f2[2].getCoords(), f2[3].getCoords()};
        Coord3D[] c24 = {f2[3].getCoords(), f2[0].getCoords()};
        Coord3D[] c25 = {f2[0].getCoords(), f2[2].getCoords()};
        Coord3D[] c26 = {f2[1].getCoords(), f2[3].getCoords()};
        List<Coord3D[]> seg2 = List.of(c21, c22, c23, c24, c25, c26);

        List<Double> distances = new ArrayList<>();
        for (Coord3D[] s1 : seg1) {
            for (Coord3D[] s2 : seg2) {
                distances.add(dist3D_Segment_to_Segment(s1[0], s1[1], s2[0], s2[1]));
            }
        }
        return PiMath.min(distances);
    }
    private static final double SMALL_NUM = 0.00000001;

    /**
     * Get the 3D minimum distance between 2 segments
     *
     * @param s1p1 first point of the first segment
     * @param s1p2 second point of the first segment
     * @param s2p1 first point of the second segment
     * @param s2p2 second point of the second segment
     * @return the shortest distance between S1 and S2
     */
    public static double dist3D_Segment_to_Segment(final Coord3D s1p1, final Coord3D s1p2, final Coord3D s2p1,
            final Coord3D s2p2) {
        final Vector3d u = new Vector3d(s1p2, s1p1);
        final Vector3d v = new Vector3d(s2p2, s2p1);
        final Vector3d w = new Vector3d(s1p1, s2p1);
        final double a = u.dotProduct(u); // always >= 0
        final double b = u.dotProduct(v);
        final double c = v.dotProduct(v);
        final double d = u.dotProduct(w);
        final double e = v.dotProduct(w);
        final double D = a * c - b * b; // always >= 0
        double sc, sN, sD = D; // sc = sN / sD, default sD = D >= 0
        double tc, tN, tD = D; // tc = tN / tD, default tD = D >= 0

        // compute the line parameters of the two closest points
        if (D < SMALL_NUM) { // the lines are almost parallel
            sN = 0.0; // force using point P0 on segment S1
            sD = 1.0; // to prevent possible division by 0.0 later
            tN = e;
            tD = c;
        } else { // get the closest points on the infinite lines
            sN = (b * e - c * d);
            tN = (a * e - b * d);
            if (sN < 0.0) { // sc < 0 => the s=0 edge is visible
                sN = 0.0;
                tN = e;
                tD = c;
            } else if (sN > sD) { // sc > 1 => the s=1 edge is visible
                sN = sD;
                tN = e + b;
                tD = c;
            }
        }

        if (tN < 0.0) { // tc < 0 => the t=0 edge is visible
            tN = 0.0;
            // recompute sc for this edge
            if (-d < 0.0) {
                sN = 0.0;
            } else if (-d > a) {
                sN = sD;
            } else {
                sN = -d;
                sD = a;
            }
        } else if (tN > tD) { // tc > 1 => the t=1 edge is visible
            tN = tD;
            // recompute sc for this edge
            if ((-d + b) < 0.0) {
                sN = 0;
            } else if ((-d + b) > a) {
                sN = sD;
            } else {
                sN = (-d + b);
                sD = a;
            }
        }
        // finally do the division to get sc and tc
        sc = (Math.abs(sN) < SMALL_NUM ? 0.0 : sN / sD);
        tc = (Math.abs(tN) < SMALL_NUM ? 0.0 : tN / tD);

        // get the difference of the two closest points
        u.times(sc);
        v.times(tc);
        u.sub(v);
        w.add(u);

        return w.getNorm(); // return the closest distance
    }

    /**
     * get the shortest distance between both boxes Execution times:
     * <p>
     * <ul>
     * <li>8ms:10</li>
     * <li>20ms:100</li>
     * <li>123ms:1000 502ms:10 000</li>
     * <li>4s, 625ms:100 000</li>
     * <li>46s, 73ms:1 000 000
     * <li>7m, 51s, 58ms:10 000 000</li>
     * <li>2h, 3m, 15s, 204ms:100 000 000</li>
     * </ul>
     * </p>
     *
     * @param b box for comparison
     * @return the shortest distance between both boxes
     * @deprecated use distance
     */
    public double distanceWithBigest(Box b) {
        List<Point[]> pointFaces = this.getPointFaces();
        List<Point[]> pointFaces2 = b.getPointFaces();
        double d1 = 0;
        int id1 = 0;
        double d2 = 0;
        int id2 = 0;
        for (int i = 0; i < pointFaces.size(); i++) {
            Point[] s1 = pointFaces.get(i);
            double d = s1[0].getCoords().distance(s1[2].getCoords());
            if (d > d1) {
                d1 = d;
                id1 = i;
            }
            Point[] s2 = pointFaces2.get(i);
            d = s2[0].getCoords().distance(s2[2].getCoords());
            if (d > d2) {
                d2 = d;
                id2 = i;
            }
        }
        Point[] s1 = pointFaces.get(id1);
        Point[] s2 = pointFaces2.get(id2);
        return distance2Faces(s1, s2);
    }

    /**
     * Test if a point is inside the box.
     *
     * @param point point to be tested
     * @return True if the point is inside the box, false otherwise.
     */
    public boolean isInside(Point point) {
        return this.isInside(point.getCoords());
    }

    public boolean isInside(Point point, double gap) {
        return this.isInside(point.getCoords(), gap);
    }

    public boolean isInside(Coord3D p) {
        return this.isInside(p, 0.0001);
    }

    public boolean isInside(Coord3D p, double gap) {
        Coord3D d = this.points[0].getCoords();
        Coord3D f = this.points[this.points.length - 1].getCoords();

        List<Point> ps = new ArrayList<>();
        ps.addAll(Arrays.asList(points));
        Point.sortByMinDist(ps, d);
        Coord3D h = ps.get(1).getCoords();
        Coord3D c = ps.get(2).getCoords();
        Coord3D p3 = ps.get(3).getCoords();
        Coord3D a;
        Coord3D dh = h.sub(d);
        Coord3D d3 = p3.sub(d);
        if (dh.dotProduct(d3) < 0.0001) {
            a = p3;
        } else {
            a = ps.get(4).getCoords();
        }
        double xlength = d.distance(a);
        double ylength = d.distance(c);
        double zlength = d.distance(h);

        Coord3D xlocal = a.sub(d).divide(xlength);
        Coord3D ylocal = c.sub(d).divide(ylength);
        Coord3D zlocal = h.sub(d).divide(zlength);

        Coord3D i = d.medium(f);
        Coord3D v = p.sub(i);

        double px = Math.abs(v.dotProduct(xlocal));
        double py = Math.abs(v.dotProduct(ylocal));
        double pz = Math.abs(v.dotProduct(zlocal));

        boolean isInside = (2 * px) - gap <= xlength && (2 * py) - gap <= ylength && (2 * pz) - gap <= zlength;
//         System.out.println("2px: "+(2 * px)+" 2py: "+(2 * py)+" 2pz: "+(2 * pz));
//         System.out.println(xlength+" "+ylength+" "+zlength);
        return isInside;
    }

    /*
     * public boolean isInside(Point point) { boolean inside =
     * point.getCoords().isBetween(this.points[0].getCoords(),
     * this.points[this.points.length - 1].getCoords()); return inside; }
     */
    public Point getCenter() {
        return new Point(this.points[0].getCoords().medium(this.points[this.points.length - 1].getCoords()));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Box other = (Box) obj;
        return Arrays.deepEquals(this.points, other.points);
    }

    /**
     * Gets point which each coordinate represents the highest value obtained
     * for the coordinates.
     *
     * @return The point whose coordinates are highest.
     */
    public Point getMax() {
        double x = Math.max(this.points[0].getCoords().getX(), this.points[this.points.length - 1].getCoords().getX());
        double y = Math.max(this.points[0].getCoords().getY(), this.points[this.points.length - 1].getCoords().getY());
        double z = Math.max(this.points[0].getCoords().getZ(), this.points[this.points.length - 1].getCoords().getZ());
        return new Point(x, y, z);
    }

    /**
     * Gets point which each coordinate represents the lowest value obtained for
     * the coordinates.
     *
     * @return The point which coordinates are lowest.
     */
    public Point getMin() {
        double x = Math.min(this.points[0].getCoords().getX(), this.points[this.points.length - 1].getCoords().getX());
        double y = Math.min(this.points[0].getCoords().getY(), this.points[this.points.length - 1].getCoords().getY());
        double z = Math.min(this.points[0].getCoords().getZ(), this.points[this.points.length - 1].getCoords().getZ());
        return new Point(x, y, z);
    }
}
