package com.f2b2c.eco.dao.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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
	 * 根据主键查询记录
	 * @param id 主键id
	 * @return 查找的对象
	 */
	T find(ID id);
	/**
	 * 根据主键删除记录
	 * @param id 主键id
	 * @return 删除的行数
	 */
	int delete(ID id);

   /**
    * 新增一条记录
    * @param entity 实体类对象
    * @return 插入的行数
    */
    int insert(T entity);

    /**
     * 新增一条记录,并对记录属性判断是否为空
     * @param entity 实体类对象
     * @return 插入的行数
     */
    int insertSelective(T entity);

    /**
     * 根据主键查询记录对象
     * @param id
     * @return 记录
     */
    T select(ID id);

    /**
     * 更新记录
     * @param entity 
     * @return 更新的行数
     */
    int update(T entity);
    
    /**
     * 更新非空的字段
     * @param entity 
     * @return 更新的行数
     */
    int updateByPrimaryKeySelective(T entity);
    
    /**
     * 查询所有的记录
     * @return 记录集合
     */
    List<T> findAll();
    
    /**
     * 查询总记录数
     * @return 记录总数
     */
    int count();
    
    /**
     * 查询总记录数带参数
     * @param map
     * @return
     */
    int countWithMap(Map<String,Object> map);
    
    /**
     * 分页查询
     * 添加LIMIT ${offset}, ${pageSize}
     * 请将Pageable pageable对象放入map中，key值为pageable
     * map.put("pageable",pageable);
     * @param pageable
     * @return
     */
    List<T> findWithPagination(Map<String, Object> map);
}
