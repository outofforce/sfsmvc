package bean.DaoImpl;

import bean.Dao.TestUser;
import bean.Dao.TestUserDao;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-22
 * Time: 下午3:18
 * To change this template use File | Settings | File Templates.
 */
public class TestUserDaoImpl implements TestUserDao{
	private TestUser testUser;

	public TestUser getTestUser() {
		return testUser;
	}

	public void setTestUser(TestUser testUser) {
		this.testUser = testUser;
	}

	@Override
	public int getUserNum() {
		return this.testUser.getUserNum();  //To change body of implemented methods use File | Settings | File Templates.
	}

}
