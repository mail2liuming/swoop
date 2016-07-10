package com.vc.swoop.model;

import java.util.LinkedList;
import java.util.List;

public class CategoryMDL {
	private int id;
	private String name;
	private String des;
	public CategoryMDL(int i,String n,String d)
	{
		setId(i);
		setName(n);
		setDes(d);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	
	public static List<CategoryMDL> GetDatas()
	{
		List<CategoryMDL> lists= new LinkedList<CategoryMDL>();
		//CategoryMDL c=new CategoryMDL(1,"","");
		lists.add(new CategoryMDL(1,"Electronics",""));
		lists.add(new CategoryMDL(2,"Clothing",""));
		lists.add(new CategoryMDL(3,"Flatmates",""));
		lists.add(new CategoryMDL(4,"Home & Garden",""));
		lists.add(new CategoryMDL(5,"Sports",""));
		lists.add(new CategoryMDL(6,"Everything else ",""));
		return lists;
	}
}
