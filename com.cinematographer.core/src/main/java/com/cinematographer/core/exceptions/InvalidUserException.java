/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cinematographer.core.exceptions;

/**
 *
 * @author Aleksandar
 */
public class InvalidUserException extends RuntimeException
{

    /**
     * Creates a new instance of <code>InvalidUser</code> without detail
     * message.
     */
    public InvalidUserException()
    {
    }

    /**
     * Constructs an instance of <code>InvalidUser</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUserException(String msg)
    {
        super(msg);
    }
}
