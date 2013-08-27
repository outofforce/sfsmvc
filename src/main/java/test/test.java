package test;

import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: linyiming
 * Date: 13-8-26
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class test {
	public static void main(String[] args) throws IOException {
		String path="/Users/linyiming/Desktop/result.doc";
		File[] contents;
		File file1=new File(path);
		FileInputStream inputStream=new FileInputStream(file1);
		WordExtractor extractor=new WordExtractor(inputStream);
		String str=extractor.getText();
		String[] strings=str.split("\n");
		String ss="";
		for(int i=0;i<strings.length;i++){
			String s=strings[i];
			String[] strs=s.split(" ");
			if(s.split(" ").length==3){
				ss+="insert into apply_result_info (apply_no,apply_name,result_date) values ("+strs[1]+","+strs[2]+",'201308')\r\n";
				//ss+=strs[1]+"\t"+strs[2]+"\t"+"201308"+"\r\n";
				String sql="insert into apply_result_info_tmp (apply_no,apply_name,result_date) values (?,?,'201308')";
				Object[] objects=new String[] {strs[1].substring(1),strs[2]};
				//TestJDBC.exq(sql,objects);
				//System.out.println(strs[1].substring(1));
				//jdbcTemplate1.update(sql,objects);
			}
		}
		File file2=new File("/Users/linyiming/Desktop/test.txt");
		try {
			OutputStreamWriter os = null;

			os = new OutputStreamWriter(new FileOutputStream(file2),"UTF-8");

			os.write(ss);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("  s  s  s");
	}
}
