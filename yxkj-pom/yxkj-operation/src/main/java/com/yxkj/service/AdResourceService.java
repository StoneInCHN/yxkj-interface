package com.yxkj.service; 

import com.yxkj.entity.AdResource;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.AdResourceRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AdResourceService extends BaseService<AdResource,Long>{

		AdResource updateAdResource(AdResourceRequest request);

		void batchUpload(Map<String, MultipartFile> fileMap, CommonEnum.FileType fileType);
}
