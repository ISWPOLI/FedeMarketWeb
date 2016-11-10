package com.qantica.fedemarket.managebean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.LoginBean;
import com.qantica.fedemarket.ejb.LoginBeanLocal;
import com.qantica.fedemarket.ejb.LoginBeanRemote;
import com.qantica.fedemarket.entidad.Usuario;

/**
 * BackBean encargado de gestionar la seguridad de la aplicacion BackBean de
 * sesion para permitir el almacenamiento de la informacion realacionada
 * @author Juan Rubiano	
 * 09/11/2016
 */

@ManagedBean
@SessionScoped
public class SeguridadBean {

	//Nombre del usuario
	String nUsuario;

	//Contraseña
	String pass;

	//Estado del Login
	boolean autenticado = false;

	@EJB(name = "LoginBean/local")
	LoginBeanLocal miEjb;
	
	/**
	 * Método encargado de validar el login
	 */
	public String login() {
		if (miEjb == null) {
			return "";
		} else {
			Usuario response = miEjb.validarUsuario(nUsuario, pass);
			if (response != null) {
				autenticado = true;
			} else {
				FacesContext.getCurrentInstance().addMessage("log",new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										"El Nombre De Usuario o Contraseñas No Son Correctos",
										"El Nombre De Usuario o Contraseñas No Son Correctos"));
				FacesContext.getCurrentInstance().addMessage("logLogin",new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										"El Nombre De Usuario o Contraseñas No Son Correctos",
										"El Nombre De Usuario o Contraseñas No Son Correctos"));
				return "";
			}
			return "./listContenido.jsf";
		}
	}

	/**
	 * Método encargado de cerrar la sesión e inicializar las variables de nuevo
	 */
	public void cerrar() {
		nUsuario = "";
		pass = "";
		autenticado = false;
	}

	public String getnUsuario() {
		return nUsuario;
	}

	public void setnUsuario(String nUsuario) {
		this.nUsuario = nUsuario;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public boolean isAutenticado() {
		return autenticado;
	}

	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

}
