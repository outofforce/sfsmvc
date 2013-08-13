package bean.DaoImpl;

import bean.Dao.RegisterDao;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-11
 * Time: 上午11:05
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class RegisterDaoImpl implements RegisterDao {
	public static JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public String setRegister(String userName,String passwd){
		//设置激活随机码
		String randomStr=WebUtil.randomString(6);
		Date date=new Date();
		String sql="insert into User (user_name,passwd,create_time,status,chg_time,email) values (?,?,?,0,?,?)";
		Object[] objects=new Object[]{userName,passwd,date,date,userName};
		try{
			jdbcTemplate.update(sql,objects);
			sql="insert into UserEvent (user_id,event_type,event_value,status,create_time,chg_time) values (?,?,?,?,?,?)";
			objects=new Object[]{userName,0,randomStr,1,date,date};
			jdbcTemplate.update(sql,objects);
			return randomStr;
		}catch (Exception e){
			return "error";
		}
	}
}
