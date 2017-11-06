package com.yxkj.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.yxkj.dao.SceneDao;
import com.yxkj.entity.Scene;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.json.admin.response.SceneProfile;

@Repository("sceneDaoImpl")
public class SceneDaoImpl extends BaseDaoImpl<Scene, Long> implements SceneDao {

  @Override
  public List<Scene> getByKey(String key, Long pId) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Scene> criteriaQuery = criteriaBuilder.createQuery(Scene.class);
    Root<Scene> scene = criteriaQuery.from(Scene.class);
    Predicate restrictions =
        criteriaQuery.getRestriction() != null ? criteriaQuery.getRestriction() : criteriaBuilder
            .conjunction();
    restrictions =
        criteriaBuilder.and(restrictions, criteriaBuilder.like(scene.get("sn"), "%" + key + "%"));
    restrictions =
        criteriaBuilder.or(restrictions, criteriaBuilder.like(scene.get("name"), "%" + key + "%"));
    restrictions =
        criteriaBuilder.and(restrictions,
            criteriaBuilder.equal(scene.get("removeStatus"), CommonStatus.ACITVE));
    if (pId != null) {
      restrictions =
          criteriaBuilder.and(restrictions,
              criteriaBuilder.equal((scene.get("propertyKeeper")).get("id"), pId));
    }

    criteriaQuery.where(restrictions);
    List<javax.persistence.criteria.Order> orderList =
        new ArrayList<javax.persistence.criteria.Order>();
    orderList.add(criteriaBuilder.asc(scene.get("sn")));
    criteriaQuery.orderBy(orderList);
    TypedQuery<Scene> query =
        entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT);
    return query.getResultList();
  }

 @Override
 public List<SceneProfile> getSceneListByKeeper(Long id) {
	 List<SceneProfile> sceneProfiles = new ArrayList<SceneProfile>();
	 String sql = null;
	 if (id == null) {
		 sql = "select id,name,sn,remove_status from t_scene where cntr_keeper is null order by id";
	 }else {
		 sql = "select id,name,sn,remove_status from t_scene where cntr_keeper is null or cntr_keeper = " + id + " order by id";
	 }
		@SuppressWarnings("rawtypes")
		List list = entityManager.createNativeQuery(sql).setFlushMode(FlushModeType.COMMIT).getResultList();
	    for (Object object : list) {
	    	Object[] entity = (Object[]) object;
	    	SceneProfile sceneProfile = new SceneProfile(entity[0].toString(),entity[1].toString(), entity[2].toString(), 
	    			Integer.parseInt(entity[3].toString()) == 0? false:true);
	    	sceneProfiles.add(sceneProfile);
	    }
	return sceneProfiles;
 }
 @Override
 public List<SceneProfile> getSceneListByProperty(Long id) {
	 List<SceneProfile> sceneProfiles = new ArrayList<SceneProfile>();
	 String sql = null;
	 if (id == null) {
		 sql = "select id,name,sn,remove_status from t_scene where property_keeper is null order by id";
	 }else {
		 sql = "select id,name,sn,remove_status from t_scene where property_keeper is null or property_keeper = " + id + " order by id";
	 }
		@SuppressWarnings("rawtypes")
		List list = entityManager.createNativeQuery(sql).setFlushMode(FlushModeType.COMMIT).getResultList();
	    for (Object object : list) {
	    	Object[] entity = (Object[]) object;
	    	SceneProfile sceneProfile = new SceneProfile(entity[0].toString(),entity[1].toString(), entity[2].toString(), 
	    			Integer.parseInt(entity[3].toString()) == 0? false:true);
	    	sceneProfiles.add(sceneProfile);
	    }
	return sceneProfiles;
 }
}
