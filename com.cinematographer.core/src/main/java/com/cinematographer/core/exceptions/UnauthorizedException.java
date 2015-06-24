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
public class UnauthorizedException extends RuntimeException
{

    /**
     * Creates a new instance of <code>UnauthrizedException</code> without
     * detail message.
     */
    public UnauthorizedException()
    {
    }

    /**
     * Constructs an instance of <code>UnauthrizedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnauthorizedException(String msg)
    {
        super(msg);
    }
}
