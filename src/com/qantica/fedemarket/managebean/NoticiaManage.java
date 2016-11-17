package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.ejb.NoticiaBeanRemote;
import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Noticia;
import com.qantica.fedemarket.entidad.Rol;

/**
 * Mamejador para la entidad Noticia
 * @author Juan Rubiano	
 * 13/11/16
 */

@ViewScoped
@ManagedBean
public class NoticiaManage {

	@EJB(name = "NoticiaBean/remote")
	NoticiaBeanRemote miEJB;
	
	@EJB(name = "RolBean/remote")
	RolBeanRemote miEJBrol;
	
	List<Rol> roles;
	List<Noticia> noticias;
	
	Noticia noticia = new Noticia();
	int id;
	int rol;


	public void adicionarNoticia() {		
		if (!noticia.getTitulo().isEmpty() && !noticia.getDescripcion().isEmpty() && !noticia.getFuente().isEmpty()){
			
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
		if (noticia.getTitulo().length() > 2 && noticia.getDescripcion().length() > 5){
			noticia.setFecha(FechaActual.timestamp());
			miEJB.actualizarNoticia(noticia);
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
		noticia = new Noticia();
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

	public List<Noticia> getNoticias() {
		noticias = miEJB.listarNoticias();
		return noticias;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}
	
	
}
