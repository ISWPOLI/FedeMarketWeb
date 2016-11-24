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
import com.qantica.fedemarket.entidad.Contenido;

/**
 * Servlet implementation class ServletActualizar
 */
public class ServletActualizar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	ContenidoBeanLocal miEJB;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (ContenidoBeanLocal) context.lookup("ContenidoBean/local");

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletActualizar() {
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
			System.out.println(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			System.out.println("Entro a --> Servlet Actualizar");

			List<Contenido> misContenidos = miEJB.listarContenidoServlet(0,0);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			for (int i = 0; i < misContenidos.size(); i++) {
				out.println(misContenidos.get(i).getId() + "|"
						+ misContenidos.get(i).getDescargas() + "|"
						+ misContenidos.get(i).getRating() + "|"
						+ misContenidos.get(i).getVersion() + "|"
						+ misContenidos.get(i).getNombre() + "|"
						+ misContenidos.get(i).getEstado() + "|"
						+ misContenidos.get(i).getDescripcion() + "|"
						+ misContenidos.get(i).getCaptura_1() + "|"
						+ misContenidos.get(i).getCaptura_2() + "|"
						+ misContenidos.get(i).getIcono() + ">");
			}

			out.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
