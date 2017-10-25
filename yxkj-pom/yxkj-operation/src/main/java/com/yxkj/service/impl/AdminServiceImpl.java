package com.yxkj.service.impl; 

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.yxkj.dao.AdminDao;
import com.yxkj.entity.Admin;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import com.yxkj.service.AdminService;

@Service("adminServiceImpl")
public class AdminServiceImpl extends BaseServiceImpl<Admin,Long> implements AdminService {

    @Resource(name="adminDaoImpl")
    public void setBaseDao(AdminDao adminDao) {
        super.setBaseDao(adminDao);
    }

	@Override
	public Admin findByUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return null;
		}		
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("userName", userName));
		//filters.add(Filter.eq("adminStatus", AccountStatus.ACTIVED));
		
		return findFirst(filters, null);
	}
}