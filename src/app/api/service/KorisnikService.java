package app.api.service;

import javax.ws.rs.core.Response;
import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import app.api.dto.LoginResponseDto;
import app.api.dto.ResponseDto;
import app.db.controllers.FotografijaDbController;
import app.db.controllers.KorisnikDbController;
import app.db.entities.FotografijaDetails;
import app.db.entities.Korisnik;
import app.db.entities.KorisnikReport;
import app.utils.Const;

public class KorisnikService extends ServiceAbstract<Korisnik, KorisnikDbController> {
	public KorisnikService(KorisnikDbController dbController) {
		super(dbController);
	}

	public LoginResponseDto Login(String username, String password) {
		return dbController.Login(username, password);
	}

	public Response register(Korisnik korisnik) {
		korisnik.setAktivan(false);
		korisnik.setUloga(0);

		if (this.dbController.checkForUsername(korisnik)) {
			if (this.dbController.add(korisnik) != -1) {
				sendActivationMail(korisnik);
				return Response.ok().build();
			} else {
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
		}
		return Response.ok().entity(new ResponseDto(null, Const.USERNAME_EXISTIS)).build();
	}

	private boolean sendActivationMail(Korisnik korisnik) {
		System.out.println("Usao u sendActivationMail()");
		try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
        
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
          

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("loncarj23@gmail.com", "jjloncar1");
                }
            });

            MimeMessage message = new MimeMessage(session);

            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(korisnik.getEmail()));
            message.setSubject("Prodaja Fotografija Aktivacioni MAil");
            message.setText("Molim Vas kliknite na link da bi ste aktivirali svoj nalog: "
            		+ "http://localhost:8080/ProdajaFotografija/rest/korisnik/activate/" + korisnik.getUsername());

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public Response activate(String username) {
		if(this.dbController.activateUserById(username)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response getSellers() {
		List<Korisnik> result = this.dbController.getSellers();
		if(result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response getUsersForTest() {
		List<Korisnik> result = this.dbController.getUsersForTest();
		if(result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response userBecomeSeller(int id) {
		FotografijaDbController fotografijaDbController = new FotografijaDbController();
		List<FotografijaDetails> photosToDelete = fotografijaDbController.getPhotosFromBuyer(id);
		List<Integer> photoIds = new ArrayList<Integer>();
		for (FotografijaDetails fotografijaDetails : photosToDelete) {
			photoIds.add((int)fotografijaDetails.getId());
			File f = new File(fotografijaDetails.getPutanja());
			if (!f.delete()) {
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
		}
		
		if (!fotografijaDbController.removePhotos(photoIds)) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		
		if(this.dbController.userBecomeSeller(id)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response userFailTest(int id) {
		FotografijaDbController fotografijaDbController = new FotografijaDbController();
		List<FotografijaDetails> photosToDelete = fotografijaDbController.getPhotosFromBuyer(id);
		List<Integer> photoIds = new ArrayList<Integer>();
		for (FotografijaDetails fotografijaDetails : photosToDelete) {
			photoIds.add((int)fotografijaDetails.getId());
			File f = new File(fotografijaDetails.getPutanja());
			if (!f.delete()) {
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
		}
		
		if (!fotografijaDbController.removePhotos(photoIds)) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		
		return Response.ok().build();
	}
	
	public Response getSellersOfBougthPhotos(int userId) {
		List<Integer> result = this.dbController.getSellersOfBougthPhotos(userId);
		if(result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response getReportForUser(int userId) {
		List<KorisnikReport> result = this.dbController.getReportForUser(userId);
		if(result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response getUsersBasedOnRole(int role) {
		List<Korisnik> result = this.dbController.getUsersBasedOnRole(role);
		if(result != null) {
			return Response.ok(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response deactivate(int id) {
		if(this.dbController.deactivate(id)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response changePassword(Korisnik korisnik) {
		if(this.dbController.changePassword(korisnik)) {
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	public Response resetPassword(String username) {
		String email = this.dbController.resetPassword(username);
		if(email != null && !email.isEmpty()) {
			sendResetPasswordMail(email, username);
			return Response.ok().build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}
	
	private boolean sendResetPasswordMail(String email, String username) {
		try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("loncarj23@gmail.com", "jjloncar1");
                }
            });

            MimeMessage message = new MimeMessage(session);

            message.addRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Resetovanje passworda Prodaja Fotografija");
            message.setText("Vas novi password je: " + username);

            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
	}
}
