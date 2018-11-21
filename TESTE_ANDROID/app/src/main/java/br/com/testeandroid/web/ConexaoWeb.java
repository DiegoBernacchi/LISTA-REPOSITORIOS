package br.com.testeandroid.web;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import br.com.testeandroid.model.GitHubPullRequest;
import br.com.testeandroid.model.GitHubRepositorio;
import br.com.testeandroid.service.GitHubService;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.Settings.System.DATE_FORMAT;

public class ConexaoWeb {

    private static String baseUrl = "https://api.github.com/";
    private Context context;

    public ConexaoWeb(Context context) {
        this.context = context;
    }

    public boolean verificarConexaoInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isConnected());
    }

    public List<GitHubRepositorio> listaDeRepositoriosGitHub() throws Exception {
        GitHubService service = getService(GitHubService.class);
        Call<List<GitHubRepositorio>> call = service.listaDeRepositorios();
        Response<List<GitHubRepositorio>> response = call.execute();
        return response.body();
    }

    public List<GitHubPullRequest> listaDePullRequestsGitHub(String nomeRepositorio) throws Exception {
        GitHubService service = getService(GitHubService.class);
        Call<List<GitHubPullRequest>> call = service.listaDePullRequests(nomeRepositorio);
        Response<List<GitHubPullRequest>> response = call.execute();
        return response.body();
    }

    private <S> S getService(Class<S> serviceClass) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

}
