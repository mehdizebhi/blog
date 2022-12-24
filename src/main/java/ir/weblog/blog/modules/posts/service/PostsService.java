package ir.weblog.blog.modules.posts.service;

import ir.weblog.blog.config.BeanCopyConfig;
import ir.weblog.blog.modules.posts.model.Posts;
import ir.weblog.blog.modules.posts.repository.PostsRepository;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class PostsService {

    private PostsRepository postsRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @Transactional           //role back
    public Posts registerPost(Posts posts) throws IOException, IllegalAccessException, InvocationTargetException {
        if (!posts.getCoverFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img/").getAbsolutePath();
            byte[] coverFileBytes = posts.getCoverFile().getBytes();
            String coverFileName = UUID.randomUUID() + "."
                    + Objects.requireNonNull(posts.getCoverFile().getContentType()).split("/")[1];

            Files.write(Paths.get(path + File.separator + coverFileName), coverFileBytes);
            posts.setCover(coverFileName);
        }
        
        if(posts.getId() != null){
            Posts existPost = this.postsRepository.getOne(posts.getId());
            BeanCopyConfig copy = new BeanCopyConfig();
            copy.copyProperties(existPost, posts);
            return this.postsRepository.save(existPost);
        }

        return this.postsRepository.save(posts);
    }

    public List<Posts> findAllPosts() {
        return this.postsRepository.findAll();
    }
    
    public Page<Posts> findAllPosts(Pageable pageable) {
        return this.postsRepository.findAll(pageable);
    }

    public Posts findById(Long id) {
        return this.postsRepository.getOne(id);
    }

    @Transactional
    public void deleteById(Long id) {
        this.postsRepository.deleteById(id);
    }

    public List<Posts> findBySearch(Posts posts) {
        return this.postsRepository.findByTitleContaining(posts.getTitle());
    }

}
