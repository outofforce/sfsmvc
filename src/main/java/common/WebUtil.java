package common;


import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-9
 * Time: 下午2:19
 * To change this template use File | Settings | File Templates.
 */
public class WebUtil {
	public static Object getBean(HttpServletRequest request,
								 Class clazz) {
		return getBean(request, clazz, false);
	}
	/**
	 * @param setAttr
	 *            true/false 是/否设置在request的attribute中
	 * @return Gets a Object List
	 */
	public static Object getBean(HttpServletRequest request,
								 Class clazz, boolean setAttr) {
		HashMapList mapList = getMapList(request);

		Object obj = null;
		try {
			if (mapList.size() != 0) {
				obj = clazz.newInstance();
				BeanUtils.populate(obj, mapList.get(0));
			}
		} catch (Exception e) {
		}

		return obj;
	}

	private static HashMapList getMapList(HttpServletRequest request) {
		// Iterator of parameter names
		Enumeration names = request.getParameterNames();
		HashMapList mapList = new HashMapList();

		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			mapList.put(name, request.getParameterValues(name));
		}

		return mapList;
	}
	public static int getCount(String tableName){
		JdbcTemplate jdbcTemplate=getJdbcTemp();
		String sql=String.format("select count(*) from %s",tableName);
		int i=jdbcTemplate.queryForInt(sql);
		return i+1;
	}

	/**
	 * 产生随机字符串
	 * */
	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
					"0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
			//numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char [] randBuffer = new char[length];
		for (int i=0; i<randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
			//randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}
	public static JdbcTemplate getJdbcTemp(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://outofforce.f3322.org:23/cat");
		dataSource.setUsername("cat");
		dataSource.setPassword("as1a1nf0");
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		return  jdbcTemplate;
	}
}
