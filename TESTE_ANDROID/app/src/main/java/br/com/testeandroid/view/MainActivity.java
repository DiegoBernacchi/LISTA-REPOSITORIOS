package br.com.testeandroid.view;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import br.com.testeandroid.R;

public class MainActivity extends AppCompatActivity {

    private static BackClickListener backClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }

        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_main, new ListaRepositorioFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (backClickListener == null) {
            super.onBackPressed();
        } else {
            backClickListener.onBackClick();
        }
    }

    public void setOnBackClickListener(BackClickListener backClickListener ) {
        MainActivity.backClickListener  = backClickListener;
    }

    public interface BackClickListener {
        void onBackClick();
    }

}