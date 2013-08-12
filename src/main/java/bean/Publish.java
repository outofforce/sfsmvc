package bean;

import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-9
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */

public class Publish {
	private int userId;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	private String context;
	private String gisInfo;
	private String contextImg;
	private String createTime;
	private int status;
	private int id;
	private String userName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getGisInfo() {
		return gisInfo;
	}

	public void setGisInfo(String gisInfo) {
		this.gisInfo = gisInfo;
	}

	public String getContextImg() {
		return contextImg;
	}

	public void setContextImg(String contextImg) {
		this.contextImg = contextImg;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserId() {

		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
