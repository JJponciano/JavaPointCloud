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

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 * Provides tools for working with vectors.
 */
public class VectorOperations {

	public static double angleBetweenTwoVectors(Vector3D v1, Vector3D v2) {
		double theta;
		double dotProd = v1.dotProduct(v2);
		double normProd = v1.getNorm() * v2.getNorm();
		double cosTheta = dotProd/normProd;
		
		theta = Math.acos(cosTheta);
		
		return theta;
	}
}
