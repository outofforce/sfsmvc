package bean.DaoImpl;

import bean.Dao.UserMsgDao;
import bean.bean.UserPublish;
import common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-23
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
@Transactional
public class UserMsgDaoImpl implements UserMsgDao{
	public static JdbcTemplate jdbcTemplate= WebUtil.getJdbcTemp();
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public boolean insertToUserPublish(UserPublish publish){
		String query="user_id,create_time,chg_time,status";
		String val=publish.getUserId()+"&"+"newDate"+"&newDate&1";
		String whereClause = "";
		Date inactiveDate=new Date();
		if(!"".equals(publish.getPostImg())&&publish.getPostImg()!=null){
			query+=",context_img";
			val+="&"+publish.getPostImg();
		}
		if(!"".equals(publish.getPostContext())&&publish.getPostContext()!=null){
			query+=",context";
			val+="&"+publish.getPostContext();
		}
		if(!"".equals(publish.getGisInfo())&&publish.getGisInfo()!=null){
			query+=",gis_info";
			val+="&"+publish.getGisInfo();
		}
		if(!"".equals(publish.getSimpleImg())&&publish.getSimpleImg()!=null){
			query+=",simple_img";
			val+="&"+publish.getSimpleImg();
		}
		if(!"".equals(publish.getTtl_type())&&publish.getTtl_type()!=null)  {
			Calendar c=Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.HOUR_OF_DAY,Integer.parseInt(publish.getTtl_type()));
			System.out.println(c.getTime());
			query+=",inactive_time";
			val+="&inActiVeDaTE";
			inactiveDate=c.getTime();
		}
		if(!"".equals(publish.getToGroup())&&publish.getToGroup()!=null){
			query+=",publish_type";
			val+="&1";
		}else if("".equals(publish.getToGroup())||publish.getToGroup()==null){
			query+=",publish_type";
			val+="&0";
		}
		String[] strs=val.split("&");
		Object[] objects=new Object[strs.length];
		for(int i=0;i<strs.length;i++){
			if(strs[i].equals("newDate")){
				objects[i]=new Date();
			}else if(strs[i].equals("inActiVeDaTE")){
				objects[i]=inactiveDate;
			}else {
				objects[i]=strs[i];
			}
			whereClause+=",?";
		}
		whereClause=whereClause.substring(1);
		String sql="insert into UserPublish ("+query+") values ("+whereClause+")";
		System.out.println(sql);
		try{
			jdbcTemplate.update(sql,objects);
			int number=jdbcTemplate.queryForInt("select max(id) from UserPublish");
					System.out.println("getId===="+number);
			if(!"".equals(publish.getToGroup())&&publish.getToGroup()!=null){
				String[] togroup=publish.getToGroup().replace("{","").replace("}","").split(",");
				for(String s:togroup){
					System.out.println("toGroup=="+s);
					String insertIntoUserMsgQueue="insert into UserMsgQueue (user_id,publish_id,status,create_time,chg_time) values (?,?,?,?,?)";
					Object[] objects1=new Object[] {s,number,1,new Date(),new Date()};
					jdbcTemplate.update(insertIntoUserMsgQueue,objects1);
				}
			}
			return true;
		}catch (Exception e){
			System.out.println("error:"+e.getMessage());
			return false;
		}
	}
}
