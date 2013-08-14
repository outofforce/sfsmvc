package bean.DaoImpl;

import bean.Dao.RecruitInfoDao;
import bean.RecruitInfo;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午11:08
 * To change this template use File | Settings | File Templates.
 */
public class RecruitInfoDaoImpl implements RecruitInfoDao{
	private JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Override
	public void insertRecruitInfo(RecruitInfo recruitInfo) {
		//To change body of implemented methods use File | Settings | File Templates.
		String sql="insert into RecruitInfo (recruit_num,base_code,job_desc,create_time,status,company_id) values(?,?,?,?,?,?)";
		Object[] objects=new Object[] {recruitInfo.getRecruitNum(),recruitInfo.getBaseCode(),recruitInfo.getJobDesc(),new Date(),"1",recruitInfo.getCompanyId()};
		jdbcTemplate.update(sql,objects);
	}
}
