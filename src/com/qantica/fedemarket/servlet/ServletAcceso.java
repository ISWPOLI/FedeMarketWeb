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

import com.qantica.fedemarket.ejb.IngresoBeanRemote;
import com.qantica.fedemarket.ejb.UsuarioBeanRemote;
import com.qantica.fedemarket.entidad.Ingreso;
import com.qantica.fedemarket.entidad.Usuario;
import com.qantica.fedemarket.mundo.FechaActual;

/**
 * Servlet que registra el ingreso al sistema
 * 24/11/2016
 */

public class ServletAcceso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	
	@EJB(name="UsuarioBean/remote")
	UsuarioBeanRemote miEJB;
	
	@EJB(name="IngresoBean/remote")
	IngresoBeanRemote miEJBIngreso;

	public void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAcceso() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		/*try {

			PrintWriter out = response.getWriter();
			out.println("<500>");
			out.close();

		} catch (Exception e) {
			System.out.println(e);
		}*/
		
		PrintWriter out = response.getWriter();	
		try {
			String idUser = request.getParameter("idusuario");
			System.out.println("Usuario ID: "+idUser);
			Usuario user = miEJB.buscarUsuario(Integer.parseInt(idUser));
			Ingreso ing = new Ingreso();
			ing.setFecha(FechaActual.timestamp());
			ing.setUsuario(user);
			miEJBIngreso.addRegistro(ing);
			out.print("<200>");
		} catch (Exception e) {
			e.printStackTrace();	
			out.print("<503>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		try {
			PrintWriter out = response.getWriter();	
			
			String idUser;
			idUser = request.getParameter("idusuario");
			System.out.println("Usuario ID: "+idUser);
			
			Usuario user = miEJB.buscarUsuario(Integer.parseInt(idUser));
			
			Ingreso ing = new Ingreso();
			ing.setFecha(FechaActual.timestamp());
			ing.setUsuario(user);
			miEJBIngreso.addRegistro(ing);
			out.print("<200>");
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();	
			out.print("<503>");
		}
	}

}
