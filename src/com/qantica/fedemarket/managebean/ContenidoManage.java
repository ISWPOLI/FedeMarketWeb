package com.qantica.fedemarket.managebean;

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
import javax.servlet.ServletException;

import org.primefaces.model.UploadedFile;

import com.qantica.fedemarket.mundo.ExtensionArchivo;
import com.qantica.fedemarket.mundo.FechaActual;
import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.CategoriaBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.RolBeanRemote;
import com.qantica.fedemarket.ejb.SubcategoriaBeanRemote;
import com.qantica.fedemarket.entidad.Categoria;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Rol;
import com.qantica.fedemarket.entidad.Subcategoria;
import com.sun.syndication.feed.rss.Description;

/**
 * Clase manejadora del Bean Contenido
 * @author Juan Rubiano
 * 25/07/2016
 * Q-antica Ltda. 
 */

@ManagedBean
@ViewScoped
public class ContenidoManage {

	@EJB(name = "ContenidoBean/remote")
	ContenidoBeanRemote miEJBContenido;

	@EJB(name = "CategoriaBean/remote")
	CategoriaBeanRemote miEJBCategoria;

	private Contenido contenido = new Contenido();
	private Categoria categoria;
	private Subcategoria subCategoria;	

	//ID de los tres niveles
	int id_categoria = 0;
	int id_intercategoria = 0;
	int id_subcategoria = 0;

	//ID del contenido que se va a buscar
	int id;

	//Archivo que se va a cargar correspondiente al contenido
	private UploadedFile file;

	//Ícono del contenido
	private UploadedFile file_icon;

	//Imagen principal del contenido
	private UploadedFile screen_principal;

	//Segunda imagen del contenido
	private UploadedFile screen_secundario;

