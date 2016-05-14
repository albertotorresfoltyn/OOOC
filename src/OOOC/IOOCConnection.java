package OOOC;

public interface IOOCConnection {

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
	 * Connection specific functions for the OOOC database system
	 * 
	 * @author Rob Carr
	 * @version 1.0.0
	 */


	    /**
	     * Establishes a connection to the database
	     * 
	     * @param  location    the location of the databases
	     * @return        returns true upon successful connection
	     */
	    boolean connect(String location);

	    /**
	     * Return the path of the database currently connected to
	     * 
	     * @return        returns the path of the database
	     */
	    String getPath();
	    
	    /**
	     * Used to check if a connection exists
	     * 
	     * @return        returns true if a connection is established
	     */
	    boolean isConnected();
	
}
