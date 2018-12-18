package com.example.nds.choosetheclothe;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nds.choosetheclothe.adding.AddingFragment;
import com.example.nds.choosetheclothe.app.App;
import com.example.nds.choosetheclothe.eventbus.EventBus;
import com.example.nds.choosetheclothe.eventbus.events.UpdateTempEvent;
import com.example.nds.choosetheclothe.interfaces.ILoadingListener;
import com.example.nds.choosetheclothe.selection.SelectionFragment;
import com.example.nds.choosetheclothe.selectioninfinite.SelectionInfiniteFragment;
import com.example.nds.choosetheclothe.weather.IWeatherService;
import com.example.nds.choosetheclothe.weather.WeatherResponce;

import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ILoadingListener {

    TextView tvSityNameDate;
    TextView tvTemperature;
    TextView tvStatusConnection;
    ProgressBar pBarConnectionStatus;
    ConstraintLayout clContainer;
    RelativeLayout rlProgress;
    EventBus mEventBus;
    SelectionInfiniteFragment infiniteScroll;
    ImageView ivRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEventBus = new EventBus();
        createFragments();
        clContainer = findViewById(R.id.cl_container);
        rlProgress = findViewById(R.id.rl_progress);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvSityNameDate = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_drawer_header_city_date);
        tvStatusConnection= (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_progress_bar_drawer_header);
        pBarConnectionStatus= (ProgressBar) navigationView.getHeaderView(0).findViewById(R.id.progress_bar_drawer);
        tvTemperature = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_drawer_header_teperatures);
        ivRefresh = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ic_drawer_temp_refresh);
        ivRefresh.setOnClickListener(v->loadTemp());
        tvSityNameDate.setText("Simferopol");

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            openAddingDragment();
        } else if (id == R.id.nav_gallery) {
            openSelectionFragment();
        } else if (id == R.id.nav_slideshow) {
            openInfiniteScrollFragment();
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createFragments(){
        infiniteScroll = new SelectionInfiniteFragment();
        infiniteScroll.setLoadingListener(this);
        mEventBus.addObserver(infiniteScroll);
    }

    private void openInfiniteScrollFragment(){
        if(infiniteScroll!=null) {
            getSupportFragmentManager().beginTransaction().replace(clContainer.getId(), infiniteScroll).commitAllowingStateLoss();
        }
    }

    private void openSelectionFragment(){
        SelectionFragment selectionFragment = new SelectionFragment();
        selectionFragment.setLoadingListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_container,selectionFragment).commit();
    }

    private void openAddingDragment(){
        AddingFragment addingFragment = new AddingFragment();
        addingFragment.setLoadingListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_container,addingFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTemp();
    }

    private void loadTemp(){
        IWeatherService weatherService = App.get(this).getRetrofit().create(IWeatherService.class);
        final retrofit2.Call<WeatherResponce> weatherResponceCall = weatherService.getWeatherById(693805, getResources().getString(R.string.weather_api_key), "metric");
        weatherResponceCall.enqueue(new Callback<WeatherResponce>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherResponce> call, Response<WeatherResponce> response) {
                tvStatusConnection.setVisibility(View.GONE);
                pBarConnectionStatus.setVisibility(View.GONE);
                handleWeatherResponce(response.body());

            }

            @Override
            public void onFailure(retrofit2.Call<WeatherResponce> call, Throwable t) {
                tvTemperature.setText("Sorry, but server response is: " + t.getMessage());
                tvStatusConnection.setVisibility(View.GONE);
                pBarConnectionStatus.setVisibility(View.GONE);
                showError(t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void handleWeatherResponce(WeatherResponce responce) {
        tvTemperature.setText(responce.getName().concat(String.valueOf(responce.getMain().getTemp())));
        mEventBus.notifyEvent(new UpdateTempEvent(responce.getMain().getTemp()));
    }

    @Override
    public void startLoading() {
        rlProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void completeLoading() {
        rlProgress.setVisibility(View.GONE);
    }

    @Override
    public void errorLoading(String errorMessage) {

    }


}
