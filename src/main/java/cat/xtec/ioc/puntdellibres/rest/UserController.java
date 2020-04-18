package cat.xtec.ioc.puntdellibres.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "registre")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "registre";
	}

   @PostMapping(value = "registre")
   public String create(HttpServletRequest request,
         @Valid @ModelAttribute("user") User user,
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

	@PutMapping(value = "/modificar-dades", produces = MediaType.APPLICATION_JSON)
	public ResponseEntity<Object> updateData(@RequestParam("username") String username, @RequestParam("email") String email,
			@RequestParam("name") String name, @RequestParam("lastName") String lastName,
			@RequestParam("location") String location, @RequestParam("password") String password,
			//@RequestParam("file") MultipartFile[] files, 
			Principal user) throws IOException {
		
		//MultipartFile file = files[0];
	    //String fileName = files[0].getOriginalFilename();
		User userToUpdate = userService.findMe(user);

		userToUpdate.setUsername(username);
		userToUpdate.setEmail(email);
		userToUpdate.setName(name);
		userToUpdate.setLastName(lastName);
		userToUpdate.setLocation(location);
		userToUpdate.setPassword(password);
		
//		if (!fileName.isEmpty()) {
//	         BufferedOutputStream outputStream = new BufferedOutputStream(
//	               new FileOutputStream(new File("./src/main/resources/static/img/profilePics", fileName)));
//	         outputStream.write(file.getBytes());
//	         outputStream.flush();
//	         outputStream.close();
//	         userToUpdate.setProfile(fileName);
//	      }

		userService.save(userToUpdate);

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
