package com.qantica.fedemarket.managebean;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Rol;

@ManagedBean
public class RolManage {
	
	@EJB(name = "RolBean/remote")
	RolBeanRemote miEJBRol;
	
	Rol rol = new Rol();	
	
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
							"Verifique La Información Suministrada!",
							"Rol Adicionado"));
		}else{
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
							"Verifique La Información Suministrada!",
							"Alguno de los campos está incompleto!"));
		}
	}
	
	public void limpiar(){
		rol = new Rol();
		nombre = "";
		estado = false;
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
	
	

}
