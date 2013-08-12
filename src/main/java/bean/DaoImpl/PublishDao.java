package bean.DaoImpl;

import bean.Publish;
import common.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
			publish.setCreateTime(rs.getTime("create_time"));
			publish.setGisInfo(rs.getString("gis_info"));
			publish.setId(rs.getInt("id"));
			publish.setStatus(rs.getInt("status"));
			publish.setUserId(rs.getInt("user_id"));
			return publish;
		}
	}
}
