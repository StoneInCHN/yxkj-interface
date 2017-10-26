package com.yxkj.service; 

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.MachineApkVersionRequest;

public interface MachineApkVersionService extends BaseService<MachineApkVersion,Long>{

		MachineApkVersion saveMachineApkVersion(MachineApkVersionRequest request);

		MachineApkVersion addUpdateScene(MachineApkVersionRequest request);

		Page<MachineApkVersion> findPageBySceneName(Pageable pageable,String sceneName);
}
