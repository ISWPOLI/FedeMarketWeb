package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.ejb.NoticiaBeanRemote;
import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Noticia;
import com.qantica.fedemarket.entidad.Rol;

@ManagedBean
public class NoticiaManage {

	@EJB(name = "NoticiaBean/remote")
	NoticiaBeanRemote miEJB;
	
	@EJB(name = "RolBean/remote")
	RolBeanRemote miEJBrol;
	
	List<Rol> roles;
	
	Noticia noticia = new Noticia();
	Noticia selectNoticia = new Noticia();
	int id;
	int rol;

	String titulo;
	String descripcion;
	String fuente;

	public List<Noticia> getNoticias() {
		return miEJB.listarNoticias();
	}

	public void adicionarNoticia() {		
		if (!titulo.isEmpty() && !descripcion.isEmpty() && !fuente.isEmpty()){			
			noticia.setTitulo(titulo);
			noticia.setDescripcion(descripcion);
			noticia.setFuente(fuente);
			noticia.setRol(miEJBrol.buscarRol(rol));
			noticia.setFecha(FechaActual.timestamp());

			miEJB.adicionarNoticia(noticia);

			limpiar();
			
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Verifique la información suministrada.",
							"Noticia adicionada"));
		} else {
			FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
							"Verifique la información suministrada.",
							"Alguno de los campos se encuentra vacio."));
		}
	}

	public void update() {
		if (selectNoticia.getTitulo().length() > 2 && selectNoticia.getDescripcion().length() > 5){
			selectNoticia.setFecha(FechaActual.timestamp());
			miEJB.actualizarNoticia(selectNoticia);
			limpiar();
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Verifique la información suministrada.",
							"Noticia modificada"));
		} else {
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
							"Verifique la información suministrada.",
							"Alguno de los campos se encuentra vacio"));
		}
	}

	public void limpiar() {

		titulo = "";
		fuente = "";
		descripcion = "";

		noticia = new Noticia();
		selectNoticia=new Noticia();
	}

	public void buscar() {
		miEJB.buscarNoticia(id);
	}

	public void actualizar() {
		miEJB.actualizarNoticia(noticia);
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public Noticia getSelectNoticia() {
		return selectNoticia;
	}

	public void setSelectNoticia(Noticia selectNoticia) {
		this.selectNoticia = selectNoticia;
	}

	public List<Rol> getRoles() {
		roles = miEJBrol.listarRoles();
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}
	
	
	
}
