package com.yxkj.service.impl; 

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 

import com.yxkj.entity.ContainerCategory;
import com.yxkj.dao.ContainerCategoryDao;
import com.yxkj.service.ContainerCategoryService;
import com.yxkj.framework.filter.Filter;
import com.yxkj.framework.service.impl.BaseServiceImpl;

@Service("containerCategoryServiceImpl")
public class ContainerCategoryServiceImpl extends BaseServiceImpl<ContainerCategory,Long> implements ContainerCategoryService {

    @Resource(name="containerCategoryDaoImpl")
    public void setBaseDao(ContainerCategoryDao containerCategoryDao) {
         super.setBaseDao(containerCategoryDao);
    }

	@Override
	public ContainerCategory getCentralCategory() {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("central", true));
		return findFirst(filters, null);
	}
}