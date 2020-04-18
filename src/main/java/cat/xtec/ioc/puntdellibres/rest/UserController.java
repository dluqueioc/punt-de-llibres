package cat.xtec.ioc.puntdellibres.rest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

}