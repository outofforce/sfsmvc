package bean.DaoImpl;

import bean.bean.UserPublish;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-21
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public class UserPublishDao {
	public DriverManagerDataSource dataSource;
	private PublishQuery publishQuery;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@PostConstruct
	public void init(){
		this.dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://outofforce.f3322.org:23/MTL");
		dataSource.setUsername("cat");
		dataSource.setPassword("as1a1nf0");
		this.publishQuery=new PublishQuery(dataSource);
	}
	public List<UserPublish> getPublishList(){
		init();
		List list=publishQuery.execute();
		return list;
	}

	public class PublishQuery extends MappingSqlQuery<UserPublish> {
		public PublishQuery(DataSource ds){
			super(ds,"select * from (select a.*,b.user_name,b.nick_name from UserPublish a,User b where a.user_id = b.id and a.inactive_time is null \n" +
					"UNION\n" +
					"select a.*,b.user_name,b.nick_name from UserPublish a,User b where a.user_id = b.id and a.inactive_time > SYSDATE()) c order by c.create_time desc;") ;
			compile();
		}

		@Override
		protected UserPublish mapRow(ResultSet rs, int i) throws SQLException {
			UserPublish publish=new UserPublish();
			publish.setPostContext(rs.getString("context"));
			publish.setPostImg(rs.getString("context_img"));
			publish.setCreate_time(format.format(rs.getTime("create_time")));
			System.out.println("createTime==" + format.format (rs.getTime("create_time")));
			publish.setGisInfo(rs.getString("gis_info"));
			publish.setId(rs.getInt("id"));
			publish.setStatus(rs.getInt("status"));
			publish.setUserId(Integer.toString(rs.getInt("user_id")));
			publish.setSimpleImg(rs.getString("simple_img"));
			publish.setUserName(rs.getString("user_name"));
			publish.setNickName(rs.getString("nick_name"));
			return publish;
		}
	}
}
