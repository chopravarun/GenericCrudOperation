package com.generic.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.generic.exception.GenericException;
import com.generic.fnd.BasicEntity;

public class GenericDaoImpl extends AbstractDao{
	Map<String,List<Map<Object,Object>>> database;
	public GenericDaoImpl() {
		database = new HashMap<>();
		
	}

	@Override
	public <T extends BasicEntity> boolean create(T t) throws GenericException {
		String tableName = this.getEntityConfig(t).getTableName();
		if(tableName==null || "".equals(tableName.trim())){
			throw new GenericException("table name is empty");
		}
		
		Map<Object,Object> row = getDBDetails(t);
			
		if(database.get(tableName)==null){
			List<Map<Object,Object>> tableData =new ArrayList();
			tableData.add(row);
			database.put(tableName, tableData);
		}else{
			 database.get(tableName).add(row);
		}
					
		return true;
	}

	@Override
	public <T extends BasicEntity> boolean update(T t) throws GenericException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends BasicEntity> boolean delete(T t) throws GenericException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends BasicEntity> List<T> list(T t) throws GenericException {
		String tableName = this.getEntityConfig(t).getTableName();
		
		List<Map<Object,Object>> rows = database.get(tableName);
		List<T> data = new ArrayList<>();
		for(Map<Object,Object> row : rows){
			T m = this.getObject(t, row);
			data.add(m);
		}
		
		return data;
	}

}
