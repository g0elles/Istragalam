package cuc.edu.co.istragalam.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import Utils.BottomNavigationViewHelper;
import cuc.edu.co.istragalam.R;

public class ProfileActivity extends AppCompatActivity {
    private static final ProfileActivity ourInstance = new ProfileActivity();
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: Started");
        setUtbottomNavigationView();
        setupToolbar();
    }

        private void setupToolbar(){
            Toolbar toolbar = findViewById(R.id.profileToolbar);
            setSupportActionBar(toolbar);
            ImageView profileMenu = findViewById(R.id.profileMenu);
            profileMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: navigaring to account settings");
                    Intent intent = new Intent(ProfileActivity.this, AccountSettingsActivity.class);
                    startActivity(intent);
                }
            });
        }

    /*
     * BottomNavigationView setup
     * **/
    private void setUtbottomNavigationView(){
        Log.d(TAG, "setUtbottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ProfileActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}
