package cat.xtec.ioc.puntdellibres.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	BCryptPasswordEncoder encoder;

	@GetMapping(value = "registre")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "registre";
	}

	@PostMapping(value = "registre")
	public String create(HttpServletRequest request, @Valid @ModelAttribute("user") User user,
			BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "registre";
		}

		String password = user.getPassword();
		userService.save(user);

		try {
			request.login(user.getUsername(), password);
		} catch (ServletException e) {
			System.out.println(e);
		}

		return "redirect:/home";
	}

	@PostMapping(value = "/modificar-dades", produces = MediaType.APPLICATION_JSON)
	public String updateData(@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("lastName") String lastName, @RequestParam("location") String location, 
			@RequestParam("geoLocationLat") String geoLocationLat, @RequestParam("geoLocationLng") String geoLocationLng,
			@RequestParam("password") String password, @RequestParam("file") MultipartFile[] files, Principal user,
			Model model, HttpServletRequest request) throws Exception {

		MultipartFile file = files[0];
		String fileName = files[0].getOriginalFilename();

		User userToUpdate = userService.findMe(user);

		System.out.println("Password: " + password);
		System.out.println("Other: " + userToUpdate.getPassword());
		System.out.println(encoder.matches(password, userToUpdate.getPassword()) ? "matches!" : "nop!");

		if (! encoder.matches(password, userToUpdate.getPassword())) {
			String referer = request.getHeader("Referer");
			return "redirect:" + referer;
		}

		userToUpdate.setEmail(email);
		userToUpdate.setName(name);
		userToUpdate.setLastName(lastName);
		userToUpdate.setLocation(location);
		userToUpdate.setPassword(password);
		if((!geoLocationLat.isEmpty())&&(!geoLocationLng.isEmpty())) {
			double latitud = Double.parseDouble(geoLocationLat);
			double longitud = Double.parseDouble(geoLocationLng);
			GeometryFactory gf = new GeometryFactory();
			Point p = gf.createPoint(new Coordinate(longitud, latitud));
			p.setSRID(4326);
			userToUpdate.setGeoLocation(p);
		}

		if (!fileName.isEmpty()) {
			File newFile = new File("./src/main/resources/static/img/avatars", fileName);
			String mimeType = Files.probeContentType(newFile.toPath());

			if (mimeType.equals("image/jpeg")) {
				FileOutputStream fileOutputStream = new FileOutputStream(newFile);
				BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
				outputStream.write(file.getBytes());
				outputStream.flush();
				outputStream.close();
				String uniqueFileName = UUID.randomUUID() + ".jpg";
				newFile.renameTo(new File("./src/main/resources/static/img/avatars", uniqueFileName));
				userToUpdate.setAvatar(uniqueFileName);
			}
		}

		userService.save(userToUpdate);

		model.addAttribute("userData", userToUpdate);
		return "usuari";
	}

}