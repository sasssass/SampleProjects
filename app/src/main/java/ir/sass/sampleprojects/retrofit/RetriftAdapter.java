package ir.sass.sampleprojects.retrofit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.sass.sampleprojects.R;

public class RetriftAdapter extends RecyclerView.Adapter<RetriftAdapter.VH>
{
    List<Model> list;
    Context context;

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;

    public RetriftAdapter(Context context , List<Model> list)
    {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RetriftAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_retrofit,
                parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int pos)
    {
        vh.onBind(pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class VH extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView img;

        public VH(@NonNull View itemView)
        {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
        }


        public void onBind(int position)
        {
            Model item = list.get(position);
            title.setText(item.title);
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(item.url)
                    .placeholder((R.drawable.ic_launcher_background))
                    .into(img);
        }
    }

}
