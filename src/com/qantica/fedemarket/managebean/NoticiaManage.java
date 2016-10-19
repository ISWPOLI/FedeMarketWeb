package com.qantica.fedemarket.managebean;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.ejb.NoticiaBeanRemote;
import com.qantica.fedemarket.entidad.Noticia;

@ManagedBean
public class NoticiaManage {

	@EJB(name = "NoticiaBean/remote")
	NoticiaBeanRemote miEJB;

	Noticia noticia = new Noticia();
	Noticia selectNoticia = new Noticia();
	int id;

	String titulo;
	String descripcion;
	String fuente;

	public List<Noticia> getNoticias(int rol) {
		return miEJB.listarNoticias(rol);
	}

	/**
	 * METODOS ADICION, BUSQUEDA Y ACTUALIZACION
	 */

	public void adicionarNoticia() {		
		if (titulo.length() > 3 && descripcion.length() > 5	&& fuente.length() > 2){
			noticia.setId(0);
			noticia.setTitulo(titulo);
			noticia.setDescripcion(descripcion);
			noticia.setFuente(fuente);
			noticia.setFecha(FechaActual.timestamp());

			miEJB.adicionarNoticia(noticia);

			limpiar();
			FacesContext.getCurrentInstance().addMessage(
					"formul",

					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Verifique La Información Suministrada!",
							"Noticia Adicionada"));
		} else {
			FacesContext.getCurrentInstance().addMessage(
					"formul",

					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Verifique La Información Suministrada!",
							"Alguno de los campos no tiene el tamaño correcto!"));
		}
	}

	/**
	 * metodo encargado de modificar la noticia
	 */
	public void update() {

		if (selectNoticia.getTitulo().length() > 2 && selectNoticia.getDescripcion().length() > 5)
		{

			selectNoticia.setFecha(FechaActual.timestamp());

			miEJB.actualizarNoticia(selectNoticia);

			limpiar();
			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Verifique La Información Suministrada!",
							"Noticia Modificada"));
		} else {

			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Verifique La Información Suministrada!",
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

	/**
	 * METODOS ACCESORES Y MODIFICADORES
	 * 
	 * @return
	 */

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


}
