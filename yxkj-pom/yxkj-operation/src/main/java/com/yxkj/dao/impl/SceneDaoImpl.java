package com.yxkj.dao.impl; 

import org.springframework.stereotype.Repository; 

import com.yxkj.entity.Scene;
import com.yxkj.framework.dao.impl.BaseDaoImpl;
import com.yxkj.dao.SceneDao;
@Repository("sceneDaoImpl")
public class SceneDaoImpl extends  BaseDaoImpl<Scene,Long> implements SceneDao {

}