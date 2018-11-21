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
import br.com.testeandroid.adapter.ListaCardRepositorioAdapter;
import br.com.testeandroid.delegate.ExecutaTaskDelegate;
import br.com.testeandroid.model.GitHubRepositorio;
import br.com.testeandroid.task.ProgressoTask;
import br.com.testeandroid.web.ConexaoWeb;

public class ListaRepositorioFragment extends Fragment implements MainActivity.BackClickListener {

    private MainActivity mainActivity;
    private ConexaoWeb conexaoWeb;

    private static String TAG_ERRO = "ERRO";
    private static String TAG_NOME_REPOSITORIO = "NOME_REPOSITORIO";

    private RecyclerView rclListaRepositorio;
    private ListaCardRepositorioAdapter listaCardRepositorioAdapter;

    private List<GitHubRepositorio> listaDeRepositorios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_lista_repositorio, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) this.getActivity();

        if (mainActivity != null) {
            mainActivity.setOnBackClickListener(this);
            if (mainActivity.getSupportActionBar() != null) {
                mainActivity.getSupportActionBar().setTitle(mainActivity.getResources().getString(R.string.titulo_lista_repositorio));
            }
        }

        conexaoWeb = new ConexaoWeb(mainActivity);

        rclListaRepositorio = mainActivity.findViewById(R.id.rclListaRepositorio);
        rclListaRepositorio.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mainActivity);
        rclListaRepositorio.setLayoutManager(layoutManager);
        rclListaRepositorio.setItemAnimator(new DefaultItemAnimator());

        buscarDados();
    }

    private void buscarDados() {
        if (conexaoWeb.verificarConexaoInternet()) {
            new ProgressoTask(mainActivity, "Aguarde...", "Carregando dados", new ExecutaTaskDelegate() {
                @Override
                public Bundle executar() {
                    Bundle bundle = new Bundle();
                    try {
                        listaDeRepositorios = conexaoWeb.listaDeRepositoriosGitHub();
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
                        listaCardRepositorioAdapter = new ListaCardRepositorioAdapter(listaDeRepositorios);
                        listaCardRepositorioAdapter.setOnItemClickListener(new ListaCardRepositorioAdapter.ClickListener() {
                            @Override
                            public void onItemClick(String nomeRepositorio) {
                                Bundle args = new Bundle();
                                args.putString(TAG_NOME_REPOSITORIO, nomeRepositorio);

                                ListaPullRequestFragment listaPullRequestFragment = new ListaPullRequestFragment();
                                listaPullRequestFragment.setArguments(args);

                                FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_main, listaPullRequestFragment);
                                fragmentTransaction.commit();
                            }
                        });
                        rclListaRepositorio.setAdapter(listaCardRepositorioAdapter);
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
        new AlertDialog.Builder(mainActivity)
                .setMessage("Deseja sair do sistema?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mainActivity.finish();
                    }

                })
                .setNegativeButton("Não", null)
                .show();
    }

}
