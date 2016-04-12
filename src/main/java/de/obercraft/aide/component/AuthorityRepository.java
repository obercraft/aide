package de.obercraft.aide.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.obercraft.aide.dto.Authority;
import de.obercraft.aide.dto.Comment;
import de.obercraft.aide.dto.User;
import de.obercraft.aide.dto.UserSecret;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
}