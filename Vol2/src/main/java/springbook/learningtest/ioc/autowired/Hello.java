package springbook.learningtest.ioc.autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Hello {
	
	@Value("Spring3.0") 
	private String name;
	
	@Autowired 
	private Printer printer;
	
	public Hello() {
	}

	public Hello(String name, Printer printer) {
		this.name = name;
		this.printer = printer;
	}
	
	public String sayHello(){
		return "Hello "+ this.name; 
	}
	
	public void print(){
		this.printer.print(this.sayHello());
	}
	
}
