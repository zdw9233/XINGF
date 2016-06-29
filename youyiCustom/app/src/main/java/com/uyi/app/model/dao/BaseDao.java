package com.uyi.app.model.dao;

import java.io.Serializable;
import java.util.List;

import com.uyi.app.model.bean.OrderBy;



@SuppressWarnings("serial")
public interface BaseDao<T> {
	
	public T get(Serializable serializable);
	
	public T get(String key,Object value);
	
	public List<T> get(String key[],Object value[]);
	
	public boolean save(T t);
	
	public boolean save(List<T> t);
	
	public boolean update(T t);
	
	public boolean remove(T t);
	
	public boolean remove(List<T> t);
	
	public boolean remove(String key[],Object value[]);
	
	public boolean remove(String key,Object value);
	
	public boolean remove(Serializable serializable);
	
	public List<T> findAll();
	
	public List<T> list(String key[],String Object[],int pageNumber,int pageSize,OrderBy OrderBy);
	
	public List<T> list(String key,String Object,int pageNumber,int pageSize,OrderBy OrderBy);
	
	public void clear();
	
}
