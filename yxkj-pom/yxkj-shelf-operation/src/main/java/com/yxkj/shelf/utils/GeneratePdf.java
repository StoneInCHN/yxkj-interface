package com.yxkj.shelf.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.generic.NEW;

import com.ibm.icu.math.BigDecimal;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 生成商品二维码列表 PDF
 * @author luzhang
 *
 */
public class GeneratePdf {
	
	private String companyName;
	private String companyId;
	private List<Map<String,Object>> goodsList;
	private OutputStream out;
	private String projectDeployUrl;	
	private final int saleBottom = 30;
	private final int descHeight = 22;
	
	public GeneratePdf(String companyName,String companyId,List<Map<String,Object>> goodsList,OutputStream out,String projectDeployUrl){
		this.companyName = companyName;
		this.companyId = companyId;
		this.goodsList = goodsList;
		this.out = out;
		this.projectDeployUrl = projectDeployUrl;
	}

    public void generatePdf(){

        try {
            //设置中文字体
        	BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
          
            //设置字体样式
            Font textFont = new Font(bfChinese,9,Font.NORMAL); //正常
            Font boldFont = new Font(bfChinese,9,Font.BOLD); //加粗
            Font firsetTitleFont = new Font(bfChinese,22,Font.BOLD); //一级标题

            Document doc = new Document(PageSize.A4, 0, 0,10, 10);
            
            //创建输出流        	
            PdfWriter.getInstance(doc, out);
            
            doc.open();
            doc.newPage();
            
//            Paragraph p1 = new Paragraph("商品二维码列表", firsetTitleFont);
//            p1.setLeading(50);
//            p1.setAlignment(Element.ALIGN_CENTER);
//            doc.add(p1);
                
//            p1 = new Paragraph();  
//            p1.setLeading(20);
//            p1.setAlignment(Element.ALIGN_CENTER);
//            Phrase ph1 = new Phrase(); 
//            Chunk c3 = new Chunk("公司名称：", textFont) ;
//            Chunk c33 = new Chunk(companyName, boldFont) ;
//            ph1.add(c3);
//            ph1.add(c33);
//            p1.add(ph1);
//            doc.add(p1);
//            Paragraph p1 = new Paragraph("   ");
//            p1.setLeading(20);
//            doc.add(p1);
            
            // 创建一个有4列的表格  
            PdfPTable qrTable = new PdfPTable(4);
            PdfPCell cell = null;
            Image img = null;
            for (int i = 0; i < goodsList.size()/4; i++) {
            	for (int j = 0; j < 4; j++) {
            		Map<String,Object> goods = goodsList.get(i*4 + j);   
        			String desc = "";
        			if (goods.get("name") != null) {
        				desc += getShortStr(goods.get("name").toString(), 16, false);
					}
        			if (goods.get("spec") != null) {
        				desc += goods.get("spec").toString();
					}
                    cell = new PdfPCell(new Phrase(desc, textFont));
                    qrTable.addCell(setCommonStyle(cell, descHeight)); 
				}
            	for (int j = 0; j < 4; j++) {  
            		String shortName = getShortStr(companyName, 6, true);
                    cell = new PdfPCell(new Phrase(shortName, textFont));
                    qrTable.addCell(setCommonStyle(cell, 12)); 
				}
            	for (int j = 0; j < 4; j++) {
            		Map<String,Object> goods = goodsList.get(i*4 + j);                    
                    String content = projectDeployUrl+"/h5/shelf/"+companyId+"/"+goods.get("sn");  
                    System.out.println(content);
                    QRCodeGenerator qr = new QRCodeGenerator();
                    img = Image.getInstance(qr.generateQrImage(content));                    
                    cell= new PdfPCell(img);
                    qrTable.addCell(setCommonStyle(cell, 60)); 
				}
            	for (int j = 0; j < 4; j++) {  
            		Map<String,Object> goods = goodsList.get(i*4 + j);
        			String salePrice = "";
        			if (goods.get("salePrice")!=null) {
        				salePrice += "￥"+goods.get("salePrice").toString();
					}
                    cell = new PdfPCell(new Phrase(salePrice, textFont));
                    qrTable.addCell(setCommonStyle(cell, saleBottom)); 
				}
			}
            if (goodsList.size()%4 > 0) {
            	int left = goodsList.size()%4;
				int level = goodsList.size()/4;
            	for (int j = 0; j < 4; j++) {
            		if (j<left) {
            			Map<String,Object> goods = goodsList.get(level*4 + j); 
            			String desc = "";
            			if (goods.get("name")!=null) {
            				desc += getShortStr(goods.get("name").toString(), 16, false);
						}
            			if (goods.get("spec")!=null) {
            				desc += goods.get("spec").toString();
						}
                        cell = new PdfPCell(new Phrase(desc, textFont));
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}
                    qrTable.addCell(setCommonStyle(cell, descHeight)); 
				}
            	for (int j = 0; j < 4; j++) {  
            		if (j<left) {
                		String shortName = getShortStr(companyName, 6, true);
                        cell = new PdfPCell(new Phrase(shortName, textFont));
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}

                    qrTable.addCell(setCommonStyle(cell, 12)); 
				}
            	for (int j = 0; j < 4; j++) {
            		if (j<left) {
            			Map<String,Object> goods = goodsList.get(level*4 + j); 
                        String content = projectDeployUrl+"/h5/shelf/"+companyId+"/"+goods.get("sn");  
                        System.out.println(content);
                        QRCodeGenerator qr = new QRCodeGenerator();
                        img = Image.getInstance(qr.generateQrImage(content));
                        cell= new PdfPCell(img);
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}      
                    qrTable.addCell(setCommonStyle(cell, 60)); 
				}
            	for (int j = 0; j < 4; j++) {
            		if (j<left) {
            			Map<String,Object> goods = goodsList.get(level*4 + j); 
            			String salePrice = "";
            			if (goods.get("salePrice")!=null) {
            				salePrice += "￥"+goods.get("salePrice").toString();
    					}
                        cell = new PdfPCell(new Phrase(salePrice, textFont)); 
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}
                    qrTable.addCell(setCommonStyle(cell, saleBottom)); 
				}
			}
            
            doc.add(qrTable);  
            doc.close();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getShortStr(String str, int length, boolean tag) {
    	if (tag) {
    		if (str.length() > length) {
    			return "("+str.substring(0, length)+ "...)";
    		}
    		return "("+str+")";
		}else {
			if (str.length() > length) {
				return str.substring(0, length)+ "...";
			}
			return str;
		}
	}

	private PdfPCell setCommonStyle(PdfPCell cell, float height) {
    	cell.disableBorderSide(1);
    	cell.disableBorderSide(2);
    	cell.disableBorderSide(4);
    	cell.disableBorderSide(8);
    	cell.setMinimumHeight(height); //设置单元格高度
    	cell.setUseAscender(true); //设置可以居中
    	cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); //设置水平居中  
    	//cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); //设置垂直居中
        return cell;
	}

    public static void main(String[] args) {

        try {
        	List<Map<String,Object>> goodsList = new ArrayList<Map<String,Object>>();
        	Map<String,Object> map = new HashMap<String, Object>();
        	for (int i = 0; i < 134; i++) {
        		map.put("id", "1");
            	map.put("sn", "100001");
            	map.put("name", "我喜欢出发我喜欢出发我喜欢出发我喜欢出发我喜欢出发我喜欢出发我喜欢出发");
            	map.put("spec", "80g");
            	map.put("salePrice", new BigDecimal("12.5"));
            	goodsList.add(map);
        	}
        	OutputStream out = new FileOutputStream(new File("E:\\商品二维码.pdf"));
            GeneratePdf gp = new GeneratePdf("全成首位科技有限公司","1",goodsList,out,"http://test.ybjcq.com");
            gp.generatePdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
//    	String string = "　";
//    	System.out.println(string.length());

    }

}