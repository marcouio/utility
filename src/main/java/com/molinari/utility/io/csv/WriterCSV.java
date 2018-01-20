package com.molinari.utility.io.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.molinari.utility.controller.ControlloreBase;
import com.opencsv.CSVWriter;

public class WriterCSV<T> {

	private String pathFile;
	private Class<T> beanClass;
	
	public WriterCSV(Class<T> beanClass, String pathFile) {
		this.beanClass = beanClass;
		this.pathFile = pathFile;
	}
	
	public void write(List<T> bean) throws IOException {
		if(bean == null || bean.isEmpty()) {
			return;
		}
		setType(beanClass);
		
		CSVWriter csvWriter = getCSVWriter();
		
		List<Method> getters = createGetter();
		processAndWriteObjects(csvWriter, bean, getters);
		csvWriter.close();
		
	}
	
	
	
	private List<Method> createGetter() {
		List<Method> ret = new ArrayList<>();
		Method[] methods = beanClass.getMethods();
		if(methods != null) {
			for (Method method : methods) {
				String metName = method.getName();
				if(metName.startsWith("get")) {
					ret.add(method);
				}
			}
		}
		return ret;
	}

	private void setType(Class<T> beanClassPar) {
		this.beanClass = beanClassPar;
	}

	private void processAndWriteObjects(CSVWriter csv, List<?> objects, List<Method> getters) {
        for (Object obj : objects) {
            String[] line = processObject(getters, obj);
            csv.writeNext(line);
        }
    }
	
	protected String[] processObject(List<Method> getters, Object bean) {
		List<String> values = new ArrayList<>();
		// retrieve bean values
		try {
			for (Method getter : getters) {
				Object value = getter.invoke(bean, (Object[]) null);
				if (value == null) {
					values.add("null");
				} else {
					values.add(value.toString());
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			ControlloreBase.getLog().log(Level.SEVERE, e.getMessage(), e);
		}
		return values.toArray(new String[0]);
	}
	
	protected CSVWriter getCSVWriter() throws IOException{
		FileWriter fw = new FileWriter(pathFile, true);
		return new CSVWriter(fw);
	}
	
}
