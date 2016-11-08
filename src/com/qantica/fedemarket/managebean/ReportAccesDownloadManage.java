package com.qantica.fedemarket.managebean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.qantica.fedemarket.ejb.IngresoBeanRemote;
import com.qantica.fedemarket.entidad.Ingreso;

@ManagedBean
@ViewScoped
public class ReportAccesDownloadManage {

	@EJB(name = "IngresoBean/remote")
	IngresoBeanRemote miEjb;
	
	public List<Ingreso> getIngresos() {
		
		List<Ingreso> lista = miEjb.listarIngresosSinDescarga();
		
		return lista;
	}
	
}
