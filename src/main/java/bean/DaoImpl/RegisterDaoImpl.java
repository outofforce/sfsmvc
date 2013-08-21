package bean.DaoImpl;

import bean.Dao.RegisterDao;
import bean.bean.UserDao;
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
	public String setRegister(UserDao userDao){
		String check="select count(*) from User where user_name = ?";
		Object[] userName=new Object[]{userDao.getUserName()};
		int count=jdbcTemplate.queryForInt(check,userName);
		if(count==0){
			Date date=new Date();
			String queries="user_name,passwd,create_time,status,chg_time,email,nick_name,head_img";
			String whereClause="?,?,?,0,?,?,?,?";
			Object[] objects=new Object[]{userDao.getUserName(),userDao.getPasswd(),date,date,userDao.getUserName(),userDao.getNick_name(),userDao.getHeadImg()};

			if(userDao.getHeadImg()==null){
				queries="user_name,passwd,create_time,status,chg_time,email,nick_name";
				whereClause= "?,?,?,0,?,?,?";
				objects=new Object[]{userDao.getUserName(),userDao.getPasswd(),date,date,userDao.getUserName(),userDao.getNick_name()};

			}
			//设置激活随机码
			String randomStr=WebUtil.randomString(6);
			String sql=String.format("insert into User (%s) values (%s)",queries,whereClause);
			try{
				jdbcTemplate.update(sql,objects);
				sql="insert into UserEvent (user_id,event_type,event_value,status,create_time,chg_time) values (?,?,?,?,?,?)";
				objects=new Object[]{userDao.getUserName(),0,randomStr,1,date,date};
				jdbcTemplate.update(sql,objects);
				return randomStr;
			}catch (Exception e){
				return "error";
			}
		}else {
			return "reduplicate";
		}
	}
}
