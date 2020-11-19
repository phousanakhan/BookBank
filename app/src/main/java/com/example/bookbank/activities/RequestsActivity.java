package com.example.bookbank.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bookbank.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class RequestsActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        firebaseAuth = FirebaseAuth.getInstance();

        if (checkServices()){
            Button location = (Button) findViewById(R.id.map_button);
            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RequestsActivity.this, SetLocationActivity.class);
                    startActivity(intent);
                }
            });

            Button viewLocation = (Button) findViewById(R.id.view_location);
            viewLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String requestDoc = "iyijYWL2nsbRtuTwcnYc"; //Just to test for now
                    Intent intent = new Intent(RequestsActivity.this, ViewLocationActivity.class);
                    intent.putExtra("REQUEST_DOC", requestDoc);
                    startActivity(intent);
                }
            });
        }


        // --------------------------Required for Toolbar---------------------------------//
        // set tool bar
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }


    /**
     * Check Google Services to make sure map requests is possible for user
     * @return
     */
    public boolean checkServices(){
        Log.d("LOCATION", "Check Google Services Version!");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(RequestsActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d("LOCATION", "Google Play Services is working!");
            return  true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d("Location", "Fixable error!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(RequestsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "We can't make map requests!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    // --------------------------Create Toolbar Menu---------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.activity_main_drawer);
        tb.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
        return true;
    }

    // --------------------------Create Toolbar Menu---------------------------------//
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.nav_my_profile:
                startActivity(new Intent(RequestsActivity.this, EditProfileActivity.class));
                break;
            case R.id.nav_my_books:
                startActivity(new Intent(RequestsActivity.this, OwnerBooksActivity.class));
                break;
            case R.id.nav_borrowed_books:
                startActivity(new Intent(RequestsActivity.this, BorrowedBooksActivity.class));
                break;
            case R.id.nav_search_books:
                startActivity(new Intent(RequestsActivity.this, SearchBooksActivity.class));
                break;
            case R.id.nav_notifications:
                startActivity(new Intent(RequestsActivity.this, NotificationsActivity.class));
                break;
            case R.id.nav_search_users:
                startActivity(new Intent(RequestsActivity.this, SearchUsernameActivity.class));
                break;
            case R.id.nav_my_requests:
                startActivity(new Intent(RequestsActivity.this, RequestsActivity.class));
                break;
            case R.id.nav_sign_out:
                firebaseAuth.signOut();
                Toast.makeText(RequestsActivity.this, "succcessfully signed out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RequestsActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
        return true;
    }
}