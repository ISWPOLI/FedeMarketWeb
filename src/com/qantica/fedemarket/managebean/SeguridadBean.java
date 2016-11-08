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
 */

@ManagedBean
@SessionScoped
public class SeguridadBean {

	/**
	 * variable que almacena el nombre del usuario que para esta aplicacion sera
	 * el correo electronico o email
	 */
	String nUsuario;

	/**
	 * varibale que almacena la contrase�a de acceso del usuario
	 */
	String pass;

	/**
	 * variable que tendra almacenado el estado de loguin
	 */
	boolean autenticado = false;

	/**
	 * instancia del EJB encargado de gestionar la seguridad de la aplicacion
	 */
	// @EJB (name = "LoginBean/remote")
	// LoginBeanRemote miEjb;

	@EJB(name = "LoginBean/local")
	LoginBeanLocal miEjb;

	/**
	 * metodo encargado de realizar la consulta de usuarios para el logueo.
	 * verifica si es un cliente o un administrador de lo contrario reporta el
	 * error en la pagina correspondiente
	 * 
	 * @return redireccion de a la pagina principal
	 */
	public String login() {

		if (miEjb == null) {

			return "";
		} else {
			Usuario response = miEjb.validarUsuario(nUsuario, pass);

			if (response != null) {

				autenticado = true;

			} else {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"log",
								new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										"El Nombre De Usuario o Contraseñas No Son Correctos",
										"El Nombre De Usuario o Contraseñas No Son Correctos"));
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"logLogin",
								new FacesMessage(
										FacesMessage.SEVERITY_FATAL,
										"El Nombre De Usuario o Contraseñas No Son Correctos",
										"El Nombre De Usuario o Contraseñas No Son Correctos"));
				return "";
//				return "./index.jsf";
			}

			return "./listContenido.jsf";
		}
	}

	/**
	 * metodo encargado de cerrar la sesion de login y convertir todos los
	 * atributos a su estado por defecto
	 */
	public void cerrar() {
		nUsuario = "";
		pass = "";
		autenticado = false;
	}

	/**
	 * metodo accesor de la variable nUsuario
	 * 
	 * @return nombre de usuario
	 */
	public String getnUsuario() {
		return nUsuario;
	}

	/**
	 * metodo modificador de la variable nUsuario
	 * 
	 * @param nUsuario
	 */
	public void setnUsuario(String nUsuario) {
		this.nUsuario = nUsuario;
	}

	/**
	 * metodo accesor de la variable pass
	 * 
	 * @return contrase�a del usuario
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * metodo modificador de la variable pass
	 * 
	 * @param pass
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * metodo accesor de la varibale autenticado
	 * 
	 * @return verdadero o falso
	 */
	public boolean isAutenticado() {
		return autenticado;
	}

	/**
	 * metodo modificador de la variable autenticado
	 * 
	 * @param autenticado
	 */
	public void setAutenticado(boolean autenticado) {
		this.autenticado = autenticado;
	}

}
