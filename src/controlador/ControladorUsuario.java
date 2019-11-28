package controlador;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.digest.DigestUtils;

import modelo.*;
import entidades.*;


@ManagedBean(name="ControladorUsuario")
@SessionScoped
public class ControladorUsuario {
	
	private UsuarioModelo usuario = new UsuarioModelo();
	private Usuario nuevoUsuario = new Usuario();
	private Usuario usuIniciaSesion = new Usuario();
	private Usuario usu = new Usuario();
	
	
	public String add() {
		
		String clave = "";
		String letras = "010123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ?/.,";
		 Random rnd = new Random();
		for (int i = 0; i < 8; i++) {
			clave+= letras.charAt(rnd.nextInt(letras.length()));
		}
		System.out.println(clave);
		nuevoUsuario.setLogin("normal");
		nuevoUsuario.setPassword(DigestUtils.md5Hex(clave));
		nuevoUsuario.setApellidosNombres("Cliente");
		nuevoUsuario.setFechaPassword(new Date());
		nuevoUsuario.setActivo("S");
		nuevoUsuario.setIntentos(3);
		nuevoUsuario.setTipoUsuario("normal");
		nuevoUsuario.setFechaUltimoPassword(new Date());
		nuevoUsuario.setIntentosPassword(0);
		
		nuevoUsuario.setClave(DigestUtils.md5Hex(clave));
		
		usuIniciaSesion=this.usuario.validateMail(this.nuevoUsuario.getCorreo());
		if(null==usuIniciaSesion) {
		 try {
		        String gRecaptchaResponse = FacesContext.getCurrentInstance().
		        getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
		        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
		        if(verify){
		        	this.usuario.create(this.nuevoUsuario);
		        	 String destinatario =  this.nuevoUsuario.getCorreo(); //A quien le quieres escribir.
		     	    String asunto = "Bienvenido a Sofy-Parking";
		     	    String cuerpo = "Te damos la bienvenida Sofy-Parking "+this.nuevoUsuario.getNombre()+"\n"
		     	    		+ "Tus datos son los siguientes: \n"
		     	    		+"Usuario: "+this.nuevoUsuario.getCorreo()
		     	    +"\nContraseña: "+clave
		     	    +"\nDisfruta de tu aplicacion";

		     	    enviarConGMail(destinatario, asunto, cuerpo);
		    		this.nuevoUsuario = new Usuario();
		    		this.nuevoUsuario.setCorreo("");
		    		FacesContext.getCurrentInstance().addMessage("Successful", new FacesMessage("Successful", "Registrado con exito"));
		             return "index";
		        }else{
//		        	FacesContext context = FacesContext.getCurrentInstance();
//		             context.addMessage( null, new FacesMessage( "Select Captcha") );
		        	
		        	
		             FacesContext.getCurrentInstance().addMessage("form:g-recaptcha", 
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Seleccionar Captcha","g-recaptcha"));	
			        

					
		             return null;
		          }
		         } catch (Exception e) {
		             System.out.println(e);
		         }finally {
					FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				}
		        
		}else {
			FacesContext.getCurrentInstance().addMessage("form:mensaje", 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo ya se encuentra registrado","mensaje"));	
			
			/*FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage( null, new FacesMessage( "El correo ya se encuentra registrado") );
			System.out.println("El correo ya se encuentra registrado");*/
			return null;
		}
		/*this.usuario.create(this.nuevoUsuario);
		this.nuevoUsuario = new Usuario();
		return "index";*/
		return null;
	}
	
	public String iniciarSesion() {
		System.out.println("iniciado sesion");
		String resultado="";
		
		//System.out.println("aqui "+usuIniciaSesion.getLogin());
		//String rta = this.usuario.validate(this.nuevoUsuario.getCorreo(), this.nuevoUsuario.getPassword());
		usuIniciaSesion=this.usuario.validateMail(this.nuevoUsuario.getCorreo());
		if(null!=usuIniciaSesion) {
			usu = usuIniciaSesion;
			System.out.println("el correo existe");
			usuIniciaSesion =this.usuario.validate(this.nuevoUsuario.getCorreo(), DigestUtils.md5Hex(this.nuevoUsuario.getClave()));
			if(null!=usuIniciaSesion) {
				if(!usuIniciaSesion.getActivo().equalsIgnoreCase("b")) {
					usuIniciaSesion.setIntentosPassword(0);
					this.usuario.update(usuIniciaSesion);
					
					if(this.usuIniciaSesion.getLogin().equalsIgnoreCase("admin")) {
						resultado = "InterfazSuper";
					}else {
						resultado = "InterfazUsuario";
					}
					
				}else {
					FacesContext.getCurrentInstance().addMessage("form:txtClave", 
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu cuenta esta bloqueada, contactar al Administrador","mensaje"));	
					resultado = null;
				}
			}else {
//				usuIniciaSesion.setIntentosPassword(usuIniciaSesion.getIntentosPassword()+1);
				System.out.println(usu.getIntentosPassword());
				usu.setIntentosPassword(usu.getIntentosPassword()+1);
				this.usuario.update(usu);
				if(usu.getIntentosPassword()<3 && !usu.getActivo().equalsIgnoreCase("b")) {
				FacesContext.getCurrentInstance().addMessage("form:txtClave", 
						new FacesMessage(FacesMessage.SEVERITY_INFO, "la clave es incorrecta te quedan " +(3-usu.getIntentosPassword())+"  intentos, MAX 3 INTENTOS","mensaje"));	
				resultado = null;
				}else {
					usu.setActivo("B");
					usu.setIntentosPassword(0);
					this.usuario.update(usu);
					FacesContext.getCurrentInstance().addMessage("form:txtClave", 
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu cuenta ha sido bloqueada, Maximo intentos de contraña","mensaje"));	
					
					String destinatario =  this.usu.getCorreo(); //A quien le quieres escribir.
			     	    String asunto = "Cuenta de Sofy-Parking Bloqueada";
			     	    String cuerpo = "Tu cuenta ha sido bloqueada \n"
			     	    		+ "Ponte en contacto con el administrador del sistema para desbloquear la misma.\nPuedes comunicartea este correo";
			     	   enviarConGMail(destinatario, asunto, cuerpo);
					resultado = null;
				}
				
			}
			
		}else {
			FacesContext.getCurrentInstance().addMessage("form:txtCorreo", 
					new FacesMessage(FacesMessage.SEVERITY_INFO, "El correo no se encuentra registrado","mensaje"));	
			resultado = null;
		}
		this.nuevoUsuario.setCorreo("");
		return resultado;
		/*usuIniciaSesion = this.usuario.validate(this.nuevoUsuario.getCorreo(), DigestUtils.md5Hex(this.nuevoUsuario.getClave()));
		this.nuevoUsuario = new Usuario();
		System.out.println("aqui"+usuIniciaSesion);

		//System.out.println(rta);
		if(usuIniciaSesion!=null && usuIniciaSesion.getLogin().equalsIgnoreCase("normal")) {
			
		
			System.out.println("normal");
			//Editar Usuario Normal
			
			System.out.println(usuIniciaSesion.getNombre());
			resultado = "InterfazUsuario";
		}else if(usuIniciaSesion!=null && usuIniciaSesion.getLogin().equalsIgnoreCase("admin") ) {
			System.out.println("admin");
			//Editar Usuario Admin
			resultado = "InterfazUsuario";
		}else if(usuIniciaSesion==null) {
			
			resultado="IniciarSesion";
		}
		System.out.println(resultado);
		return resultado;*/
	}
	
	private void enviarConGMail(String destinatario, String asunto, String cuerpo) {
	    // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
	    	String remitente = "sofyparking@gmail.com";  //Para la dirección nomcuenta@gmail.com

	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
	    props.put("mail.smtp.user", remitente);
	    props.put("mail.smtp.clave", "sofyparking.1");    //La clave de la cuenta
	    props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
	    props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
	    props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);

	    try {
	        message.setFrom(new InternetAddress(remitente));
	        message.addRecipients(Message.RecipientType.TO, destinatario);   //Se podrían añadir varios de la misma manera
	        message.setSubject(asunto);
	        message.setText(cuerpo);
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", remitente, "sofyparking.1");
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
	    }
	    catch (MessagingException me) {
	        me.printStackTrace();   //Si se produce un error
	    }
	}
	

	
	public String edit(Usuario u) {
		System.out.println("en editar");
		
		this.usuIniciaSesion = u;
		return "Usuarios/EditarPerfilUsuario";
		
	}
	public String editUsu(Usuario s) {
		this.nuevoUsuario = s;
		System.out.println("entro");
		return "EditarUsuario";
	}
	public String edit() {
		usuIniciaSesion.setClave(DigestUtils.md5Hex(this.usuIniciaSesion.getClave()));
		this.usuario.update(this.usuIniciaSesion);;
		return "InterfazUsuario";
		
	}
	
	public void delete(Usuario u) {
		this.usuario.delete(u);
		
	}
	
	public List<Usuario> findAll(){
		return usuario.findAll();
	}
	public List<Usuario> findAdmin(){
		return usuario.findAdmin();
	}
	public List<Usuario> findUser(){
		return usuario.findUser();
	}
	
	public Usuario getNuevoUsuario() {
		return nuevoUsuario;
	}


	public void setNuevoUsuario(Usuario nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public UsuarioModelo getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioModelo usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuIniciaSesion() {
		return usuIniciaSesion;
	}

	public void setUsuIniciaSesion(Usuario usuIniciaSesion) {
		this.usuIniciaSesion = usuIniciaSesion;
	}



}
