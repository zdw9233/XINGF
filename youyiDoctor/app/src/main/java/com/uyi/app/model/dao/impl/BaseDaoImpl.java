package com.uyi.app.model.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.uyi.app.model.bean.OrderBy;
import com.uyi.app.model.dao.BaseDao;

import android.content.Context;

public class BaseDaoImpl<T> implements BaseDao<T> {

	private final static String dbname = "uyidoctor.db";
	protected Context context;
	private static final int dbVersion = 2;
	private Class<T> entityClass;
	DbUtils dbUtils = null;
	public BaseDaoImpl(Context context) {
		this.context = context;
		dbUtils  = DbUtils.create(context, dbname, dbVersion, null);
		dbUtils.configDebug(false); // debug模式 会输出sql语句
		dbUtils.configAllowTransaction(true); // 允许事务
		Class c = getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) parameterizedType[0];
        }
	}
	
	
	@Override
	public T get(Serializable serializable) {
		try {
			return (T)dbUtils.findById(entityClass, serializable);
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public T get(String key, Object value) {
		try {
			List<T> l = dbUtils.findAll(Selector.from(entityClass).where(key, "=", value));
			return (l!= null&&l.size()>0)?l.get(0):null;
		} catch (DbException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<T> get(String[] key, Object[] value) {
		try {
			Selector selector = Selector.from(entityClass);
			selector.where("'1'","=", "1");
			for(int i = 0 ;i < key.length ;i++){
				selector.and(key[i],"=", value[i]);
			}
			List<T> l =dbUtils.findAll(selector);
			return l==null?new ArrayList<T>():l;
		} catch (DbException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}

	@Override
	public boolean save(T t) {
		try {
			dbUtils.save(t);
		} catch (DbException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	@Override
	public boolean save(List<T> ts) {
		for(T t:ts){
			save(t);
		}
		return true;
	}
	
	
	@Override
	public boolean update(T t) {
		try {
			dbUtils.update(t, null);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(T t) {
		try {
			dbUtils.delete(t);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean remove(List<T> t) {
		
		for(T s : t){
			remove(s);
		}
		
		return true;
	}
	
	@Override
	public boolean remove(Serializable serializable) {
		try {
			dbUtils.deleteById(entityClass, serializable);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<T> findAll() {
		try {
			List<T> l = dbUtils.findAll(Selector.from(entityClass));
			return l==null?new ArrayList<T>():l;
		} catch (DbException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}
	
	@Override
	public List<T> list(String key, String value, int pageNumber, int pageSize, OrderBy OrderBy) {
		return list(new String[]{key}, new String[]{value}, pageNumber, pageSize, OrderBy);
	}
	
	@Override
	public List<T> list(String[] key, String[] value, int pageNumber, int pageSize, OrderBy OrderBy) {
		
		Selector selector = Selector.from(entityClass);
		
		selector.where("'1'", "=", "1");
		
		for(int i = 0 ;i < key.length ;i++){
			
			selector.and(key[i], "=", value[i]);
			
		}
		selector.orderBy(OrderBy.orderName, OrderBy.isDesc)
		.limit((pageNumber - 1) * pageSize)
		.offset(pageSize);
		
		try {
			List<T> l = dbUtils.findAll(selector);
			return l==null?new ArrayList<T>():l;
		} catch (DbException e) {
			e.printStackTrace();
			return new ArrayList<T>();
		}
	}


	@Override
	public boolean remove(String[] key, Object[] value) {
		WhereBuilder whereBuilder = WhereBuilder.b("'1'", "=", "1");
		
		for(int i = 0 ;i < key.length ;i++){
			whereBuilder.and(key[i], "=", value[i]);
		}
		try {
			dbUtils.delete(entityClass, whereBuilder);
			return true;
		} catch (DbException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public boolean remove(String key, Object value) {
		return remove(new String[]{key}, new Object[]{value});
	}


	@Override
	public void clear() {
		try {
			dbUtils.deleteAll(entityClass);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}




}
