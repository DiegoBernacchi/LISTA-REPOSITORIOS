package br.com.testeandroid.service;

import java.util.List;
import br.com.testeandroid.model.GitHubPullRequest;
import br.com.testeandroid.model.GitHubRepositorio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/octokit/repos")
    Call<List<GitHubRepositorio>> listaDeRepositorios();

    @GET("repos/octokit/{repositorio}/pulls")
    Call<List<GitHubPullRequest>> listaDePullRequests(@Path("repositorio") String repositorio);
}
