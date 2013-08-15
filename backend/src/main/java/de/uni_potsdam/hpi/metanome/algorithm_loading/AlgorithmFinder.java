package de.uni_potsdam.hpi.metanome.algorithm_loading;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Class that provides utilities to retrieve information on the available algorithm jars. 
 */
public class AlgorithmFinder {
	
	protected static final String bootstrapClassTagName = "Algorithm-Bootstrap-Class";

	/**
	 * 
	 * @param pathToFolder		Path to the folder where the algorithm jars are located
	 * @param algrithmSubclass	Class of algorithms to retrieve, or null if all subclasses
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public String[] getAvailableAlgorithms(String pathToFolder, Class<?> algrithmSubclass) throws IOException, ClassNotFoundException {
		
		LinkedList<String> availableAlgorithms = new LinkedList<String>();
		File[] jarFiles = retrieveJarFiles(pathToFolder);
		
		for(File jarFile : jarFiles){
			if (getAlgorithmClass(jarFile).isAssignableFrom(algrithmSubclass))
				availableAlgorithms.add(jarFile.getName());
		}
		
		String[] stringArray = new String[availableAlgorithms.size()];
		return availableAlgorithms.toArray(stringArray);
	}

	/**
	 * 
	 * @param pathToFolder	Path to search for jar files
	 * @return an array of Files with ".jar" ending
	 */
	private File[] retrieveJarFiles(String pathToFolder) {
		File folder = new File(pathToFolder);
		File[] jars = folder.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File file, String name) {
		        return name.endsWith(".jar");
		    }
		});
		
		if (jars == null) jars = new File[0];
		return jars;
	}
	
	/**
	 * Finds out which subclass of Algorithm is implemented by the source code in the file. 
	 * 
	 * @param file
	 * @return the superclass of the algorithm implementation in file, which should be a 
	 * 			subclass of Algorithm
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Class<?> getAlgorithmClass(File file) throws IOException, ClassNotFoundException {
		JarFile jar = new JarFile(file);
		
		Manifest man = jar.getManifest();
        Attributes attr = man.getMainAttributes();
        String className = attr.getValue(bootstrapClassTagName);
        
        URL[] url = {file.toURI().toURL()};
        ClassLoader loader = new URLClassLoader(url);
        
        Class<?> algorithmClass = Class.forName(className, false, loader);
        
		return algorithmClass.getSuperclass();
	}
	
}