package ir.weblog.blog.modules.posts.controller;

import ir.weblog.blog.modules.posts.model.Category;
import ir.weblog.blog.modules.posts.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories/rest")
public class CategoryRestController {

    private CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public List<Category> getCategories() {
        return categoryService.findAllCategories();
    }

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    public Category registerCategory(@RequestBody Category category) {
        return categoryService.registerCategory(category);
    }

}
