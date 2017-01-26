/*******************************************************************************
 * Copyright (c) 2009  Egon Willighagen <egonw@users.sf.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact: http://www.bioclipse.net/
 ******************************************************************************/
package net.bioclipse.owl.business;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.StringMatrix;
import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.pellet.business.PelletManager;
import net.bioclipse.rdf.business.IRDFStore;
import net.bioclipse.rdf.business.RDFManager;

import org.eclipse.core.runtime.CoreException;

public class OWLManager implements IBioclipseManager {

    PelletManager pellet = new PelletManager();
    RDFManager rdf = new RDFManager();
    
    public String getManagerName() {
        return "owl";
    }

    public StringMatrix listClasses(IRDFStore store)
        throws IOException, BioclipseException, CoreException {
        return pellet.isRDFType(
            store, "http://www.w3.org/2002/07/owl#Class"
        );
    }

    public List<String> getClasses(IRDFStore store)
        throws IOException, BioclipseException, CoreException {
    	if (store == null) return Collections.emptyList();
    	if (rdf.size(store) < 1) return Collections.emptyList();
    	return pellet.getRDFType(
            store, "http://www.w3.org/2002/07/owl#Class"
        );
    }

    public StringMatrix listObjectProperties(IRDFStore store)
    throws IOException, BioclipseException, CoreException {
    	return pellet.isRDFType(
    		store, "http://www.w3.org/2002/07/owl#ObjectProperty"
    	);
    }

    public List<String> getObjectProperties(IRDFStore store)
    throws IOException, BioclipseException, CoreException {
    	if (store == null) return Collections.emptyList();
    	if (rdf.size(store) < 1) return Collections.emptyList();
    	return pellet.getRDFType(
    		store, "http://www.w3.org/2002/07/owl#ObjectProperty"
    	);
    }

    public StringMatrix listDatatypeProperties(IRDFStore store)
    throws IOException, BioclipseException, CoreException {
    	return pellet.isRDFType(
    		store, "http://www.w3.org/2002/07/owl#DatatypeProperty"
    	);
    }

    public List<String> getDatatypeProperties(IRDFStore store)
    throws IOException, BioclipseException, CoreException {
    	if (store == null) return Collections.emptyList();
    	if (rdf.size(store) < 1) return Collections.emptyList();
    	return pellet.getRDFType(
    		store, "http://www.w3.org/2002/07/owl#DatatypeProperty"
    	);
    }
}
