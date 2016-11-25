package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Subcategoria;

/**
 * Servlet que lista las subcategorias de acuerdo al id de la categoria
 * @author Juan Rubiano
 * 24/08/2016
 */

public class ServletSubCategoria extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;

	@EJB(name="SubCategoriaBean/remote")
	SubcategoriaBeanRemote miEJB;

	public void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public ServletSubCategoria() {
		super();
	}

	/**
	 * GET
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<500>");
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		try{
			int idCategoria = Integer.parseInt(request.getParameter("categoria"));

			List<Subcategoria> misContenidos = miEJB.listarSubcategorias(idCategoria);			

			response.setContentType("text/html;charset=UTF-8");

			PrintWriter out = response.getWriter();

			if(misContenidos.isEmpty()){
				out.print("<404>");
			}else{
				for (int i = 0; i < misContenidos.size(); i++) {
					//Valido si la categoria del contenido está activa
					if (misContenidos.get(i).getCategoria().getEstado()) {
						if (misContenidos.get(i).getMiSubCategoria() != null) {
							out.println(misContenidos.get(i).getId()
									+ "|"
									+ misContenidos.get(i).getNombre()
									+ "|"
									+ misContenidos.get(i).getMiSubCategoria().getId()
									+ "|"
									+ misContenidos.get(i).getCategoria().getId()
									+ "|"
									+ misContenidos.get(i).getIcono() + "|>");
						} else {
							out.println(misContenidos.get(i).getId()
									+ "|"
									+ misContenidos.get(i).getNombre()
									+ "|0|"
									+ misContenidos.get(i).getCategoria().getId()
									+ "|"
									+ misContenidos.get(i).getIcono() + "|>");
						}

					}
				}			

			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<503");
		}
	}

}
