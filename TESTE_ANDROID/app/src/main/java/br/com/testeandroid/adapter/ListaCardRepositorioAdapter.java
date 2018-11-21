package br.com.testeandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import br.com.testeandroid.R;
import br.com.testeandroid.model.GitHubRepositorio;

public class ListaCardRepositorioAdapter extends RecyclerView.Adapter<ListaCardRepositorioAdapter.CardRepositorioViewHolder> {

    private static ClickListener clickListener;

    private List<GitHubRepositorio> listaRepositorio;

    public ListaCardRepositorioAdapter(List<GitHubRepositorio> listaRepositorio) {
        this.listaRepositorio = listaRepositorio;
    }

    @Override
    public CardRepositorioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_card_repositorio, parent, false);
        return new CardRepositorioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardRepositorioViewHolder holder, int position) {
        Picasso.get()
                .load(listaRepositorio.get(position).getUsuario().getUrlAvatar())
                .resize(600, 300)
                .centerInside()
                .into(holder.imgFotoAutor);
        holder.txtValNomeAutor.setText(listaRepositorio.get(position).getUsuario().getNomeAutor());
        holder.txtValStar.setText(String.valueOf(listaRepositorio.get(position).getNumeroStars()));
        holder.txtValFork.setText(String.valueOf(listaRepositorio.get(position).getNumeroForks()));
        holder.txtValNomeRepositorio.setText(listaRepositorio.get(position).getNomeRepositorio());
        holder.txtValDescricaoRespositorio.setText(listaRepositorio.get(position).getDescricaoRepositorio());
    }

    @Override
    public int getItemCount() {
        return listaRepositorio.size();
    }

    class CardRepositorioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgFotoAutor;
        private TextView txtValNomeAutor, txtValStar, txtValFork, txtValNomeRepositorio, txtValDescricaoRespositorio;

        CardRepositorioViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            this.imgFotoAutor = view.findViewById(R.id.imgFotoAutor);

            this.txtValNomeAutor = view.findViewById(R.id.txtValNomeAutor);
            this.txtValStar = view.findViewById(R.id.txtValStar);
            this.txtValFork = view.findViewById(R.id.txtValFork);
            this.txtValNomeRepositorio = view.findViewById(R.id.txtValNomeRepositorio);
            this.txtValDescricaoRespositorio = view.findViewById(R.id.txtValDescricaoRepositorio);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(listaRepositorio.get(getAdapterPosition()).getNomeRepositorio());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener ) {
        ListaCardRepositorioAdapter.clickListener  = clickListener;
    }

    public interface ClickListener {
        void onItemClick(String nomeRepositorio);
    }

}
