package com.yxkj.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;

import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.ordering.Ordering;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;

/**
 * Service 基类
 * 
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {

  /**
   * 查找实体对象
   * 
   * @param id ID
   * @return 实体对象，若不存在则返回null
   */
  T find(ID id);
  /**
   * 查找首个实体对象
   * @param filters 筛选
   * @param orders 排序
   * @return 实体对象，若不存在则返回null
   */
  T findFirst(List<Filter> filters, List<Ordering> orderings);

  /**
   * 查找所有实体对象集合
   * 
   * @return 所有实体对象集合
   */
  List<T> findAll();

  /**
   * 查找实体对象集合
   * 
   * @param ids ID
   * @return 实体对象集合
   */
  List<T> findList(ID... ids);

  /**
   * 查找实体对象集合
   * 
   * @param count 数量
   * @param filters 筛选
   * @param orders 排序
   * @return 实体对象集合
   */
  List<T> findList(Integer count, List<Filter> filters, List<Ordering> orders);

  /**
   * 查找实体对象集合
   * 
   * @param first 起始记录
   * @param count 数量
   * @param filters 筛选
   * @param orders 排序
   * @return 实体对象集合
   */
  List<T> findList(Integer first, Integer count, List<Filter> filters, List<Ordering> orders);

  /**
   * 查找实体对象分页
   * 
   * @param pageable 分页信息
   * @return 实体对象分页
   */
  Page<T> findPage(Pageable pageable);

  /**
   * 查询实体对象总数
   * 
   * @return 实体对象总数
   */
  long count();

  /**
   * 查询实体对象数量
   * 
   * @param filters 筛选
   * @return 实体对象数量
   */
  long count(Filter... filters);

  /**
   * 判断实体对象是否存在
   * 
   * @param id ID
   * @return 实体对象是否存在
   */
  boolean exists(ID id);

  /**
   * 判断实体对象是否存在
   * 
   * @param filters 筛选
   * @return 实体对象是否存在
   */
  boolean exists(Filter... filters);

  /**
   * 保存实体对象
   * 
   * @param entity 实体对象
   */
  void save(T entity);

  /**
   * 保存多个实体对象
   */
  void save(List<T> entities);

  /**
   * 更新实体对象
   * 
   * @param entity 实体对象
   * @return 实体对象
   */
  T update(T entity);

  /**
   * 更新多个实体对象
   * 
   * @param entities 实体对象集合
   */
  void update(List<T> entities);

  /**
   * 更新实体对象
   * 
   * @param entity 实体对象
   * @param ignoreProperties 忽略属性
   * @return 实体对象
   */
  T update(T entity, String... ignoreProperties);

  /**
   * 删除实体对象
   * 
   * @param id ID
   */
  void delete(ID id);

  /**
   * 删除实体对象
   * 
   * @param ids ID
   */
  void delete(ID... ids);

  /**
   * 删除实体对象
   * 
   * @param entity 实体对象
   */
  void delete(T entity);

  /**
   * 将对象设置为游离状态
   * 
   * @param entity
   */
  void detach(T entity);

  /**
   * 将对象设置为游离状态
   * 
   * @param entity
   */
  void detach(Collection<T> entitys);

  /**
   * Lucene分页查询
   */
  Page<T> search(Query query, Pageable pageable, Analyzer analyzer,
      org.apache.lucene.search.Filter filter, SortField sortField);

  /**
   * 重建索引
   */
  void refreshIndex();
}
