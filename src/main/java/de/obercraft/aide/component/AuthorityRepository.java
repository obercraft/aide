package de.obercraft.aide.component;

import org.springframework.data.repository.CrudRepository;

import de.obercraft.aide.dto.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    
}