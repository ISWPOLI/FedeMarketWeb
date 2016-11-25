package com.qantica.fedemarket.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.conf.Conf;

/**
 * Servlet que realiza la descarga del ícono de la categoria
 * @author Juan Rubiano
 * 10/08/2016
 */

public class ServletIconCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;

	public ServletIconCategoria() {
		super();
	}
	
	/**
	 * GET 
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		try {

			String key = request.getParameter("src_icon");
			String archivo =  Conf.RUTA_ICO_CONTENIDO+key;

			System.out.println("NOMBRE DEL ARCHIVO --> "+archivo);
			File f = new File(archivo);
			response.setContentType("image/jpg");			
			response.setContentLength((int) f.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""	+ archivo + "\"");
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			int bit = 256;
			try {
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace(System.out);
			}

			outs.flush();
			outs.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			// out.close();
		}
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<500>");
	}
}
