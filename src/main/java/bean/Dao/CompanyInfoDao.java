package bean.Dao;

import bean.CompanyInfo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午9:51
 * To change this template use File | Settings | File Templates.
 */
public interface CompanyInfoDao {
	public void setCompanyInfo(CompanyInfo companyInfo);
	public List<String> getCompanyName();
}
