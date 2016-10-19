package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.NoticiaBeanRemote;
import com.qantica.fedemarket.entidad.Noticia;

/**
 * 
 * @author Juan Rubiano
 * Q-antica Ltda.
 * Colombia
 * 18/08/2016
 */

public class ServletNoticias extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	
	@EJB(name="NoticiaBean/remote")
	NoticiaBeanRemote miEJB;

	public void init() {
		try {
			context = new InitialContext();
			miEJB = (NoticiaBeanRemote) context.lookup("NoticiaBean/remote");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ServletNoticias() {
		super();
	}

	/**
	 * Petición GET
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String rol = request.getParameter("rol");
			int idRol = Integer.parseInt(rol);
			List<Noticia> misNoticias = miEJB.listarNoticias(idRol);

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			for (int i = 0; i < misNoticias.size(); i++) {
				out.println(misNoticias.get(i).getTitulo() + "|"
						+ misNoticias.get(i).getDescripcion() + "|"
						+ misNoticias.get(i).getFecha() + ">");
			}

			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Petición POST
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String rol = request.getParameter("rol");
			int idRol = Integer.parseInt(rol);
			List<Noticia> misNoticias = miEJB.listarNoticias(idRol);

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			for (int i = 0; i < misNoticias.size(); i++) {
				out.println(misNoticias.get(i).getTitulo() + "|"
						+ misNoticias.get(i).getDescripcion() + "|"
						+ misNoticias.get(i).getFecha() + ">");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
