package com.qantica.fedemarket.managebean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.mundo.ExtensionArchivo;
import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Subcategoria;

@ManagedBean
@ViewScoped
public class ContenidoAltertManager {

	@EJB(name = "ContenidoBean/remote")
	ContenidoBeanRemote miEJB;
	
	private Contenido contenido = new Contenido();
	
	//Listado de contenido
	private List<Contenido> lista ;	
	
	//Id del contenido a buscar
	int id;

	private UploadedFile archivo;
	private UploadedFile file;
	private UploadedFile screen_principal;
	private UploadedFile screen_secundario;

	public void update() {
		try {			
			contenido.setNombre(new String(contenido.getNombre().getBytes("ISO-8859-1"), "UTF-8"));
			contenido.setDescripcion(new String(contenido.getDescripcion().getBytes("ISO-8859-1"), "UTF-8"));			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		try {
			if (isValidFile(archivo)) {
				if (copyFile(archivo.getFileName(), archivo.getInputstream())) {
					System.out.println("File upload");
					contenido.setRuta(archivo.getFileName());
				}
			}

			if (isValid(file)) {
				if (copyFileIcon(file.getFileName(), file.getInputstream())) {
					System.out.println("File icon is upload");
					contenido.setIcono(file.getFileName());
				}
			}

			if (isValid(screen_principal)) {
				if (copyFileScreen(screen_principal.getFileName(),
						screen_principal.getInputstream())) {
					contenido.setCaptura_1(screen_principal.getFileName());
				}
			}

			if (isValid(screen_secundario)) {
				if (copyFileScreen(screen_secundario.getFileName(),
						screen_secundario.getInputstream())) {
					contenido.setCaptura_2(screen_secundario.getFileName());
				}
			}

			miEJB.actualizarContenido(contenido);
			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Contenido Actualizado!",
							"El Contenido Se Actualizo Correctamente!"));
		} catch (Exception e) {

			FacesContext.getCurrentInstance().addMessage(
					"form",

					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error En La Actualización!",
							"Error En La Actualización. Intentelo Mas Tarde!"));
		}

	}
	
	/**
	 * Verifica si la extensión de la imagen es válida o no 
	 * @param file
	 * @return true si la extensión es válida
	 * false si la extensión es inválida
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

	/**
	 * Verifica si la extensión del archivo es válida o no 
	 * @param file
	 * @return true si la extensión es válida
	 * false si la extensión es inválida
	 */
	private boolean verificarExtensionFile(UploadedFile file) {

		String[] cont = ExtensionArchivo.contenidoContenido;

		for (int i = 0; i < cont.length; i++) {

			if (file.getFileName().contains(cont[i])) {
				i = cont.length + 1;
			} else if (i == cont.length - 1) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Verifica si la extensión del archivo es válido o no
	 * @param file
	 * @return true si la extensión es válida
	 * false si la extensión es inválida
	 */
	private boolean isValidFile(UploadedFile file) {
		if (file != null) {
			return verificarExtensionFile(file);
		}
		return false;
	}

	/**
	 * Verifica si la imagen existe o no
	 * @param file
	 * @return true si la extensión es válida
	 * false si la extensión es inválida
	 */
	private boolean isValid(UploadedFile file) {
		if (file != null) {
			return verificarExtension(file);
		}
		return false;
	}


	/**
	 * Método que carga la imagen al servidor
	 * @param fileName
	 * @param in
	 * @return true si se cargo la imagen false si no
	 */
	public boolean copyFile(String fileName, InputStream in) {

		try {
			File mFile = new File(Conf.RUTA_CONTENIDO + fileName);
			// write the inputStream to a FileOutputStream
			OutputStream out = new FileOutputStream(mFile);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();

			System.out.println("[Upload] - Nuevo Archivo Creado!");
			return true;

		} catch (IOException e) {

			System.out.println("[Upload] - Error Cargando el Archivo "+fileName);
			return false;
		}

	}

	/**
	 * Método que carga el ícono del contenido al servidor
	 * @param fileName
	 * @param in
	 * @return true si la imagen fue cargada o false si no
	 */
	public boolean copyFileIcon(String fileName, InputStream in) {

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

			System.out.println("[Upload] - Nuevo Archivo Creado!");
			return true;

		} catch (IOException e) {
			System.out.println("[Upload] - Error Cargando el Archivo!");
			return false;
		}

	}

	/**
	 * Método que arga la imagen de captura al servidor
	 * @param fileName
	 * @param in
	 * @return true si es cargada o false si no
	 */
	public boolean copyFileScreen(String fileName, InputStream in) {

		try {
			File mFile = new File(Conf.RUTA_SCREEN + fileName);
			OutputStream out = new FileOutputStream(mFile);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}

			in.close();
			out.flush();
			out.close();

			System.out.println("[Upload] - Nuevo Archivo Creado "+fileName);
			return true;

		} catch (IOException e) {
			System.out.println("[Upload] - Error Cargando el Archivo "+fileName);
			return false;
		}

	}
	
	/**
	 * 
	 * ¡GETTERS AND SETTERS!
	 * 
	 */
	
	public List<Contenido> getLista() {
		System.out.println("Obteniendo la lista");
		lista = miEJB.listarContenido();
		return lista;
	}
	
	
	public void setLista(List<Contenido> lista) {
		this.lista = lista;
	}

	
	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}
	public Contenido getContenido() {
		return contenido;
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

	public UploadedFile getScreen_principal() {
		return screen_principal;
	}

	public void setScreen_principal(UploadedFile screen_principal) {
		this.screen_principal = screen_principal;
	}

	public UploadedFile getScreen_secundario() {
		return screen_secundario;
	}

	public void setScreen_secundario(UploadedFile screen_secundario) {
		this.screen_secundario = screen_secundario;
	}

	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}
}
