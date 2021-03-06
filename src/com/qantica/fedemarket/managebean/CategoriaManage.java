﻿package com.qantica.fedemarket.managebean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.mundo.ExtensionArchivo;
import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Rol;
import com.qantica.fedemarket.entidad.Subcategoria;

@ManagedBean
@ViewScoped
public class CategoriaManage {

	@EJB(name = "CategoriaBean/remote")
	CategoriaBeanRemote miEJBCategoria;
	
	@EJB(name="RolBean/remote")
	RolBeanRemote miEJBRol;

	Categoria categoria = new Categoria();
	Subcategoria subCategoria;

	private UploadedFile file;
	
	private String nombre;
	private String descripcion;
	int rol;
	
	public List<Subcategoria> subCategorias = new ArrayList<Subcategoria>();	
	private List<Rol> listaRoles;
	
	int id_categoria = 0;
	int id_intercategoria = 0;
	int id_subcategoria = 0;
		
	public void adicionarCategoria() {		
		//Codifica el nombre y la descripción en UTF-8
		try {
			categoria.setNombre(new String(nombre.getBytes("ISO-8859-1"),"UTF-8"));
			categoria.setDescripcion(new String(descripcion.getBytes("ISO-8859-1"),"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			if (nombre.isEmpty() || descripcion.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage("formul",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Verifique la información suministrada.",
								"Alguno de los campos se encuentra vacio."));
			} else if (!verificarExtension()) {
				FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"El archivo seleccionado en el campo \"Seleccione Un Icono \" no es una imagen.",
										"El archivo seleccionado en el campo \"Seleccione Un Icono \" no es una imagen."));
			} else if (copyFile(file.getFileName(), file.getInputstream())) {
				categoria.setRol(miEJBRol.buscarRol(rol));
				categoria.setIcono(file.getFileName());
				categoria.setEstado(true);
				miEJBCategoria.adicionarCategoria(categoria);
				categoria = new Categoria();

				FacesContext.getCurrentInstance().addMessage("formul",new FacesMessage(
						FacesMessage.SEVERITY_INFO,
								"Verifique la información suministrada.",
								"Categoria adicionada"));
			} else {
				FacesContext.getCurrentInstance().addMessage("formul",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Se produjo un urror durante la carga del archivo. Consulte al administrador.",
										"Alguno de los campos, no se encuentra diligenciado."));
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("formul",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Verifique la información suministrada.",
									"Verifique la información suministrada o intentelo mas tarde."));
		}
	}
	
	/**
	 * Lista los roles para el CheckList de ingreso de contenido
	 * @return List roles
	 */
	public List<Rol> getRoles(){
		listaRoles = miEJBRol.listarRoles();
		return listaRoles;
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

	public void actualizar() {
		miEJBCategoria.actualizarCategoria(categoria);
	}

	public List<Categoria> getCategorias() {
		return miEJBCategoria.listarCategoriasServlet();
	}

	public List<Subcategoria> getSubCategorias() {
		return subCategorias;
	}

	public void updateCategories() {
		if (id_categoria != 0) {
			subCategorias = miEJBCategoria.listarSubcategorias(id_categoria);
		}
	}

	public void updateSubCategories() {
		if (id_intercategoria != 0) {
			subCategorias = miEJBCategoria.listarSubcategorias(id_intercategoria);
		}
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public CategoriaBeanRemote getMiEJB() {
		return miEJBCategoria;
	}

	public void setMiEJB(CategoriaBeanRemote miEJB) {
		this.miEJBCategoria = miEJB;
	}

	public int getId_categoria() {
		return id_categoria;
	}

	public void setId_categoria(int id_categoria) {
		this.id_categoria = id_categoria;
	}

	public int getId_intercategoria() {
		return id_intercategoria;
	}

	public void setId_intercategoria(int id_intercategoria) {
		this.id_intercategoria = id_intercategoria;
	}

	public int getId_subcategoria() {
		return id_subcategoria;
	}

	public void setId_subcategoria(int id_subcategoria) {
		this.id_subcategoria = id_subcategoria;
	}

	public void setSubCategorias(List<Subcategoria> subCategorias) {
		this.subCategorias = subCategorias;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Subcategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Subcategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getRol() {
		return rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	public List<Rol> getListaRoles() {
		listaRoles = miEJBRol.listarRoles();
		return listaRoles;
	}

	public void setListaRoles(List<Rol> listaRoles) {
		this.listaRoles = listaRoles;
	}
	
	
}
