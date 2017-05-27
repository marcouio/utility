package com.molinari.utility.database.dao.columninfo;

import java.lang.reflect.Field;

public interface Definition {

	Field getField();

	void setField(Field field);
}
