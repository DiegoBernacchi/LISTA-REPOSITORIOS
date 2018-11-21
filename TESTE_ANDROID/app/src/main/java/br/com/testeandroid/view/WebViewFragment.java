package br.com.testeandroid.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import br.com.testeandroid.web.ConexaoWeb;
import br.com.testeandroid.R;

public class WebViewFragment extends Fragment implements MainActivity.BackClickListener {

    private MainActivity mainActivity;

    private ConexaoWeb conexaoWeb;

    private static String TAG_URL = "URL";
    private static String TAG_NOME_REPOSITORIO = "NOME_REPOSITORIO";

    private WebView webView;

    private String url;
    private String nomeRepositorio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_web_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity) this.getActivity();

        if (mainActivity != null) {
            mainActivity.setOnBackClickListener(this);
            if (mainActivity.getSupportActionBar() != null) {
                mainActivity.getSupportActionBar().setTitle(mainActivity.getResources().getString(R.string.titulo_pagina_web));
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
            url = args.getString(TAG_URL);
            nomeRepositorio = args.getString(TAG_NOME_REPOSITORIO);

            conexaoWeb = new ConexaoWeb(mainActivity);

            webView = mainActivity.findViewById(R.id.webview);

            carregarDados();
        }

    }

    private void carregarDados() {
        if (conexaoWeb.verificarConexaoInternet()) {
            webView.loadUrl(url);
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
        Bundle args = new Bundle();
        args.putString(TAG_NOME_REPOSITORIO, nomeRepositorio);

        ListaPullRequestFragment listaPullRequestFragment = new ListaPullRequestFragment();
        listaPullRequestFragment.setArguments(args);

        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, listaPullRequestFragment);
        fragmentTransaction.commit();
    }

}
