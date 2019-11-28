package entidades;
// Generated 16/11/2019 11:28:39 AM by Hibernate Tools 5.0.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Parqueadero generated by hbm2java
 */
@Entity
@Table(name = "parqueadero", catalog = "BtB9DLrhnQ")
public class Parqueadero implements java.io.Serializable {
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)*/
	private Integer id;
	private int idEmpresa;
	private String nombreParqueadero;
	private String ciudad;
	private String correo;
	private int disponibilidad;
	private String servicio;
	private String fidelizacion;
	private String estado;
	private String tipo;
	private int idTarifa;

	public Parqueadero() {
	}

	public Parqueadero( int idEmpresa, String nombreParqueadero, String ciudad, String correo,
			int disponibilidad, String servicio, String fidelizacion, String estado,String tipo,int idTarifa) {
		//this.id = id;
		this.idEmpresa = idEmpresa;
		this.nombreParqueadero = nombreParqueadero;
		this.ciudad = ciudad;
		this.correo = correo;
		this.disponibilidad = disponibilidad;
		this.servicio = servicio;
		this.fidelizacion = fidelizacion;
		this.estado = estado;
		this.idTarifa = idTarifa;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "idEmpresa", nullable = false)
	public int getIdEmpresa() {
		return this.idEmpresa;
	}

	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	@Column(name = "nombreParqueadero", nullable = false, length = 30)
	public String getNombreParqueadero() {
		return this.nombreParqueadero;
	}

	public void setNombreParqueadero(String nombreParqueadero) {
		this.nombreParqueadero = nombreParqueadero;
	}

	@Column(name = "ciudad", nullable = false, length = 30)
	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	@Column(name = "correo", nullable = false, length = 45)
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	@Column(name = "disponibilidad", nullable = false)
	public int getDisponibilidad() {
		return this.disponibilidad;
	}

	public void setDisponibilidad(int disponibilidad) {
		this.disponibilidad = disponibilidad;
	}

	@Column(name = "servicio", nullable = false, length = 45)
	public String getServicio() {
		return this.servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	@Column(name = "fidelizacion", nullable = false, length = 1)
	public String getFidelizacion() {
		return this.fidelizacion;
	}

	public void setFidelizacion(String fidelizacion) {
		this.fidelizacion = fidelizacion;
	}

	@Column(name = "estado", nullable = false, length = 1)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Column(name = "tipo", nullable = false, length = 30)
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Column(name = "idTarifa", nullable = false)
	public int getIdTarifa() {
		return idTarifa;
	}

	public void setIdTarifa(int idTarifa) {
		this.idTarifa = idTarifa;
	}
	
	

}