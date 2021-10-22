package com.example.login2;

import java.io.Serializable;
import java.util.ArrayList;

public class ObjNotas implements Serializable {

    Integer id;
    String titulo;
    String desc;

    public ObjNotas(){

        this.id = id;
        this.titulo = titulo;
        this.desc = desc;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
