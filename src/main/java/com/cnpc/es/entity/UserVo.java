package com.cnpc.es.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

@Document(indexName = "myindex",type = "test")
public class UserVo implements Serializable {
    @Id
    private String id;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserVo(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public UserVo() {
    }
}
