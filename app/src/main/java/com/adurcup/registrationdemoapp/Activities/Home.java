package com.adurcup.registrationdemoapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.adurcup.registrationdemoapp.CotentProvider.User;
import com.adurcup.registrationdemoapp.CotentProvider.UserLocalStore;
import com.adurcup.registrationdemoapp.R;

/**
 * Created by kshivang on 08/04/16.
 */
public class Home extends AppCompatActivity{
    UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userLocalStore = new UserLocalStore(this);

        User user = userLocalStore.getLoggedInUser();

        ((TextView) findViewById(R.id.name)).setText("Name : " + user.name);

        ((TextView) findViewById(R.id.mobile_no)).setText("Mobile No. : " + user.contact);

        ((TextView) findViewById(R.id.api_key)).setText("Api Key : " + user.apiKey);
        Button logout = (Button) findViewById(R.id.logout);
        logout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);
                startActivity(new Intent(Home.this, Login.class));
                finish();
            }
        });

    }
}
