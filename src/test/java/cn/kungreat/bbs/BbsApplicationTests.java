package cn.kungreat.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BbsApplication.class)
public class BbsApplicationTests {

	@Test
	public void contextLoads() {
		String s = "hasRole('测试')";
		String substring = s.substring(9, s.length() - 2);
		System.out.println(substring);
	}

}