	/**
	 * Adición del contenido
	 */
	public void adicionarContenido() {
		try {
			contenido.setNombre(new String(contenido.getNombre().getBytes("ISO-8859-1"), "UTF-8"));
			contenido.setDescripcion(new String(contenido.getDescripcion().getBytes("ISO-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		/*if (contenido.getNombre().length() != 0
				&& contenido.getVersion().length() != 0
				&& contenido.getDescripcion().length() != 0) {
			if (!contenido.isCaficultor() && !contenido.isEmpleado()
					&& !contenido.isInvestigador() && !contenido.isOtros()) {
				FacesContext.getCurrentInstance().addMessage(
						"formul",
						new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Verifique La Información Suministrada!",
								"Aun No Se Encuentra Ningun Rol Seleccionado"));
			} else if (id_categoria == 0 ){//|| id_intercategoria == 0|| id_subcategoria == 0) { //Acá están los tres niveles de catergorías.
				FacesContext.getCurrentInstance().addMessage(
								"formul",
								new FacesMessage(
										FacesMessage.SEVERITY_ERROR,
										"Verifique Las Categorias Seleccionadas",
										"Por favor seleccione una categoria"));
			} else {
				try {
					if (verificarExtension()) {
						boolean archivo_contenido = copyFile(
								file.getFileName(), file.getInputstream());
						boolean archivo_icono = copyFileIcon(
								file_icon.getFileName(),
								file_icon.getInputstream());
						boolean captura_1 = copyFileScreen(
								screen_principal.getFileName(),
								screen_principal.getInputstream());
						boolean captura_2 = copyFileScreen(
								screen_secundario.getFileName(),
								screen_secundario.getInputstream());

						if (archivo_contenido && archivo_icono && captura_1
								&& captura_2) {

							categoria = miEJBcategoria
									.buscarCategoria(id_categoria);

							subCategoria = miEJBcategoria
									.buscarSubCategoria(id_subcategoria);

							contenido.setCategoria(categoria);
							contenido.setSubCategoria(subCategoria);
							contenido.setPublicacion(FechaActual.timestamp());
							contenido.setRuta(file.getFileName());
							contenido.setIcono(file_icon.getFileName());
							contenido.setCaptura_1(screen_principal
									.getFileName());
							contenido.setCaptura_2(screen_secundario
									.getFileName());

							try {

								double version = Double.parseDouble(contenido
										.getVersion());


							} catch (Exception e) {
								FacesContext
										.getCurrentInstance()
										.addMessage(
												"formul",
												new FacesMessage(
														FacesMessage.SEVERITY_INFO,
														"La versión no es un valor numerico!",
														"La versión no es un valor numerico!"));
							}

							try {


								contenido.setEstado(true);
								miEJB.adicionarContenido(contenido);
								contenido = new Contenido();
								file = null;
								FacesContext
										.getCurrentInstance()
										.addMessage(
												"formul",
												new FacesMessage(
														FacesMessage.SEVERITY_INFO,
														"Se Adiciono El Contenido Correctamente!",
														"Se Adiciono El Contenido Correctamente!"));

							} catch (Exception e) {
								FacesContext
								.getCurrentInstance()
								.addMessage(
										"formul",
										new FacesMessage(
												FacesMessage.SEVERITY_INFO,
												"No se pudo insertar el contenido, intentelo mas tarde!",
												"No se pudo insertar el contenido, intentelo mas tarde!"));
							}


						} else {
							FacesContext
									.getCurrentInstance()
									.addMessage(
											"formul",
											new FacesMessage(
													FacesMessage.SEVERITY_ERROR,
													"Se Produjo Un Error Durante la Carga del Archivo!",
													"Algunos de los campos de archivos no se encuentran diligenciados!"));
						}
					}
				} catch (IOException e) {

					FacesContext
							.getCurrentInstance()
							.addMessage(
									"formul",
									new FacesMessage(
											FacesMessage.SEVERITY_ERROR,
											"Se Produjo Un Error Durante la Carga del Archivo!",
											"Se Produjo Un Error Durante la Carga del Archivo!"));
				}

			}

		} else {

			FacesContext.getCurrentInstance().addMessage(
					"formul",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Verifique La InformaciÃ³n Suministrada!",
							"Alguno De Los Campos Se Encuentra Vacio"));
		}*/

	}

	/**
	 * metodo encargado de verificar si la extension del archivo es la correcta
	 * 
	 * @return
	 */
	private boolean verificarExtension() {

		String[] img = ExtensionArchivo.contenidoImg;
		String[] cont = ExtensionArchivo.contenidoContenido;
		boolean imgIcono = false;
		boolean imgCap1 = false;
		boolean imgCap2 = false;

		for (int i = 0; i < cont.length; i++) {
			if (file.getFileName().contains(cont[i])) {
				i = cont.length + 1;
			} else if (i == cont.length - 1) {
				FacesContext
				.getCurrentInstance()
				.addMessage(
						"formul",
						new FacesMessage(
								FacesMessage.SEVERITY_ERROR,
								"El archivo seleccionado en el campo \"Seleccione el Contenido *\" no cuenta con un formato valido!",
								"El archivo seleccionado en el campo \"Seleccione el Contenido *\" no cuenta con un formato valido!"));

				return true;
			}

		}

		for (int i = 0; i < img.length; i++) {
			System.out.println("file_icon " + file_icon + "Aca");
			System.out.println("screen_principal " + screen_principal + "Aca");
			System.out.println("screen_secundario " + screen_secundario + "Aca");

			if (file_icon.getFileName().contains(img[i])) {
				imgIcono = true;
			}
			if (screen_principal.getFileName().contains(img[i]) || screen_principal.getFileName() == "") {
				imgCap1 = true;
			}
			if (screen_secundario.getFileName().contains(img[i]) || screen_principal.getFileName() == "") {
				imgCap2 = true;
			}

		}

		if (!imgCap1) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"formul",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"El archivo seleccionado en el campo \"Seleccione Captura 1 *\" no es una imagen!",
							"El archivo seleccionado en el campo \"Seleccione Captura 1 *\" no es una imagen!"));
			return false;
		} else if (!imgCap2) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"formul",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"El archivo seleccionado en el campo \"Seleccione Captura 2 *\" no es una imagen!",
							"El archivo seleccionado en el campo \"Seleccione Captura 2 *\" no es una imagen!"));
			return false;
		} else if (!imgIcono) {
			FacesContext
			.getCurrentInstance()
			.addMessage(
					"formul",
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"El archivo seleccionado en el campo \"Seleccione el Icono *\" no es una imagen!",
							"El archivo seleccionado en el campo \"Seleccione el Icono *\" no es una imagen!"));
			return false;
		}

		return true;
	}

	/**
	 * METODO PARA LA CARGA DE LA IMAGEN AL SERVIDOR
	 * 
	 * @param fileName
	 * @param in
	 * @return
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

			System.out.println("[Upload] - Nuevo Archivo Creado! copyFile");
			return true;

		} catch (IOException e) {

			System.out.println("[Upload] - Error Cargando el Archivo! copyFile");
			return false;
		}

	}

	/**
	 * METODO PARA LA CARGA DE LA IMAGEN DE CAPTURA AL SERVIDOR
	 * 
	 * @param fileName
	 * @param in
	 * @return
	 */
	public boolean copyFileScreen(String fileName, InputStream in) {

		if(fileName.isEmpty()){
			fileName = null;
		}
		try {

			File mFile = new File(Conf.RUTA_SCREEN + fileName);
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

			System.out.println("[Upload] - Nuevo Archivo Creado! copyFileScreen");
			return true;

		} catch (IOException e) {
			System.out.println("[Upload] - Error Cargando el Archivo! copyFileScreen");
			return false;
		}

	}

	/**
	 * Método para la carga del ícono al servidor 
	 * @param fileName
	 * @param in
	 * @return
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

			System.out.println("[Upload] - Nuevo Archivo Creado! copyFileIcon");
			return true;

		} catch (IOException e) {
			System.out.println("[Upload] - Error Cargando el Archivo! copyFileIcon");
			return false;
		}

	}

	/**
	 * Metodo encargado de listar las categorias de primer nivel
	 * @return
	 */
	public List<Categoria> getCategorias() {
		return miEJBCategoria.listarCategoriasServlet();
	}

	public List<Subcategoria> getCategoriasSub() {
		List<Subcategoria> temp = new ArrayList<Subcategoria>();
		try {			
			temp = miEJBCategoria.listarSubcategorias(id_categoria);
		} catch (Exception e) {
			temp = miEJBCategoria.listarSubcategorias(0);
		}
		return temp;
	}

	/*public List<Subcategoria> getCategoriasNivel() {
		return miEJBcategoria.listarSubcategoriasNivel(id_intercategoria);
	}*/

	public List<Contenido> getDestacados() {
		List<Contenido> lista = miEJBContenido.listarDestacados(true);
		return lista;
	}

	public void buscar() {
		miEJBContenido.buscarContenido(id);
	}

	/** GETTERS AND SETTERS **/

	public Contenido getContenido() {
		return contenido;
	}

	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Subcategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(Subcategoria subCategoria) {
		this.subCategoria = subCategoria;
	}

	public UploadedFile getFile_icon() {
		return file_icon;
	}

	public void setFile_icon(UploadedFile file_icon) {
		this.file_icon = file_icon;
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

}
