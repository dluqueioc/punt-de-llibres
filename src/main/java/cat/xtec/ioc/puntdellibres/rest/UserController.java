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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@PostMapping(value = "/modificar-dades")
	public String updateData(@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("lastName") String lastName, @RequestParam("location") String location,
			@RequestParam("password") String password, @RequestParam("file") MultipartFile[] files, Principal user,
			Model model, HttpServletRequest request, RedirectAttributes redirAttrs) throws Exception {

		MultipartFile file = files[0];
		String fileName = files[0].getOriginalFilename();

		User userToUpdate = userService.findMe(user);

		if (!encoder.matches(password, userToUpdate.getPassword())) {
			String referer = request.getHeader("Referer");
			redirAttrs.addFlashAttribute("passwordError", true);
			return "redirect:" + referer;
		}

		userToUpdate.setEmail(email);
		userToUpdate.setName(name);
		userToUpdate.setLastName(lastName);
		userToUpdate.setLocation(location);
		userToUpdate.setPassword(password);

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