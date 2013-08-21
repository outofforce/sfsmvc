package bean.bean;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-9
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */
public class UserDao {
	private String userName;
	private String headImg;
	private String email;
	private String phone;
	private int status;
	private Date createTime;
	private Date chgTime;
	private String passwd;
	private String nick_name;

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nike_name) {
		this.nick_name = nike_name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getChgTime() {
		return chgTime;
	}

	public void setChgTime(Date chgTime) {
		this.chgTime = chgTime;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
