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

import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import info.ponciano.lab.jpc.pointcloud.components.PointCloudMap;
import info.ponciano.lab.jpc.math.Point;



/**
 * Allows shaping a bounding box that seeks to fit a point cloud better than an AxisAlignedBoundingBox.
 * 
 * @see info.ponciano.lab.knowdip.math.geometry.AxisAlignedBoundingBox
 */
public class OrientedBoundingBox extends BoundingBox {
	
	private Point centroid = new Point(0, 0, 0);
	
	private Vector3D xAxis;
	private Vector3D yAxis;
	private Vector3D zAxis;
	
	PointCloudMap model = new PointCloudMap();
	AxisAlignedBoundingBox modelAABB;
	
	public OrientedBoundingBox(List<Point> points) {
		calculateCentroid(points);
		
		Matrix b = calculateAxis(points.iterator());
		
		xAxis = new Vector3D(b.getArray()[0]);
		yAxis = new Vector3D(b.getArray()[1]);
		zAxis = new Vector3D(b.getArray()[2]);
		
		double rx = VectorOperations.angleBetweenTwoVectors(new Vector3D(1, 0, 0), xAxis);
		double ry = VectorOperations.angleBetweenTwoVectors(new Vector3D(0, 1, 0), yAxis);
		double rz = VectorOperations.angleBetweenTwoVectors(new Vector3D(0, 0, 1), zAxis);
	
		for (Point point : points) {
			Point translatedPoint = AffineTransformations.translate(point, 
					-centroid.getCoords().getX(), -centroid.getCoords().getY(), -centroid.getCoords().getZ());
			Point translatedRotatedPoint = AffineTransformations.rotate(translatedPoint, rx, ry, rz);
			model.add(translatedRotatedPoint);
		}
		
		modelAABB = new AxisAlignedBoundingBox(model);
		
		this.width = modelAABB.getWidth();
		this.height = modelAABB.getHeight();
		this.depth = modelAABB.getDepth();
	}
	
	public Point getCentroid() { return centroid; }
	
	public Vector3D getXAxis() { return xAxis; }
	
	public Vector3D getYAxis() { return yAxis; }
	
	public Vector3D getZAxis() { return zAxis; }

	public PointCloudMap getModel() { return model; }
	
	@Override
	public boolean contains(double x, double y, double z) {
		double rx = VectorOperations.angleBetweenTwoVectors(new Vector3D(1, 0, 0), xAxis);
		double ry = VectorOperations.angleBetweenTwoVectors(new Vector3D(0, 1, 0), yAxis);
		double rz = VectorOperations.angleBetweenTwoVectors(new Vector3D(0, 0, 1), zAxis);
		
		Point translatedPoint = AffineTransformations.translate(new Point(x, y, z), 
				-centroid.getCoords().getX(), -centroid.getCoords().getY(), -centroid.getCoords().getZ());
		Point translatedRotatedPoint = AffineTransformations.rotate(translatedPoint, rx, ry, rz);
		
		return modelAABB.contains(translatedRotatedPoint);
	}

	@Override
	public double getWidth() {
		return modelAABB.getWidth();
	}

	@Override
	public double getHeight() {
		return modelAABB.getHeight();
	}

	@Override
	public double getDepth() {
		return modelAABB.getDepth();
	}

	/**
	 * Calculates the centroid of the point cloud.
	 * 
	 * @param points
	 */
	private void calculateCentroid(List<Point> points) {
		Iterator<Point> it = points.iterator();
		
		while (it.hasNext()) {
			Point point = it.next();
			
			centroid.getCoords().set(centroid.getCoords().getX() + point.getCoords().getX(), 
					centroid.getCoords().getY() + point.getCoords().getY(), 
					centroid.getCoords().getZ() + point.getCoords().getZ());
		}
		
		centroid.getCoords().set(centroid.getCoords().getX() / points.size(), 
				centroid.getCoords().getY() / points.size(), 
				centroid.getCoords().getZ() / points.size());
	}
	
	/**
	 * Calculates the matrix composed by the column vectors corresponding to the x, y and z axis of the model space.
	 * 
	 * @param it
	 * @return the matrix composed by the column vectors corresponding to 
	 * the x, y and z axis of the model space
	 */
	private Matrix calculateAxis(Iterator<Point> it) {
		Matrix covarianceMatrix = new Matrix(3, 3);
		
		while(it.hasNext()) {
			Point point = it.next();
			
			double[] pointV = { point.getCoords().getX(), point.getCoords().getY(), 
					point.getCoords().getZ() };
			double[] centroidV = { centroid.getCoords().getX(), centroid.getCoords().getY(), 
					centroid.getCoords().getZ() };
			double[] diffV = { pointV[0] - centroidV[0], pointV[1] - centroidV[1], 
					pointV[2] - centroidV[2] };
			
			Matrix m = new Matrix (diffV, 1);
			Matrix mt = m.transpose();
			
			Matrix prod = mt.times(m);
			
			covarianceMatrix = covarianceMatrix.plus(prod);
		}
		
		EigenvalueDecomposition ed = covarianceMatrix.eig();
		
		return ed.getV();
	}
	
}
