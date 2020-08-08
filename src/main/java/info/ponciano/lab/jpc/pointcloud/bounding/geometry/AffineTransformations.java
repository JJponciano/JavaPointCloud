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

import Jama.Matrix;
import info.ponciano.lab.jpc.math.Point;

/**
 * Provides some methods for affine transformations, such as translation or rotation.
 */
public class AffineTransformations {

	/**
	 * Translates the point by the vector {@code (x, y, z) }.
	 * 
	 * @param point is the point to be translated
	 * @param x translation vector x coordinate
	 * @param y translation vector y coordinate
	 * @param z translation vector z coordinate
	 * @return the rotated point
	 */
	public static Point translate(Point point, double x, double y, double z) {
		Point translatedPoint;
		
		Matrix v = new Matrix(new double[] { point.getCoords().getX(), 
				point.getCoords().getY(), point.getCoords().getZ(), 1
		}, 1).transpose();
		Matrix tMat = new Matrix(new double[][] {
			{ 1, 0, 0, x },
			{ 0, 1, 0, y},
			{ 0, 0, 1, z},
			{ 0, 0, 0, 1 }
		});
		
		Matrix t = tMat.times(v);
		
		translatedPoint = new Point((float) t.get(0, 0), (float) t.get(1, 0), (float) t.get(2, 0));
		
		return translatedPoint;
	}
	
	/**
	 * Rotates the point around the three axis using Euler angles.
	 * 
	 * @param point is the point to be rotated
	 * @param rotationX is the angle by which to rotate the point around the X axis
	 * @param rotationY is the angle by which to rotate the point around the Y axis
	 * @param rotationZ is the angle by which to rotate the point around the Z axis
	 * @return a rotated point
	 */
	public static Point rotate(Point point, double rotationX, double rotationY, double rotationZ) {
		Matrix v = new Matrix(new double[] { point.getCoords().getX(), 
				point.getCoords().getY(), point.getCoords().getZ() }, 1);
		
		v = v.transpose();
		
		Matrix rxMat = new Matrix( new double[][] { 
			{ 1, 		0, 				0 }, 
			{ 0, Math.cos(rotationX), -Math.sin(rotationX) }, 
			{ 0, Math.sin(rotationX), Math.cos(rotationX) }
		} );
		
		Matrix ryMat = new Matrix( new double[][] { 
			{ Math.cos(rotationY), 0, Math.sin(rotationY) }, 
			{ 	0,			1, 		0 }, 
			{ -Math.sin(rotationY), 0, Math.cos(rotationY) } 
		} );
		
		Matrix rzMat = new Matrix( new double[][] { 
			{ Math.cos(rotationZ), -Math.sin(rotationZ), 0 }, 
			{ Math.sin(rotationZ), Math.cos(rotationZ), 0 }, 
			{ 		0, 			0, 			1 } 
		} );
		
		Matrix r = rzMat.times(ryMat.times(rxMat.times(v)));
		
		Point rotatedPoint = new Point((float) r.get(0, 0), (float) r.get(1, 0),
				(float) r.get(2, 0));
		
		return rotatedPoint;
	}

}
