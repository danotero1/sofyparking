package controlador;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.PrimeFaces;

import entidades.Usuario;
import entidades.Vehiculo;
import modelo.VehiculoModelo;

@ManagedBean(name="ControladorVehiculos")
@SessionScoped
public class ControladorVehiculos {
	
	private VehiculoModelo vehiculo = new VehiculoModelo();
	private Vehiculo nuevoVehiculo = new Vehiculo();
	private Vehiculo vehiculoActual = new Vehiculo();
	
	public String add(Usuario usuario) {
		System.out.println("en agregar");
		this.nuevoVehiculo.setEstado("a");
		this.nuevoVehiculo.setCorreoUsuario(usuario.getCorreo());
		this.vehiculo.create(this.nuevoVehiculo);
		this.nuevoVehiculo = new Vehiculo();
		return "VehiculosUsuarios";
	}
	
	public String edit(Vehiculo v) {
		this.nuevoVehiculo = v;
		System.out.println("entro");
		return "EditarVehiculo";
	}
	public String edit() {
		this.vehiculo.update(this.nuevoVehiculo);
		return "/InterfazUsuario.xhtml";
		
	}
	public List<Vehiculo> vehiculosDelUsuario(String correoUsuario){
		return this.vehiculo.obtenerVehiculosporUsuario(correoUsuario);
	}
	public List<String> vehiculosUsuarioString(Usuario usuario){
		String tabla = "vehiculo";
		return this.vehiculo.encontrarVehiculoDelUsuarioString(usuario.getCorreo(), tabla);
	}
	public void openDialog() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgChooseCar').show();");
	}
	

	
	public void delete (Vehiculo v) {
		this.vehiculo.delete(v);
	}
	
	public List<Vehiculo> findAll(){
		System.out.println("llenando tabla vehiculo");
		return vehiculo.findAll();
	}

	public VehiculoModelo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(VehiculoModelo vehiculo) {
		this.vehiculo = vehiculo;
	}

	public Vehiculo getNuevoVehiculo() {
		return nuevoVehiculo;
	}

	public void setNuevoVehiculo(Vehiculo nuevoVehiculo) {
		this.nuevoVehiculo = nuevoVehiculo;
	}

	public Vehiculo getVehiculoActual() {
		return vehiculoActual;
	}

	public void setVehiculoActual(Vehiculo vehiculoActual) {
		this.vehiculoActual = vehiculoActual;
	}


}
