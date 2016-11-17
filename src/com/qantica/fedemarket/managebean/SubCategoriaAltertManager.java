package com.qantica.fedemarket.managebean;

<<<<<<< HEAD
=======

>>>>>>> master
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Subcategoria;

@ViewScoped
@ManagedBean
public class SubCategoriaAltertManager {

	@EJB(name = "SubCategoriaBean/remote")
	SubcategoriaBeanRemote miEJB;
<<<<<<< HEAD

=======
	
>>>>>>> master
	private Subcategoria subcategoria = new Subcategoria();
	private List<Subcategoria> lista ;

	int id;

	public void update() {
<<<<<<< HEAD
		try	{
			miEJB.actualizarSubcategoria(subcategoria);
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Subcategoria actualizada.",
=======
		try{
			miEJB.actualizarSubcategoria(subcategoria);
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Subcategoria actualizada!",
>>>>>>> master
							"La Subcategoria se actualizó correctamente."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
<<<<<<< HEAD
							"Error En La Actualización.",
							"Error En La Actualización, comuníquese con el administrador."));
=======
							"Error en la actualización.",
							"Error en la actualización. Comuníquese con el administrador."));
>>>>>>> master
		}
	}

	public List<Subcategoria> getLista() {
<<<<<<< HEAD
		lista = miEJB.listarSubcategoriasServlet(); 
=======
		lista = miEJB.listarSubcategoriasServlet();
>>>>>>> master
		return lista;
	}

	public void setLista(List<Subcategoria> lista) {
		this.lista = lista;
	}
<<<<<<< HEAD
	
=======

>>>>>>> master
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
