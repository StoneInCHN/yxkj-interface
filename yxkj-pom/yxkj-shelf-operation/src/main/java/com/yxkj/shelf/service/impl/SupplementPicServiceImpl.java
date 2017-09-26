package com.yxkj.shelf.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementPic;
import com.yxkj.shelf.dao.SupplementPicDao;
import com.yxkj.shelf.service.SupplementPicService;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;

@Service("supplementPicServiceImpl")
public class SupplementPicServiceImpl extends BaseServiceImpl<SupplementPic,Long> implements SupplementPicService {

      @Resource(name="supplementPicDaoImpl")
      public void setBaseDao(SupplementPicDao supplementPicDao) {
         super.setBaseDao(supplementPicDao);
  }
}