package com.molinari.utility.io;

import com.molinari.utility.servicesloader.ServiceLoaderBase;

public class FactoryExecutorFiles {

	private FactoryExecutorFiles() {
		// do nothing
	}
	
	public static ExecutorFiles createExecutorFiles(FileOperation operation){
		ServiceLoaderBase<ExecutorFiles> slb = new ServiceLoaderBase<>();
		return slb.load(ExecutorFiles.class).createInstance(operation);
	}
}
