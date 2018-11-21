package br.com.testeandroid.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import br.com.testeandroid.R;
import br.com.testeandroid.adapter.ListaCardPullRequestAdapter;
import br.com.testeandroid.delegate.ExecutaTaskDelegate;
import br.com.testeandroid.model.GitHubPullRequest;
import br.com.testeandroid.task.ProgressoTask;
import br.com.testeandroid.web.ConexaoWeb;

public class ListaPullRequestFragment extends Fragment implements MainActivity.BackClickListener {

    private MainActivity mainActivity;
    private ConexaoWeb conexaoWeb;

    private static String TAG_ERRO = "ERRO";
    private static String TAG_NOME_REPOSITORIO = "NOME_REPOSITORIO";
    private static String TAG_URL = "URL";

    private RecyclerView rclListaPullRequest;
    private ListaCardPullRequestAdapter listaCardPullRequestAdapter;

    private List<GitHubPullRequest> listaDePullRequests;

    private String nomeRepositorio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_lista_pull_request, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) this.getActivity();

        if (mainActivity != null) {
            mainActivity.setOnBackClickListener(this);
            if (mainActivity.getSupportActionBar() != null) {
                mainActivity.getSupportActionBar().setTitle(mainActivity.getResources().getString(R.string.titulo_pull_requests));
            }
        }

        Bundle args = this.getArguments();
        if (args == null) {
            new AlertDialog.Builder(mainActivity)
                    .setMessage("Erro ao carregar dados da tela|")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainActivity.finish();
                        }

                    })
                    .show();
        } else {
            nomeRepositorio = args.getString(TAG_NOME_REPOSITORIO);

            conexaoWeb = new ConexaoWeb(mainActivity);

            rclListaPullRequest = mainActivity.findViewById(R.id.rclListaPullRequest);
            rclListaPullRequest.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
            rclListaPullRequest.setLayoutManager(layoutManager);
            rclListaPullRequest.setItemAnimator(new DefaultItemAnimator());

            carregarDados();
        }

    }

    private void carregarDados() {
        if (conexaoWeb.verificarConexaoInternet()) {
            new ProgressoTask(mainActivity, "Aguarde...", "Carregando dados", new ExecutaTaskDelegate() {
                @Override
                public Bundle executar() {
                    Bundle bundle = new Bundle();
                    try {
                        listaDePullRequests = conexaoWeb.listaDePullRequestsGitHub(nomeRepositorio);
                    } catch (Exception e) {
                        bundle.putString(TAG_ERRO, e.getMessage());
                    }
                    return bundle;
                }

                @Override
                public void tratarRetorno(Bundle dados) {
                    if (dados.getString(TAG_ERRO) != null) {
                        new AlertDialog.Builder(mainActivity)
                                .setMessage("Erro ao carregar dados " + dados.getString(TAG_ERRO))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mainActivity.finish();
                                    }

                                })
                                .show();
                    } else {
                        if (listaDePullRequests.size() == 0) {
                            new AlertDialog.Builder(mainActivity)
                                    .setMessage("Repositório não possui Pull Requests!")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                            fragmentTransaction.replace(R.id.fragment_main, new ListaRepositorioFragment());
                                            fragmentTransaction.commit();
                                        }

                                    })
                                    .show();
                        } else {
                            listaCardPullRequestAdapter = new ListaCardPullRequestAdapter(listaDePullRequests);
                            listaCardPullRequestAdapter.setOnItemClickListener(new ListaCardPullRequestAdapter.ClickListener() {
                                @Override
                                public void onItemClick(String url) {
                                    Bundle args = new Bundle();
                                    args.putString(TAG_URL, url);
                                    args.putString(TAG_NOME_REPOSITORIO, nomeRepositorio);

                                    WebViewFragment webViewFragment = new WebViewFragment();
                                    webViewFragment.setArguments(args);

                                    FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_main, webViewFragment);
                                    fragmentTransaction.commit();
                                }
                            });
                            rclListaPullRequest.setAdapter(listaCardPullRequestAdapter);
                        }
                    }
                }

            }).execute();
        } else {
            new AlertDialog.Builder(mainActivity)
                    .setMessage("Sem conexão com a internet|")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainActivity.finish();
                        }

                    })
                    .show();
        }
    }

    @Override
    public void onBackClick() {
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, new ListaRepositorioFragment());
        fragmentTransaction.commit();
    }

}