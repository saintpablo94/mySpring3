package springbook.learningtest.ioc.bean;

import org.springframework.stereotype.Component;

@Component
public class Hello {
	private String name;
	private Printer printer;
		
	public Hello(){}
	/*
	public Hello(String name, Printer printer){
		this.name = name;
		this.printer = printer;
	}
	*/
	
	public String sayHello(){
		return "Hello "+this.name;
	}
	
	public void print(){
		this.printer.print(this.sayHello());
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
}
