package com.yxkj.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.common.log.LogUtil;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.service.FileService;
import com.yxkj.utils.ImageUtils;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {


  private static final String DEST_EXTENSION = "jpg";

  @Value("${ImageUploadPath}")
  private String uploadPath;
  @Value("${ProjectUploadPath}")
  private String projectUploadPath;

  @Value("${system.project_name}")
  private String projectName;

  @Resource(name = "taskExecutor")
  private Executor threadPoolExecutor;

  @Value("${system.project_deploy_url}")
  private String projectDeployUrl;

  @Value("${qrCode.prefix.url}")
  private String qrCodePrefixUrl;


  /**
   * 批量上传图片
   * 
   * @param multipartFile
   * @return
   */
  @Override
  public List<String> saveImage(MultipartFile[] multipartFile) {
    List<String> webPaths = new ArrayList<String>();
    String projectPath = projectUploadPath;
    if (multipartFile == null || multipartFile.length == 0) {
      return null;
    }
    try {
      for (MultipartFile multiFile : multipartFile) {
        String uuid = UUID.randomUUID().toString();
        String sourcePath =
            uploadPath + File.separator + "src_" + uuid + "."
                + FilenameUtils.getExtension(multiFile.getOriginalFilename());
        String webPath =
            projectPath + File.separator + "src_" + uuid + "."
                + FilenameUtils.getExtension(multiFile.getOriginalFilename());
        webPaths.add(webPath);
        File tempFile =
            new File(System.getProperty("java.io.tmpdir") + File.separator + "upload_"
                + UUID.randomUUID() + ".tmp");
        if (!tempFile.getParentFile().exists()) {
          tempFile.getParentFile().mkdirs();
        }

        multiFile.transferTo(tempFile);
        // 异步执行
        threadPoolExecutor.execute(new Runnable() {
          @Override
          public void run() {
            proccessImage(tempFile, sourcePath);
          }
        });

      }
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return webPaths;
  }



  @Override
  public String saveImage(MultipartFile multiFile, ImageType imageType) {
    String webPath = "";
    String imgUploadPath = "";
    String projectPath = "";
    try {

      if (multiFile == null) {
        return null;
      }
      String uuid = UUID.randomUUID().toString();

      if (imageType == ImageType.KEEPER_SUPP_IMG) { // 管家补货图片
        imgUploadPath = uploadPath + File.separator + "keeper";
        projectPath = projectUploadPath + File.separator + "keeper";
      }
      String sourcePath =
          imgUploadPath + File.separator + "src_" + uuid + "."
              + FilenameUtils.getExtension(multiFile.getOriginalFilename());
      if (StringUtils.isNotBlank(projectName)) {
        webPath += File.separator + projectName + File.separator;
      }
      webPath +=
          projectPath + File.separator + "src_" + uuid + "."
              + FilenameUtils.getExtension(multiFile.getOriginalFilename());

      File tempFile =
          new File(System.getProperty("java.io.tmpdir") + File.separator + "upload_"
              + UUID.randomUUID() + ".tmp");
      if (!tempFile.getParentFile().exists()) {
        tempFile.getParentFile().mkdirs();
      }
      multiFile.transferTo(tempFile);
      proccessImage(tempFile, sourcePath);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return webPath;
  }

  /**
   * 直接保存图片
   * 
   * @param tempFile
   * @param sourcePath
   * @param resizedPath
   */
  private void proccessImage(File tempFile, String sourcePath) {
    try {
      File destSrcFile = new File(sourcePath);
      FileUtils.moveFile(tempFile, destSrcFile);
    } catch (IOException e) {
      FileUtils.deleteQuietly(tempFile);
      LogUtil.debug(this.getClass(), "proccessImage", "Catch IOException:" + e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * 处理并保存图片
   * 
   * @param tempFile
   * @param sourcePath
   * @param resizedPath
   * @param width
   * @param height
   * @param moveSource
   */
  @SuppressWarnings("unused")
  private void proccessImage(File tempFile, String sourcePath, String resizedPath, Integer width,
      Integer height, boolean moveSource) {
    String tempPath = System.getProperty("java.io.tmpdir");
    File resizedFile =
        new File(tempPath + File.pathSeparator + "upload_" + UUID.randomUUID() + "."
            + DEST_EXTENSION);
    ImageUtils.zoom(tempFile, resizedFile, width, height);// 图片缩放

    File destFile = new File(resizedPath);
    try {
      if (moveSource) {
        File destSrcFile = new File(sourcePath);
        FileUtils.moveFile(tempFile, destSrcFile);
      }
      FileUtils.moveFile(resizedFile, destFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getProjectDeployUrl() {
    if (StringUtils.isNotBlank(projectDeployUrl)) {
      return projectDeployUrl;
    }
    return "";
  }

  @Override
  public String getQrCodePrefixUrl() {
    if (StringUtils.isNotBlank(qrCodePrefixUrl)) {
      return qrCodePrefixUrl;
    }
    return "";
  }

}
