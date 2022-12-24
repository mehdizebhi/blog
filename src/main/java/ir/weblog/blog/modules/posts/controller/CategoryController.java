package ir.weblog.blog.modules.posts.controller;

import ir.weblog.blog.modules.posts.model.Category;
import ir.weblog.blog.modules.posts.service.CategoryService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String showCategories(Model model) {
        model.addAttribute("categories", this.categoryService.findAllCategories());
        return "categories/categories";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerCategoryPage(Model model) {
        model.addAttribute("category", new Category());
        return "categories/registerCategories";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerCategory(@ModelAttribute(name = "category", value = "category") @Valid Category category,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "categories/registerCategories";
        }

        this.categoryService.registerCategory(category);
        return "redirect:/categories";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editCategoryPage(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("category", this.categoryService.findById(id));
        return "categories/registerCategories";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteCategoryPage(@PathVariable(value = "id") Long id) {
        this.categoryService.deleteById(id);
        return "redirect:/categories";
    }

}
