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
import info.ponciano.lab.jpc.pointcloud.bounding.EstimatedCompare;

/**
 * Provides the bounds of a list of points and its approximate dimensions.
 */
public abstract class BoundingBox {
	
	protected double width;
	protected double height;
	protected double depth;
	
	public double getWidth() { return width; };
	
	public double getHeight() { return height; }
	
	public double getDepth() { return depth; }
	
	public double getVolume() { return width * height * depth; }
	
	public abstract boolean contains(double x, double y, double z);
	
	/**
	 * @param point is the point to be verified as being in or out
	 * @return true if the parameter point is within the boundaries of the box
	 */
	public boolean contains(Point point) {
		return contains(point.getCoords().getX(), point.getCoords().getY(), point.getCoords().getZ());
	}
	
	public boolean equals(BoundingBox bbox, double precision) {
		return (EstimatedCompare.compareFloat(width, bbox.getWidth(), precision) == 0) && (EstimatedCompare.compareFloat(height, bbox.getHeight(), precision) == 0) &&
				(EstimatedCompare.compareFloat(depth, bbox.getDepth(), precision) == 0);
	}
	
	@Override
	public String toString() {
		return super.toString() + " --- w: " + width + "\th: " + height + "\td: " + depth;
	}
	
}
