package frame.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-5-31
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMailSender {

	public boolean sendTextMail(MailSenderInfo mailInfo) {
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			InternetAddress[] to=new InternetAddress[mailInfo.getToAddress().length];
			for(int i=0;i<mailInfo.getToAddress().length;i++){
				to[i]=new InternetAddress(mailInfo.getToAddress()[i]);
			}
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipients(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			String mailContent = mailInfo.getContent();
			mailMessage.setText(mailContent);
			//后面的BodyPart将加入到此处创建的Multipart中
			/*
			Multipart mp = new MimeMultipart();
			//获取附件
			int FileCount = this.arrFileName.getItemCount();
			if (FileCount > 0) {
				for (int i = 0; i < FileCount; i++) {
					MimeBodyPart mbp = new MimeBodyPart();
					//选择出附件名
					fileName = arrFileName.getItem(i).toString();
					//得到数据源
					FileDataSource fds = new FileDataSource(fileName);
					//得到附件本身并至入BodyPart
					mbp.setDataHandler(new DataHandler(fds));
					//得到文件名同样至入BodyPart
					mbp.setFileName(fds.getName());
					mp.addBodyPart(mbp);
				}
				 MimeBodyPart mbp = new MimeBodyPart();
     			 mbp.setText(this.body);
      			mp.addBodyPart(mbp);
     			 //移走集合中的所有元素
     			 arrFileName.removeAll();
    			  //Multipart加入到信件
    			  msg.setContent(mp);
			*/
			// 发送邮件
			Transport.send(mailMessage);
			System.out.println("mail has already been sended");
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 以HTML格式发送邮件
	 * @param mailInfo 待发送的邮件信息
	 */
	public static boolean sendHtmlMail(MailSenderInfo mailInfo){
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		//如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			InternetAddress[] to=new InternetAddress[mailInfo.getToAddress().length];
			for(int i=0;i<mailInfo.getToAddress().length;i++){
				to[i]=new InternetAddress(mailInfo.getToAddress()[i]);
			}
			// Message.RecipientType.TO属性表示接收者的类型为TO
			mailMessage.setRecipients(Message.RecipientType.TO,to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
