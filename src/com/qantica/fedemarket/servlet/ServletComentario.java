package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ComentarioBeanRemote;
import com.qantica.fedemarket.entidad.Comentario;

/**
 * Servlet implementation class ServletComentario
 */
public class ServletComentario extends HttpServlet {
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
    public ServletComentario() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			System.out.println("Entro a --> Servlet Comentario");

			int id = Integer.parseInt(request.getParameter("id_app"));
			int valoracion = Integer.parseInt(request.getParameter("valoracion"));
			String descripcion = request.getParameter("comentario");
			String uid = request.getParameter("uid");
			String uname = request.getParameter("uname");;
			
			miEJB.adicionarComentario(id, uid, valoracion, descripcion, uname);
			
			miEJB.actualizarRating(id);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<010>");
			out.close();
			
		} catch (Exception e) {
			
			PrintWriter out = response.getWriter();
			out.println("<011>");
			out.close();
			e.printStackTrace();
		}
		
	}

}
