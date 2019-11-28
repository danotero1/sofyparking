package controlador;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
@ManagedBean(name="Rutas")
@SessionScoped
public class Rutas {

	public String historial() {
		return "/Reservas/HistorialReservas.xhtml";
	}
	public String historialReservaUsuario() {
		return "/Reservas/HistorialReservasUsuario.xhtml";
	}
	public String historialMovimientosUsuario() {
		return "/Movimientos/HistorialMovimientosUsuario.xhtml";
	}
	public String parqueadero() {
		System.out.println("entro");
		return "/Parqueaderos/Parqueaderos.xhtml";
	}
	public String usuarios() {
		return "/Usuarios/Usuarios.xhtml";
		
	}
	public String tarifas() {
		return "/Tarifas/Tarifas.xhtml";
	}
	public String salir() {
		return "/index.xhtml";
	}
	public String vehiculos() {
		return "/Vehiculos/VehiculosUsuarios.xhtml";
	}
	public String usuario() {
		return "/Usuarios/Usuarios.xhtml";
	}
	public String usuAdmin() {
		return "/Usuarios/UsuariosAdmin.xhtml";
	}
	public String reserva() {
		return "/Reservas/Reservas.xhtml";
	}
	public String inicioUsuario() {
		return "InterfazUsuario.xhtml";
	}
	public String iniciar() {
		return "IniciarSesion.xhtml";
	}
}
