package com.example.pm2e10634.tablas;

public class Paises {


    private Integer id;
    private String pais;
    private String codigo;





    public Paises(){}
    public Paises(Integer id, String pais, String codigo) {
        this.id = id;
        this.pais = pais;
        this.codigo = codigo;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


}
