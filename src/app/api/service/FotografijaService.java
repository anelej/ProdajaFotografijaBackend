package app.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import sun.misc.BASE64Decoder;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.Response;

import app.api.dto.FotografijaBuyDto;
import app.api.dto.FotografijaDto;
import app.api.dto.FotografijaRateDto;
import app.api.dto.FotografijaSaveDto;
import app.api.dto.FotografijaSearchDto;
import app.api.dto.ResponseDto;
import app.db.controllers.FotografijaDbController;
import app.db.controllers.FotografijaKatalogDbController;
import app.db.controllers.FotografijaKeywordDbController;
import app.db.controllers.IstorijaKupovineDbController;
import app.db.controllers.KorisnikDbController;
import app.db.entities.Fotografija;
import app.db.entities.FotografijaDetails;
import app.db.entities.FotografijaKatalog;
import app.db.entities.FotografijaKeyword;
import app.db.entities.IstorijaKupovine;
import app.db.entities.Korisnik;
import app.utils.Const;
import app.utils.Utils;

public class FotografijaService extends ServiceAbstract<Fotografija, FotografijaDbController> {
	public FotografijaService(FotografijaDbController dbController) {
		super(dbController);
	}

	public Response ratePhoto(FotografijaRateDto photo) {
		int id = dbController.checkIfRateExists(photo);
		if (id == -1) {
			if (dbController.ratePhoto(photo)) {
				return Response.ok().build();
			}
			return Response.ok(new ResponseDto(null, Const.SERVER_ERROR)).build();
				
		} else if (id > 0){
			if(dbController.updateRatePhoto(photo, id)) {
				return Response.ok().build();
			}
			return Response.ok(new ResponseDto(null, Const.SERVER_ERROR)).build();
		} 
		return Response.ok(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}

	public Response approvePhoto(int id) {
		if (dbController.approvePhoto(id)) {
			return Response.ok().build();
		}
		return Response.ok(new ResponseDto(null, Const.SERVER_ERROR)).build();
	}

	public Response searchPhoto(FotografijaSearchDto fotografija) {
		List<FotografijaDetails> list = this.dbController.searchPhoto(fotografija);
		List<FotografijaDto> result = new ArrayList<FotografijaDto>();
		if (list != null && list.size() > 0) {
			for (FotografijaDetails foto : list) {
				FotografijaDto fotoDto = new FotografijaDto(foto);

				fotoDto.setSrc(imageToBase64(foto.getPutanja()));
				result.add(fotoDto);
			}
		}
		return Response.ok(result).build();
	}

	public Response getFotografijaById(long id, int korisnikId) {
		FotografijaDetails photo = dbController.getFotografijaDetails((int) id, korisnikId);
		if (photo != null) {
			FotografijaDto result = new FotografijaDto(photo);
			result.setSrc(imageToBase64(photo.getPutanja()));
			return Response.ok().entity(result).build();
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
	}

	public Response addPhotoWithValidation(FotografijaSaveDto object) {
		Response r = addPhotoValidation(object);
		if (r != null) {
			return r;
		}
		return add(object);
	}
	
	private Response addPhotoValidation(FotografijaSaveDto object) {
		long numberOfPhotosAddedToday = dbController.getNumberOfPhotosAddedTodayForUser(object.getProdavacId());
		if (numberOfPhotosAddedToday > 3) {
			return Response.ok().entity(new ResponseDto(null, Const.MAX_NUM_OF_PHOTOS_FOR_ONE_DAY_ERROR)).build();
		} else if (numberOfPhotosAddedToday == -1) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		
		long numberOfPhotosAddedThisWeek = dbController.getNumberOfPhotosAddedThisWeekForUser(object.getProdavacId());
		if (numberOfPhotosAddedThisWeek > 8) {
			return Response.ok().entity(new ResponseDto(null, Const.MAX_NUM_OF_PHOTOS_FOR_WEEK_ERROR)).build();
		}else if (numberOfPhotosAddedThisWeek == -1) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		return null;
	}
	
	public Response add(FotografijaSaveDto object) {
		String path = saveImage(object.getSrc(), object.getNaziv(), object.getProdavacId());
		if (path != null && !path.isEmpty()) {
			object.setPutanja(path);
			Fotografija f = new Fotografija(object);
			f.setOdobrena(false);
			long fotografijaId = this.dbController.add(f);

			FotografijaKatalogDbController photoCatalogController = new FotografijaKatalogDbController();
			FotografijaKeywordDbController photoKeywordController = new FotografijaKeywordDbController();
			if (fotografijaId != -1) {
				// sacuvaj rezolucije i cene
				if (object.getListaRezolucija() != null) {
					for (FotografijaKatalog photoCatalog : object.getListaRezolucija()) {
						photoCatalog.setFotografijaId(fotografijaId);
						if (photoCatalogController.add(photoCatalog) == -1) {
							return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
						}
					}
				}
				
				// sacuvaj keywords
				if (object.getKeywords() != null) {
					for (int i = 0; i < object.getKeywords().split(",").length; i++) {
						FotografijaKeyword photoKeyword = new FotografijaKeyword();
						photoKeyword.setFotografjaId(fotografijaId);
						photoKeyword.setKeyword(object.getKeywords().split(",")[i]);
						if (photoKeywordController.add(photoKeyword) == -1) {
							return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
						}
					}
				}
				return Response.ok().build();
			} else {
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
		} else {
			return Response.ok().entity(new ResponseDto(null, Const.SAVE_IMAGE_ERROR)).build();
		}
	}

	private String saveImage(String base64String, String name, long prodavacId) {

		// base64String =
		// 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAPAAAADwCAYAAAA+VemSAAAgAEl...==';
		String imageString = base64String.split(",")[1];
		String ext = base64String.split(",")[0].split("data:image/")[1].split(";")[0];

		BufferedImage image = null;
		byte[] imageByte;

		try {
			BASE64Decoder decoder = new BASE64Decoder();
			imageByte = decoder.decodeBuffer(imageString);
			ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
			image = ImageIO.read(bis);
			bis.close();
			String path = "D:\\FotografijeZaProdaju\\" + name + prodavacId + ".png";

			File outputfile = new File(path);
			if (!outputfile.createNewFile()) {
				return null;
			}
			ImageIO.write(image, ext, outputfile);
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String imageToBase64(String imagePath) {
		String encodedfile = null;
		try {
			File file = new File(imagePath);
			FileInputStream fileInputStreamReader = new FileInputStream(file);
			byte[] bytes = new byte[(int) file.length()];
			fileInputStreamReader.read(bytes);
			encodedfile = Base64.getEncoder().encodeToString(bytes).toString();
			fileInputStreamReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return encodedfile;
	}

	public Response buy(List<FotografijaBuyDto> object) {
		IstorijaKupovineDbController istorijaKupovineDbController = new IstorijaKupovineDbController();
		KorisnikDbController korisnikDbController = new KorisnikDbController();
		FotografijaDbController fotografijaController = new FotografijaDbController();

		Korisnik kupac = korisnikDbController.getById(object.get(0).getKorisnikId());
		for (FotografijaBuyDto fotografijaBuy : object) {
			IstorijaKupovine istorija = new IstorijaKupovine();
			istorija.setDatumKupovine(new Date());
			istorija.setFotografijaKatalogId(fotografijaBuy.getResolution().getId());
			istorija.setKorisnikId(fotografijaBuy.getKorisnikId());
			istorijaKupovineDbController.add(istorija);

			Fotografija photo = fotografijaController.getById(fotografijaBuy.getPhoto().getId());
			int width = Integer.parseInt(fotografijaBuy.getResolution().getRezolucija().split("x")[0]);
			int height = Integer.parseInt(fotografijaBuy.getResolution().getRezolucija().split("x")[1]);
			String outputimagePath = "D:\\FotografijeZaProdaju\\Scaled\\" + fotografijaBuy.getKorisnikId()
					+ fotografijaBuy.getPhoto().getProdavacId() + ".png";
			try {
				Utils.resize(photo.getPutanja(), outputimagePath, width, height);
			} catch (IOException e) {
				e.printStackTrace();
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
			Korisnik prodavac = korisnikDbController.getById(fotografijaBuy.getPhoto().getProdavacId());

			if (kupac == null || prodavac == null || photo == null) {
				return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
			}
			sendMail("Kupljena fotografija", "Postovani u prilogu Vam se nalazi kupljena fotografija", kupac.getEmail(),
					outputimagePath);
			sendMail("Kupljena fotografija",
					"Postovani kupljena je fotografija " + fotografijaBuy.getPhoto().getNaziv(), prodavac.getEmail(),
					null);

		}
		return Response.ok().build();
	}

	private boolean sendMail(String title, String body, String mailTo, String filePath) {
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

			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject(title);
			message.setText(body);

			if (filePath != null && !filePath.isEmpty()) {
				MimeBodyPart messageBodyPart = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();

				messageBodyPart = new MimeBodyPart();
				String fileName = "download.png";
				DataSource source = new FileDataSource(filePath);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);
				message.setContent(multipart);
			}

			Transport.send(message);

			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public Response removeById(long id) {
		Fotografija photo = dbController.getById(id);
		if (photo == null) {
			return Response.ok().entity(new ResponseDto(null, "Fotografija ne postoji u bazi")).build(); 
		}
		File file = new File(photo.getPutanja());
		if (file.delete()) {
			return super.removeById(id);
		}
		return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build(); 
	}
	
	public Response getPhotosFromBuyer(int id) {
		List<FotografijaDetails> list = this.dbController.getPhotosFromBuyer(id);
		List<FotografijaDto> result = new ArrayList<FotografijaDto>();
		if (list != null && list.size() > 0) {
			for (FotografijaDetails foto : list) {
				FotografijaDto fotoDto = new FotografijaDto(foto);

				fotoDto.setSrc(imageToBase64(foto.getPutanja()));
				result.add(fotoDto);
			}
		}
		return Response.ok(result).build();
	}
	
	public Response sendPhotoOnMail(FotografijaRateDto object) {
		FotografijaKatalogDbController fotografijaKatalogDbController = new FotografijaKatalogDbController();
		KorisnikDbController korisnikDbController = new KorisnikDbController();
		
		Fotografija photo = dbController.getById(object.getFotografijaId());
		
		if (photo == null) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		String resolution = fotografijaKatalogDbController.getPhotoResolutionForUser((int) photo.getId(), object.getKorisnikId());
		
		int width = Integer.parseInt(resolution.split("x")[0]);
		int height = Integer.parseInt(resolution.split("x")[1]);
		
		String outputimagePath = "D:\\FotografijeZaProdaju\\Scaled\\" + object.getKorisnikId()
				+ photo.getProdavacId() + ".png";
		try {
			Utils.resize(photo.getPutanja(), outputimagePath, width, height);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		
		Korisnik kupac = korisnikDbController.getById(object.getKorisnikId());
		if (kupac == null) {
			return Response.ok().entity(new ResponseDto(null, Const.SERVER_ERROR)).build();
		}
		
		sendMail("Kupljena fotografija", "Postovani u prilogu Vam se nalazi kupljena fotografija", kupac.getEmail(),
				outputimagePath);
		return Response.ok().build();
	}
}
