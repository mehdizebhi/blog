package ir.weblog.blog.modules.posts.controller;

import ir.weblog.blog.modules.posts.model.Posts;
import ir.weblog.blog.modules.posts.service.PostsService;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/rest")
public class PostsRestController {

    private PostsService postsService;

    @Autowired
    public PostsRestController(PostsService postsService) {
        this.postsService = postsService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Posts> getPosts() {
        return postsService.findAllPosts();
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public Posts registerPost(@RequestBody Posts posts) throws IOException, IllegalAccessException, InvocationTargetException {
        return postsService.registerPost(posts);
    }

}
