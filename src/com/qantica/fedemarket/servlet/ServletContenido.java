package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.ContenidoBeanLocal;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.NoticiaBeanRemote;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Noticia;

/**
 * Servlet implementation class ServletContenido
 */
public class ServletContenido extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	// ContenidoBeanRemote miEJB;
	ContenidoBeanLocal miEJB;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (ContenidoBeanLocal) context.lookup("ContenidoBean/local");

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletContenido() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			PrintWriter out = response.getWriter();
			out.println("<500>");
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			List<Contenido> misContenidos = miEJB.listarContenido(0);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			for (int i = 0; i < misContenidos.size(); i++) {

				if (misContenidos.get(i).getSubCategoria() != null) {

					out.println(misContenidos.get(i).getId() + "|"
							+ misContenidos.get(i).getNombre() + "|"
							+ misContenidos.get(i).getDescripcion() + "|"
							+ misContenidos.get(i).getDescargas() + "|"
							+ misContenidos.get(i).getVersion() + "|"
							+ +misContenidos.get(i).getCategoria().getId()
							+ "|"
							+ misContenidos.get(i).getSubCategoria().getId()
							+ "|" + misContenidos.get(i).getCaptura_1() + "|"
							+ misContenidos.get(i).getCaptura_2() + "|"
							+ misContenidos.get(i).getIcono() + "|"
							+ misContenidos.get(i).getRating() + "|"
							+ misContenidos.get(i).getEstado() + ">");
				} else {
					out.println(misContenidos.get(i).getId() + "|"
							+ misContenidos.get(i).getNombre() + "|"
							+ misContenidos.get(i).getDescripcion() + "|"
							+ misContenidos.get(i).getDescargas() + "|"
							+ misContenidos.get(i).getVersion() + "|"
							+ +misContenidos.get(i).getCategoria().getId()
							+ "|0|" + misContenidos.get(i).getCaptura_1() + "|"
							+ misContenidos.get(i).getCaptura_2() + "|"
							+ misContenidos.get(i).getIcono() + "|"
							+ misContenidos.get(i).getRating() + "|"
							+ misContenidos.get(i).getEstado() + ">");
				}
			}
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
