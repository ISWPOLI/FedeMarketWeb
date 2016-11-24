package com.qantica.fedemarket.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.ejb.UsuarioBeanRemote;
import com.qantica.fedemarket.entidad.Usuario;


public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Context context;

	@EJB(name="UsuarioBean/remote")
	UsuarioBeanRemote miEJB;

	public void init() {
		try {			
			context = new InitialContext();
			miEJB = (UsuarioBeanRemote) context.lookup("UsuarioBean/remote");	
		} catch (NameNotFoundException e) {			
		}catch (Exception e) {
			System.out.println("Excepci�n");
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletLogin() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			//Defino los parametros que capturo por GET
			String nombre = request.getParameter("nombre_usuario");
			String contrasena = request.getParameter("contrasena");
			
			//Declaro un Usuario que contendr� la respuesta del bean 
			Usuario aux = miEJB.login(nombre, contrasena);
			
			//Defino la decodificaci�n de la respuesta		
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			if(aux != null){
				out.println(aux.toString());
			}else{
				out.println("false");
			}
			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		try {
			//Defino los parametros que capturo por POST
			String nombre = request.getParameter("nombre_usuario");
			String contrasena = request.getParameter("contrasena");
			
			//Declaro un Usuario que contendr� la respuesta del bean 
			Usuario aux = miEJB.login(nombre, contrasena);
			
			//Defino la decodificaci�n de la respuesta
			response.setHeader("Content-Type", "text/html; charset=UTF-8"); 
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");			
			
			PrintWriter out = response.getWriter();
			
			if(aux != null){
				out.println(aux.toString());
			}else{
				out.println("false");
			}			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
