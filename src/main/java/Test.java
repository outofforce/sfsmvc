/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-22
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
import bean.Dao.TestUserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	public static TestUserDao testUserDao;
	public static void init(){
		ApplicationContext context=new ClassPathXmlApplicationContext("springjdbc.xml");
		testUserDao=(TestUserDao)context.getBean("testUserDao");
	}

	public static void main(String[] args){
		init();
		System.out.println(testUserDao.getUserNum());
	}

}
