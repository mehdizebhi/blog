package ir.weblog.blog.modules.posts.service;

import ir.weblog.blog.modules.posts.model.Category;
import ir.weblog.blog.modules.posts.repository.CategoryRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category registerCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }
    
    public Category findById(Long id){
        return this.categoryRepository.getOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

}
