package com.yxkj.shelf.service; 

import com.yxkj.entity.Admin;
import com.yxkj.shelf.framework.service.BaseService;

public interface AdminService extends BaseService<Admin,Long>{

	Admin findByUserName(String userName);

}