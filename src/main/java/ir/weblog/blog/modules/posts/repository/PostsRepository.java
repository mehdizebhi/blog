
package ir.weblog.blog.modules.posts.repository;

import ir.weblog.blog.modules.posts.model.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Posts, Long> {
    
    public List<Posts> findByTitleContaining(String title);
    
   
    /*
    //    @Query("select p from Posts p where :#{#posts.title} is null or p.title like concat('%',:#{#posts.title},'%')")
    @Query("select p from Posts p join p.categories pc where (:#{#posts.title} is null or " +
            "p.title like concat('%',:#{#posts.title},'%')) and " +
            "(coalesce(:#{#posts.categories},null) is null or " +
            "pc in (:#{#posts.categories})) " +
            "group by p.id having count (p.id) >= :num")
    List<Posts> findBySearch(Posts posts,@Param("num") Long size);

    */
    
}
