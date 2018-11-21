package br.com.testeandroid.delegate;

import android.os.Bundle;

public interface ExecutaTaskDelegate {
    Bundle executar();
    void tratarRetorno(Bundle dados);
}
