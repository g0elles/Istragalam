package cuc.edu.co.istragalam.Profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cuc.edu.co.istragalam.R;

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingsActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountsettings);
        Log.d(TAG, "onCreate:  started");
        setupSettingslist();

        //la barra para volveer al perfil
        ImageView backArrow =  findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'ProfileActivity'");
                finish();
            }
        });


        
    }
    
    private void setupSettingslist(){
        Log.d(TAG, "setupSettingslist: inicio account settings list");
        ListView listView = findViewById(R.id.lvAccountSettings);
        ArrayList <String> options =  new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sing_out));


        ArrayAdapter adapter = new ArrayAdapter(AccountSettingsActivity.this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);

    }
}

