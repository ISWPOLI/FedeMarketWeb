package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Rol;

/**
 * Manejador para la entidad Rol
 * @author Juan Rubiano
 * 13/11/16
 */
@ManagedBean
public class RolManage {
	
	@EJB(name = "RolBean/remote")
	RolBeanRemote miEJBRol;
	
	Rol rol = new Rol();	
	List<Rol> roles;
	private String nombre;	
	private boolean estado;
	
	public void adicionarRol(){
		if(!nombre.isEmpty()){
			rol.setNombre(nombre);
			rol.setEstado(estado);			
			miEJBRol.adicionarRol(rol);
			limpiar();
			
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Verifique la información suministrada.",
							"Rol Adicionado"));
		}else{
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
							"Verifique la información suministrada-",
							"Por favor ingrese el nombre del rol."));
		}
	}
	
	public void update(){
		rol.setNombre(rol.getNombre());
		rol.setEstado(rol.isEstado());
		miEJBRol.actualizarRol(rol);
	}
	
	public void limpiar(){
		rol = new Rol();
		nombre = "";
		estado = true;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public List<Rol> getRoles() {
		roles = miEJBRol.listarTodosRoles();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	

}
