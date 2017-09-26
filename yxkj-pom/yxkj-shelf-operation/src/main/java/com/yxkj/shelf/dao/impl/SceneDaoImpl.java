package com.yxkj.shelf.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Scene;
import com.yxkj.shelf.framework.dao.impl.BaseDaoImpl;
import com.yxkj.shelf.dao.SceneDao;
@Repository("sceneDaoImpl")
public class SceneDaoImpl extends  BaseDaoImpl<Scene,Long> implements SceneDao {

}