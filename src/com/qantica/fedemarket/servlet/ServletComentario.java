package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ComentarioBeanRemote;
import com.qantica.fedemarket.ejb.UsuarioBeanRemote;
import com.qantica.fedemarket.entidad.Comentario;
import com.qantica.fedemarket.entidad.Usuario;

/**
 * Servlet para la entidad Comentario
 * @author Juan Rubiano
 * 22/11/206
 *
 */
public class ServletComentario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Context context;
	
	@EJB(name="ComentarioBean/remote")
	ComentarioBeanRemote miEJB;
	

	@EJB(name="UsuarioBean/remote")
	UsuarioBeanRemote miEJBUsuario;
	
	public void init() {
		try {

			context = new InitialContext();

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
    public ServletComentario() {
        super();
    }

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Usuario user = miEJBUsuario.buscarUsuario(Integer.parseInt(request.getParameter("uid")));
			int id = Integer.parseInt(request.getParameter("id_app"));
			int valoracion = Integer.parseInt(request.getParameter("valoracion"));
			String descripcion = request.getParameter("comentario");
			//String uid = request.getParameter("uid");
			String uname = request.getParameter("uname");
			
			miEJB.adicionarComentario(id, user, valoracion, descripcion, uname);
			
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
