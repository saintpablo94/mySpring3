package springbook.learningtest.ioc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.util.StringUtils;
import org.springframework.util.ClassUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.learningtest.ioc.resource.Hello;

public class AnnotationConfigrationTest {
	
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/";
	                    
	@Test
	public void atResource(){
		ApplicationContext ac = new GenericXmlApplicationContext(basePath+"resource.xml");
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("stringprinter").toString(), is("Hello SpringAnnotation"));
		
	}
	
	@Test
	public void adAutowired(){
		ApplicationContext ac = new GenericXmlApplicationContext(basePath+"autowired.xml");
		
		Hello hello = ac.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(ac.getBean("myprinter").toString(), is("Hello Spring3.0"));
				
	}
}
