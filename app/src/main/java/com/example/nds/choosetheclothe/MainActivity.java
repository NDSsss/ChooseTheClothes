package com.example.nds.choosetheclothe;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nds.choosetheclothe.adding.AddingFragment;
import com.example.nds.choosetheclothe.app.App;
import com.example.nds.choosetheclothe.app.DataBase;
import com.example.nds.choosetheclothe.clothe.Clothe;
import com.example.nds.choosetheclothe.eventbus.Event;
import com.example.nds.choosetheclothe.eventbus.EventBus;
import com.example.nds.choosetheclothe.eventbus.IEventObserver;
import com.example.nds.choosetheclothe.eventbus.IGetEventBus;
import com.example.nds.choosetheclothe.eventbus.events.SettingClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateClotheEvent;
import com.example.nds.choosetheclothe.eventbus.events.UpdateTempEvent;
import com.example.nds.choosetheclothe.interfaces.ILoadingListener;
import com.example.nds.choosetheclothe.selection.SelectionFragment;
import com.example.nds.choosetheclothe.selectioninfinite.SelectionInfiniteFragment;
import com.example.nds.choosetheclothe.weather.IWeatherService;
import com.example.nds.choosetheclothe.weather.WeatherResponce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements ILoadingListener, IGetEventBus, IEventObserver {

    public static final int LOAD_CLOTHE = 0;
    public static final int UPDATE_CLOTHE = 1;
    private static final String TAG = "MainActivity";
    TextView tvSityNameDate;
    TextView tvTemperature;
    TextView tvStatusConnection;
    ProgressBar pBarConnectionStatus;
    RelativeLayout clContainer;
    RelativeLayout rlProgress;
    EventBus mEventBus;
    SelectionInfiniteFragment infiniteScroll;
    BottomNavigationView bnvMain;
    RelativeLayout rlTempContainer;
    DataBase base;
    ArrayList<Clothe> clothes;
    List<Clothe> rawClothes;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rlProgress = findViewById(R.id.rl_progress);
        handler = new Handler();
        base = Room.databaseBuilder(this, DataBase.class, "database")
                .build();
        loadData();
        mEventBus = new EventBus();
        mEventBus.addObserver(this::onEvent);
        clContainer = findViewById(R.id.cl_container);
        bnvMain = findViewById(R.id.bnv_main);
        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_gallery:
                        openInfiniteScrollFragment();
                        break;
                }
                return false;
            }
        });
        tvTemperature = findViewById(R.id.tv_main_temp);
        tvSityNameDate = findViewById(R.id.tv_main_city);
        rlTempContainer = findViewById(R.id.rl_main_weather_container);
        rlTempContainer.setOnClickListener(v -> loadTemp());
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    private void handleBaseLoad(Message msg) {
        switch (msg.arg1) {
            case LOAD_CLOTHE:
                createFragments((List<Clothe>)msg.obj);
                bnvMain.setSelectedItemId(R.id.nav_gallery);
                SettingClotheEvent event = new SettingClotheEvent((List<Clothe>) msg.obj);
                mEventBus.notifyEvent(event);
                loadTemp();
                completeLoading();
                break;
            case UPDATE_CLOTHE:
                completeLoading();
                break;
        }
    }

    private void loadData() {
        Log.d(TAG, "loadData: ");
        startLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                rawClothes = base.employeeDao().getAll();
                if (rawClothes == null || rawClothes.size() == 0) {
                    Log.d(TAG, "run: ");
                            ArrayList<Clothe> newClothes = new ArrayList<>();
                    newClothes.add(new Clothe(10, 20, 5, R.drawable.thirt_midle, "tMiddle", Clothe.TYPE_THIRT));
                    newClothes.add(new Clothe(15, 30, 5, R.drawable.thirt_cold, "tCold", Clothe.TYPE_THIRT));
                    newClothes.add(new Clothe(-15, 10, 5, R.drawable.thirt_warm, "tWarm", Clothe.TYPE_THIRT));
                    newClothes.add(new Clothe(10, 20, 5, R.drawable.pans_midle, "pMiddle", Clothe.TYPE_PANS));
                    newClothes.add(new Clothe(15, 30, 5, R.drawable.pans_cold, "pCold", Clothe.TYPE_PANS));
                    newClothes.add(new Clothe(-15, 10, 5, R.drawable.pans_warm, "pWarm", Clothe.TYPE_PANS));
                    newClothes.add(new Clothe(10, 20, 5, R.drawable.shoes_midle, "sMiddle", Clothe.TYPE_SHOES));
                    newClothes.add(new Clothe(15, 30, 5, R.drawable.shoes_cold, "sCold", Clothe.TYPE_SHOES));
                    newClothes.add(new Clothe(-15, 10, 5, R.drawable.shoes_warm, "sWarm", Clothe.TYPE_SHOES));
                    base.employeeDao().insert(newClothes);
                    rawClothes = base.employeeDao().getAll();
                }
                Collections.sort(rawClothes);
                Message message = new Message();
                message.obj = rawClothes;
                message.arg1 = LOAD_CLOTHE;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        handleBaseLoad(message);
                    }
                });
            }
        }).start();

    }

    private void updateClothe(Clothe clothe) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                base.employeeDao().update(clothe);
            }
        }).start();
    }

    private void createFragments(List<Clothe> rawClothes) {
        ArrayList<Clothe> clotheArrayList = (ArrayList<Clothe>) rawClothes;
        infiniteScroll = new SelectionInfiniteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(SelectionInfiniteFragment.SELECTION_INFINITE_FRAGMENT_RAWR_CLOTHES,clotheArrayList);
        infiniteScroll.setArguments(bundle);
        infiniteScroll.setLoadingListener(this);
        infiniteScroll.setGetEventBus(this::getEventBus);
//        infiniteScroll.initData(clothes);
        mEventBus.addObserver(infiniteScroll);
    }

    private void openInfiniteScrollFragment() {
        if (infiniteScroll != null) {
            getSupportFragmentManager().beginTransaction().replace(clContainer.getId(), infiniteScroll).commitAllowingStateLoss();
        }
    }

    private void openSelectionFragment() {
        SelectionFragment selectionFragment = new SelectionFragment();
        selectionFragment.setLoadingListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_container, selectionFragment).commit();
    }

    private void openAddingDragment() {
        AddingFragment addingFragment = new AddingFragment();
        addingFragment.setLoadingListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.cl_container, addingFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadTemp() {
        startLoading();
        IWeatherService weatherService = App.get(this).getRetrofit().create(IWeatherService.class);
        final retrofit2.Call<WeatherResponce> weatherResponceCall = weatherService.getWeatherById(693805, getResources().getString(R.string.weather_api_key), "metric");
        weatherResponceCall.enqueue(new Callback<WeatherResponce>() {
            @Override
            public void onResponse(retrofit2.Call<WeatherResponce> call, Response<WeatherResponce> response) {
                completeLoading();
                handleWeatherResponce(response.body());
            }

            @Override
            public void onFailure(retrofit2.Call<WeatherResponce> call, Throwable t) {
                completeLoading();
                tvTemperature.setText("Sorry, but server response is: " + t.getMessage());
                showError(t.getMessage());
                mEventBus.notifyEvent(new UpdateTempEvent(-100));
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void handleWeatherResponce(WeatherResponce responce) {
        tvTemperature.setText((String.valueOf(responce.getMain().getTemp())));
        tvSityNameDate.setText(responce.getName());
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


    @Override
    public EventBus getEventBus() {
        return mEventBus;
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getmEventType()) {
            case Event.UPDATE_CLOTHE_IN_DB:
                updateClothe(((UpdateClotheEvent) event).getClothe());
                break;
        }
    }
}
