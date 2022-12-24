package ir.weblog.blog.modules.users.service;

import ir.weblog.blog.config.BeanCopyConfig;
import ir.weblog.blog.modules.users.model.Users;
import ir.weblog.blog.modules.users.repository.UsersRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class UsersService {

    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public Users registerUser(Users user) throws FileNotFoundException, IOException, IllegalAccessException, InvocationTargetException {
        
        if (!user.getCoverFile().isEmpty()) {
            String path = ResourceUtils.getFile("classpath:static/img/").getAbsolutePath();
            byte[] coverFileBytes = user.getCoverFile().getBytes();
            String coverFileName = UUID.randomUUID() + "."
                    + Objects.requireNonNull(user.getCoverFile().getContentType()).split("/")[1];
            Files.write(Paths.get(path + File.separator + coverFileName), coverFileBytes);
            user.setCover(coverFileName);
        }

        if (!user.getPassword().isEmpty()) {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }

        if (user.getId() != null) {
            Users existUser = this.usersRepository.getOne(user.getId());
            BeanCopyConfig copy = new BeanCopyConfig();
            copy.copyProperties(existUser, user);
            return this.usersRepository.save(existUser);
        }

        return this.usersRepository.save(user);
    }

    public List<Users> findAllUsers() {
        return this.usersRepository.findAll();
    }

    public Users findById(Long id) {
        return this.usersRepository.getOne(id);
    }
    
    @Transactional
    public void deleteById(Long id){
        this.usersRepository.deleteById(id);
    }

    public Users findByEmail(String email) {
        return this.usersRepository.findByEmail(email);
    }

}
