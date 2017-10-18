package com.yxkj.shelf.service.impl; 

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource; 

import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yxkj.entity.Area;
import com.yxkj.entity.Company;
import com.yxkj.entity.CompanyShelf;
import com.yxkj.entity.ShelfCategory;
import com.yxkj.shelf.dao.CompanyDao;
import com.yxkj.shelf.service.AreaService;
import com.yxkj.shelf.service.CompanyService;
import com.yxkj.shelf.service.CompanyShelfService;
import com.yxkj.shelf.service.ShelfCategoryService;
import com.yxkj.shelf.framework.filter.Filter;
import com.yxkj.shelf.framework.service.impl.BaseServiceImpl;
import com.yxkj.shelf.json.admin.request.CompanyData;
import com.yxkj.shelf.json.admin.request.GoodsShelveRow;

@Service("companyServiceImpl")
public class CompanyServiceImpl extends BaseServiceImpl<Company,Long> implements CompanyService {

	@Resource(name = "areaServiceImpl")
	private AreaService areaService;		
	
	@Resource(name = "companyDaoImpl")
	private CompanyDao companyDao;
	
	@Resource(name = "shelfCategoryServiceImpl")
	private ShelfCategoryService shelfCategoryService;
	
	@Resource(name = "companyShelfServiceImpl")
	private CompanyShelfService companyShelfService;
	
    @Resource(name="companyDaoImpl")
    public void setBaseDao(CompanyDao companyDao) {
         super.setBaseDao(companyDao);
    }

	@Override
	public CompanyData getCompanyData(Company company) {
		  if (company == null) {
			return null;
		  }
	      CompanyData companyData = new CompanyData();
	      companyData.setSn(company.getSn());
	      companyData.setRemark(company.getRemark());
	      companyData.setDisplayName(company.getDisplayName());
	      companyData.setFullName(company.getFullName());
	      companyData.setContactPerson(company.getContactPerson());
	      companyData.setContactPhone(company.getContactPhone());
	      companyData.setAddress(company.getAddress());
	      
	      Area area = company.getArea();
	      if (area != null) {
	    	  companyData.setAreaFullName(area.getFullName());
		  }
	      List<Long> areaIds = new ArrayList<Long>();
	      if (area != null) {    	  
	    	  if (area.getParent() != null) {
	    		  Area father = area.getParent();
	    		  if (area.getParent().getParent() != null) {
	    			  Area grandpa = area.getParent();
	    			  areaIds.add(grandpa.getId());
	    		  }
	    		  areaIds.add(father.getId());
			  }
	    	  areaIds.add(area.getId());
		  }
	      companyData.setArea(areaIds);
	      Set<CompanyShelf> goodsShelves = company.getGoodsShelves();
	      List<GoodsShelveRow> allRows = getShelfList(null);
	      for (int i = 0; i < allRows.size(); i++) {
	    	  GoodsShelveRow goodsShelveRow = allRows.get(i);
				for (CompanyShelf goodsShelve : goodsShelves) {
					if (goodsShelveRow.getId() == goodsShelve.getShelfCate().getId()) {
						goodsShelveRow.setCount(goodsShelve.getCount());
						goodsShelveRow.setNeed(true);
						break;
					}
				}
		  }
		  companyData.setGoodsShelves(allRows);
	      return companyData;
	}

	@Override
	public Company getCompnayEntity(CompanyData companyData, Long companyId) {
	  Company company = null;
	  if (companyId == null) {
		 company = new Company();
	  }else {
		  company = find(companyId);
	  }  	  
  	  //company.setSn(companyData.getSn());
  	  company.setAddress(companyData.getAddress());
  	  company.setContactPerson(companyData.getContactPerson());
  	  company.setContactPhone(companyData.getContactPhone());
  	  company.setDisplayName(companyData.getDisplayName());
  	  company.setFullName(companyData.getFullName());
  	  company.setRemark(companyData.getRemark());
  	  List<Long> areas = companyData.getArea();
  	  if (areas != null && areas.size() > 0) {
  		  Long areaId = areas.get(areas.size()-1);
  		  company.setArea(areaService.find(areaId));
		  }
  	  List<GoodsShelveRow> rowList = companyData.getGoodsShelves();
  	  if (rowList != null && rowList.size() > 0) {
  		  company.setGoodsShelves(new HashSet<CompanyShelf>());
  		  for (GoodsShelveRow row : rowList) {
  			  if (row.getNeed() != null && row.getNeed() && row.getCount() > 0) {
          		  CompanyShelf companyShelf = new CompanyShelf();
          		  companyShelf.setComp(company);
          		  companyShelf.setCount(row.getCount());
          		  companyShelf.setShelfCate(shelfCategoryService.find(row.getId()));
          		  company.getGoodsShelves().add(companyShelf);
			  }
		  }
	  }
	  return company;
	}

	@Override
	public List<GoodsShelveRow> getShelfList(Long id) {
	      List<GoodsShelveRow> rowList = new ArrayList<GoodsShelveRow>();
	      List<Filter> filters = new ArrayList<Filter>();
	      if (id == null) {// 查询顶级地区
	        filters.add(Filter.isNull("parent"));
	      } else {// 查询areaId下的子级地区
	        filters.add(Filter.eq("parent", id));
	      }

	      List<ShelfCategory> list = shelfCategoryService.findAll();
	      for (ShelfCategory shelfCategory : list) {
	    	  rowList.add(new GoodsShelveRow(shelfCategory.getId(), shelfCategory.getSpec(), 0, false));
		  }
		return rowList;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCompany(CompanyData companyData, Long id) {
		Company company = find(id);
		Set<CompanyShelf> goodsShelves = company.getGoodsShelves();
		for (CompanyShelf companyShelf : goodsShelves) {
			companyShelfService.delete(companyShelf);
		}
  	    Company newCompany = getCompnayEntity(companyData, id);  
  	    update(newCompany);
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteCompany(Long[] ids) {
  	  List<Company> companys = findList(ids);
  	  for (Company company : companys) {
		if (company.getGoodsShelves() != null && company.getGoodsShelves().size() > 0) {
			for (CompanyShelf companyShelf : company.getGoodsShelves()) {
				companyShelfService.delete(companyShelf);
			}
		}
	  }
  	  delete(ids);
	}

	@Override
	public String genComSn() {
		String sn = companyDao.genComSn();
		while (sn.length() <= 10) {
			sn = "0"+sn;
		}
		return sn;
	}
}