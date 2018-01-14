package com.example.android.smartbear;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.smartbear.navigation.Navigator;
import com.example.android.smartbear.session.SessionManager;
import com.example.android.smartbear.session.SessionManagerImpl;
import com.example.android.smartbear.user.UserModel;
import com.squareup.otto.Bus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ActionBarDrawerToggle toggle;
    FragmentTransaction fragmentTransaction;

    SessionManager session;

    private FeedPagerFragment courseTabFragment;

    public static Bus bus = new Bus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigator = new Navigator();

        session = new SessionManagerImpl(getApplicationContext());

        if (session.checkLogin()) {
            finish();
        }

        UserModel user = session.getUserDetails();
        String userName = user.getName();
        String userEmail = user.getEmail();
        Boolean userIsAdmin = user.isAdmin();

        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView nameInHeader = navHeaderView.findViewById(R.id.name_in_nav_header);
        if (userIsAdmin) {
            nameInHeader.setText(userName + " (ya admin)");
        } else {
            nameInHeader.setText(userName);
        }

        TextView emailInHeader = navHeaderView.findViewById(R.id.email_in_nav_header);
        emailInHeader.setText(userEmail);

        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);

        if (savedInstanceState == null) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            courseTabFragment = new FeedPagerFragment();
            fragmentTransaction.add(R.id.main_container, courseTabFragment);
            fragmentTransaction.commit();
        } else {
            courseTabFragment = (FeedPagerFragment) getSupportFragmentManager().findFragmentById(R.id.main_container);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        } else if (id == R.id.logout_user) {
            session.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_courses_list) {
            navigator.navigateToFeedPagerFragment(this, R.id.main_container);
        } else if (id == R.id.nav_tools) {
            navigator.navigateToToolsFragment(this, R.id.main_container);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
