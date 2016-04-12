package de.obercraft.aide.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.obercraft.aide.dto.Comment;
import de.obercraft.aide.dto.User;
import de.obercraft.aide.dto.UserSecret;

public interface UserSecretRepository extends CrudRepository<UserSecret, Long> {
    List<UserSecret> findByUserName(String userName);
    
    List<UserSecret> findByEmail(String email);
}