package br.com.testeandroid.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUsuario {

    @SerializedName("login")
    private String nomeAutor;

    @SerializedName("avatar_url")
    private String urlAvatar;

    public GitHubUsuario(String nomeAutor, String urlAvatar) {
        this.nomeAutor = nomeAutor;
        this.urlAvatar = urlAvatar;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

}
