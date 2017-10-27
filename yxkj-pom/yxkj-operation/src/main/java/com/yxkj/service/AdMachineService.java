package com.yxkj.service; 

import com.yxkj.entity.AdMachine;
import com.yxkj.framework.service.BaseService;
import com.yxkj.json.admin.request.AdMachineRequest;

public interface AdMachineService extends BaseService<AdMachine,Long>{

		AdMachine updateAdMachine(AdMachineRequest request);

		void batchUpdateAdMachine(AdMachineRequest request);
}
