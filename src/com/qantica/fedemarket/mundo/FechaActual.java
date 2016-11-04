package com.qantica.fedemarket.mundo;

import java.util.Date;

public class FechaActual {

	
	public static String timestamp(){
		
		Date timeDate=new Date();
		int aux=timeDate.getYear()+1900;
		
		return timeDate.getDate()+"/"+(timeDate.getMonth()+1)+"/"+aux;
		
	}
	
}
