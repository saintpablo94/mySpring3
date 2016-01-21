package springbook.learningtest.ioc;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
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
	public void registerBeanWithDependency2(){
		StaticApplicationContext appContext = new StaticApplicationContext();
		
		appContext.registerBeanDefinition("printer", new RootBeanDefinition(StringPrinter.class));
		
		BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
		helloDef.getPropertyValues().addPropertyValue("name", "Spring");
		helloDef.getPropertyValues().addPropertyValue("printer", new RuntimeBeanReference("printer"));
		
		appContext.registerBeanDefinition("hello", helloDef);
		
		Hello hello = (Hello)appContext.getBean("hello", Hello.class);
		hello.print();
		
		assertThat(appContext.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericApplicationContext(){
//		GenericApplicationContext appContext = new GenericApplicationContext();
//		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(appContext);
//		reader.loadBeanDefinitions("springbook/learningtest/ioc/genericApplicationContext.xml");
//		appContext.refresh();
		
		GenericApplicationContext appContext = 
				new GenericXmlApplicationContext("springbook/learningtest/ioc/genericApplicationContext.xml");
		
		Hello hello = appContext.getBean("Hello", Hello.class);
		assertThat(appContext.getBean("printer").toString(), is("Hello Spring"));
	}
}
