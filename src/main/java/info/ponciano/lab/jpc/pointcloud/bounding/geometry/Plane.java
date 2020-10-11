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

package info.ponciano.lab.jpc.pointcloud.bounding.geometry;



import info.ponciano.lab.jpc.math.Point;
import info.ponciano.lab.jpc.math.vector.Vector3d;
import info.ponciano.lab.jpc.pointcloud.bounding.EstimatedCompare;
import java.util.LinkedList;
import java.util.List;



/**
 * Represents the model of a plane in the cartesian form {@code ax+by+cz-d=0}.
 */
public class Plane {
	
	private double a;	// component x of the normal to the plane
	private double b;	// component y of the normal to the plane
	private double c; 	// component z of the normal to the plane
	private double d;	// distance of the plane to the origin
	
	/**
	 * Creates a plan from three points.
	 * 
	 * @param p0 is a point of the plan
	 * @param p1 is a point of the plan
	 * @param p2 is a point of the plan
	 */
	public Plane(Point p0, Point p1, Point p2) {
		Vector3d normal = computeNormal(p0, p1, p2);
		
		a = normal.getX();
		b = normal.getY();
		c = normal.getZ();
		d = - (a * p0.getCoords().getX() + b * p0.getCoords().getY() + c * p0.getCoords().getZ());
	}
	
	/**
	 * Instantiates a plan using the cartesian form {@code ax + by + cz - d = 0}.
	 * 
	 * @param a is the x component of the normal to the plane
	 * @param b is the y component of the normal to the plane
	 * @param c is the z component of the normal to the plane
	 * @param d is the distance of the plane to the origin
	 */
	public Plane(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * Instantiates a plan using the Hesse form {@code r . n0 - d = 0}.
	 * 
	 * @param n is the normal to the plane
	 * @param d is the distance of the plane to the origin
	 */
	public Plane(Vector3d n, double d) {
		this.a = n.getX();
		this.b = n.getY();
		this.c = n.getZ();
		this.d = d;
	}
	
	/**
	 * @return the x component of the normal to the plane
	 */
	public double getA() { return a; }
	
	/**
	 * @return the y component of the normal to the plane
	 */
	public double getB() { return b; }
	
	/**
	 * @return the z component of the normal to the plane
	 */
	public double getC() { return c; }
	
	/**
	 * @return the distance of the plane to the origin
	 */
	public double getD() { return d; }
	
	/**
	 * @return the normal to the plane
	 */
	public Vector3d getNormal() {
		return new Vector3d(a, b, c);
	}
	
	/**
	 * @return the normalized normal vector to the plane
	 */
	public Vector3d getNormalizedNormal() {
		Vector3d nn = getNormal();
		
		nn.normalize();
		
		return nn;
	}
	
	/**
	 * @param points is the list of points to check
	 * @param precision determines the range in which a point is considered to be on the plane
	 * @return the points that are on the plane by a certain precision
	 */
	public List<Point> getPointsOnPlane(List<Point> points, double precision) {
		List<Point> pointsOnPlane = new LinkedList<>();
		
		for (Point point : points) {
			if (pointIsOnPlane(point, this, precision)) {
				pointsOnPlane.add(point);
			}
		}
		
		return pointsOnPlane;
	}
	
	public boolean pointIsOnPlane(Point point, double precision) {
		return (pointPlaneDistance(point) < precision);
	}
	
	/**
	 * @param point is the point to find the distance of
	 * @return the distance of a point to plane
	 */
	public double pointPlaneDistance(Point point) {
		return (double) (Math.abs(a * point.getCoords().getX() 
				+ b * point.getCoords().getY()
				+ c * point.getCoords().getZ()
				- d) / Math.sqrt(a*a + b*b + c*c));
	}
	
	/**
	 * @param point is the point to find the distance of
	 * @param plane is the plane to find the distance to
	 * @return the distance of a point to a plane
	 */
	public static double pointPlaneDistance(Point point, Plane plane) {
		return plane.pointPlaneDistance(point);
	}
	
	/**
	 * @param point is the point to check
	 * @param plane is the plane to check
	 * @param precision is the accepted calculation error
	 * @return true if the parameter point is on the parameter plane
	 */
	public static boolean pointIsOnPlane(Point point, Plane plane, double precision) {
		return plane.pointIsOnPlane(point, precision);
	}
	
	/**
	 * @param point is the point to check
	 * @param plane is the plane to check
	 * @return true if the parameter point is inside the half space of the parameter plan
	 */
	public static boolean pointIsInsideHalfSpace(Point point, Plane plane) {
		double value = point.getCoords().getX() * plane.getA() + point.getCoords().getY() * plane.getB()
				+ point.getCoords().getZ() * plane.getC() + plane.getD();
		
		boolean result = (EstimatedCompare.compareDouble(value, 0.0, 0.005) == 0)
				|| (EstimatedCompare.compareDouble(value, 0.0, 0.005) == -1);
		
		return result;
	}
	
	/**
	 * @param p0 is the origin of the two vectors of the plane
	 * @param p1 is the first vector of the plane
	 * @param p2 is the second vector of the plane
	 * @return normal vector to the plane
	 */
	private Vector3d computeNormal(Point p0, Point p1, Point p2) {
		Vector3d vec1 = new Vector3d(p1.getCoords().getX() - p0.getCoords().getX(), 
				p1.getCoords().getY() - p0.getCoords().getY(), p1.getCoords().getZ() - p0.getCoords().getZ());
		Vector3d vec2 = new Vector3d(p2.getCoords().getX() - p0.getCoords().getX(), 
				p2.getCoords().getY() - p0.getCoords().getY(), p2.getCoords().getZ() - p0.getCoords().getZ());
		double c1 = vec1.getY() * vec2.getZ() - vec1.getZ() * vec2.getY();
		double c2 = vec1.getZ() * vec2.getX() - vec1.getX() * vec2.getZ();
		double c3 = vec1.getX() * vec2.getY() - vec1.getY() * vec2.getX();
		
		Vector3d n = new Vector3d(c1, c2, c3);
		
		return n;
	}
	
}
