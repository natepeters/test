package com.offsec.nethunter;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.offsec.nethunter.utils.NhPaths;
import com.offsec.nethunter.utils.ShellExecuter;

public class EditSourceActivity extends AppCompatActivity {

    private String configFilePath = "";
    NhPaths nh;
    ShellExecuter exe = new ShellExecuter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nh = new NhPaths();
        Bundle b = getIntent().getExtras();
        configFilePath = b.getString("path");
        setContentView(R.layout.source);
        if (Build.VERSION.SDK_INT >= 21) {
            // detail for android 5 devices
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkTitle));
        }

        EditText source = (EditText) findViewById(R.id.source);
        source.setText("Loading...\n\nFILE: " + configFilePath);
        exe.ReadFile_ASYNC(configFilePath, source);

        ActionBar ab = getSupportActionBar();
        if (ab != null ) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        nh.showMessage("File Loaded");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateSource(View arg0) {

        EditText source = (EditText) findViewById(R.id.source);
        String newSource = source.getText().toString();
        Boolean isSaved = exe.SaveFileContents(newSource, configFilePath);
        if(isSaved){
            nh.showMessage("Source updated");
        } else {
            nh.showMessage("Source not updated");
        }
    }
}