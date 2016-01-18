package springbook.learningtest.ioc.bean;

public class StringPrinter implements Printer {

	StringBuffer buffer = new StringBuffer();

	@Override
	public void print(String message) {
		this.buffer.append(message);
	}
	
	public String toString(){
		return this.buffer.toString();
	}
}
