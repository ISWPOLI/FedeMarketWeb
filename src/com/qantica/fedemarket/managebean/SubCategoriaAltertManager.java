package com.qantica.fedemarket.managebean;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Subcategoria;

@ManagedBean
public class SubCategoriaAltertManager {

	/**
	 * EJB para la conexion con la capa de negocio
	 */
	@EJB(name = "SubCategoriaBean/remote")
	SubcategoriaBeanRemote miEJB;


	/**
	 * ATRIBUTOS REQUERIDOS PARA LA INSERCION DE UN CONTENIDO
	 */
	private Subcategoria subcategoria = new Subcategoria();
	private List<Subcategoria> lista ;

	int id;

	public void update() {
		try
		{

			miEJB.actualizarSubcategoria(subcategoria);
			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Subcategoria Actualizada!",
							"La Subcategoria Se Actualizo Correctamente!"));
		} catch (Exception e) {


			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error En La Actualización!",
							"Error En La Actualización. Intentelo Mas Tarde!"));
		}

	}

	public List<Subcategoria> getLista(int idCategoria) {
		lista=miEJB.listarSubcategorias(idCategoria);
		return lista;
	}

	public void setLista(List<Subcategoria> lista) {
		this.lista = lista;
	}


	public Subcategoria getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria) {
		this.subcategoria = subcategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
