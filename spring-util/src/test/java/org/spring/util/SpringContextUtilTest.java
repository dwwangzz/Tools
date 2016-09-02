package org.spring.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jngld.springutil.SpringContextUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:jngld-module-springutil.xml"})
public class SpringContextUtilTest{
	
	@Test
	public void testSpringContextUtil(){
//		Object o = SpringContextUtil.getBean("springContextUtil");
//		Assert.assertNotNull(o);
//		Assert.assertTrue(o instanceof SpringContextUtil);
	}

}
