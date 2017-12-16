package com.generic.dao;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;

import com.config.EntityConfig;
import com.config.Property;
import com.generic.exception.GenericException;
import com.generic.fnd.BasicEntity;

public abstract class AbstractDao {
	Map<String,EntityConfig> cache ;
	public abstract <T extends BasicEntity> boolean create(T t) throws GenericException;
	public abstract <T extends BasicEntity> boolean update(T t) throws GenericException;
	public abstract <T extends BasicEntity> boolean delete(T t) throws GenericException;
	public abstract <T extends BasicEntity> List<T> list(T t) throws GenericException;
	
	public AbstractDao(){
		cache = new HashMap<>();		
	}
	/**
	 * Get Object details like database table name, class name, property names 
	 * @param t
	 */
	public <T extends BasicEntity> EntityConfig getEntityConfig(T t) throws GenericException {
		String configFileName = ((BasicEntity)t).dbConfigFileName();
		if(configFileName ==null || "".equals(configFileName.trim())){
			throw new GenericException("Config file name not defined");
		}
		if(cache.get(configFileName)!=null){
			return cache.get(configFileName);
		}
		
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			EntityConfig config = mapper.readValue(new File("./"+configFileName+".json"), EntityConfig.class);
			cache.put(configFileName, config);
			return config;
		} catch (IOException e) {
			throw new GenericException(e);
		}
	}
	
	public <T extends BasicEntity> Map<Object, Object> getDBDetails(T t) throws GenericException{
		EntityConfig config = this.getEntityConfig(t);
		Map<Object,Object> columnData = new HashMap<>();
		for(Property prop : config.getProperties()){
			columnData.put(prop.getColumnName(), this.getPropertyData(t, prop.getProperty()));
		}
		return columnData;
		
	}
	private <T extends BasicEntity> Object getPropertyData(T t,String property) throws GenericException{
		Object data = null;
		try{
			Class<?> clazz = t.getClass();
			Method method = clazz.getMethod("get"+property, null);
			data =  method.invoke(t, null);					
		}catch(NoSuchMethodException e){
			throw new GenericException("Method Names not properly defined"+e);
		} catch (IllegalAccessException e) {
			throw new GenericException(e);		
		} catch (IllegalArgumentException e) {
			throw new GenericException(e);			
		} catch (InvocationTargetException e) {
			throw new GenericException(e);			
		}			
		return data;
	}
	
	public <T extends BasicEntity> T getObject(T t,Map<Object,Object> dbRows) throws GenericException{
		EntityConfig config = getEntityConfig(t);
		
		Class<?> clazz;
		try {
			clazz = Class.forName(config.getClassName());
			T o = (T)clazz.newInstance();
			
			Map<Object,Object> columnToProperty = this.getColumnToPropertyMapping(config);
			
			for(Entry<Object, Object> row : dbRows.entrySet()){			
				Method m = clazz.getMethod("set"+columnToProperty.get(row.getKey()), String.class);
				m.invoke(o, row.getValue());
			}
			return o;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException  | SecurityException e) {
			throw new GenericException("unable to create pojo"+ e);
		} catch(NoSuchMethodException|InvocationTargetException e){
			throw new GenericException("Method name not defined properly"+e);
		} catch(ClassCastException e){
			throw new GenericException("config class name does not match with entity type"+e);
		}
		
		
		
	}
	private <T extends BasicEntity> Map<Object,Object> getColumnToPropertyMapping(EntityConfig config){
		Map<Object,Object> columnToProperty = new HashMap<>();
		for(Property p : config.getProperties()){
			columnToProperty.put(p.getColumnName(), p.getProperty());
		}
		return columnToProperty;
	}
}
