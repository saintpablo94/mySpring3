package springbook.learningtest.ioc;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

import springbook.learningtest.ioc.bean.Hello;
import springbook.learningtest.ioc.bean.StringPrinter;


public class ApplicationContextTest {
	
	@Test
	public void registerBean(){
		StaticApplicationContext appContext = new StaticApplicationContext();
		appContext.registerSingleton("hello1", Hello.class);
		
		Hello hello1 = appContext.getBean("hello1", Hello.class);
		assertThat(hello1, is(notNullValue()));
		
		BeanDefinition beanDef = new RootBeanDefinition(Hello.class);
		beanDef.getPropertyValues().addPropertyValue("name", "Spring");
		appContext.registerBeanDefinition("hello2", beanDef);
		
		Hello hello2 = appContext.getBean("hello2",Hello.class);
		assertThat(hello2.sayHello(), is("Hello Spring"));
		assertThat(hello1, is(not(hello2)));
		assertThat(appContext.getBeanFactory().getBeanDefinitionCount(), is(2));
	}
	
	@Test
	public void registerBeanWithDependency(){
		StaticApplicationContext appContext = new StaticApplicationContext();
		appContext.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));
	}
}
