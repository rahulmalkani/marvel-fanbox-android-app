package com.marvelfanbox.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    private GestureDetector gestureDetector;
    private static Context context;
    private TabLayout tabLayout;
    private DrawerLayout drawer;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tabLayout = findViewById(R.id.tab_layout);

        Fragment fragment = new PhasesFragment();
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HOME_FRAGMENT").commit();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.toolbarColor)));

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.toolbarColor));
        }

        gestureDetector = new GestureDetector(this);
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {

        Log.e("SWIPE", "onFling");
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
            return false;
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            Log.e("SWIPE", diffX + "       " + diffY);
            if (Math.abs(diffX) > Math.abs(diffY)) {
                Log.e("SWIPE", "OuterIf");
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(v) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        Log.e("SWIPE", "onFlingIf");
                        onSwipeRight();
                    } else {
                        Log.e("SWIPE", "onFlingElse");
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent e)
    {
        super.dispatchTouchEvent(e);
        return gestureDetector.onTouchEvent(e);
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();
        String TAG = "";

        if(id == R.id.nav_home) {
            if(!(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") instanceof PhasesFragment)) {
                getSupportActionBar().setTitle("Home");
                fragment = new PhasesFragment();
                TAG = "HOME_FRAGMENT";
            }

        }
        else if (id == R.id.nav_wallpapers) {
            if(!(getSupportFragmentManager().findFragmentByTag("WALLPAPER_FRAGMENT") instanceof WallpaperFragment)) {
                getSupportActionBar().setTitle("Wallpaper");
                fragment = new WallpaperFragment();
                TAG = "WALLPAPER_FRAGMENT";
            }
        }
        else if (id == R.id.nav_ringtones) {
            if(!(getSupportFragmentManager().findFragmentByTag("RINGTONE_FRAGMENT") instanceof RingtoneFragment)) {
                getSupportActionBar().setTitle("Ringtone");
                fragment = new RingtoneFragment();
                TAG = "RINGTONE_FRAGMENT";
            }
        }
        else if (id == R.id.nav_ffacts) {
            if(!(getSupportFragmentManager().findFragmentByTag("FUNFACTS_FRAGMENT") instanceof FunFactsFragment)) {
                getSupportActionBar().setTitle("Fun Facts");
                fragment = new FunFactsFragment();
                TAG = "FUNFACTS_FRAGMENT";
            }
        }
        else if (id == R.id.nav_news) {
            if(!(getSupportFragmentManager().findFragmentByTag("NEWS_FRAGMENT") instanceof NewsFragment)) {
                getSupportActionBar().setTitle("News");
                fragment = new NewsFragment();
                TAG = "NEWS_FRAGMENT";
            }
        }
        else if (id == R.id.nav_share) {
            Snackbar.make(drawer, "Coming Soon!", 600).show();

        }
        else if (id == R.id.nav_send) {
            Snackbar.make(drawer, "Coming Soon!", 600).show();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, TAG).commit();

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else {
            if(getSupportFragmentManager().findFragmentByTag("HOME_FRAGMENT") instanceof PhasesFragment)
                super.onBackPressed();
            else {
                Fragment fragment = new PhasesFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HOME_FRAGMENT").commit();
                getSupportActionBar().setTitle("Home");
            }

        }
    }

    private void onSwipeLeft() {
        Log.e("SWIPE", "onSwipeLeft");
        int pos = tabLayout.getSelectedTabPosition();
        if(pos == tabLayout.getTabCount())
            return;

        TabLayout.Tab tab = tabLayout.getTabAt(pos + 1);
        tab.select();
    }

    private void onSwipeRight() {
        Log.e("SWIPE", "onSwipeRight");
        int pos = tabLayout.getSelectedTabPosition();
        if(pos == 0)
            return;
        TabLayout.Tab tab = tabLayout.getTabAt(pos - 1);
        tab.select();
    }
}
