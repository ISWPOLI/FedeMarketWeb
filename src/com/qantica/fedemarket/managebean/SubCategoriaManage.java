package com.qantica.fedemarket.managebean;

import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Subcategoria;

@ManagedBean
@ViewScoped
public class SubCategoriaManage {

	@EJB(name = "SubCategoriaBean/remote")
	SubcategoriaBeanRemote miEJB;

	@EJB(name = "CategoriaBean/remote")
	CategoriaBeanRemote miEJBCategoria;

	Subcategoria subCategoria = new Subcategoria();
	
	int id_intercategoria = -1;
	int categoria = 1;
	int id;

	public void adicionarSubCategoria() {

		try {
			subCategoria.setId(0);
			subCategoria.setEstado(true);
			subCategoria
					.setCategoria(miEJBCategoria.buscarCategoria(categoria));
			
			if (id_intercategoria != -1 ) {
				subCategoria.setMiSubCategoria(miEJB.buscarSubcategoria(id_intercategoria));
			}
			miEJB.adicionarSubcategoria(subCategoria);
			subCategoria = new Subcategoria();
			id_intercategoria = 0;
			categoria = 1;

			FacesContext.getCurrentInstance().addMessage(
					"formul",

					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Verifique La Información Suministrada!",
							"SubCategoria Adicionada!"));

		} catch (Exception e) {
			e.getStackTrace();

			FacesContext
					.getCurrentInstance()
					.addMessage(
							"formul",

							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Verifique La Información Suministrada!",
									"Se ha producido un error por favor intentelo mas tarde!"));
		}

	}

	public void buscar() {
		miEJB.buscarSubcategoria(id);
	}

	public void actualizar() {
		miEJB.actualizarSubcategoria(subCategoria);
	}

	public List<Categoria> getCategorias() {
		return miEJBCategoria.listarCategoriasServlet();
	}

	public List<Subcategoria> getCategoriasSub() {
		return miEJBCategoria.listarSubcategorias(categoria);
	}

	public Subcategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Subcategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoria() {
		return categoria;
	}

	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}

	public int getId_intercategoria() {
		return id_intercategoria;
	}

	public void setId_intercategoria(int id_intercategoria) {
		this.id_intercategoria = id_intercategoria;
	}

}
