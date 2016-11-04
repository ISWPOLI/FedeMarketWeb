package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.qantica.fedemarket.entidad.Usuario;
import com.qantica.fedemarket.ejb.UsuarioBeanRemote;

@ViewScoped
@ManagedBean
public class ReportListUser {

	@EJB(name = "UsuarioBean/remote")
	UsuarioBeanRemote miEjbUsuario;

	public List<Usuario> getListUserAcces() {

		List<Usuario> lista = miEjbUsuario.listarUsuarios();
		return lista;
	}

	public String redirect() {

		return "./ReportUser.jsf";
	}

}
