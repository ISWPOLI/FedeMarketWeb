package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.qantica.fedemarket.entidad.Usuario;
import com.qantica.fedemarket.ejb.ComentarioBeanRemote;
import com.qantica.fedemarket.ejb.DescargaBeanRemote;
import com.qantica.fedemarket.ejb.IngresoBeanRemote;
import com.qantica.fedemarket.ejb.UsuarioBeanRemote;
import com.qantica.fedemarket.entidad.Comentario;
import com.qantica.fedemarket.entidad.Descarga;
import com.qantica.fedemarket.entidad.Ingreso;

@SessionScoped
@ManagedBean
public class ReportUserBean {

	@EJB(name = "DescargaBean/remote")
	DescargaBeanRemote miEjbDescarga;

	@EJB(name = "ComentarioBean/remote")
	ComentarioBeanRemote miEjbComentario;

	@EJB(name = "IngresoBean/remote")
	IngresoBeanRemote miEjb;
	

	public String user="";
	
	public List<Comentario> getLista_Comentarios() {
		
		return miEjbComentario.listarComentariosUsuario(user);
	}

	public List<Descarga> getLista_Descargas() {
		
		return miEjbDescarga.listDescargaUsuario(user);
	}

	public List<Ingreso> getLista_Ingresos() {
		
		return miEjb.listarIngresos(user);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
