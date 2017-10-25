package com.yxkj.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.controller.base.BaseController;
import com.yxkj.entity.Scene;
import com.yxkj.json.base.BaseListRequest;
import com.yxkj.json.base.ResponseMultiple;
import com.yxkj.service.SceneService;
import com.yxkj.utils.FieldFilterUtils;

/**
 * 优享空间 - Controller
 * 
 * @author Andrea
 * @version 2017年10月24日 上午10:42:55
 */
@Controller("sceneController")
@RequestMapping("/admin/scene")
@Api(value = "(货柜后台)优享空间", description = "优享空间")
public class SceneController extends BaseController {



  @Resource(name = "sceneServiceImpl")
  private SceneService sceneService;

  /**
   * 获取优享空间下拉列表
   * 
   * @param request
   * @return
   */
  @RequestMapping(value = "/ddList", method = RequestMethod.POST)
  @ApiOperation(value = "优享空间下拉列表", httpMethod = "POST", response = ResponseMultiple.class,
      notes = "优享空间下拉列表")
  @ApiResponses({@ApiResponse(code = 200, message = "code描述[0000:请求成功; 1000:操作失败]")})
  public @ResponseBody ResponseMultiple<Map<String, Object>> list(
      @ApiParam(name = "请求参数(json)", value = "参数[userName:登录用户名; key:优享空间地址或编号]", required = false) @RequestBody BaseListRequest request) {
    ResponseMultiple<Map<String, Object>> response = new ResponseMultiple<Map<String, Object>>();
    String key = request.getKey();
    List<Scene> scenes = new ArrayList<Scene>();
    if (key == null) {
      scenes = sceneService.findAll();
    } else {
      scenes = sceneService.getByKey(key);
    }

    String[] propertys = {"id", "name"};
    List<Map<String, Object>> result = FieldFilterUtils.filterCollection(propertys, scenes);
    response.setMsg(result);
    response.setCode(CommonAttributes.SUCCESS);
    return response;
  }

}
