package com.yxkj.controller.file;

import io.swagger.annotations.Api;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.common.log.LogUtil;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.ContainerChannel;
import com.yxkj.entity.Goods;
import com.yxkj.entity.VendingContainer;
import com.yxkj.entity.commonenum.CommonEnum;
import com.yxkj.entity.commonenum.CommonEnum.ImageType;
import com.yxkj.framework.filter.Filter;
import com.yxkj.json.base.BaseRequest;
import com.yxkj.json.base.BaseResponse;
import com.yxkj.json.base.ResponseOne;
import com.yxkj.service.AdResourceService;
import com.yxkj.service.ContainerChannelService;
import com.yxkj.service.FileService;
import com.yxkj.service.GoodsService;
import com.yxkj.service.VendingContainerService;
import com.yxkj.utils.FieldFilterUtils;
import com.yxkj.utils.GeneratePdf;
import com.yxkj.utils.ImportExcel;
import com.yxkj.utils.TimeUtils;


/**
 * Controller - 文件上传管理
 * 
 * 
 */
@Controller("fileController")
@RequestMapping("/file")
@Api(value = "(货架后台)公共", description = "公共")
public class FileController extends BaseController {

  @Resource(name = "fileServiceImpl")
  private FileService fileService;
  
  @Resource(name = "goodsServiceImpl")
  private GoodsService goodsService;
  
  
  @Resource(name = "adResourceServiceImpl")
  private AdResourceService adResourceService;
  
  @Resource(name = "vendingContainerServiceImpl")
  private VendingContainerService vendingContainerService;
  
  @Resource(name = "containerChannelServiceImpl")
  private ContainerChannelService containerChannelService;
  
  @Resource(name = "taskExecutor")
  private Executor threadPoolExecutor;

