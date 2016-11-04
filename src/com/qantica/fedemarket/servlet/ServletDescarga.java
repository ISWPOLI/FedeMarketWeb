package com.qantica.fedemarket.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qantica.fedemarket.conf.Conf;
import com.qantica.fedemarket.ejb.ComentarioBeanRemote;
import com.qantica.fedemarket.ejb.ContenidoBeanRemote;
import com.qantica.fedemarket.ejb.DescargaBeanRemote;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Descarga;

/**
 * Servlet implementation class ServletDescarga
 */
public class ServletDescarga extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	Context context;
	DescargaBeanRemote miEJB;
	ContenidoBeanRemote miEJBContenido;
	ComentarioBeanRemote miEJBComentario;

	public void init() {
		try {

			context = new InitialContext();
			miEJB = (DescargaBeanRemote) context.lookup("DescargaBean/remote");
			miEJBContenido = (ContenidoBeanRemote) context
					.lookup("ContenidoBean/remote");
			miEJBComentario = (ComentarioBeanRemote) context
					.lookup("ComentarioBean/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDescarga() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		// parametros de llegada
		String contenido = request.getParameter("id_contenido");
		String usuario = request.getParameter("id_usuario");
		String nombre = request.getParameter("nombre_usuario");

		//configuracion de la fecha actual con hora
		java.util.Date utilDate = new java.util.Date(); // fecha actual
		long lnMilisegundos = utilDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
		SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
		String cadenaFecha = formato.format(sqlTimestamp);
		
		
		// instacia de la entidad descarga
		Descarga miDescarga = new Descarga();
		
		Contenido miContenido = miEJBContenido.buscarContenido(Integer.parseInt(contenido));
		miContenido.setDescargas(miContenido.getDescargas()+1);
		
		miEJBContenido.updateContenido(miContenido);
		
		miDescarga.setId(0);
		miDescarga.setContenido(miContenido);
		miDescarga.setUsuario(usuario);
		miDescarga.setNombre(nombre);
		miDescarga.setFecha(cadenaFecha);
		
		// descarga del archivo
		response.setContentType("text/html;charset=UTF-8");
		// PrintWriter out = response.getWriter();
		try {
			//String archivo = "C:/Users/USUARIO WINDOWS/Desktop/hola.jpg";
			String archivo = Conf.RUTA_CONTENIDO+miContenido.getRuta();
			
//			System.out.println(new File(".").getAbsolutePath());
			
			File f = new File(archivo);
			response.setContentType("image/jpg");// Se define el tipo de archivo
													// a descargar
			response.setContentLength((int) f.length());
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ archivo + "\"");
			InputStream in = new FileInputStream(f);
			ServletOutputStream outs = response.getOutputStream();
			int bit = 256;
			try {
				while ((bit) >= 0) {
					bit = in.read();
					outs.write(bit);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace(System.out);
			}

			outs.flush();
			outs.close();
			in.close();
			miEJB.adicionarDescarga(miDescarga);
			
		} finally {
			// out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	}

}
