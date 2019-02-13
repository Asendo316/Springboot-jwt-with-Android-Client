/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almondcareers.restapi.service;

import com.almondcareers.restapi.domain.RandomCity;
import com.almondcareers.restapi.domain.User;
import java.util.List;

/**
 *
 * @author Adeoluwa
 */
public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();
}

