package com.qantica.fedemarket.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.DescargaBeanRemote;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Descarga;

/**
 * Servlet implementation class SevletScreenCapture
 */
public class ServletScreenCapture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	
	public ServletScreenCapture() {
		super();
	}

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		// descarga del archivo
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter();
		try {
			String src = request.getParameter("id_img");
			String archivo = Conf.RUTA_SCREEN + src;

			File f = new File(archivo);
			response.setContentType("image/jpg");// Se define el tipo de archivo
													// a descargar
			response.setContentLength((int) f.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ archivo + "\"");
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
				PrintWriter out = response.getWriter();
				out.print("<503>");
			}

			outs.flush();
			outs.close();
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.print("<503>");
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
