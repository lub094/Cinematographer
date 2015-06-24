/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinematographer.core.user.manager;

import com.cinematographer.core.exceptions.InvalidUserException;
import com.cinematographer.core.exceptions.UnauthorizedException;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.User;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

/**
 *
 * @author Aleksandar
 */
public interface IUserService
{
    User findUser(String name);
    void addUser (User user);
    void authenticate(String name, String password) throws InvalidUserException;
    void authorize (User user, Role role) throws UnauthorizedException;
    String getMessageDigest(String password);
    Collection<User> getAllUsers();
}
