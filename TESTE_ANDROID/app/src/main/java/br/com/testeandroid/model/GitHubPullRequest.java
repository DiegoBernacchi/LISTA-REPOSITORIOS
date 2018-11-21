package br.com.testeandroid.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

public class GitHubPullRequest {

    @SerializedName("user")
    private GitHubUsuario usuario;

    @SerializedName("title")
    private String titulo;

    @SerializedName("created_at")
    private Date dataDeCriacao;

    private String body;

    @SerializedName("html_url")
    private String url;

    public GitHubPullRequest(GitHubUsuario usuario, String titulo, Date dataDeCriacao, String body, String url) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.dataDeCriacao = this.dataDeCriacao;
        this.body = body;
        this.url = url;
    }

    public GitHubUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(GitHubUsuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getDataDeCriacao() {
        return dataDeCriacao;
    }

    public void setDataDeCriacao(Date dataDeCriacao) {
        this.dataDeCriacao = dataDeCriacao;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
