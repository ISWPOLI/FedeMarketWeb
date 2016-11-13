package com.qantica.fedemarket.managebean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Subcategoria;
import com.qantica.fedemarket.mundo.ExtensionArchivo;
import com.qantica.fedemarket.mundo.FechaActual;

/**
 * Manejador del bean Categoria
 * @author Juan Rubiano	
 * 13/11/16
 *
 */
@ManagedBean
@ViewScoped
public class CategoriaAltertManager {
	
	@EJB(name = "CategoriaBean/remote")
	CategoriaBeanRemote miEJB;
	
	private Categoria categoria = new Categoria();
	private List<Categoria> lista;
	int id;
	private UploadedFile file;
	
	public void delete(){		
		System.out.println("Entro a eliminar el codigo: "+categoria.getId());
	}
	
	public void update(){
		try {
			categoria.setNombre(new String(categoria.getNombre().getBytes("ISO-8859-1"), "UTF-8"));
			categoria.setDescripcion(new String(categoria.getDescripcion().getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			if (isValidFile(file)) {
				if (copyFile(file.getFileName(), file.getInputstream())) {
					System.out.println("File upload");
					categoria.setIcono(file.getFileName());
				}
			}
			miEJB.actualizarCategoria(categoria);
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_INFO,
							"Categoria actualizada.",
							"El categoria se actualizó correctamente."));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("form",new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
							"Error en la actualización",
							"Error en la actualización. Comuníquese con el administrador."));
		}
	}

	/**
	 * Método para la carga de la imágen al servidor
	 * @param fileName
	 * @param in
	 * @return
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
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Metodo encargado de verificar si el archivo es válido
	 * @return boolean true si es válido, false si no
	 */
	private boolean isValidFile(UploadedFile file) {
		if (file != null) {
			return verificarExtension(file);
		}
		return false;
	}

	/**
	 * Método que verifica si el archivo es una imágen
	 * @return boolean true si es una imágen, false si no.
	 */
	private boolean verificarExtension(UploadedFile file) {
		String[] cont = ExtensionArchivo.contenidoImg;
		for (int i = 0; i < cont.length; i++) {
			if (file.getFileName().contains(cont[i])) {
				i = cont.length + 1;
			} else if (i == cont.length - 1) {
				return false;
			}
		}
		return true;
	}

	public List<Categoria> getLista() {
		lista = miEJB.listarCategoriasServlet();
		return lista;
	}

	public void setLista(List<Categoria> lista) {
		this.lista = lista;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
