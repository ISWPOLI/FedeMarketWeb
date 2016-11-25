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

import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Contenido;

/**
 * Servlet que trae la lista de las categorias de acuerdo al rol
 * @author Juan Rubiano
 * 19/08/2016
 */

public class ServletCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;

	@EJB(name="CategoriaBean/remote")
	CategoriaBeanRemote miEJB;

	public void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}	

	public ServletCategoria() {
		super();
	}

	/**
	 * GET
	 * @param request
	 * @para response
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		PrintWriter out = response.getWriter();
		out.print("<500>");
	}

	/**
	 * POST
	 * @param request
	 * @param response
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String rol = request.getParameter("rol");		
			List<Categoria> misContenidos = miEJB.listarCategoriaMovil(Integer.parseInt(rol));
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();			

			if(misContenidos.isEmpty()){
				out.println("<404>");
			}else{
				for (int i = 0; i < misContenidos.size(); i++) {
					out.println(misContenidos.get(i).getId() + "|"
							+ misContenidos.get(i).getNombre() +"|"+misContenidos.get(i).getIcono()+ ">");
				}
			}		
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.print("<503>");
		}


	}

}
