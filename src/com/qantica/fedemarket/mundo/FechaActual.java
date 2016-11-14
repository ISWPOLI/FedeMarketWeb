package com.qantica.fedemarket.mundo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FechaActual {

	
	public static String timestamp(){		
		/*Date timeDate=new Date();
		int aux=timeDate.getYear()+1900;		
		return timeDate.getDate()+"/"+(timeDate.getMonth()+1)+"/"+aux;*/
		Date date = new Date();
		DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
		return hourdateFormat.format(date);
	}
	
}
