﻿package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.RolBeanLocal;
import com.qantica.fedemarket.ejb.UsuarioBeanLocal;
import com.qantica.fedemarket.entidad.Rol;
import com.qantica.fedemarket.entidad.Usuario;

@ManagedBean
public class UsuarioManage {

	@EJB(name = "UsuarioBean/local")
	UsuarioBeanLocal miEJB;
	
	@EJB(name = "RolBean/local")
	RolBeanLocal miEJBRol;
	
//	@ManagedProperty(value="#{usuario}")
	Usuario usuario = new Usuario();
	
	int rol;
	
	List<Rol> roles;
	
	int id;
	String nombre;
	String apellido;
	String identificacion;
	String user;
	String pass;	
	
	public void adicionarUsuario(){		
		if (!nombre.isEmpty() && !apellido.isEmpty() && !identificacion.isEmpty() &&
				!user.isEmpty() && !pass.isEmpty()){
			usuario.setNombre(nombre);
			usuario.setApellido(apellido);
			usuario.setIdentificacion(identificacion);
			usuario.setUsuario(user);
			usuario.setContrasena(pass);
			usuario.setRol(miEJBRol.buscarRol(rol));
			
			miEJB.adicionarUsuario(usuario);
			
			limpiar();
		}else{
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Verifique La Información Suministrada!",
							"Usuario Adicionado"));
		}
	}
	
	public void limpiar(){
		usuario = new Usuario();
		nombre = "";
		apellido = "";
		identificacion = "";
		user = "";
		pass = "";
		rol = 0;
	}
	
	public List<Rol> getRoles() {
		roles = miEJBRol.listarRoles();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public void buscar() {
		miEJB.buscarUsuario(id);
	}

	public void actualizar() {
		miEJB.actualizarUsuario(usuario);
	}

	public void login() {		

	}

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}
	
	

}
