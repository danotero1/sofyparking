package modelo;

import org.hibernate.*;

import entidades.*;

import java.io.*;
import java.util.*;

public abstract class AbstractModel <T>{

	private Class<T> entity;
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public AbstractModel (Class<T> entity){
		this.entity = entity;
	}
	
	public Tarifa obtenerTarifaParqueadero(int idTarifa) {
		Tarifa tarifa = null;
		Session session = sessionFactory.openSession();
		try {
			System.out.println("En el qury"+" "+entity.getName());
			
			String hql = "from "+entity.getName()+" WHERE id = '"+idTarifa+"'";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				System.out.println("En el if");
				
				tarifa = (Tarifa) query.list().get(0);
				
			}
		}finally {
			session.close();
		}
		return tarifa;
	}
	public List<T> historialUsuario(String correo){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()+" WHERE correoUsuario = '"+correo+"'").list();
			//result.get(result.size()-1);
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public Movimiento obtenerMovimientoPorCorreo(String correo) {
		Movimiento movimiento = null;
		Session session = sessionFactory.openSession();
		try {
			
			String hql = "from "+entity.getName()+" WHERE correoUsuario = '"+correo+"' and activo = 'A'";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				System.out.println("En el if");
				
				movimiento = (Movimiento) query.list().get(query.list().size()-1);
				
			}
		}finally {
			session.close();
		}
		return movimiento;
	}
	public Tarifa encontrarTarifaPorId(int id,String tabla){
		Tarifa result = null;
		System.out.println("tablas "+tabla+" id:"+id);
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//SELECT DISTINCT last_name FROM usuarios
			String sqlQuery ="SELECT FROM "+tabla+" WHERE id ='"+id+"'";
			result = (Tarifa)session.createSQLQuery(sqlQuery);
			System.out.println(result);
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public List<T> ReservasActivasDelUsuario(String correo){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()+" WHERE correoUsuario = '"+correo+"' AND activo = 'A'").list();
			//result.get(result.size()-1);
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public Usuario validateMail(String mail) {
		Boolean result = false;
		Usuario usuario = null;
		Session session = sessionFactory.openSession();
		try {
			System.out.println("En el qury");
			String hql = "from "+entity.getName()+" WHERE correo = '"+mail+"' ";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				
				usuario = (Usuario) query.list().get(0);
//				if(usuario.getCorreo()!=null) {
//					System.out.println("En el if");
//					result=true;
//				}
				
			}
		}finally {
			session.close();
		}
		return usuario;
	}
	public Reserva obtenerReservaPorCorreo(String correo) {
		Reserva reserva = null;
		Session session = sessionFactory.openSession();
		try {
			System.out.println("En el qury"+" "+entity.getName());
			
			String hql = "from "+entity.getName()+" WHERE correoUsuario = '"+correo+"'";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				System.out.println("En el if");
				
				reserva = (Reserva) query.list().get(query.list().size()-1);
				
			}
		}finally {
			session.close();
		}
		return reserva;
	}
	public List<T> encontrarParqueaderoPorCiudad(String ciudad,String tipo){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()+" WHERE ciudad = '"+ciudad+"' AND tipo = '"+tipo+"'").list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public List<T> obtenerVehiculosporUsuario(String correo){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()+" WHERE correoUsuario = '"+correo+"'").list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public Usuario validateClave(String clave) {
		Boolean result = false;
		Usuario usuario = null;
		Session session = sessionFactory.openSession();
		try {
			System.out.println("En el qury");
			String hql = "from "+entity.getName()+" WHERE correo = '"+clave+"' ";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				
				usuario = (Usuario) query.list().get(0);
				
				
			}
		}finally {
			session.close();
		}
		return usuario;
	}
	public List<T> ReservasActivasDelUsuario(int idUsuario){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()+" WHERE idUsuario = '"+idUsuario+"' AND activo = 'A'").list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
		
	}
	/**
	 * trae valores sin repetir
	 * @param columna
	 * @return
	 */
	public List<String> encontrarObjetosDeColumnaString(String columna,String tabla){
		List<String> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//SELECT DISTINCT last_name FROM usuarios
			String sqlQuery ="SELECT DISTINCT "+columna+" FROM "+tabla+"";
			result = session.createSQLQuery(sqlQuery).list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	
	public int traerId(String des, String columna,String tabla) {
		int result = 0;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//SELECT DISTINCT last_name FROM usuarios
			String sqlQuery ="SELECT DISTINCT "+columna+" FROM "+tabla+" WHERE descripcionTipoCobro = '"+ des+"'";
			
			result = (Integer) session.createSQLQuery(sqlQuery).list().get(0);
			System.out.println("esta es la respuestaa:  "+  result );
			transaction.commit();
		} catch (Exception e) {
			result = 0;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		
		
		return result;
	}
	public Usuario validate(String mail,String password) {
		String result = null;
		Usuario usuario = null;
		Session session = sessionFactory.openSession();
		try {
			System.out.println("En el qury");
			String hql = "from "+entity.getName()+" WHERE correo = '"+mail+"' and clave = '"+password+"'";
			Query query = session.createQuery(hql);
			if(!query.list().isEmpty()) {
				System.out.println("En el if");
				usuario = (Usuario) query.list().get(0);
				result = usuario.getLogin();
				
			}
		}finally {
			session.close();
		}
		return usuario;
	}
	public List<String> encontrarVehiculoDelUsuarioString(String usuario,String tabla){
		List<String> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//SELECT DISTINCT last_name FROM usuarios
			String sqlQuery ="SELECT placa FROM "+tabla+" WHERE correoUsuario  = '"+usuario+"'";
			result = session.createSQLQuery(sqlQuery).list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public List<T> findUser(){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "from " + entity.getName()+" WHERE tipoUsuario= 'normal'";
			Query query = session.createQuery(hql);
			//result = session.createQuery("from " + entity.getName()+"WHERE tipoUsuario= 'ADMIN'").list();
			result = query.list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public List<T> findAdmin(){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			String hql = "from " + entity.getName()+" WHERE tipoUsuario= 'ADMIN'";
			Query query = session.createQuery(hql);
			//result = session.createQuery("from " + entity.getName()+"WHERE tipoUsuario= 'ADMIN'").list();
			result = query.list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	public List<T> findAll(){
		List<T> result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = session.createQuery("from " + entity.getName()).list();
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	
	public T find(Object id){
		T result = null;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			result = (T) session.get(entity, (Serializable) id);
			transaction.commit();
		} catch (Exception e) {
			result = null;
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
		return result;
	}
	


	
	public void create(T entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch (Exception e) {
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
	}
	
	public void update(T entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} catch (Exception e) {
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
	}
	
	public void delete(T entity) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		} catch (Exception e) {
			
			if(transaction!=null) {
				transaction.rollback();
			}
		}finally {
			session.close();
		}
	}
	
}
