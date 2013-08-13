package bean.DaoImpl;

import bean.Dao.ActiveDao;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-11
 * Time: 下午12:47
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class ActiveDaoImpl implements ActiveDao{
	public static JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String active(String userName, String value) {
		String sql="update UserEvent set status = 0 where user_id = ? and event_value = ?";
		Object[] objects=new Object[] {userName,value};
		try{
			jdbcTemplate.update(sql,objects);
			sql="update User set status = 1 where user_name = ? ";
			jdbcTemplate.update(sql,new Object[] {userName});
			return "success";
		} catch (Exception e){
			System.out.println(e.getMessage());
			return "error";
		}
	}
}
