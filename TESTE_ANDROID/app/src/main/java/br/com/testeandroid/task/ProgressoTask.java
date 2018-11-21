package br.com.testeandroid.task;

import android.content.Context;
import android.os.Bundle;
import br.com.testeandroid.delegate.ExecutaTaskDelegate;

public class ProgressoTask extends BaseTask<Object, Integer, Bundle> {

    private final ExecutaTaskDelegate delegate;

    public ProgressoTask(Context context, String titulo, String mensagem, ExecutaTaskDelegate delegate) {
        super(context, titulo, mensagem);
        this.delegate = delegate;
    }

    @Override
    protected Bundle doInBackground(Object... params) {
        Bundle retorno = null;
        if (getDelegate() != null) {
            retorno = getDelegate().executar();
        }
        return retorno;
    }

    @Override
    protected void onPostExecute(Bundle result) {
        super.onPostExecute(result);
        if (getDelegate() != null) {
            getDelegate().tratarRetorno(result);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public ExecutaTaskDelegate getDelegate() {
        return delegate;
    }

}