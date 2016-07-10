package com.vc.swoop.model;

import java.util.LinkedList;
import java.util.List;

public class ReportMDL {
	private int id;
	private String name;
	private String des;
	public ReportMDL(int i,String n,String d)
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
	
	
	public static List<ReportMDL> GetDatas()
	{
		List<ReportMDL> lists= new LinkedList<ReportMDL>();
		//CategoryMDL c=new CategoryMDL(1,"","");
		lists.add(new ReportMDL(1,"Stolen property",""));
		lists.add(new ReportMDL(2,"Abusive comments",""));
		lists.add(new ReportMDL(3,"Fraud",""));
		lists.add(new ReportMDL(4,"Faulty goods",""));
		lists.add(new ReportMDL(5,"Misleading advertising",""));
		lists.add(new ReportMDL(6,"Objectionable material",""));
		lists.add(new ReportMDL(7,"Other",""));
		return lists;
	}
}
