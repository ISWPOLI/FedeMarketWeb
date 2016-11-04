package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.ComentarioBeanRemote;
import com.qantica.fedemarket.entidad.Comentario;

/**
 * Servlet implementation class ServletListaComentario
 */
public class ServletListaComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	ComentarioBeanRemote miEJB;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (ComentarioBeanRemote) context
					.lookup("ComentarioBean/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletListaComentario() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			
			int id = Integer.parseInt(request.getParameter("aid"));
			ArrayList<Comentario> result = (ArrayList<Comentario>) miEJB
					.listarComentarios(id);

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			for (int i = 0; i < result.size(); i++) {

				out.println(result.get(i).getNombre() + "|"
						+ result.get(i).getDescripcion() + "|"
						+ result.get(i).getRating() + ">");
			}

			out.close();

		} catch (Exception e) {

			PrintWriter out = response.getWriter();
			out.println("<011>");
			out.close();
			e.printStackTrace();
		}

	}

}
