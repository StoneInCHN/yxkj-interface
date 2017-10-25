package com.yxkj.service; 

import com.yxkj.entity.AdResource;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.AdResourceRequest;

public interface AdResourceService extends BaseService<AdResource,Long>{

		AdResource updateAdResource(AdResourceRequest request);
}
