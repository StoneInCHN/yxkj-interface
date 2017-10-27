package com.yxkj.service.impl;

import javax.annotation.Resource;

import com.yxkj.entity.MachineAppUpgrade;
import com.yxkj.entity.Scene;
import com.yxkj.framework.paging.Page;
import com.yxkj.framework.paging.Pageable;
import com.yxkj.json.admin.request.MachineApkVersionRequest;
import com.yxkj.service.MachineAppUpgradeService;
import com.yxkj.service.SceneService;
import org.springframework.stereotype.Service;

import com.yxkj.entity.MachineApkVersion;
import com.yxkj.dao.MachineApkVersionDao;
import com.yxkj.service.MachineApkVersionService;
import com.yxkj.framework.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("machineApkVersionServiceImpl")
public class MachineApkVersionServiceImpl extends BaseServiceImpl<MachineApkVersion, Long>
    implements MachineApkVersionService {

  private MachineApkVersionDao machineApkVersionDao;
  @Resource(name = "sceneServiceImpl")
  private SceneService sceneService;
  @Resource(name = "machineAppUpgradeServiceImpl")
  private MachineAppUpgradeService machineAppUpgradeService;

  @Resource(name = "machineApkVersionDaoImpl")
  public void setBaseDao(MachineApkVersionDao machineApkVersionDao) {
    super.setBaseDao(machineApkVersionDao);
    this.machineApkVersionDao = machineApkVersionDao;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public MachineApkVersion saveMachineApkVersion(MachineApkVersionRequest request) {

    MachineApkVersion machineApkVersion = new MachineApkVersion();
    machineApkVersion.setApkPath(request.getApkPath());
    machineApkVersion.setVersionCode(request.getVersionCode());
    machineApkVersion.setVersionName(request.getVersionName());
    machineApkVersion.setUpdateContent(request.getUpdateContent());
    machineApkVersionDao.persist(machineApkVersion);
    for (Long sceneId : request.getSceneIds()) {
      Scene scene = sceneService.find(sceneId);

      MachineAppUpgrade machineAppUpgrade = new MachineAppUpgrade();
      machineAppUpgrade.setScene(scene);
      machineAppUpgrade.setMachineApkVersion(machineApkVersion);

      machineAppUpgradeService.save(machineAppUpgrade);
    }

    return machineApkVersion;
  }

  @Override
  public MachineApkVersion addUpdateScene(MachineApkVersionRequest request) {

    MachineApkVersion apkVersion = machineApkVersionDao.find(request.getId());
    for (Long sceneId : request.getSceneIds()) {
      Scene scene = sceneService.find(sceneId);

      MachineAppUpgrade machineAppUpgrade = new MachineAppUpgrade();
      machineAppUpgrade.setScene(scene);
      machineAppUpgrade.setMachineApkVersion(apkVersion);

      machineAppUpgradeService.save(machineAppUpgrade);
    }
    return apkVersion;
  }

  @Override
  public Page<MachineApkVersion> findPageBySceneName(Pageable pageable, String sceneName) {
    String jpql = null;
    Map<String, String> paramMap = new HashMap<>();
    if (sceneName != null) {
      jpql =
          "select apk from MachineApkVersion apk where apk.id in( select u.machineApkVersion.id from MachineAppUpgrade u where u.scene.name like:sceneName) ";
      paramMap.put("sceneName", "%" + sceneName + "%");
    } else {
      jpql =
          "select apk from MachineApkVersion apk where apk.id in( select u.machineApkVersion.id from MachineAppUpgrade u)";
    }

    return machineApkVersionDao.findPageCustomized(pageable, jpql, paramMap);
  }
}
