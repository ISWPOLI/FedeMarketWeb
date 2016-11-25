package com.qantica.fedemarket.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.ejb.EJB;
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
import com.qantica.fedemarket.ejb.UsuarioBeanRemote;
import com.qantica.fedemarket.entidad.Contenido;
import com.qantica.fedemarket.entidad.Descarga;
import com.qantica.fedemarket.entidad.Usuario;

/**
 * Servlet para descargar un archivo, inserta log de descarga
 * @author Juan Rubiano
 * 09/11/2016
 */
public class ServletDescarga extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	Context context;
	
	@EJB(name="DescargaBean/remote")
	DescargaBeanRemote miEJBDescarga;
	
	@EJB(name="ContenidoBean/remote")
	ContenidoBeanRemote miEJBContenido;
	
	@EJB(name="ComentarioBean/remote")
	ComentarioBeanRemote miEJBComentario;	

	@EJB(name="UsuarioBean/remote")
	UsuarioBeanRemote miEJBUsuario;

	public void init() {
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	public ServletDescarga() {
		super();
	}

	/**
	 * HTTP GET
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		String contenido = request.getParameter("id_contenido");
		String usuario = request.getParameter("id_usuario");

		//configuracion de la fecha actual con hora
		java.util.Date utilDate = new java.util.Date(); // fecha actual
		long lnMilisegundos = utilDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
		SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
		String cadenaFecha = formato.format(sqlTimestamp);
				
		//Busca el contenido que se está descargando
		Contenido miContenido = miEJBContenido.buscarContenido(Integer.parseInt(contenido));
		
		Usuario  miUsuario = miEJBUsuario.buscarUsuario(Integer.parseInt(usuario));
		
		//Aumento en 1 el contador de las descargas
		miContenido.setDescargas(miContenido.getDescargas()+1);
		
		//Actualizo el contenido
		miEJBContenido.updateContenido(miContenido);
		
		//Creo un objeto Descarga para insertar en el log
		Descarga miDescarga = new Descarga();
		miDescarga.setId(0);
		miDescarga.setContenido(miContenido);
		miDescarga.setUsuario(miUsuario);
		miDescarga.setFecha(cadenaFecha);
		
		//System.out.println(miDescarga.getContenido());
		
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			//String archivo = "C:/Users/jrubiaob/Documents";
			String archivo = Conf.RUTA_CONTENIDO+miContenido.getRuta();
			
			File f = new File(archivo);
			
			// Se define el tipo de archivo a descargar
			response.setContentType("image/jpg");
			
			miEJBDescarga.adicionarDescarga(miDescarga);
			
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
			
			miEJBDescarga.adicionarDescarga(miDescarga);
			
		} finally {
			// out.close();
		}
	}

	/**
	 * POST
	 */
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		out.print("<500>");
	}

}
