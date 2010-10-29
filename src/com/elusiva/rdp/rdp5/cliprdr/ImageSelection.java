/* ImageSelection.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:41 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: 
 */
package com.elusiva.rdp.rdp5.cliprdr;

import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.elusiva.rdp.Common;
import com.elusiva.rdp.Utilities_Localised;
	 public class ImageSelection
	    implements Transferable 
	  {
	    // the Image object which will be housed by the ImageSelection
	    private Image image;

	    public ImageSelection(Image image) {
	      this.image = image;
	    }

	    // Returns the supported flavors of our implementation
	    public DataFlavor[] getTransferDataFlavors() 
	    {
	      return new DataFlavor[] {Utilities_Localised.imageFlavor};
	    }
	    
	    // Returns true if flavor is supported
	    public boolean isDataFlavorSupported(DataFlavor flavor) 
	    {
	      return Utilities_Localised.imageFlavor.equals(flavor);
	    }

	    // Returns Image object housed by Transferable object
	    public Object getTransferData(DataFlavor flavor)
	      throws UnsupportedFlavorException,IOException 
	    {
	      if (!Utilities_Localised.imageFlavor.equals(flavor)) 
	      {
	        throw new UnsupportedFlavorException(flavor);
	      }
	      // else return the payload
	      return image;
	    }
	  }
