package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.qantica.fedemarket.ejb.DescargaBeanRemote;
import com.qantica.fedemarket.entidad.Descarga;

@ManagedBean
public class DescargaManage {

	@EJB(name="DescargaBean/remote")
	DescargaBeanRemote miEJB;
	
	Descarga descarga;
	int id;

	public void adicionarDescarga(){
		miEJB.adicionarDescarga(descarga);
		descarga=new Descarga();
	}
	
	public void buscar() {
		miEJB.buscarDescarga(id);
	}

	public void actualizar() {
		miEJB.actualizarDescarga(descarga);
	}
	
	/**
	 * lista de descargas de la aplicación
	 * @return
	 */
	public List<Descarga> getDescargas(){
		return miEJB.historialDescarga();
	}
	
	
	public Descarga getDescarga() {
		return descarga;
	}

	public void setDescarga(Descarga descarga) {
		this.descarga = descarga;
	}

}
