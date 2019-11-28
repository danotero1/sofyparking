package controlador;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.primefaces.PrimeFaces;

import modelo.*;
import entidades.*;


@ManagedBean(name="ControladorMovimiento")
@SessionScoped
public class ControladorMovimientos {
	
	private MovimientoModelo movimiento = new MovimientoModelo();
	private Movimiento nuevoMovimiento = new Movimiento();
	private Movimiento movimientoActual = new Movimiento();
	private TarifaModelo tarifa = new TarifaModelo();
	private List<Movimiento>listaFiltrada ;

	
	
	
	public String add(Reserva reserva) {
		System.out.println(reserva.getPlaca());
		
		this.nuevoMovimiento.setPlaca(reserva.getPlaca());
		this.nuevoMovimiento.setCorreoUsuario(reserva.getCorreoUsuario());
		this.nuevoMovimiento.setTipoParqueadero(reserva.getTipoParqueadero());
		this.nuevoMovimiento.setFechaHoraReserva(reserva.getFechaHoraReserva());
		this.nuevoMovimiento.setFechaHoraLlegada(reserva.getFechaHoraLlegada());
		this.nuevoMovimiento.setActivo("A");
		this.nuevoMovimiento.setFechaHoraSalida(new Date(11,11,1990));
		this.nuevoMovimiento.setTiempo(new Date(11,11,1990));
		this.nuevoMovimiento.setValorCobro(0);
		this.nuevoMovimiento.setTipoCobro(reserva.getTipoCobro());
		
		this.movimiento.create(this.nuevoMovimiento);
		this.nuevoMovimiento = new Movimiento();
		System.out.println("eo");
		

		return "InterfazSuper";
	}
	public void checkOutDialog(){

		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgCheckOut').show();");
		

	}
	
	public List<Movimiento> historialMovimientosUsuario(String correo){
		return movimiento.historialUsuario(correo);
	}
	public String checkOutAction() {
		String rta="InterfazSuper";
		int cobro =0;
		System.out.println(this.nuevoMovimiento.getCorreoUsuario());
		this.movimientoActual = this.movimiento.obtenerMovimientoPorCorreo(this.nuevoMovimiento.getCorreoUsuario());

		
		if(this.movimientoActual!=null) {
			System.out.println(movimientoActual.getId()+"<--");

			Date fechaActual = new Date();
			
			this.movimientoActual.setFechaHoraSalida(fechaActual);
//			this.movimientoResumen.setFechaHoraSalida(fechaActual);
//			this.movimientoResumen.setFechaHoraReserva(this.movimientoActual.getFechaHoraReserva());
//			this.movimientoResumen.setFechaHoraLlegada(this.movimientoActual.getFechaHoraLlegada());

			System.out.println("actual fecha "+fechaActual);
			System.out.println("fecha enter "+movimientoActual.getFechaHoraReserva());
			//Date elapsed = new Date(millis);
			DateTime salida = new DateTime(fechaActual);
			DateTime entrada = new DateTime(movimientoActual.getFechaHoraReserva());
			Duration elapsed = new Duration(entrada,salida);
			System.out.println("elapsed day  "+elapsed.getStandardDays());
			System.out.println("elapsed min  "+elapsed.getStandardMinutes());
			System.out.println("elapsed sec  "+elapsed.getStandardSeconds());
			Date tiempo = new Date(elapsed.getStandardSeconds()*1000);
			long minutes  = elapsed.getStandardMinutes();


			this.movimientoActual.setTiempo(tiempo);
			
			Tarifa tarifaActual = tarifa.find(movimientoActual.getTipoCobro());
			cobro = (int) (minutes*tarifaActual.getValorTarifa());
			if(tarifaActual.getDescuento()!=0) {
				cobro += obtenerDescuento(tarifaActual.getDescuento(), cobro);
			}
			this.movimientoActual.setValorCobro(cobro);
			System.out.println("aquii");
			
			System.out.println(tarifaActual.getDescripcionTipoCobro()+"<--------");
			this.movimientoActual.setActivo("D");
			
			
			this.movimiento.update(this.movimientoActual);
			
			movimientoActual = new Movimiento();
		}else {
			
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('warnDlg').show();");
		}
		
		
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgCheckOut').hide();");
		
		return rta;
	}
	public int obtenerDescuento(int dec,int cobro) {
		int descuento =dec/100;
		System.out.println(cobro*descuento);
		return cobro*descuento;
	}

	
	
	
	public String edit() {
		return "InterfazUsuario";
		
	}
	
	public void delete(Movimiento u) {
		this.movimiento.delete(u);
		
	}
	
	public List<Movimiento> findAll(){
		return movimiento.findAll();
	}



	public Movimiento getNuevoMovimiento() {
		return nuevoMovimiento;
	}



	public void setNuevoMovimiento(Movimiento nuevoMovimiento) {
		this.nuevoMovimiento = nuevoMovimiento;
	}



	public Movimiento getMovimientoActual() {
		return movimientoActual;
	}



	public void setMovimientoActual(Movimiento movimientoActual) {
		this.movimientoActual = movimientoActual;
	}
	public List<Movimiento> getListaFiltrada() {
		return listaFiltrada;
	}
	public void setListaFiltrada(List<Movimiento> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}
	
	
	



	
	
	

}
