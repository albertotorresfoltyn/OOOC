package OOOC;

/**
*	This file is the core part of OOOCdbms.	
*	OOOCdbms is an object dbms
*/

/**
 * Used to establish a connection to a OOOC database and perform connection specific tasks
 * 
 * @author Rob Carr
 * @version 1.1.3
 */

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class Manager implements IOOCConnection, IOOCInteraction
{	
	private boolean connected;
	private String dbPath;
	private String dbName;
	private String dbDescription;
	private String dbCreated;
	
	@Override
	public boolean connect(String location) {
		/*
		 * If a connection to the OOOC database is successfully made then the instance variables
		 * will be populated with the appropriate data from the databases db.OOOC file.
		 * 
		 * Should any exceptions be caught (i.e. an IO exception when trying to access the file),
		 * the connected attribute will be set to false to indicate no connection exists and the
		 * function will return false.
		 */
		try
		{
			FileInputStream fis = new FileInputStream(location + "/db.OOOC");
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(dis));
			this.dbName = buffer.readLine();
			this.dbDescription = buffer.readLine();
			this.dbCreated = buffer.readLine();
			this.dbPath = location;
			this.connected = true;
			return true;
		}
		catch (Exception e)
		{
			this.connected = false;
			return false;
		}
	}
	
	/**
     * Creates a OOOC database
     * 
     * @param  	database 	the path in which to create the database
     * @return  returns true upon successful creation
     */
	public static boolean createDatabase(String database)
	{
		// Check to see if a directory already exists with the name specified
		File f = new File(database);
		if(f.exists())
		{
			return false;
		}
		else
		{
			// If no directory was found then attempt to create it
			if(f.mkdir())
			{
				// If the database's root directory was created then attempt to create
				// the directory for the clusters to be stored in
				f = new File(database + "/clusters");
				if(f.mkdir())
				{
					// If the clusters directory was created then attempt 
					// to create the database information file
					f = new File(database + "/db.OOOC");
					try {
						if(f.createNewFile())
						{
							// If the file was created then write the default information to the file
							FileWriter fs;
							fs = new FileWriter(f);
							BufferedWriter fo = new BufferedWriter(fs);
							fo.write("Database Name\r\nDatabase Description\r\nDate Created");
							fo.flush();
							return true;
						}
						else
						{
							return false;
						}
					} catch (IOException e) {
						return false;
					}
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public String getPath() {
		return this.dbPath;
	}

	@Override
	public boolean isConnected() {
		return this.connected;
	}
	
	public String toString(){
		return this.dbName + ", " + this.dbDescription + ", " + this.dbCreated;
	}
	
	public Manager()
	{
	}
	
	public Manager(String location){
		connect(location);
	}
	
	@Override
	public boolean clusterExists(String cluster) {
		/*
		 * If a connection has been established the method will check to see if
		 * the cluster exists by accessing the cluster.OOOC file inside the cluster's
		 * private folder. Should the file exist the method will return true indicating
		 * that the cluster does indeed exists; if the file does not exist the method
		 * will return false, indicating that the cluster does not exist.
		 */
		if(isConnected())
		{
			File f = new File(this.dbPath + "/clusters/" + cluster + "/cluster.OOOC");
			if(f.exists())
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean createCluster(String cluster) {
		/*
		 * Prior to the creation of a cluster, the clusterExists method is called to
		 * ensure that a cluster with the same name does not already exist.
		 */
		if(clusterExists(cluster))
		{
			return false;
		}
		else
		{
			/*
			 * Should the clusterExists function return false (indicating no name collisions
			 * were found) the cluster directory and the cluster.OOOC directory will be created.
			 * 
			 * If an IOException is caught the method will return false to indicate the cluster
			 * was not created, and no files or directories will be created.
			 */
			File f = new File(this.dbPath + "/clusters/" + cluster);
			if(f.mkdir())
			{
				f = new File(this.dbPath + "/clusters/" + cluster + "/cluster.OOOC");
				try {
					if(f.createNewFile())
					{
						/*
						 * cluster.OOOC is given a default value of "0" as the first object
						 * in the cluster should be given the ID 0
						 */
						FileWriter fs = new FileWriter(f);
						BufferedWriter fo = new BufferedWriter(fs);
						fo.write("0");
						fo.flush();
						return true;	
					}
					else
					{
						return false;
					}
				} catch (IOException e) {
					return false;
				}
			}
			else
			{
				return false;
			}
		}
	}
	
	@Override
	public Collection<String> getClusters() {
		if(isConnected())
		{
			/*
			 * If a connection to a database is established then the method
			 * will create a new File object that points to the clusters directory
			 * of the database and subsequently fetching an array of File objects
			 * which represent the directories found within the clusters directory. 
			 */
			File dbpath = new File(this.dbPath + "/clusters");
			File[] clusters = dbpath.listFiles();
			
			/*
			 * Once the array of Files has been made, each one will be added to the
			 * LinkedList that will be returned after being built.
			 */
			Collection<String> clusterCollection = new LinkedList<String>();
			for(File cluster : clusters)
			{
				if(cluster.isDirectory())
				{
					clusterCollection.add(cluster.getName());
				}
			}
			return clusterCollection;
		}
		else
		{
			// If no connection exists then return null
			return null;
		}
	}
	
	@Override
	public Object getObject(String cluster, int oid) {
		/*
		 * If the specified cluster exists then open the appropriate
		 * object file and return its data in the form of an object
		 * 
		 * Should any IO exceptions, file not found exceptions
		 * or class not found exceptions be thrown, return null
		 */
		if(clusterExists(cluster))
		{
			try {
				FileInputStream fis = new FileInputStream(this.dbPath + "/clusters/" + cluster + "/" + oid + ".dat");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				return obj;
			} catch (FileNotFoundException e) {
				return null;
			} catch (IOException e) {
				return null;
			} catch (ClassNotFoundException e) {
				return null;
			}	
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean purgeCluster(String cluster) {
		/*
		 * If the specified cluster exists then loop through each file
		 * in the cluster's directory and delete it
		 */
		if(clusterExists(cluster))
		{
			File c = new File(this.dbPath + "/clusters/" + cluster);
			if(c.exists())
			{
				File[] f = c.listFiles();
				for(File cf : f)
				{
					cf.delete();
				}
	
				/* 
				 * Once the files have been deleted inside the cluster's directory
				 * recreate the cluster.OOOC file with a new starting value of 0
				 * 
				 * If a problem occurs in creating this file then false should be returned
				 */
				try
				{
					File idFile = new File(this.dbPath + "/clusters/" + cluster + "/cluster.OOOC");
					if(!idFile.createNewFile())
					{
						return false;
					}
					Writer output = new BufferedWriter(new FileWriter(idFile));
					output.write("0");
					output.close();
				}
				catch(IOException e)
				{
					return false;
				}
				
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	private void deleteFile(File f)
	{
		/*
		 * If the file or directory specified exists then
		 * check to see if it is a directory, if so loop
		 * through each sub-directory and file and recursively
		 * call the deleteFile method
		 */
		if(f.exists())
		{
			if(f.isDirectory())
			{
				File[] c = f.listFiles();
				for(File cf : c )
				{
					deleteFile(cf);
				}
				f.delete();
			}
			else
			{
				f.delete();
			}
		}
	}
	
	@Override
	public boolean purgeDatabase() {
		/*
		 * Loop through all the files and directories
		 * in the clusters directory and pass them to the
		 * deleteFile method
		 * 
		 * After the recursive functionality has been 
		 * completed delete the clusters directory itself
		 * 
		 * If the clusters directory doesn't already exist then return false
		 */
		File c = new File(this.dbPath + "/clusters");
		if(c.exists())
		{
			File[] f = c.listFiles();
			for(File cf : f)
			{
				deleteFile(cf);
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean removeCluster(String cluster) {
		/*
		 * If the cluster exists then loop through each file inside
		 * the cluster's directory and delete it, after purging the
		 * cluster's directory, delete the cluster's directory itself
		 * 
		 * If the cluster doesn't exist then return false
		 */
		if(clusterExists(cluster))
		{
			File c = new File(this.dbPath + "/clusters/" + cluster);
			if(c.exists())
			{
				File[] f = c.listFiles();
				for(File cf : f)
				{
					cf.delete();
				}
				
				return c.delete();
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	

	@Override
	/*
	 * If the specified cluster exists then check to see if
	 * an object with the specifid OID exists, if it does then
	 * delete the object and return the result of the deletion
	 * 
	 * If the cluster or OID does not exist then return false
	 */
	public boolean removeObject(String cluster, int oid) {
		if(clusterExists(cluster))
		{
			File o = new File(this.dbPath + "/clusters/" + cluster + "/" + oid + ".dat");
			if(o.exists())
			{
				return o.delete();
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	@Override
	public boolean storeObject(String cluster, Object object) {
		/*
		 * If the specified cluster exists then open the cluster.OOOC file and
		 * find the OID the next object should be assigned
		 * 
		 * If there is an issue opening the cluster.OOOC file then return false
		 */
		if(clusterExists(cluster))
		{
			File idFile = new File(this.dbPath + "/clusters/" + cluster + "/cluster.OOOC");
			Scanner s;
			try {
				s = new Scanner(idFile);
			} catch (FileNotFoundException e1) {
				return false;
			}
			int id = s.nextInt();
			s.close();
			
			/*
			 * Once the next OID has been scanned attempt to serialize the
			 * specified object to the database and increment the OID in
			 * cluster.OOOC
			 */
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(this.dbPath + "/clusters/" + cluster + "/" + id + ".dat");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(object);
				oos.flush();
				oos.close();
				id++;
				Writer output = new BufferedWriter(new FileWriter(idFile));
				output.write(Integer.toString(id));
				output.close();
				return true;
			} catch (FileNotFoundException e) {
				return false;
			} catch (IOException e) {
				return false;
			}			
		}
		else
		{
			return false;
		}
	}
}