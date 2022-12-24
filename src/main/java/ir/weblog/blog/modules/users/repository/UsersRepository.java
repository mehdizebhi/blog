package ir.weblog.blog.modules.users.repository;

import ir.weblog.blog.modules.users.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    public Users findByEmail(String email);

    //@Query("select u from Users where u.email= :email")
    //Users findEmailByQuery(@Param("email") String userEmail);

    //Users findByEmail(String email);
    /*
    @Query(nativeQuery = false, value = "select u.email, u.name from Users u where")
 // @Query(nativeQuery = true, value = "select * from users where email=''")
    @Query(nativeQuery = false, value = "select u from Users u where u.email = :email")
    Users findUserEmail(@Param("email") String userEmail);
     */
}
