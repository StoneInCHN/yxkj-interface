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
 * Service 鍩虹被
 * 
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {

  /**
   * 鏌ユ壘瀹炰綋瀵硅薄
   * 
   * @param id ID
   * @return 瀹炰綋瀵硅薄锛岃嫢涓嶅瓨鍦ㄥ垯杩斿洖null
   */
  T find(ID id);
  /**
   * 鏌ユ壘棣栦釜瀹炰綋瀵硅薄
   * @param filters 绛涢€?
   * @param orders 鎺掑簭
   * @return 瀹炰綋瀵硅薄锛岃嫢涓嶅瓨鍦ㄥ垯杩斿洖null
   */
  T findFirst(List<Filter> filters, List<Ordering> orderings);

  /**
   * 鏌ユ壘鎵€鏈夊疄浣撳璞￠泦鍚?
   * 
   * @return 鎵€鏈夊疄浣撳璞￠泦鍚?
   */
  List<T> findAll();

  /**
   * 鏌ユ壘瀹炰綋瀵硅薄闆嗗悎
   * 
   * @param ids ID
   * @return 瀹炰綋瀵硅薄闆嗗悎
   */
  List<T> findList(ID... ids);

  /**
   * 鏌ユ壘瀹炰綋瀵硅薄闆嗗悎
   * 
   * @param count 鏁伴噺
   * @param filters 绛涢€?
   * @param orders 鎺掑簭
   * @return 瀹炰綋瀵硅薄闆嗗悎
   */
  List<T> findList(Integer count, List<Filter> filters, List<Ordering> orders);

  /**
   * 鏌ユ壘瀹炰綋瀵硅薄闆嗗悎
   * 
   * @param first 璧峰璁板綍
   * @param count 鏁伴噺
   * @param filters 绛涢€?
   * @param orders 鎺掑簭
   * @return 瀹炰綋瀵硅薄闆嗗悎
   */
  List<T> findList(Integer first, Integer count, List<Filter> filters, List<Ordering> orders);

  /**
   * 鏌ユ壘瀹炰綋瀵硅薄鍒嗛〉
   * 
   * @param pageable 鍒嗛〉淇℃伅
   * @return 瀹炰綋瀵硅薄鍒嗛〉
   */
  Page<T> findPage(Pageable pageable);

  /**
   * 鏌ヨ瀹炰綋瀵硅薄鎬绘暟
   * 
   * @return 瀹炰綋瀵硅薄鎬绘暟
   */
  long count();

  /**
   * 鏌ヨ瀹炰綋瀵硅薄鏁伴噺
   * 
   * @param filters 绛涢€?
   * @return 瀹炰綋瀵硅薄鏁伴噺
   */
  long count(Filter... filters);

  /**
   * 鍒ゆ柇瀹炰綋瀵硅薄鏄惁瀛樺湪
   * 
   * @param id ID
   * @return 瀹炰綋瀵硅薄鏄惁瀛樺湪
   */
  boolean exists(ID id);

  /**
   * 鍒ゆ柇瀹炰綋瀵硅薄鏄惁瀛樺湪
   * 
   * @param filters 绛涢€?
   * @return 瀹炰綋瀵硅薄鏄惁瀛樺湪
   */
  boolean exists(Filter... filters);

  /**
   * 淇濆瓨瀹炰綋瀵硅薄
   * 
   * @param entity 瀹炰綋瀵硅薄
   */
  void save(T entity);

  /**
   * 淇濆瓨澶氫釜瀹炰綋瀵硅薄
   */
  void save(List<T> entities);

  /**
   * 鏇存柊瀹炰綋瀵硅薄
   * 
   * @param entity 瀹炰綋瀵硅薄
   * @return 瀹炰綋瀵硅薄
   */
  T update(T entity);

  /**
   * 鏇存柊澶氫釜瀹炰綋瀵硅薄
   * 
   * @param entities 瀹炰綋瀵硅薄闆嗗悎
   */
  void update(List<T> entities);

  /**
   * 鏇存柊瀹炰綋瀵硅薄
   * 
   * @param entity 瀹炰綋瀵硅薄
   * @param ignoreProperties 蹇界暐灞炴€?
   * @return 瀹炰綋瀵硅薄
   */
  T update(T entity, String... ignoreProperties);

  /**
   * 鍒犻櫎瀹炰綋瀵硅薄
   * 
   * @param id ID
   */
  void delete(ID id);

  /**
   * 鍒犻櫎瀹炰綋瀵硅薄
   * 
   * @param ids ID
   */
  void delete(ID... ids);

  /**
   * 鍒犻櫎瀹炰綋瀵硅薄
   * 
   * @param entity 瀹炰綋瀵硅薄
   */
  void delete(T entity);

  /**
   * 灏嗗璞¤缃负娓哥鐘舵€?
   * 
   * @param entity
   */
  void detach(T entity);

  /**
   * 灏嗗璞¤缃负娓哥鐘舵€?
   * 
   * @param entity
   */
  void detach(Collection<T> entitys);

  /**
   * Lucene鍒嗛〉鏌ヨ
   */
  Page<T> search(Query query, Pageable pageable, Analyzer analyzer,
      org.apache.lucene.search.Filter filter, SortField sortField);

  /**
   * 閲嶅缓绱㈠紩
   */
  void refreshIndex();
}