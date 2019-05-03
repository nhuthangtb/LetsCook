package com.CNPM.letcook.View.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.CNPM.letcook.R;

public class FlashScreenActivity extends AppCompatActivity {

    TextView txtVersion, txtDeverlopTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashscreen);
        txtVersion = findViewById(R.id.txtVersion);
        txtDeverlopTeam = findViewById(R.id.txtDeverlopTeam);
        txtDeverlopTeam.setText("Power by" + getString(R.string.team));
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtVersion.setText(getString(R.string.version) + " " + packageInfo.versionName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent iLogin = new Intent(FlashScreenActivity.this,LoginActivity.class);
                    startActivity(iLogin);
                    finish();
                }
            },3000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
