package controlador;
import java.util.*;

import javax.faces.bean.*;

import modelo.*;
import entidades.*;


@ManagedBean(name="ControladorParqueadero")
@SessionScoped
public class ControladorParqueadero {
	
		private ParqueaderoModelo parqueadero = new ParqueaderoModelo();
		private Parqueadero  nuevoParqueadero  = new Parqueadero();
		private Parqueadero parqueaderoActual = new Parqueadero();
		private String tarifa = "";
		private int idTarifa = 0;
		public String getTarifa() {
			return tarifa;
		}
		public void setTarifa(String tarifa) {
			this.tarifa = tarifa;
		}
		
		public int convertirTarifa() {
			
			idTarifa = (this.parqueadero.traerId(this.tarifa,"id","tarifa"));
			
			return idTarifa;
		}
		public String add() {
			
			this.nuevoParqueadero.setIdEmpresa(0);
			//this.nuevoParqueadero.setNombreParqueadero("sd");
			//this.nuevoParqueadero.setCiudad("sd");
			//this.nuevoParqueadero.setCorreo("sds");
			//this.nuevoParqueadero.setDisponibilidad(0);
			//this.nuevoParqueadero.setServicio("ds");
			//this.nuevoParqueadero.setFidelizacion("s");
			//this.nuevoParqueadero.setEstado("s");
			//this.nuevoParqueadero.setTipo("s");
			//this.nuevoParqueadero.setIdTarifa(0);
			
			
			convertirTarifa();
			
			this.nuevoParqueadero.setIdTarifa(this.idTarifa);
			System.out.println(this.nuevoParqueadero.getIdTarifa());
			this.parqueadero.create(this.nuevoParqueadero);
			this.nuevoParqueadero = new Parqueadero();
			return "Parqueaderos.xhtml";
		}
		public String edit(Parqueadero p) {
			this.nuevoParqueadero = p;
			System.out.println("entro");
			return "EditarParqueadero";
			
		}
		
		public String edit() {
			this.parqueadero.update(this.nuevoParqueadero);
			return "index";
			
		}
		public String filtrarParqueadero(){
			System.out.println(this.nuevoParqueadero.getCiudad());
			System.out.println(this.nuevoParqueadero.getTipo());
			parqueaderoActual.setCiudad(this.nuevoParqueadero.getCiudad());
			parqueaderoActual.setTipo(this.nuevoParqueadero.getTipo());
			return "ConsultaParqueaderos";
		}
		public List<String> ciudadesParqueaderos(){
			String columna = "ciudad";
			String tabla = "parqueadero";
			return parqueadero.encontrarObjetosDeColumnaString(columna,tabla);
		}
		public List<String> tarifasParqueaderos(){
			String columna = "descripcionTipoCobro";
			String tabla = "tarifa";
			return parqueadero.encontrarObjetosDeColumnaString(columna,tabla);
		}
		
		public void delete(Parqueadero p) {
			this.parqueadero.delete(p);
		}
		public List<Parqueadero> findAll(){
			return parqueadero.findAll();
		}
		public List<Parqueadero> encontarPorCiudad(){
			return parqueadero.encontrarParqueaderoPorCiudad(this.parqueaderoActual.getCiudad(),this.parqueaderoActual.getTipo());
		}
		
		public ParqueaderoModelo getParqueadero() {
			return parqueadero;
		}
		public void setParqueadero(ParqueaderoModelo parqueadero) {
			this.parqueadero = parqueadero;
		}
		public Parqueadero getNuevoParqueadero() {
			return nuevoParqueadero;
		}
		public void setNuevoParquedero(Parqueadero nuevoParqueadero) {
			this.nuevoParqueadero = nuevoParqueadero;
		}
		
		
		
		

}
