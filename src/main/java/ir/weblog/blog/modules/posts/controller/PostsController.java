package ir.weblog.blog.modules.posts.controller;

import ir.weblog.blog.modules.posts.model.Posts;
import ir.weblog.blog.modules.posts.service.CategoryService;
import ir.weblog.blog.modules.posts.service.PostsService;
import ir.weblog.blog.modules.users.service.UsersService;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
public class PostsController {

    private PostsService postsService;
    private CategoryService categoryService;
    private UsersService usersService;

    @Autowired
    public PostsController(PostsService postsService, CategoryService categoryService, UsersService usersService) {
        this.postsService = postsService;
        this.categoryService = categoryService;
        this.usersService = usersService;
    }
    
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showPosts(Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("posts", this.postsService.findAllPosts(pageable));
        return "posts/posts";
    }
    
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public @ResponseBody List<Posts> search(@ModelAttribute Posts posts){
        return this.postsService.findBySearch(posts);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPosts(Model model) {
        model.addAttribute("post", new Posts());
        model.addAttribute("categories", this.categoryService.findAllCategories());
        return "posts/registerPosts";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerPosts(@ModelAttribute(name = "post", value = "post") @Valid Posts posts,
            BindingResult bindingResult, Model model, Principal principal) throws IOException, IllegalAccessException, InvocationTargetException {
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAllCategories());
            return "posts/registerPosts";
        }
        
        posts.setUsers(this.usersService.findByEmail(principal.getName()));
        this.postsService.registerPost(posts);
        return "redirect:/posts";
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editPostPage(@PathVariable(value = "id") Long id, Model model){
        model.addAttribute("post", this.postsService.findById(id));
        return "posts/registerPosts";
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable(value = "id") Long id){
        this.postsService.deleteById(id);
        return "redirect:/posts";
    }
    

}
