package cuc.edu.co.istragalam.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import Utils.BottomNavigationViewHelper;
import cuc.edu.co.istragalam.R;

public class RegisterActivity extends AppCompatActivity {
    private static final RegisterActivity ourInstance = new RegisterActivity();
    private static final String TAG = "LikesActivity";
    private static final int ACTIVITY_NUM = 3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Started");
         setUtbottomNavigationView();

    }

    /*
     * BottomNavigationView setup
     * **/
    private void setUtbottomNavigationView(){
        Log.d(TAG, "setUtbottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(RegisterActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
