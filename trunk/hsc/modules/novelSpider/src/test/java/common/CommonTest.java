package common;

import java.util.regex.Pattern;

import org.junit.Test;

public class CommonTest {

	
	@Test
	public void testPatter(){
		 Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		 java.util.regex.Matcher matcher= pattern.matcher("[更新时间] 2012-05-07 00:09:53    [字数] 4783");
		 
		 if(matcher.find()){
			 System.out.println(matcher.group(0));
		 }
	
//		 matcher.
//		int i=0;
//		for(String s:dateText){
//			System.out.println("i:"+i+",dateText:"+s);
//		}
	}
}
