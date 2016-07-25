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
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;
import jna.CInterfaceException;
import jna.ConverterCJ;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  Jean-Jacques Ponciano
 */
public class CloudCJ {

    private final ConverterCJ javaCpp;
    private final CosLib cpp;
    private float[] x;
    private float[] y;
    private float[] z;
    private PointerByReference xByReference;
    private PointerByReference yByReference;
    private PointerByReference zByReference;
    private IntByReference arraylengthByReference;

    public CloudCJ() throws CInterfaceException {
        this.arraylengthByReference = new IntByReference();
        this.zByReference = new PointerByReference();
        this.yByReference = new PointerByReference();
        this.xByReference = new PointerByReference();
        this.javaCpp = new ConverterCJ();
        this.cpp = CosLib.INSTANCE;
        if (!this.cpp.isOK()) {
            throw new CInterfaceException("Library is not connected\n "
                    + "Did you put your dll files directly in the root of your java sources?");
        }
    }

    /**
     * Extract all coordinate of the point in X Y and Z arrays.
     */
    protected void extractCloud() {
        try {
            // extract the pointed-to pointer
            Pointer xp = xByReference.getValue();
            Pointer yp = yByReference.getValue();
            Pointer zp = zByReference.getValue();
            //get real result
            this.x = this.javaCpp.getFloatArray(xp, arraylengthByReference);
            this.y = this.javaCpp.getFloatArray(yp, arraylengthByReference);
            this.z = this.javaCpp.getFloatArray(zp, arraylengthByReference);

            //delete the cloud at the memory.
            this.cpp.cleanup(xp);
            this.cpp.cleanup(yp);
            this.cpp.cleanup(zp);
        } catch (CInterfaceException ex) {
            Logger.getLogger(CloudCJ.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float[] getX() {
        return x;
    }

    public float[] getY() {
        return y;
    }

    public float[] getZ() {
        return z;
    }

}
