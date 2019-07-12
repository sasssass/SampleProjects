package ir.sass.sampleprojects.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ir.sass.sampleprojects.MainActivity;
import ir.sass.sampleprojects.R;


public class FireBaseFragment extends Fragment
{
    View container;
    SwipeRefreshLayout pullToRefresh;

    public final String TAG = "ALISASS FireBaseFragment";

    public FireBaseFragment() {
        // Required empty public constructor
    }

    public static FireBaseFragment newInstance(String param1, String param2)
    {
        FireBaseFragment fragment = new FireBaseFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        this.container = inflater.inflate(R.layout.fragment_fire_base, container, false);
        makeList();
        pullToRefresh = this.container.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getContext());
                makeList();
                pullToRefresh.setRefreshing(false);
            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult)
            {
                String pushToken = instanceIdResult.getToken();
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Log.i(TAG,"the token for firebase -> "+pushToken);
                if(settings.getString("toker_reg", "") != null &&  !(settings.getString("toker_reg", "").length() > 0))
                {

                }
                else
                {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("toker_reg",pushToken);
                    Log.i(TAG,"firebase token key saved -> "+pushToken);
                }
            }
        });


        return this.container;
    }

    public void makeList()
    {
        SQLiteDatabaseHandler db = new SQLiteDatabaseHandler(getContext());

        RecyclerView recyclerView = container.findViewById(R.id.list_notif);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        AdapterFireBase adapter = new AdapterFireBase(((FireBaseActivity)getActivity()),db.allMessage());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
