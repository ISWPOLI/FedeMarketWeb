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

import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;

/**
 * Servlet implementation class ServletActualizarCategoria
 */
public class ServletActualizarCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	CategoriaBeanRemote miEJB;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (CategoriaBeanRemote) context
					.lookup("CategoriaBean/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletActualizarCategoria() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		out.println("<500>");
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			System.out.println("Entro a --> Servlet Actualizar Categoria");
			String rol = request.getParameter("rol");
			
			List<Categoria> misCategorias = miEJB.listarCategoriaMovil(Integer.parseInt(rol));
			int size = Integer.parseInt(request.getParameter("size"));
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			if (size < misCategorias.size()) {
				for (int i = size; i < misCategorias.size(); i++) {
					
					out.println(misCategorias.get(i).getId() + "|"
							+ misCategorias.get(i).getNombre()+"|"+ misCategorias.get(i).getIcono()+">");
				}
			}
			
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
