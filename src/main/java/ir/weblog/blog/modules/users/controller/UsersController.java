package ir.weblog.blog.modules.users.controller;

import ir.weblog.blog.modules.users.model.Users;
import ir.weblog.blog.modules.users.service.UsersService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showUsersPage(Model model) {
        model.addAttribute("users", this.usersService.findAllUsers());
        return "users/users";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String ShowRegisterUsersPage(Model model) {
        model.addAttribute("user", new Users());
        return "users/registerUser";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUsers(@ModelAttribute(name = "user", value = "user") @Valid Users users,
            BindingResult bindingResult) throws FileNotFoundException, IOException, IllegalAccessException, InvocationTargetException {

        if (bindingResult.hasErrors()) {
            return "users/registerUser";
        }
        this.usersService.registerUser(users);
        return "redirect:/users";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUserPage(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("user", this.usersService.findById(id));
        return "users/registerUser";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable(value = "id") Long id) {
        this.usersService.deleteById(id);
        return "redirect:/users";
    }

}
