package com.yxkj.shelf.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.shelf.common.log.LogUtil;
import com.yxkj.shelf.service.FileService;
import com.yxkj.shelf.utils.ImageUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {


  private static final String DEST_EXTENSION = "jpg";

  @Value("${ImageUploadPath}")
  private String uploadPath;
  @Value("${ProjectUploadPath}")
  private String projectUploadPath;
  
  @Resource(name = "taskExecutor")
  private Executor threadPoolExecutor;

  /**
   * 批量上传图片
   * 
   * @param multipartFile
   * @return
   */
  @Override
  public String saveImage(MultipartFile[] multipartFile) {
    String webPath = null;
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
        webPath =
            projectPath + File.separator + "src_" + uuid + "."
                + FilenameUtils.getExtension(multiFile.getOriginalFilename());

        File tempFile =
            new File(System.getProperty("java.io.tmpdir") + File.separator + "upload_"
                + UUID.randomUUID() + ".tmp");
        if (!tempFile.getParentFile().exists()) {
          tempFile.getParentFile().mkdirs();
        }

        multiFile.transferTo(tempFile);
        //异步执行
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

    return webPath;
  }



  @Override
  public String saveImage(MultipartFile multiFile, ImageType imageType) {
    String webPath = null;
    String imgUploadPath = "";
    String projectPath = "";
    try {

      if (multiFile == null) {
        return null;
      }
      String uuid = UUID.randomUUID().toString();
      
      if (imageType == ImageType.GOODS_IMG) {//商品图片
        imgUploadPath = uploadPath + File.separator + "goods";
        projectPath = projectUploadPath + File.separator + "goods";
      }
      String sourcePath =
          imgUploadPath + File.separator + "src_" + uuid + "."
              + FilenameUtils.getExtension(multiFile.getOriginalFilename());
      webPath =
          projectPath + File.separator + "src_" + uuid + "."
              + FilenameUtils.getExtension(multiFile.getOriginalFilename());

      File tempFile =
          new File(System.getProperty("java.io.tmpdir") + File.separator + "upload_"
              + UUID.randomUUID() + ".tmp");
      if (!tempFile.getParentFile().exists()) {
        tempFile.getParentFile().mkdirs();
      }
      multiFile.transferTo(tempFile);
//      //异步执行
//      threadPoolExecutor.execute(new Runnable() {		
//		@Override
//		public void run() {
			proccessImage(tempFile, sourcePath);			
//		}
//      });      
      
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
    	LogUtil.debug(this.getClass(), "proccessImage", "Catch IOException:"+e.getMessage());
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
  private void proccessImage(File tempFile, String sourcePath, String resizedPath, Integer width,
      Integer height, boolean moveSource) {
    String tempPath = System.getProperty("java.io.tmpdir");
    File resizedFile =
        new File(tempPath + File.pathSeparator + "upload_" + UUID.randomUUID() + "."
            + DEST_EXTENSION);
    ImageUtils.zoom(tempFile, resizedFile, width, height);//图片缩放

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
}
