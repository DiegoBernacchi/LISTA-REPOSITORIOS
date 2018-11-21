package br.com.testeandroid.model;

import com.google.gson.annotations.SerializedName;

public class GitHubRepositorio {

    @SerializedName("owner")
    private GitHubUsuario usuario;

    @SerializedName("stargazers_count")
    private int numeroStars;

    @SerializedName("forks")
    private int numeroForks;

    @SerializedName("name")
    private String nomeRepositorio;

    @SerializedName("description")
    private String descricaoRepositorio;

    public GitHubRepositorio(GitHubUsuario usuario, int numeroStars, int numeroForks, String nomeRepositorio, String descricaoRepositorio) {
        this.usuario = usuario;
        this.numeroStars = numeroStars;
        this.numeroForks = numeroForks;
        this.nomeRepositorio = nomeRepositorio;
        this.descricaoRepositorio = descricaoRepositorio;
    }

    public GitHubUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(GitHubUsuario usuario) {
        this.usuario = usuario;
    }

    public int getNumeroStars() {
        return numeroStars;
    }

    public void setNumeroStars(int numeroStars) {
        this.numeroStars = numeroStars;
    }

    public int getNumeroForks() {
        return numeroForks;
    }

    public void setNumeroForks(int numeroForks) {
        this.numeroForks = numeroForks;
    }

    public String getNomeRepositorio() {
        return nomeRepositorio;
    }

    public void setNomeRepositorio(String nomeRepositorio) {
        this.nomeRepositorio = nomeRepositorio;
    }

    public String getDescricaoRepositorio() {
        return descricaoRepositorio;
    }

    public void setDescricaoRepositorio(String descricaoRepositorio) {
        this.descricaoRepositorio = descricaoRepositorio;
    }

}
