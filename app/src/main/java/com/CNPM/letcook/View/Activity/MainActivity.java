package com.CNPM.letcook.View.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.CNPM.letcook.Adapters.TabAdapter;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.Fragment.AddDishFragment;
import com.CNPM.letcook.View.Fragment.HomePageFragment;
import com.CNPM.letcook.View.Fragment.InfoUserFragment;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    BottomNavigationView nav_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            selectFragment = new HomePageFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selectFragment = new AddDishFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectFragment = new InfoUserFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                    return true;
                }
            };


}