/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almondcareers.restapi.repository;

import com.almondcareers.restapi.domain.Role;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Adeoluwa
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
}