package OOOC;

import java.io.IOException;
import java.util.*;
public interface IOOCInteraction {

/**
*	This file is part of OOOCdbms.	
*	OOOCdbms is free software: you can redistribute it and/or modify
*	it under the terms of the GNU General Public License as published by
*	the Free Software Foundation, either version 3 of the License, or
*	(at your option) any later version.
*	
*	OOOCdbms is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU General Public License for more details.
*	
*	You should have received a copy of the GNU General Public License
*	along with OOOCdbms.  If not, see <http://www.gnu.org/licenses/>.
*/

/**
 * Interaction specific functions for the OOOC database system
 * 
 * @author Rob Carr
 * @version 1.0.0
 */

  /**
     * Stores an object in the specified cluster
     * 
     * @param  cluster  the cluster name
     * @param  object   the object to be stored
     * @return          returns true upon successful insertion
     */
    boolean storeObject(String cluster, Object object);
    
    /**
     * Gets an object in the specified cluster
     * 
     * @param  cluster  the cluster name
     * @param  oid      the id of the object to fetch
     * @return          returns the object desired
     */
    Object getObject(String cluster, int oid);
    
    /**
     * Removes an object from the specified cluster
     * 
     * @param  cluster  the cluster name
     * @param  oid      the id of the object to delete
     * @return          returns true on deletion
     */
    boolean removeObject(String cluster, int oid);
    
    /**
     * Create a new cluster in the database
     * 
     * @param  cluster  the cluster name
     * @return          returns true upon successful creation
     * @throws IOException 
     */
    boolean createCluster(String cluster) throws IOException;
    
    /**
     * Checks if the specified cluster exists
     * 
     * @param  cluster  the cluster name
     * @return          returns true if the cluster exists
     */
    boolean clusterExists(String cluster);
    
    /**
     * Return a collection of the clusters within the database
     * 
     * @return          returns a colleciton of the clusters
     */
    Collection<String> getClusters();
    
    /**
     * Removes all the clusters in the database
     * 
     * @return          returns true upon successful purge
     */
    boolean purgeDatabase();
    
    /**
     * Removes the all objects from the specified cluster
     * 
     * @param   cluster the name of the cluster
     * @return          returns true upon successful purge
     */
    boolean purgeCluster(String cluster);
    
    /**
     * Removes the specified cluster
     * 
     * @param   cluster the name of the cluster
     * @return          returns true upon successful deletion
     */
    boolean removeCluster(String cluster);
}