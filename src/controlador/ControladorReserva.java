package controlador;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.PrimeFaces;

import entidades.Parqueadero;
import entidades.Reserva;
import entidades.Usuario;
import modelo.ReservaModelo;
import modelo.UsuarioModelo;


@ManagedBean(name="ControladorReserva")
@SessionScoped
public class ControladorReserva {
	private ReservaModelo reserva = new ReservaModelo();
	private Reserva nuevaReserva = new Reserva();
	private Reserva reservaActual = new Reserva();
	private Parqueadero parqueadero = new Parqueadero();
	private Date date = new Date();
	private List<Reserva>listaFiltrada ;


	public String reservar(Parqueadero parqueadero,Usuario usuario,String placa) {
		this.parqueadero = parqueadero;
		Date fechaActual = new Date();
		System.out.println("nombre parqueadero : "+parqueadero.getCiudad());
		System.out.println("nombre id usuario : "+usuario.getId());
		this.nuevaReserva.setActivo("A");
		this.nuevaReserva.setTipoCobro(1);
		this.nuevaReserva.setValorCobro(0);
		this.nuevaReserva.setTiempoEstimado(new Date(11,11,1990));
		this.nuevaReserva.setFechaHoraLlegada(new Date(11,11,1990));
		this.nuevaReserva.setFechaHoraReserva(fechaActual);
		this.nuevaReserva.setTipoParqueadero(parqueadero.getTipo());
		this.nuevaReserva.setNombreParqueadero(parqueadero.getNombreParqueadero());
		this.nuevaReserva.setTipoServicio(parqueadero.getServicio());
		this.nuevaReserva.setCorreoUsuario(usuario.getCorreo());
		this.nuevaReserva.setPlaca(placa);


		this.reserva.create(this.nuevaReserva);
		this.nuevaReserva = new Reserva();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlg2').show();");
		System.out.println(placa);


		return "/InterfazUsuario.xhtml";
	}
	
	public void abrirDialogoDeReservas() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgCheckIn').show();");
	}
	public String desactivarReserva(Reserva reserva) {
		System.out.println(reserva.getId());
		reserva.setActivo("D");
		this.reserva.update(reserva);
		return "Reservas";
	}
	
	public String checkInReserva() {
		String rta="InterfazSuper";
		System.out.println(this.nuevaReserva.getCorreoUsuario());
		reservaActual = this.reserva.obtenerReservaPorCorreo(this.nuevaReserva.getCorreoUsuario());
		
		if(reservaActual!=null) {
			Date fechaActual = new Date();
			System.out.println(reservaActual.getId()+" "+reservaActual.getTipoServicio());
			reservaActual.setFechaHoraLlegada(fechaActual);
			reservaActual.setActivo("D");
			this.reserva.update(reservaActual);
			System.out.println(reservaActual.getFechaHoraReserva()+" correo "+reservaActual.getCorreoUsuario()+" id: "+reservaActual.getId());
			rta="GenerarMovimiento";
		}else {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('warnDlg').show();");
		}
		
		
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlg').hide();");
		return rta;
	}
	public List<Reserva> historialReservasUsuario(String correoUsuario){
		return reserva.historialUsuario(correoUsuario);
	}
	public List<Reserva> ReservasActivasDelUsuario(Usuario usuario) {
		
		return reserva.ReservasActivasDelUsuario(usuario.getCorreo());
	}
	public List<Reserva> findAll(){
		return reserva.findAll();
	}
	public void mostrarResumenReserva() {
		
	}
	public Parqueadero getParqueadero() {
		return parqueadero;
	}
	public void setParqueadero(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
	}
	public Reserva getNuevaReserva() {
		return nuevaReserva;
	}
	public void setNuevaReserva(Reserva nuevaReserva) {
		this.nuevaReserva = nuevaReserva;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public Reserva getReservaActual() {
		return reservaActual;
	}

	public void setReservaActual(Reserva reservaActual) {
		this.reservaActual = reservaActual;
	}

	public List<Reserva> getListaFiltrada() {
		return listaFiltrada;
	}

	public void setListaFiltrada(List<Reserva> listaFiltrada) {
		this.listaFiltrada = listaFiltrada;
	}
	
	
	
	
	

}
