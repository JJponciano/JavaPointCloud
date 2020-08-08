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

package info.ponciano.lab.jpc.pointcloud.bounding.geometry;

import info.ponciano.lab.jpc.pointcloud.components.APointCloud;
import info.ponciano.lab.jpc.math.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Bounding box that does not account for the inclination of an object.
 */
public class AxisAlignedBoundingBox extends BoundingBox {
	
	private Point pointMin;
	private Point pointMax;
	
	public AxisAlignedBoundingBox(APointCloud points) {
		Iterator<Point> _it = points.iterator();
		
		pointMin = new Point(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
		pointMax = new Point(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
		
		while (_it.hasNext()) {
			Point current = _it.next();
			
			for (int i = 0; i < 3; i++) {
				double currentI = current.getCoords().get(i);
				double maxI = pointMax.getCoords().get(i);
				double minI = pointMin.getCoords().get(i);
				
				if (currentI > maxI) {
					pointMax.getCoords().set(i, currentI);
				}
				
				if (currentI < minI) {
					pointMin.getCoords().set(i, currentI);
				}
			}
		}
		
		width = pointMax.getCoords().getX() - pointMin.getCoords().getX();
		height = pointMax.getCoords().getZ() - pointMin.getCoords().getZ();
		depth = pointMax.getCoords().getY() - pointMin.getCoords().getY();
	}
	
	public Point getMinPoint() { return pointMin; }
	
	public Point getMaxPoint() { return pointMax; }
	
	public boolean contains(double x, double y, double z) {
		return (x >= pointMin.getCoords().getX()) && (y >= pointMin.getCoords().getY()) &&
				(z >= pointMin.getCoords().getZ()) && (x <= pointMax.getCoords().getX()) &&
				(y <= pointMax.getCoords().getY() && (z <= pointMax.getCoords().getZ()));
 	}

	/**
	 * @param boundingBox is the box to check collision with
	 * @return true if the bounding box intersects the parameter bounding box
	 */
	public boolean intersects(AxisAlignedBoundingBox boundingBox) {
		return (pointMin.compareTo(boundingBox.getMaxPoint()) <= 0) &&
				(pointMax.compareTo(boundingBox.getMinPoint()) >= 0);
	}
	
	/**
	 * 
	 * @param points is the list of points to be verified as being contained or not
	 * @return the list of points that belong to the bounding box
	 */
	public List<Point> getPointsInBounds(List<Point> points) {
		List<Point> pointsInBounds = new ArrayList<>();
		Iterator<Point> it = points.iterator();
		
		while (it.hasNext()) {
			Point next = it.next();
			
			if (contains(next)) {
				pointsInBounds.add(next);
			}
		}
		
		return pointsInBounds;
	}
	
	/**
	 * 
	 * @param points is the initial set of points to be verified
	 * @return a subset of the parameter list containing only points not being contained in bounds
	 */
	public List<Point> filterOutContained(List<Point> points) {
		List<Point> filtered = points.stream()
				.filter(point -> !contains(point))
				.collect(Collectors.toList());
		
		return filtered;
	}
	
}
