package test;

import java.sql.ResultSet;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TestJDBC {
	public static void main(String[] args) {
		ResultSet rs = null;
		Statement stmt = null;
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//new oracle.jdbc.driver.OracleDriver();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from APPLY_QUERY_LOG");
			while(rs.next()) {
				System.out.println(rs.getString("weixin_name")+"1");
				//System.out.println(rs.getInt("deptno"));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	// �����ת��List
	private static List transtoStrsList(ResultSet rs) throws SQLException
	{
		List ls = new ArrayList();
		int cols = rs.getMetaData().getColumnCount();
		while (rs.next())
		{
			String strs[] = new String[cols];
			for (int i=1; i<=cols; i++)
			{

				strs[i-1] = rs.getString(i);
			}
			ls.add(strs);
		}
		return ls;

	}
	// ��ѯ�õ�List String[]��ʽ
	public static List getStrsList(String sql)
	{
		List ls = new ArrayList();
		Connection conn = null;

		ResultSet rs = null;
		Statement stmt = null;			try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//new oracle.jdbc.driver.OracleDriver();
		conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		ls = transtoStrsList(rs);
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new RuntimeException("ִ�в�ѯ�����쳣");
	}
	finally
	{
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(stmt != null) {
				stmt.close();
				stmt = null;
			}
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return ls;
	}


	// ��ѯ�õ�List String[]��ʽ
	public static String getString(String sql)
	{
		String ls = new String();
		Connection conn = null;

		ResultSet rs = null;
		Statement stmt = null;			try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//new oracle.jdbc.driver.OracleDriver();
		conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		ls = ((String[])transtoStrsList(rs).get(0))[0];
	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new RuntimeException("ִ�в�ѯ�����쳣");
	}
	finally
	{
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(stmt != null) {
				stmt.close();
				stmt = null;
			}
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return ls;
	}

	//ִ��sql
	public static void exq(String sql)
	{
		Connection conn = null;

		ResultSet rs = null;
		Statement stmt = null;			try
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		//new oracle.jdbc.driver.OracleDriver();
		conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");
		stmt = conn.createStatement();
		stmt.executeQuery(sql);

	}
	catch (Exception e)
	{
		e.printStackTrace();
		throw new RuntimeException("ִ�в�ѯ�����쳣");
	}
	finally
	{
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if(stmt != null) {
				stmt.close();
				stmt = null;
			}
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}
	//ִ��sql
	public static void exq(String sql,Object[] objs)
	{
		Connection conn = null;

		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//new oracle.jdbc.driver.OracleDriver();
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.251.156:1527:unibss", "unibss", "unibss");
			ps = conn.prepareStatement(sql);
			setParams2PreparedStatement(ps,objs);
			rs = ps.executeQuery();
			stmt = conn.createStatement();
			stmt.executeQuery(sql);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("ִ�в�ѯ�����쳣");
		}
		finally
		{
			try {
				if(rs != null) {
					rs.close();
					rs = null;
				}
				if(stmt != null) {
					stmt.close();
					stmt = null;
				}
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private static void setParams2PreparedStatement(PreparedStatement pstmt,Object []objs)
	{
		//目前只支持字符串和数字
		if (objs == null)
		{
			return;
		}
		try
		{
			StringBuffer param = new StringBuffer("[");
			for (int i=0;i<objs.length;i++)
			{
				int type = getParamsType(objs[i]);
				switch(type)
				{
					case Types.VARCHAR:	pstmt.setString((i+1),objs[i].toString());
						break;
					case Types.INTEGER:	pstmt.setInt((i+1),Integer.parseInt(objs[i].toString()));
						break;
					case Types.DOUBLE:  pstmt.setDouble((i+1),Double.parseDouble(objs[i].toString()));
						break;
					case Types.BIGINT:	pstmt.setLong((i+1),Long.parseLong(objs[i].toString()));
						break;
					default:
						throw new RuntimeException("参数异常，请联系开发人员增加参数。");
				}
				param.append(objs[i].toString() + ",");
			}
			param.delete(param.length()-1,param.length());
			param.append("]");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	private static int getParamsType(Object obj)
	{
		//不全，但基本够用了。
		if (obj == null)
		{
			return Types.NULL;
		}
		String type = obj.getClass().toString();
		type = type.substring(type.lastIndexOf(".")+1);
		if ("String".equals(type))
		{
			return Types.VARCHAR;
		}
		else if ("Integer".equals(type))
		{
			return Types.INTEGER;
		}
		else if ("Long".equals(type))
		{
			return Types.BIGINT;
		}
		else if ("Double".equals(type))
		{
			return Types.DOUBLE;
		}

		return Types.NULL;
	}
}