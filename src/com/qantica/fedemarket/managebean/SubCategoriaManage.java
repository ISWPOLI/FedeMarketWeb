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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Subcategoria;
import com.qantica.fedemarket.mundo.ExtensionArchivo;

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
	
	UploadedFile file;

	public void adicionarSubCategoria() {

		try {
			
			if (id_intercategoria != -1 ) {
				subCategoria.setMiSubCategoria(miEJB.buscarSubcategoria(id_intercategoria));
			}
			
			if(!verificarExtension()){
				FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"El archivo seleccionado en el campo \"Seleccione Un Icono \" no es una imagen.",
						"El archivo seleccionado en el campo \"Seleccione Un Icono \" no es una imagen. Imagen null"));
			}else if (copyFile(file.getFileName(), file.getInputstream())) {
				subCategoria.setId(0);
				subCategoria.setEstado(true);
				subCategoria.setCategoria(miEJBCategoria.buscarCategoria(categoria));				
				subCategoria.setIcono(file.getFileName());
				miEJB.adicionarSubcategoria(subCategoria);
				subCategoria = new Subcategoria();

				FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
						FacesMessage.SEVERITY_INFO,
								"Verifique la información suministrada.",
								"Subcategoria adicionada"));
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("formul",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Verifique La Información Suministrada!",
									"Se ha producido un error. Por favor comuníquese con el administrador"));
		}

	}
	
	/**
	 * Verifica si el archivo es una imagen
	 * @return boolean true si es una imagen, false si no
	 */
	private boolean verificarExtension() {
		if(file == null ){
			System.out.println("La imagen entro nulo");
			return false;
		}else{
			String[] img = ExtensionArchivo.contenidoImg;
			for (int i = 0; i < img.length; i++) {
				if (file.getFileName().contains(img[i])) {
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Método que carga el archivo al servidor
	 * @param fileName archivo
	 * @param in
	 * @return boolean true si pasa, false si no
	 */
	public boolean copyFile(String fileName, InputStream in) {
		try {
			File mFile = new File(Conf.RUTA_ICO_CONTENIDO + fileName);
			OutputStream out = new FileOutputStream(mFile);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			return true;

		} catch (IOException e) {
			System.out.println("[Upload] - Error cargando la imagen al servidor");
			e.printStackTrace();
			return false;
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	
	

}
