package bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-9
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
public class PublishDao {
	private int userId;
	private String context;
	private String gisInfo;
	private String contextImg;
	private Date createTime;
	private int status;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
