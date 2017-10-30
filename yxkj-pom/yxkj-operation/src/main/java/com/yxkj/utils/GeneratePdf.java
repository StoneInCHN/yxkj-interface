package com.yxkj.utils;

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
	private Long sceneId;
	private Long containerId;
	private String title;
	private List<Map<String,Object>> channelList;
	private OutputStream out;
	private String projectDeployUrl;	
	private final int descHeight = 15;
	
	public GeneratePdf(Long sceneId, Long containerId, String title, List<Map<String,Object>> channelList,
			OutputStream out,String projectDeployUrl){
		this.sceneId = sceneId;
		this.containerId = containerId;
		this.title = title;
		this.channelList = channelList;
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
            
            Paragraph p1 = new Paragraph("货道二维码列表", firsetTitleFont);
            p1.setLeading(50);
            p1.setAlignment(Element.ALIGN_CENTER);
            doc.add(p1);
                
            p1 = new Paragraph();  
            p1.setLeading(20);
            p1.setAlignment(Element.ALIGN_CENTER);
            Phrase ph1 = new Phrase(); 
            Chunk c33 = new Chunk(title, textFont) ;
            ph1.add(c33);
            p1.add(ph1);
            doc.add(p1);
            p1 = new Paragraph("   ");
            p1.setLeading(20);
            doc.add(p1);
            
            // 创建一个有4列的表格  
            PdfPTable qrTable = new PdfPTable(6);
            PdfPCell cell = null;
            Image img = null;
            for (int i = 0; i < channelList.size()/6; i++) {
            	for (int j = 0; j < 6; j++) {
            		Map<String,Object> channel = channelList.get(i*6 + j);   
                    cell = new PdfPCell(new Phrase(channel.get("sn").toString(), textFont));
                    qrTable.addCell(setCommonStyle(cell, descHeight)); 
				}
            	for (int j = 0; j < 6; j++) {
            		Map<String,Object> channel = channelList.get(i*6 + j);                    
                    String content = projectDeployUrl+"/"+sceneId+"/"+containerId+"/"+channel.get("sn");  
                    System.out.println(content);
                    QRCodeGenerator qr = new QRCodeGenerator();
                    img = Image.getInstance(qr.generateQrImage(content));                    
                    cell= new PdfPCell(img);
                    qrTable.addCell(setCommonStyle(cell, 30)); 
				}
			}
            if (channelList.size()%6 > 0) {
            	int left = channelList.size()%4;
				int level = channelList.size()/4;
            	for (int j = 0; j < 6; j++) {
            		if (j<left) {
            			Map<String,Object> channel = channelList.get(level*6 + j); 
                        cell = new PdfPCell(new Phrase(channel.get("sn").toString(), textFont));
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}
                    qrTable.addCell(setCommonStyle(cell, descHeight)); 
				}
            	for (int j = 0; j < 6; j++) {
            		if (j<left) {
            			Map<String,Object> channel = channelList.get(level*6 + j); 
                        String content = projectDeployUrl+"/"+sceneId+"/"+containerId+"/"+channel.get("sn");
                        System.out.println(content);
                        QRCodeGenerator qr = new QRCodeGenerator();
                        img = Image.getInstance(qr.generateQrImage(content));
                        cell= new PdfPCell(img);
					}else {
	                    cell = new PdfPCell(new Phrase(" ", textFont));
					}      
                    qrTable.addCell(setCommonStyle(cell, 30)); 
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
        	List<Map<String,Object>> channelList = new ArrayList<Map<String,Object>>();
        	Map<String,Object> map = new HashMap<String, Object>();
        	for (int i = 0; i < 114; i++) {
        		map.put("id", "20");
            	map.put("sn", "A11");
            	channelList.add(map);
        	}
        	OutputStream out = new FileOutputStream(new File("D:\\商品二维码.pdf"));
            GeneratePdf gp = new GeneratePdf(1L,2L,"优享空间T1A货柜", channelList,out,"http://www.ybjstore.com");
            gp.generatePdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
//    	String string = "　";
//    	System.out.println(string.length());

    }

}