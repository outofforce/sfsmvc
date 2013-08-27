package common;


import frame.mail.MailSenderInfo;
import frame.mail.SimpleMailSender;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
	public static DriverManagerDataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		// dataSource.setUrl("jdbc:mysql://localhost:3306/mtldb");
		dataSource.setUrl("jdbc:mysql://192.168.1.104:3306/mtldb");
		dataSource.setUsername("cat");
		dataSource.setPassword("aopen7291");
		return dataSource;
	}


	public static Object getBean(HttpServletRequest request,
								 Class clazz) throws IOException {
		return getBean(request, clazz, false);
	}
	/**
	 * @param setAttr
	 *            true/false 是/否设置在request的attribute中
	 * @return Gets a Object List
	 */
	public static Object getBean(HttpServletRequest request,
								 Class clazz, boolean setAttr) throws IOException {
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

	private static HashMapList getMapList(HttpServletRequest request) throws IOException {
		// Iterator of parameter names

//		String[] strings=result.split("&");
//		for(String s:strings){
//			String[] strs=s.split("=");
//			mapList.put(strs[0],new String[]{strs[1]});
//		}
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
			numbersAndLetters = ("0123456789").toCharArray();
			//numbersAndLetters = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char [] randBuffer = new char[length];
		for (int i=0; i<randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(9)];
			//randBuffer[i] = numbersAndLetters[randGen.nextInt(35)];
		}
		return new String(randBuffer);
	}
	public static JdbcTemplate getJdbcTemp(){
		DriverManagerDataSource dataSource=getDataSource();
		JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
		return  jdbcTemplate;
	}

	public static void setResponse(HttpServletResponse response,String str) throws IOException {
		//返回结果
		OutputStream out = response.getOutputStream();
		byte[] bt=str.getBytes();
		response.setContentLength(bt.length);
		response.setCharacterEncoding("UTF-8");
		out.write(bt);
		out.close();
		out.flush();
	}

	public static void sendMail(MailSenderInfo mailSenderInfo,String userName,String content){
		mailSenderInfo.setToAddress(new String[]{userName});
		SimpleMailSender simpleMailSender=new SimpleMailSender();
		mailSenderInfo.setContent(content);
		mailSenderInfo.setSubject("请点击下面的链接激活您的账户");
		simpleMailSender.sendTextMail(mailSenderInfo);
	}

	public static void commInsert(Class clazz) throws IllegalAccessException, InstantiationException {
		 Object obj=clazz.newInstance();

	}
}