  /**
   * 上传文件(单个)
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/uploadFile", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody BaseResponse uploadFile(HttpServletRequest request, ImageType imageType) {
	  LogUtil.debug(this.getClass(), "uploadFile", "imageType= %s", imageType);
    BaseResponse response = new BaseResponse();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile mf = entity.getValue();
      String displayPath = fileService.saveImage(mf, imageType);
      response.setDesc(displayPath);
      break;
    }
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

  /**
   * 批量上传图片
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/batchUploadFile", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody ResponseOne<List<String>> batchUploadFile(HttpServletRequest request,
      ImageType imageType, CommonEnum.FileType fileType) {
    ResponseOne<List<String>> response = new ResponseOne<>();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    List<String> displayPathList = new ArrayList<>();
    if (imageType == ImageType.AD_RESOURCE) {
      adResourceService.batchUpload(fileMap, fileType);
    } else {
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
        MultipartFile mf = entity.getValue();
        String displayPath = fileService.saveImage(mf, imageType);
        displayPathList.add(displayPath);
      }
      response.setMsg(displayPathList);
    }
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }
  
  /**
   * 导出某个货柜所选货道二维码pdf
   * 
   */
  @RequestMapping(value = "/downloadQrPdf", method = {RequestMethod.GET, RequestMethod.POST})
  public void downloadQrPdf(BaseRequest request, HttpServletResponse response,
      HttpSession session) {
    if (request.getId() == null || request.getIds() == null ||  request.getIds().length == 0) {
    	LogUtil.debug(this.getClass(), "downloadQrPdf", "货柜ID为空");
    	return;
	}
    if (request.getIds() == null ||  request.getIds().length == 0 ) {
    	LogUtil.debug(this.getClass(), "downloadQrPdf", "货道列表为空");
    	return;
	}
    VendingContainer container = vendingContainerService.find(request.getId());
    Long sceneId = container.getScene().getId();
    String sceneName = container.getScene().getName();
    Long containerId = container.getId();
    String containerSn = container.getSn();
    String title = sceneName + containerSn;
    
    List<ContainerChannel> channels =  containerChannelService.findList(request.getIds());
    String[] propertys = {"id", "sn"};
    List<Map<String, Object>> channelList = FieldFilterUtils.filterCollection(propertys, channels);

    try {
      response.setContentType("octets/stream");
      String filename = TimeUtils.getDateFormatString("yyyyMMddHHmmss", new Date());
      response.addHeader("Content-Disposition", "attachment;filename=" + filename + ".pdf");

      OutputStream out = response.getOutputStream();// 获得输出流
      GeneratePdf generatePdf = new GeneratePdf(sceneId, containerId, title, channelList, out, fileService.getQrCodePrefixUrl());
      Object locker = new Object();// 当前主线程的一把锁
      synchronized (locker) {
        threadPoolExecutor.execute(// 加入到线程池中执行
            new Runnable() {
              public void run() {
                generatePdf.generatePdf();
                synchronized (locker) {
                  locker.notify();
                }
              }
            });
        locker.wait();// 主线程等待
      }

      out.flush();
      out.close();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /**
   * Excel数据导入
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/dataImport", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody BaseResponse dataImport(HttpServletRequest request) {
    BaseResponse response = new BaseResponse();
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
    String desc = "商品导入结束。导入情况：";
    String errorSn = "失败商品条码：";
    int errCount = 0;
    for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
      MultipartFile excelFile = entity.getValue();
      ImportExcel importData = new ImportExcel();
      try {
        List<Map<String, Object>> rowMaps = importData.readExcelToMapList(excelFile);
        desc += "共计" + rowMaps.size() + "个";
        for (Map<String, Object> rowMap : rowMaps) {
          if (isValidGoodsRow(rowMap)) {
            Goods goods = importData.constructGoods(rowMap);
            goodsService.save(goods);
          } else {
            errCount++;
            errorSn += rowMap.get("sn") + " ";
          }
        }
        if (errCount > 0) {
          desc += "，成功" + (rowMaps.size() - errCount) + "个，失败" + errCount + "个。" + errorSn;
        } else {
          desc += "，成功" + rowMaps.size() + "个，失败0个。";
        }
      } catch (IOException e) {
        response.setCode(CommonAttributes.FAIL_COMMON);
        return response;
      }
      break;
    }
    response.setCode(CommonAttributes.SUCCESS);
    response.setDesc(desc);
    return response;
  }

  private boolean isValidGoodsRow(Map<String, Object> rowMap) {
	if (rowMap.get("sn") != null && StringUtils.isNotBlank(rowMap.get("sn").toString())) {
		if (goodsService.exists(Filter.eq("sn", rowMap.get("sn").toString()))) {
			rowMap.put("sn", rowMap.get("sn")+"(商品条码已存在)");
			return false;
		}
		if (rowMap.get("name") == null || StringUtils.isBlank(rowMap.get("name").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(商品名称缺失)");
			return false;
		}
		if (rowMap.get("spec") == null || StringUtils.isBlank(rowMap.get("spec").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(净含量缺失)");
			return false;
		}
		if (rowMap.get("costPrice") == null || StringUtils.isBlank(rowMap.get("costPrice").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(成本价缺失)");
			return false;
		}
		if (rowMap.get("salePrice") == null || StringUtils.isBlank(rowMap.get("salePrice").toString())) {
			rowMap.put("sn", rowMap.get("sn")+"(售价价缺失)");
			return false;
		}
		if (rowMap.get("name") != null && rowMap.get("spec") != null
		        && rowMap.get("costPrice") != null && rowMap.get("salePrice") != null 
		        && StringUtils.isNotBlank(rowMap.get("name").toString()) && StringUtils.isNotBlank(rowMap.get("spec").toString())
		        && StringUtils.isNotBlank(rowMap.get("costPrice").toString()) && StringUtils.isNotBlank(rowMap.get("salePrice").toString())) {
			return true;
		}
	}
    return false;
  }
}
