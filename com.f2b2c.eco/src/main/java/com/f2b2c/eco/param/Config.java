package com.f2b2c.eco.param;

public class Config {

	private String producer;

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigUtil [producer=");
		builder.append(producer);
		builder.append("]");
		return builder.toString();
	}
	
}
