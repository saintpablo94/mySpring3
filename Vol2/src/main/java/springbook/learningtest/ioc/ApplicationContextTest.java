package springbook.learningtest.ioc;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import springbook.learningtest.ioc.bean.AnnotatedHello;
import springbook.learningtest.ioc.bean.AnnotatedHelloConfig;
import springbook.learningtest.ioc.bean.Hello;
import springbook.learningtest.ioc.bean.HelloConfig;
import springbook.learningtest.ioc.bean.Printer;
import springbook.learningtest.ioc.bean.StringPrinter;


public class ApplicationContextTest {
	
	private String basePath = StringUtils.cleanPath(ClassUtils.classPackageAsResourcePath(getClass()))+"/" ;
	
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
		GenericApplicationContext appContext = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(appContext);
		reader.loadBeanDefinitions(basePath+"genericApplicationContext.xml");
		appContext.refresh();
		
		Hello hello = appContext.getBean("hello", Hello.class);
		hello.print();
		assertThat(appContext.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void genericXmlApplicationContext(){
		GenericApplicationContext appContext = 
				new GenericXmlApplicationContext(basePath+"genericApplicationContext.xml");
		
		Hello hello = appContext.getBean("hello", Hello.class);
		hello.print();
		assertThat(appContext.getBean("printer").toString(), is("Hello Spring"));
	}
	
	@Test
	public void parentChildeApplicationContext(){
		ApplicationContext parent = new GenericXmlApplicationContext(basePath+"parentContext.xml");
		GenericApplicationContext child = new GenericApplicationContext(parent);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(child);
		reader.loadBeanDefinitions(basePath+"childContext.xml");
		child.refresh();
		
		Printer printer = child.getBean("printer", Printer.class);
		assertThat(printer, is(notNullValue()));
		
		Hello hello = child.getBean("hello", Hello.class);
		assertThat(hello, is(notNullValue()));
		
		hello.print();
		assertThat(printer.toString(), is("Hello Child"));
		
//		Hello helloParent = parent.getBean("hello", Hello.class);
//		helloParent.print();
//		assertThat(printer.toString(), is("Hello Parent"));
	}
	
	@Test
	public void simpleBeanScanning(){
		ApplicationContext ctx =
				new AnnotationConfigApplicationContext(""
						+ "springbook.learningtest.ioc.bean");
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertThat(hello, is(notNullValue()));
	}
	
	@Test
	public void simpleBeanAnnotation(){
		ApplicationContext ctx = 
				new AnnotationConfigApplicationContext(AnnotatedHelloConfig.class);
		AnnotatedHello hello = ctx.getBean("annotatedHello", AnnotatedHello.class);
		assertThat(hello, is(notNullValue()));
		
		AnnotatedHelloConfig config = ctx.getBean("annotatedHelloConfig", AnnotatedHelloConfig.class);
		assertThat(config, is(notNullValue()));
		
		assertThat(config.annotatedHello(), is(sameInstance(hello)));
	}
	
	@Test
	public void simpeBeanHelloAnnotation(){
		ApplicationContext ctx =
				new AnnotationConfigApplicationContext(HelloConfig.class);
		Hello hello = ctx.getBean("hello", Hello.class);
		hello.print();

		assertThat(ctx.getBean("printer").toString(), is("Hello Spring"));
		
		Hello hello2 = ctx.getBean("hello2", Hello.class);
		hello2.print();
//		assertThat(ctx.getBean("printer").toString(), is("Hello Spring"));

		
		
	}
}
