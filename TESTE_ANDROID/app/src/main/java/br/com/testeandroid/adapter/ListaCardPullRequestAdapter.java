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
import br.com.testeandroid.model.GitHubPullRequest;

public class ListaCardPullRequestAdapter extends RecyclerView.Adapter<ListaCardPullRequestAdapter.CardPullRequestViewHolder> {
    private static ClickListener clickListener;

    private List<GitHubPullRequest> listaPullRequest;

    public ListaCardPullRequestAdapter(List<GitHubPullRequest> listaPullRequest) {
        this.listaPullRequest = listaPullRequest;
    }

    @Override
    public CardPullRequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_card_pull_request, parent, false);
        return new CardPullRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardPullRequestViewHolder holder, int position) {
        Picasso.get()
                .load(listaPullRequest.get(position).getUsuario().getUrlAvatar())
                .resize(600, 300)
                .centerInside()
                .into(holder.imgFotoAutor);
        holder.txtValNomeAutor.setText(listaPullRequest.get(position).getUsuario().getNomeAutor());
        holder.txtValTitulo.setText(listaPullRequest.get(position).getTitulo());
        holder.txtValData.setText(String.valueOf(listaPullRequest.get(position).getDataDeCriacao()));
        holder.txtValBody.setText(listaPullRequest.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return listaPullRequest.size();
    }

    class CardPullRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imgFotoAutor;
        private TextView txtValNomeAutor, txtValTitulo, txtValData, txtValBody;

        CardPullRequestViewHolder(View view) {
            super(view);

            view.setOnClickListener(this);

            this.imgFotoAutor = view.findViewById(R.id.imgFotoAutor);

            this.txtValNomeAutor = view.findViewById(R.id.txtValNomeAutor);
            this.txtValTitulo = view.findViewById(R.id.txtValTitulo);
            this.txtValData = view.findViewById(R.id.txtValData);
            this.txtValBody = view.findViewById(R.id.txtValBody);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(listaPullRequest.get(getAdapterPosition()).getUrl());
        }
    }

    public void setOnItemClickListener(ClickListener clickListener ) {
        ListaCardPullRequestAdapter.clickListener  = clickListener;
    }

    public interface ClickListener {
        void onItemClick(String url);
    }
}