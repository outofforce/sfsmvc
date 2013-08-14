package bean.DaoImpl;

import bean.Dao.RecruitInfoDao;
import bean.Publish;
import bean.RecruitInfo;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-13
 * Time: 下午11:08
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class RecruitInfoDaoImpl implements RecruitInfoDao{
	private JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Override
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public void insertRecruitInfo(RecruitInfo recruitInfo) {
		//To change body of implemented methods use File | Settings | File Templates.
		String sql="insert into RecruitInfo (recruit_num,base_code,job_desc,create_time,status,company_id,comp_addr,position) values(?,?,?,?,?,?,?,?)";
		Object[] objects=new Object[] {recruitInfo.getRecruitNum(),recruitInfo.getBaseCode(),recruitInfo.getJobDesc(),new Date(),"1",recruitInfo.getCompanyId(),recruitInfo.getCompAddr(),recruitInfo.getPosition()};
		System.out.println(recruitInfo.getCompanyId());
		jdbcTemplate.update(sql,objects);
		sql="select company_desc from CompanyInfo where company_code = ?";
		objects=new Object[] {recruitInfo.getCompanyId()};
		final String[] compDesc = new String[1];
		jdbcTemplate.query(sql,objects,new RowMapper<Object>() {
			@Override
			public Object mapRow(ResultSet resultSet, int i) throws SQLException {
				compDesc[0] =(String)resultSet.getString("company_desc");
				System.out.println(resultSet.getString("company_desc"));
				return null;  //To change body of implemented methods use File | Settings | File Templates.
			}
		});
		String context=String.format("单位地址 : %s\n招聘岗位 : %s\n招聘人数 : %s\n岗位要求 : %s\n公司简介 : %s",recruitInfo.getCompAddr(),recruitInfo.getPosition(),recruitInfo.getRecruitNum(),recruitInfo.getJobDesc(),compDesc[0]);
		Publish publish=new Publish();
		publish.setPublishType(1);
		publish.setCreateTime(format.format(new Date()));
		publish.setStatus(1);
		publish.setContext(context);
		sql="insert into Publish (context,create_time,chg_time,status,external_id,publish_type) values (?,?,?,?,?,?)";
		objects=new Object[] {publish.getContext(),new Date(),new Date(),1,recruitInfo.getCompanyId(),1};
		jdbcTemplate.update(sql,objects);
	}
}
