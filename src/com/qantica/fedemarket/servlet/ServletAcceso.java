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

import com.qantica.fedemarket.ejb.UsuarioBeanRemote;

/**
 * Servlet que registra el ingreso al sistema
 * 15/10/2016
 * Colombia
 * Q-antica Ltda.
 */

public class ServletAcceso extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;
	
	@EJB(name="UsuarioBean/remote")
	UsuarioBeanRemote miEJB;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (UsuarioBeanRemote) context.lookup("UsuarioBean/remote");

		} catch (NamingException e) {
			// TODO Auto-generated catch block
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
			String nombre = request.getParameter("nombre_usuario");
			miEJB.addRegistro(nombre,"NULL");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
