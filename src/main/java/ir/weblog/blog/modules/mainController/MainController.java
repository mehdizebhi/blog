package ir.weblog.blog.modules.mainController;

import ir.weblog.blog.modules.jwt.jwtutil.JwtUtil;
import ir.weblog.blog.modules.jwt.model.AuthenticationRequest;
import ir.weblog.blog.modules.jwt.model.AuthenticationResponse;
import ir.weblog.blog.modules.posts.service.PostsService;
import ir.weblog.blog.modules.users.model.Users;
import ir.weblog.blog.modules.users.service.UsersService;
import java.util.Collections;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {

    private PostsService postsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtil jwtUtilToken;

    @Autowired
    public MainController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping({"", "/index"})
    public String index(Model model) {
        model.addAttribute("posts", postsService.findAllPosts());
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLogin(Model model) {
        model.addAttribute("user", new AuthenticationRequest());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String createAuthenticationToken(@ModelAttribute AuthenticationRequest authenticationRequest,
            HttpServletResponse response)
            throws Exception {
        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("invalid email or password", e);
        }

        final Users user = this.usersService.findByEmail(authenticationRequest.getEmail());
        final String jwt = this.jwtUtilToken.generatedToken(user);

        response.addHeader("Authorization", "Bearer " + jwt);
        
        Cookie cookie = new Cookie("JWT", jwt);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);

        return "redirect:/";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup() {
        return "login";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String hello(Authentication auth) {
        Users user = (Users) auth.getPrincipal();
        return user.getEmail();
    }
}
