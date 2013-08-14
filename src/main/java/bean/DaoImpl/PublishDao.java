package bean.DaoImpl;

import bean.Publish;
import common.WebUtil;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-12
 * Time: 下午3:10
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class PublishDao {
	public DriverManagerDataSource dataSource;
	private PublishQuery publishQuery;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@PostConstruct
	public void init(){
		this.dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://outofforce.f3322.org:23/cat");
		dataSource.setUsername("cat");
		dataSource.setPassword("as1a1nf0");
		this.publishQuery=new PublishQuery(dataSource);
	}
	public List<Publish> getPublishList(){
		init();
		List list=publishQuery.execute();
		return list;
	}

	public class PublishQuery extends MappingSqlQuery<Publish>{
		public PublishQuery(DataSource ds){
			super(ds,"select * from Publish order by id desc ") ;
			compile();
		}

		@Override
		protected Publish mapRow(ResultSet rs, int i) throws SQLException {
			Publish publish=new Publish();
			publish.setContext(rs.getString("context"));
			publish.setContextImg(rs.getString("context_img"));
			publish.setCreateTime(format.format(rs.getTime("create_time")));
			System.out.println("createTime==" + format.format(rs.getTime("create_time")));
			publish.setGisInfo(rs.getString("gis_info"));
			publish.setId(rs.getInt("id"));
			publish.setStatus(rs.getInt("status"));
			publish.setUserId(rs.getInt("user_id"));
			publish.setPublishType(rs.getInt("publish_type"));
			if(1==publish.getPublishType()){
				String sql="select a.id,a.company_name,b.base_code from CompanyInfo a"
				+",RecruitInfo b where a.company_code = b.company_id and a.company_code = ?";
				Object[] objects=new Object[] {rs.getString("external_id")};
				List list= WebUtil.getJdbcTemp().queryForList(sql,objects);
				Map map= (Map) list.get(0);
				publish.setBaseCode((String) map.get("base_code"));
				publish.setUserId((Integer) map.get("id"));
				publish.setUserName((String)map.get("company_name"));
			}
			return publish;
		}
	}
}
