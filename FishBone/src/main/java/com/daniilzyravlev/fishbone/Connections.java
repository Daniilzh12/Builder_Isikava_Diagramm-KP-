package com.daniilzyravlev.fishbone;

import com.daniilzyravlev.fishbone.blocks.Connection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * List of connections
 */
public class Connections implements Serializable {
    private final List<Connection> connections;

    /**
     * Constructor
     */
    public Connections()
    {
        connections = new ArrayList<>();
    }

    /**
     * @return list of connections
     */
    public List<Connection> getConnections()
    {
        return connections;
    }
}
