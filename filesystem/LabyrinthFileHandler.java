package com.benjamindebotte.labyrinth.filesystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.benjamindebotte.labyrinth.containers.Labyrinth;

public class LabyrinthFileHandler {

	String path;
	public LabyrinthFileHandler(String filepath) {
				path = filepath;
				
				if(!filepath.endsWith(".laby"))
					 path += ".laby";
	}
	
	
	public Labyrinth load() throws FileNotFoundException, IOException, ClassNotFoundException {
		Labyrinth laby = null;
		
		File f_laby = new File(path);
		if(!f_laby.exists())
			return laby;
		
		ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(f_laby)) ;
		
		laby = (Labyrinth)ois.readObject() ;
		ois.close();
		
		return laby;
	}
	
	public void save(Labyrinth laby) throws IOException {
		File f_laby =  new File(path);
		ObjectOutputStream oos =  new ObjectOutputStream(new FileOutputStream(f_laby)) ;
		oos.writeObject(laby) ;
		oos.close();

	}

}
