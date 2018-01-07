package com.molinari.utility.io;

import java.io.File;

import com.molinari.utility.servicesloader.Extensible;

public interface FileOperation extends Extensible<FileOperation>{

	void execute(String pathFile, File f);

	boolean checkFile(File f);

	boolean checkDirectory(File f);

	
}
