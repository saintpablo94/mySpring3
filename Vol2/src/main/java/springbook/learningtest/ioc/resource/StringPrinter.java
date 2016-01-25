package springbook.learningtest.ioc.resource;

import org.springframework.stereotype.Component;

@Component("stringprinter")
public class StringPrinter implements Printer {
	
	private StringBuffer buffer = new StringBuffer();
	
	public void print(String message) {
		this.buffer.append(message);
	}
	
	public String toString(){
		return this.buffer.toString();
	}

}
