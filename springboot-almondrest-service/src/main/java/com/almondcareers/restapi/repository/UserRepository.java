/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almondcareers.restapi.repository;

import com.almondcareers.restapi.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Adeoluwa
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
