package cppjava;

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
import com.sun.jna.Native;
import jna.CInterface;

/**
* Interface between C++ and Java.
* <h2>Example</h2>
* <h3>C++ side</h3>
* <p>
* <code>
* extern "C" __declspec(dllexport) void cleanup(float* pVals) { <br>
* delete[]pVals;<br>
*}<br>
* extern "C" __declspec(dllexport) void processing(float **xOut,float
* **yOut,float **zOut,int *lengthOut,const float* xIn,const float*
* yIn,const float* zIn, int lengthIn)<br>
* {<br>
* //set the result with the values of the input cloud. <br>
* *xOut=xIn; <br>
* *yOut=yIn; <br>
* *zOut=zIn; <br>
* *lengthOut=lengthIn;<br>
* }<br>
* extern "C" __declspec(dllexport) bool isOK(){<br>
* return true;<br>
* }<br>
* </code>
* </p>
* <h3>Java side</h3>
* <p>
* <code>
* void processing(PointerByReference xOut,PointerByReference yOut,
* PointerByReferencezOut,IntByReference lengthOut,Pointer  xIn,
* Pointer yIn,Pointer zIn, int lengthIn)
* </code>
* </p>
* @author  Jean-Jacques Ponciano
*/
public interface CosLib extends CInterface {

    CosLib INSTANCE = (CosLib) Native.loadLibrary("cos_lib", CosLib.class);

 
}
