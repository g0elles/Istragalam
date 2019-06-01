package cuc.edu.co.istragalam.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.BottomNavigationViewHelper;
import Utils.FirebaseMethods;
import Utils.SectionsPagerAdapter;
import Utils.StringManipulation;
import Utils.UniversalmageLoader;
import cuc.edu.co.istragalam.Profile.SignOutFragment;
import cuc.edu.co.istragalam.R;
import cuc.edu.co.istragalam.models.UserAccountSettings;
import cuc.edu.co.istragalam.models.Usuario;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private String username, email;
    private String append = " ";
    private String uid;
    //firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;

    private Context mContext = HomeActivity.this;
    public static final int RC_SIGN_IN = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting.");
        initImageLoader();
        setUtbottomNavigationView();
        setupViewPager();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef =  mFirebaseDatabase.getReference();



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull  FirebaseAuth firebaseAuth) {
                 FirebaseUser user = firebaseAuth.getCurrentUser();

  //          Intent intent = new Intent(mContext, SignOutFragment.class);

//                startActivity(intent);
                Log.d(TAG, "onAuthStateChanged: el ide "+uid);

                if (user!= null){
                    uid = user.getUid();
                    //onSignedInInitialize(user.getDisplayName());
                    username = user.getDisplayName();
                    email = user.getEmail();
                    Log.d(TAG, "onAuthStateChanged: username: "+username);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            /**
                             *  if (firebaseMethods.checkIfUsernameExists(username, uid,dataSnapshot)){
                             *                                 append= myRef.push().getKey().substring(3,10);
                             *                                 Log.d(TAG, "onDataChange: Ya existe el nombre de usuario, " +
                             *                                         "toca cambiar el nombre por uno aja ya sabes"+append);
                             *
                             *                                     }
                             */

                            //username = username + append;
                            Log.d(TAG, "addNewUser: uid"+email);
                            //agrega el nuevo usuario a la DB
                           if (checkifusergotprofile(uid)){
                               addNewUser(uid,email,username,"","","");
                           }

                            //agrega el nuevo usuario a las configuraciones de la cuenta

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: "+databaseError);

                        }
                    });
                }else{
                    //onSignetOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.GoogleBuilder().build(),
                                            new AuthUI.IdpConfig.FacebookBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()

                                     ))
                                    .setTheme(R.style.LoginTheme)
                                    .setLogo(R.drawable.istragalam_logo)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };


    }
    private void initImageLoader(){
        UniversalmageLoader universalImageLoader = new UniversalmageLoader(HomeActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }


    public static  boolean checkifusergotprofile(String id){
        final ArrayList<String> uid = new ArrayList<>();
        DatabaseReference mDatabase =FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("user_id").equalTo(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    uid.add(snapshot.child("user_id").toString());
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }   });

        for (String userid : uid){
            if (userid.equals(id)){
                return  false;
            }

        }


        return true;
    }


    public void addNewUser(String id,String email, String username, String description, String website, String profile_photo){
        //FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        //DatabaseReference  myRef =  mFirebaseDatabase.getReference();
        Usuario user = new Usuario(id, 1, email, StringManipulation.condenseUsername(username));
        myRef.child(mContext.getString(R.string.dbname_users))
                .child(id)
                .setValue(user);
        UserAccountSettings settings = new UserAccountSettings(
                description,
                username,
                0,
                0,
                0,
                profile_photo,
                username,
                website
        );

        myRef.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(id)
                .setValue(settings);


    }


    /**
     * Responsible for adding the 3 tabs: Camera, Home, Messages
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CameraFragment());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new MessageFragment());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_name);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);



    }


    /*
     * BottomNavigationView setup
     * **/
    private void setUtbottomNavigationView(){
        Log.d(TAG, "setUtbottomNavigationView: setting up bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }


    @Override
    public void onResume(){
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "pelao te logueasate", Toast.LENGTH_SHORT).show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    public void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

    }
}
