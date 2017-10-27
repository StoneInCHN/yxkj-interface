package com.yxkj.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

import com.yxkj.entity.Area;
import com.yxkj.entity.Company;
import com.yxkj.entity.ShelfOrder;

/**
 * Field过滤
 * 
 * @author sujinxuan
 *
 */
public class FieldFilterUtils<T> {



  /**
   * 设置Field Value
   * 
   * @param fieldName
   * @param fieldValue
   * @param entity
   * @return
   */
  public static Object addFieldValue(String fieldName, Object fieldValue, Object entity) {
    BeanMap beanMap = BeanMap.create(entity);
    beanMap.put(fieldName, fieldValue);
    return entity;
  }


  /**
   * 集合 字段过滤(采用beanMap)
   * 
   * @param propertys 需要保留的字段名
   * @param collection 集合
   * @return
   */
  public static <T> List<Map<String, Object>> filterCollectionMap(String[] propertys,
      Collection<T> collection) {
    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
    for (T entity : collection) {
      resultList.add(filterEntityMap(propertys, entity));
    }
    return resultList;

  }

  /**
   * entity field 过滤 (采用beanMap)
   * 
   * @param propertys 需要保留的字段名
   * @param entity 实体对象
   * @return
   */
  public static Map<String, Object> filterEntityMap(String[] propertys, Object entity) {
    Map<String, Object> map = new HashMap<String, Object>();
    // Map<String, Object> childMap = new HashMap<String, Object>();
    if (entity == null) {
      return map;
    }
    BeanMap beanMap = BeanMap.create(entity);
    for (String key : propertys) {
      String[] pros = key.split("\\.");
      if (pros != null && pros.length > 1) {// 目前只支持取2级对象，参数格式如lawyer.id
        Map<String, Object> childMap = new HashMap<String, Object>();
        String[] str = {pros[1]};
        childMap.putAll(filterEntityMap(str, beanMap.get(pros[0])));
        if (map.get(pros[0]) != null) {
          Object obj = map.get(pros[0]);
          ((Map<String, Object>) obj).putAll(childMap);
        } else {
          map.put(pros[0], childMap);
        }

      } else {
        map.put(key, beanMap.get(key));
      }
    }
    return map;

  }
  
  /**
   * 集合 字段过滤  (支持多级)
   * @param propertys
   * @param collection
   * @param hierarchical 是否返回 多层次结构Map
   * @author luzhang
   */
  public static <T> List<Map<String, Object>> filterCollection(String[] propertys,
	      Collection<T> collection) {
	    return filterCollection(propertys, collection, true);
   }
  public static <T> List<Map<String, Object>> filterCollection(String[] propertys,
	      Collection<T> collection, boolean hierarchical) {
	    List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
	    for (T entity : collection) {
	      resultList.add(filterEntity(propertys, entity, hierarchical));
	    }
	    return resultList;

   }
  /**
   * 字段过滤 (支持多级)
   * @param propertys
   * @param entity
   * @param hierarchical 是否返回 多层次结构Map
   * @author luzhang
   */
  public static Map<String, Object> filterEntity(String[] propertys, Object entity) {
	    return filterEntity(propertys, entity, true);
  }
  public static Map<String, Object> filterEntity(String[] propertys, Object entity, boolean hierarchical) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    if (entity == null) {
	      return map;
	    }
	    BeanMap beanMap = BeanMap.create(entity);
	    for (String key : propertys) {
	    	if (hierarchical) {
	    		setMapValue(key, beanMap, map);
			}else {
				map.put(key, getMapValue(beanMap, key));
			}	    	
	    }
	    return map;
  }
  /**
   * Map 赋值
   * @param key
   * @param map
   * @author luzhang
   */
  public static void setMapValue(String key, BeanMap beanMap, Map<String, Object> map){
  	String[] pros = key.split("\\.");
  	for (int i = 0; i < pros.length -1; i++) {
  		map = createMap(map, pros[i]);	    
	} 	
  	map.put(pros[pros.length - 1], getMapValue(beanMap, key));
  }
  /**
   * 创建多层次Map结构
   * @param parent
   * @param pros
   * @author luzhang
   */  
  public static Map<String, Object> createMap(Map<String, Object> parent, String pros){
		Map<String, Object> child = null;
		if (parent.containsKey(pros)) {
			child = (Map<String, Object>)parent.get(pros);
		}else {
			child = new HashMap<String, Object>();
			parent.put(pros, child);
		}
		return child;
  }
  /**
   * 根据key获取value
   * @param beanMap
   * @param key
   * @author luzhang
   */  
  public static Object getMapValue(BeanMap beanMap, String key){
	  if (key.indexOf(".") == -1) {
		return beanMap.get(key);
	  }else {
		  Object object = beanMap.get(key.substring(0, key.indexOf(".")));
		  if (object != null) {
				return getMapValue(BeanMap.create(object), 
						key.substring(key.indexOf(".")+1));
		  }else {
			return null;
		  }

	  }
  }

  public static void main(String[] args){

	  ShelfOrder shelfOrder = new ShelfOrder();
	  shelfOrder.setSn("订单编号");
	  shelfOrder.setId(1L);
	  Company company = new Company();
	  company.setSn("公司编号");
	  company.setDisplayName("XXX公司");
	  shelfOrder.setComp(company);
	  Area area = new Area();
	  area.setName("天府新区");
	  area.setFullName("四川省成都市天府新区");
	  Area parent = new Area();
	  parent.setName("成都市");
	  parent.setFullName("四川省成都市");
	  area.setParent(parent);
	  
	  company.setArea(area);	  
	  String[] props = {"sn", "comp.sn", "comp.displayName", 
			  "comp.area.name", "comp.area.fullName", "comp.area.parent.name", "comp.area.parent.fullName"};
	  
	  //单层次结构
	  Map<String, Object> map1 = filterEntity(props, shelfOrder, false);
	  System.out.println(map1);
	  //多层次结构
	  Map<String, Object> map = filterEntity(props, shelfOrder, true);
	  System.out.println(map);
  }
}
