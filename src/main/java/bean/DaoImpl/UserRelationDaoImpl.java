package bean.DaoImpl;

import bean.Dao.UserRelationDao;
import common.WebUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-21
 * Time: 下午4:13
 * To change this template use File | Settings | File Templates.
 */
public class UserRelationDaoImpl implements UserRelationDao{
	public static JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Override
	public String watchSomeOne(String watcher, String beWatcher) {
		String sql="select count(*) from UserRelation where user_id = ? and rela_user_id = ?";
		Object[] objects=new Object[] {watcher,beWatcher};
		int count=jdbcTemplate.queryForInt(sql,objects);
		if(count==0){
			sql="insert into UserRelation (user_id,relation_type,rela_user_id,status,create_time,chg_time) values (?,?,?,?,?,?)";
			objects=new Object[] {watcher,1,beWatcher,1,new Date(),new Date()};
			try{
				jdbcTemplate.update(sql,objects);
				return "success";
			}catch (Exception e){
				System.out.println(e.getMessage());
				return "error";
			}
		}else {
			sql="update UserRelation set status=1,chg_time = ? where user_id = ? and rela_user_id = ?";
			objects=new Object[] {new Date(),watcher,beWatcher};
			try{
				jdbcTemplate.update(sql,objects);
				return "success";
			}catch (Exception e){
				return "error";
			}
		}

	}

	@Override
	public String unWatchSomeOne(String watcher, String beWatcher) {
		String sql="update UserRelation set status=0,chg_time = ? where user_id = ? and rela_user_id = ?";
		Object[] objects=new Object[] {new Date(),watcher,beWatcher};
		try{
			jdbcTemplate.update(sql,objects);
			return "success";
		}catch (Exception e){
			return "error";
		}
	}

}
