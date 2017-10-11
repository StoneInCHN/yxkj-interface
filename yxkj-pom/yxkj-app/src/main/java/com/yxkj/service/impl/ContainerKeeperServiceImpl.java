package com.yxkj.service.impl; 

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yxkj.entity.ContainerKeeper;
import com.yxkj.dao.ContainerKeeperDao;
import com.yxkj.service.ContainerKeeperService;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("containerKeeperServiceImpl")
public class ContainerKeeperServiceImpl extends BaseServiceImpl<ContainerKeeper,Long> implements ContainerKeeperService {

      @Resource(name="containerKeeperDaoImpl")
      public void setBaseDao(ContainerKeeperDao containerKeeperDao) {
         super.setBaseDao(containerKeeperDao);
      }
      
      @Override
  	  public ContainerKeeper findByCellPhoneNum(String cellPhoneNum) {
  		if (StringUtils.isEmpty(cellPhoneNum)) {
  			return null;
  		}		
  		List<Filter> filters = new ArrayList<Filter>();
  		filters.add(Filter.eq("cellPhoneNum", cellPhoneNum));
  		//filters.add(Filter.eq("adminStatus", AccountStatus.ACTIVED));
  		
  		return findFirst(filters, null);
  	}

}