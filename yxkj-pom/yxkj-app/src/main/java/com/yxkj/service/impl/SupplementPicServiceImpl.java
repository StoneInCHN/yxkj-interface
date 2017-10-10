package com.yxkj.service.impl; 

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.SupplementPic;
import com.yxkj.dao.SupplementPicDao;
import com.yxkj.service.SupplementPicService;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("supplementPicServiceImpl")
public class SupplementPicServiceImpl extends BaseServiceImpl<SupplementPic,Long> implements SupplementPicService {

      @Resource(name="supplementPicDaoImpl")
      public void setBaseDao(SupplementPicDao supplementPicDao) {
         super.setBaseDao(supplementPicDao);
  }
}