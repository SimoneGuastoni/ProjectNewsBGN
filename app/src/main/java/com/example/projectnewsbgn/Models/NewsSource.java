package com.example.projectnewsbgn.Models;

import java.io.Serializable;

public class NewsSource implements Serializable {
    String id = "";
    String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
