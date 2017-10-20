package com.example.android.smartbear;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.smartbear.courses.CourseFragment;
import com.example.android.smartbear.database.DataBaseManager;
import com.example.android.smartbear.tools.ToolsFragment;

import java.util.Locale;

import butterknife.Bind;

import static com.example.android.smartbear.constants.Constants.ADMIN_KEY;
import static com.example.android.smartbear.constants.Constants.EMAIL_KEY;
import static com.example.android.smartbear.constants.Constants.NAME_KEY;
import static com.example.android.smartbear.constants.Constants.PASSWORD_KEY;
import static com.example.android.smartbear.constants.Constants.PREFERENCE_FILE_KEY;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    static NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.contains(EMAIL_KEY) && sharedPreferences.contains(PASSWORD_KEY)) {
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            String password = sharedPreferences.getString(PASSWORD_KEY, "");

            if (!DataBaseManager.validateUser(email, password)) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }

            View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
            TextView nameInHeader = navHeaderView.findViewById(R.id.name_in_nav_header);
            if (sharedPreferences.getBoolean(ADMIN_KEY, false)) {
                nameInHeader.setText(sharedPreferences.getString(NAME_KEY, "") + " (ya admin)");
            } else {
                nameInHeader.setText(sharedPreferences.getString(NAME_KEY, ""));
            }
            TextView emailInHeader = navHeaderView.findViewById(R.id.email_in_nav_header);
            emailInHeader.setText(sharedPreferences.getString(EMAIL_KEY, ""));
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new CourseFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_courses_list) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, new CourseFragment());
            fragmentTransaction.commit();
        } else if (id == R.id.nav_tools) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.main_container, new ToolsFragment());
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static NavigationView getNavigationView() {
        return navigationView;
    }
}
