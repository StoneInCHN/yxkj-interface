package com.yxkj.shelf.service; 

import java.util.List;

import com.yxkj.entity.Company;
import com.yxkj.shelf.framework.service.BaseService;
import com.yxkj.shelf.json.admin.request.CompanyData;
import com.yxkj.shelf.json.admin.request.GoodsShelveRow;

public interface CompanyService extends BaseService<Company,Long>{

	CompanyData getCompanyData(Company company);

	Company getCompnayEntity(CompanyData companyData, Long companyId);

	List<GoodsShelveRow> getShelfList(Long id);

	void updateCompany(CompanyData companyData, Long id);

	void deleteCompany(Long[] ids);

	String genComSn();

}