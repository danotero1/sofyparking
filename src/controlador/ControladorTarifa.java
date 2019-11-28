package controlador;

import java.util.*;

import javax.faces.bean.*;

import modelo.*;
import entidades.*;
@ManagedBean(name="ControladorTarifa")
@SessionScoped
public class ControladorTarifa {
	
	
	private TarifaModelo tarifa = new TarifaModelo();
	private Tarifa nuevoTarifa  = new Tarifa();
	private Tarifa tarifaActual = new Tarifa();
	
	
	public TarifaModelo getTarifa() {
		return tarifa;
	}

	public void setTarifa(TarifaModelo tarifa) {
		this.tarifa = tarifa;
	}

	public Tarifa getNuevoTarifa() {
		return nuevoTarifa;
	}

	public void setNuevoTarifa(Tarifa nuevoTarifa) {
		this.nuevoTarifa = nuevoTarifa;
	}

	public Tarifa getTarifaActual() {
		return tarifaActual;
	}

	public void setTarifaActual(Tarifa tarifaActual) {
		this.tarifaActual = tarifaActual;
	}

	public String add() {
		
//		this.nuevoTarifa.setDescripcionTipoCobro("s");
//		this.nuevoTarifa.setValorTarifa(0);
//		this.nuevoTarifa.setDescuento(0);
		this.nuevoTarifa.setEstado("A");
		System.out.println(this.nuevoTarifa.getDescripcionTipoCobro());
		this.tarifa.create(this.nuevoTarifa);
		this.nuevoTarifa = new Tarifa();
		return "Tarifas";
	}
	
	public String edit(Tarifa t) {
		this.nuevoTarifa = t;
		System.out.println("entro");
		return "EditarTarifa";
		
	}
	
	public String edit() {
		this.tarifa.update(this.nuevoTarifa);
		return "Tarifas.xhtml";
		
	}
	public void delete(Tarifa t) {
		this.tarifa.delete(t);
	}
	public List<Tarifa> findAll(){
		return tarifa.findAll();
	}
	
	
	/*
	public String obtenerTarifaParqueadero(String idTarifa) {
		String rta="";
		Tarifa tarifa = this.reserva.obtenerTarifaParqueadero(Integer.parseInt(idTarifa));
		rta=tarifa.getDescripcionTipoCobro();
		return rta;
	}
	*/
	
	

}
