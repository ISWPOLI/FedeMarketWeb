package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.ejb.EJB;
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
 * Servlet que lista los comentarios de acuerdo al contenido
 * @author Juan Rubiano
 * 22/11/206
 *
 */
public class ServletListaComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	
	@EJB(name="ComentarioBean/remote")
	ComentarioBeanRemote miEJB;

	public void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}
	
	public ServletListaComentario() {
		super();
	}

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			
			int id = Integer.parseInt(request.getParameter("aid"));
			ArrayList<Comentario> result = (ArrayList<Comentario>) miEJB.listarComentarios(id);

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
