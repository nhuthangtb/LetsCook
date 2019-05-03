package com.CNPM.letcook.View.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.CNPM.letcook.Adapters.TabAdapter;
import com.CNPM.letcook.R;
import com.CNPM.letcook.View.Fragment.AddDishFragment;
import com.CNPM.letcook.View.Fragment.HomePageFragment;
import com.CNPM.letcook.View.Fragment.InfoUserFragment;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        viewPager =  findViewById(R.id.viewPager);
        tabLayout =  findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new InfoUserFragment(), "User");
        adapter.addFragment(new HomePageFragment(), "Home Page");
        adapter.addFragment(new AddDishFragment(), "Add dish");

        //tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }


}