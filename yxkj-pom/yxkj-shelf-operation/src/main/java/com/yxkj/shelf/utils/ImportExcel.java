package com.yxkj.shelf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.yxkj.entity.Goods;
import com.yxkj.entity.commonenum.CommonEnum.CommonStatus;
/**
 * Excel 导入
 * @author luzhang
 *
 */
public class ImportExcel {
   
  public List<Map<String, Object>> readExcelToMapList(MultipartFile excelFile) throws IOException {    
    InputStream is = excelFile.getInputStream();
    XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
    List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
    if (xssfSheet == null) {
        return null;
    }
    // 第一行，标题行
    XSSFRow headerRow = xssfSheet.getRow(0);
    String[] headers = new String[headerRow.getLastCellNum()];
    for (int i = 0; i < headers.length; i++) {
      XSSFCell hederCell = headerRow.getCell(i);
      if (hederCell != null) {
        if (hederCell.getCellComment() != null) {
          headers[i] = hederCell.getCellComment().getString().toString();
        } else {
        	return null;
        }
      }
    }
    // 其他行，内容行
    for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
      Map<String, Object> map = new HashMap<String, Object>();
      XSSFRow xssfRow = xssfSheet.getRow(rowNum);
      if (xssfRow != null) {
        for (int i = 0; i < headers.length; i++) {
          map.put(headers[i], getValue(xssfRow.getCell(i)));
        }
      }
      mapList.add(map);
    }
    return mapList;
  }
  public Goods constructGoods(Map<String, Object> rowMap) {
	Goods goods = new Goods();
	goods.setSn(rowMap.get("sn").toString());
	goods.setName(rowMap.get("name").toString());
	goods.setSpec(rowMap.get("spec").toString());
	goods.setCostPrice(new BigDecimal(rowMap.get("costPrice").toString()));
	goods.setSalePrice(new BigDecimal(rowMap.get("salePrice").toString()));
	//goods.setFullName(rowMap.get("name").toString());
	goods.setStatus(CommonStatus.ACITVE);
    return goods;
  }
  
  private Object getValue(XSSFCell xssfCell) {
	    if (xssfCell == null) {
	      return "";
	    }
	    if (xssfCell.getCellType() == xssfCell.CELL_TYPE_BOOLEAN) {
	      // 返回布尔类型的值
	      return xssfCell.getBooleanCellValue();
	    } else if (xssfCell.getCellType() == xssfCell.CELL_TYPE_NUMERIC) {
	      // 返回数值类型的值,日期类型
	      if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
	        return xssfCell.getDateCellValue();
	      }
	      HSSFDataFormatter dataFormatter = new HSSFDataFormatter();
	      String cellFormatted = dataFormatter.formatCellValue(xssfCell);
	      return cellFormatted;
	    } else {
	       //返回字符串类型的值
	      return String.valueOf(xssfCell.getStringCellValue());
	    }
	  }
}
