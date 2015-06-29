package com.cinematographer.core.user.service;

import com.cinematographer.core.exceptions.InvalidUserException;
import com.cinematographer.core.exceptions.UnauthorizedException;
import com.cinematographer.core.user.Role;
import com.cinematographer.core.user.UserProfile;
import java.util.Collection;

public interface IUserService
{
    UserProfile findUser(String name);
    void addUser (UserProfile user);
    void authenticate(String name, String password) throws InvalidUserException;
    void authorize (UserProfile user, Role role) throws UnauthorizedException;
    String getMessageDigest(String password);
    Collection<UserProfile> getAllUsers();
}
