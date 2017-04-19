package com.softtek.mdm.dao;

import java.io.Serializable;
import java.util.Collection;

/**
 * 
 * Crud Stardard Mapper.
 * 
 * @param <T> Model Class
 * @param <ID> Model PrimaryKey Class
 * @author Color.Wu
 * @version 2016/3/5
 * 
 * 
 */
public interface CrudMapper<T,ID extends Serializable> {
	
	/**
	 * Saves a given entity.
	 * 
	 * @param entity -
	 * @return the number of entities being insert
	 */
	int save(T entity);
	
	/**
	 * Saves all given entities.
	 * 
	 * @param entities -
	 * @return the number of entities being insert
	 */
	int saveAll(Iterable<? extends T> entities);
	
	/**
	 * Retrives an entity by its primary key.
	 * 
	 * @param id -
	 * @return the entity with the given primary key or null if none found
	 * @throw IllegalArgumentException if primaryKey is null
	 */
	T findOne(ID id);
	
	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param id -
	 * @return true if an entity with the given id exists, false otherwise
	 * @throw IllegalArgumentException if primaryKey is null
	 */
	boolean exists(ID id);
	
	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	Collection<T> findAll();
	
	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	long count();
	
	/**
	 * Deletes the entity with the given id.
	 * 
	 * @param id -
	 * @return lines number be effected
	 */
	int deleteWithPk(ID id);
	
	/**
	 * Deletes a given entity. -
	 * 
	 * @param entity -
	 * @return lines number be effected
	 */
	int delete(T entity);
	
	/**
	 * Deletes the given entities.
	 * 
	 * @param entities -
	 * @return lines number be effected
	 */
	int deletes(Iterable<? extends T> entities);
	
	/**
	 * Deletes all entities managed by the repository.
	 * 
	 * @return lines number be effected
	 */
	int deleteAll();

	/**
	 * Updates the entity with the given id.
	 * 
	 * @param entity -
	 * @return lines number be effected
	 */
	int update(T entity);
}
