// $Id$
// Copyright (c) 1996-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.uml.cognitive.critics;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.argouml.cognitive.Designer;
import org.argouml.model.Model;
import org.argouml.uml.cognitive.UMLDecision;
/**
 * A critic to detect when a classifier has too many attributes.
 *
 * @author mkl
 */
public class CrTooManyAttr extends AbstractCrTooMany {
    
    /**
     * The initial threshold.
     */
    private static final int ATTRIBUTES_THRESHOLD = 7;

    /**
     * The constructor.
     *
     */
    public CrTooManyAttr() {
        setupHeadAndDesc();
	addSupportedDecision(UMLDecision.STORAGE);
	setThreshold(ATTRIBUTES_THRESHOLD);
	addTrigger("structuralFeature");
    }

    /*
     * @see org.argouml.uml.cognitive.critics.CrUML#predicate2(
     *      java.lang.Object, org.argouml.cognitive.Designer)
     */
    public boolean predicate2(Object dm, Designer dsgr) {
	if (!(Model.getFacade().isAClassifier(dm))) {
            return NO_PROBLEM;
        }
	// TODO: consider inherited attributes?
	Collection features = Model.getFacade().getFeatures(dm);
	if (features == null) {
            return NO_PROBLEM;
        }
	int n = 0;
	for (Iterator iter = features.iterator(); iter.hasNext();) {
	    if (Model.getFacade().isAStructuralFeature(iter.next())) {
		n++;
            }
	}
	if (n <= getThreshold()) {
            return NO_PROBLEM;
        }
	return PROBLEM_FOUND;
    }

    /**
     * @see org.argouml.uml.cognitive.critics.CrUML#getCriticizedMetatypes()
     */
    public Set<Object> getCriticizedMetatypes() {
        Set<Object> ret = new HashSet<Object>();
        ret.add(Model.getMetaTypes().getUMLClass());
        return ret;
    }
    
    /**
     * The UID.
     */
    private static final long serialVersionUID = 1281218975903539324L;
} /* end class CrTooManyAttr */
