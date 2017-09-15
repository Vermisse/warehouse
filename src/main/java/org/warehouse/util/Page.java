package org.warehouse.util;

public class Page {
	
	private int cur = 1;
	private int size = 10;
	private int count;
	
	public int getCur() {
		return cur;
	}
	public void setCur(int cur) {
		this.cur = cur;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPrev() {
		return cur <= 1 ? 1 : cur - 1;
	}
	public int getNext() {
		int max = getMax();
		return cur >= max ? max : cur + 1;
	}
	public int getMax() {
		return (int) Math.ceil((this.count + 0.0) / size);
	}
	public int getNum() {
		return (cur - 1) * size;
	}
}