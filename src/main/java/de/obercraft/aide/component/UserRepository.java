package de.obercraft.aide.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.obercraft.aide.dto.User;

public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByName(String name);
    
    public List<User> findByEmail(String email);
}