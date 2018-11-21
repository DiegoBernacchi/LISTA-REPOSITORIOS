package br.com.testeandroid.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class BaseTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private static final List<AsyncTask<?, ?, ?>> tasks = new ArrayList<>();
    //public static AppDelegate app;

    private Context context;
    private ProgressDialog caixaProgresso = null;
    private String tituloProgresso;
    private String mensagemProgresso;
    private boolean usarBarraProgresso = false;
    private static int gerador = 1;
    protected final int id;

    public BaseTask() {
        super();
        this.id = gerador++;
    }

    public BaseTask(Context context, String titulo, String mensagem) {
        this();
        this.habilitarBarraProgresso(context, titulo, mensagem);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        BaseTask.registrarTarefa(this);
        if (usarBarraProgresso) {
            this.caixaProgresso = ProgressDialog.show(context, tituloProgresso, mensagemProgresso, true, false);
        }
    }

    @Override
    protected void onPostExecute(Result result) {
        BaseTask.desregistrarTarefa(this);
        if (usarBarraProgresso) {
            this.caixaProgresso.dismiss();
        }
        super.onPostExecute(result);
    }

    public void habilitarBarraProgresso(Context context, String titulo, String mensagem) {
        this.usarBarraProgresso = true;
        this.context = context;
        this.tituloProgresso = titulo;
        this.mensagemProgresso = mensagem;
    }

    @SuppressWarnings("unused")
    public void desabilitarBarraProgresso() {
        if (this.usarBarraProgresso && caixaProgresso != null) {
            this.caixaProgresso.dismiss();
        }
        this.usarBarraProgresso = false;
    }

    public Context getContext() {
        return context;
    }

    public static void registrarTarefa(AsyncTask<?, ?, ?> task) {
        tasks.add(task);
    }

    public static void desregistrarTarefa(AsyncTask<?, ?, ?> task) {
        tasks.remove(task);
    }

    public static boolean isTarefaRodando() {
        boolean existe = false;
        for (AsyncTask task : tasks) {
            if (task.getStatus() == AsyncTask.Status.RUNNING) {
                existe = true;
                break;
            }
        }
        return existe;
    }

    public static void cancelarTodasTarefas() {
        for (AsyncTask task : tasks) {
            task.cancel(true);
        }
    }
}