package com.yxkj.shelf.service;

import com.yxkj.entity.commonenum.CommonEnum.ImageType;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	  /**
	   * 批量上传图片
	   * 
	   * @param multipartFile
	   * @return
	   */
	  public String saveImage(MultipartFile[] multipartFile);

	  /**
	   * 按类型上传图片
	   * 
	   * @param multipartFile
	   * @param imageType
	   * @return
	   */
	  public String saveImage(MultipartFile multipartFile, ImageType imageType);


}
