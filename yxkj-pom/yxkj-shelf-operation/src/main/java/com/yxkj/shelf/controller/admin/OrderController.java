package com.yxkj.shelf.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.shelf.beans.CommonAttributes;
import com.yxkj.shelf.controller.base.BaseController;
import com.yxkj.shelf.json.base.BaseRequest;
import com.yxkj.shelf.json.base.BaseResponse;
import com.yxkj.shelf.json.base.PageResponse;
import com.yxkj.shelf.json.base.ResponseMultiple;


/**
 * Controller - 订单管理
 * 
 */
@Controller("orderController")
@RequestMapping("/admin/order")
@Api(value = "(货架后台)订单管理页面", description = "订单管理页面")
public class OrderController extends BaseController {   

    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "订单列表", httpMethod = "POST", response = BaseResponse.class, notes = "用于获取订单列表")
    @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
    public @ResponseBody ResponseMultiple<Map<String, Object>> getOrderList(
    		@ApiParam @RequestBody BaseRequest request) {

      ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();      

      //测试数据 start
      Map<String, Object> map1 = new HashMap<String, Object>();
      map1.put("companyName", "全程首位信息有限公司");
      map1.put("nickName", "qqq优比家");
      map1.put("userName", "微信OpenId");
      map1.put("paymentTime", "2017-08-01 12:09:25");
      map1.put("sn", "10000000");
      map1.put("paymentType", "微信");
      map1.put("amount", "12.50");
      map1.put("orderItemsNum","2");
      map1.put("status","0");
      
      Map<String, Object> map2 = new HashMap<String, Object>();
      map2.put("companyName", "全程首位信息有限公司");
      map2.put("nickName", "优比家");
      map2.put("userName", "微信OpenId");
      map2.put("paymentTime", "2017-08-01 12:09:25");
      map2.put("sn", "10000000");
      map2.put("paymentType", "支付宝");
      map2.put("amount", "12.50");
      map2.put("orderItemsNum","2");
      map2.put("status","1");
      
      Map<String, Object> map3 = new HashMap<String, Object>();
      map3.put("companyName", "全程首位信息有限公司");
      map3.put("nickName", "优比家");
      map3.put("userName", "微信OpenId");
      map3.put("paymentTime", "2017-08-01 12:09:25");
      map3.put("sn", "10000000");
      map3.put("paymentType", "微信");
      map3.put("amount", "12.50");
      map3.put("orderItemsNum","2");
      map3.put("status","2");
      
      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
      list.add(map1);  
      list.add(map2);
      list.add(map3);
      //测试数据 end
      
      PageResponse pageInfo = new PageResponse();
      pageInfo.setPageNumber(1);
      pageInfo.setPageSize(10);
      pageInfo.setTotal(9);
      response.setPage(pageInfo);
      response.setMsg(list);

      response.setCode(CommonAttributes.SUCCESS);
      return response;
    }
}
