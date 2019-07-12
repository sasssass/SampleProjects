package ir.sass.sampleprojects.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.sass.sampleprojects.MainActivity;
import ir.sass.sampleprojects.R;

public class AdapterFireBase extends RecyclerView.Adapter<AdapterFireBase.VH>
{
    List<MessageFireBase> list;
    FireBaseActivity activityContext;

    public AdapterFireBase(FireBaseActivity activityContext , List<MessageFireBase> list)
    {
        this.activityContext = activityContext;
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos)
    {
        View view = LayoutInflater.from(activityContext).inflate(R.layout.adapter_fire_base,viewGroup,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int pos)
    {
        vh.title.setText(list.get(pos).title);
        vh.message.setText(list.get(pos).message);
        vh.makeClick(pos);
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder
    {
        TextView title , message;
        public VH(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
        }

        public void makeClick(final int pos)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override public void onClick(View v)
                {
                    SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(activityContext);
                    if(db.deleteOne(list.get(pos)))
                    {
                        list.remove(pos);
                        notifyDataSetChanged();
                        Toast.makeText(activityContext,"Done",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(activityContext,"Error",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
