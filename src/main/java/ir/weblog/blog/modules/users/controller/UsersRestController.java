package ir.weblog.blog.modules.users.controller;

import ir.weblog.blog.modules.users.model.Users;
import ir.weblog.blog.modules.users.service.UsersService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/users/rest")
public class UsersRestController {

    private UsersService usersService;

    @Autowired
    public UsersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public List<Users> getUsers() {
        return this.usersService.findAllUsers();
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public Users registerUser(@RequestBody Users users) throws IOException, FileNotFoundException, IllegalAccessException, InvocationTargetException {
        return this.usersService.registerUser(users);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String userDetails(Principal principal) {
        //Users user = this.usersService.findByEmail(principal.getName());
        
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        
        Users user = (Users) token.getPrincipal();
        
        return principal.toString() + " , " + principal.getName() + " , " + user.getEmail();
        //return user.getEmail() + ", " + user.getName();
    }
    
    @RequestMapping(value = "/info/auth", method = RequestMethod.GET)
    public String userAuth() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       Users userDtails = (Users) auth.getPrincipal();
       return "hello auth :" + auth.getName() +
               " , " + userDtails.getEmail();
       
                /*
                " ," + RequestContextHolder.currentRequestAttributes().getSessionId() +
                " ," + auth.getDetails() + 
                " ," + auth.getCredentials();
                */
    }
    
    @RequestMapping(value = "/info/auth2", method = RequestMethod.GET)
    public String userAuth2(Authentication auth) {
       Users userDtails = (Users) auth.getPrincipal();
       return "hello auth : " + auth.getPrincipal() +
               " , " + auth.getName() + 
               " , " + auth + 
               " , " + userDtails.getEmail() +
               " , " + userDtails.getPassword();
    }
}

