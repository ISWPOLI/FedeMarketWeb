package com.qantica.fedemarket.managebean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.qantica.fedemarket.ejb.UsuarioBeanLocal;
import com.qantica.fedemarket.entidad.Usuario;

@ManagedBean
public class UsuarioManage {

	@EJB(name = "UsuarioBean/local")
	UsuarioBeanLocal miEJB;

	Usuario usuario;
	int id;

	String nombreUsuario;
	String contrasena;

	/**
	 * METODOS ADICION, BUSQUEDA Y ACTUALIZACION
	 */

	public void adicionarUsuario() {
		miEJB.adicionarUsuario(usuario);
		usuario = new Usuario();
	}

	public void buscar() {
		miEJB.buscarUsuario(id);
	}

	public void actualizar() {
		miEJB.actualizarUsuario(usuario);
	}

	public void login() {
		

	}

	/**
	 * METODOS ACCESORES Y MODIFICADORES
	 * 
	 * @return
	 */

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
