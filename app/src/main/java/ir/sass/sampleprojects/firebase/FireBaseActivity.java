package ir.sass.sampleprojects.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.sass.sampleprojects.R;

public class FireBaseActivity extends AppCompatActivity
{
    FireBaseFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fire_base);
        
        setTitle("FireBaseActivity");

        fragment = new FireBaseFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout,fragment).commit();
    }
}
