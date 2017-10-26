package com.yxkj.json.admin.request;

import com.yxkj.json.admin.bean.SceneData;
import com.yxkj.json.base.BaseRequest;

public class SceneRequest extends BaseRequest{		

	private SceneData sceneData;

	public SceneData getSceneData() {
		return sceneData;
	}

	public void setSceneData(SceneData sceneData) {
		this.sceneData = sceneData;
	}
	
}
