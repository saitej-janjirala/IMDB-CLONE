package com.saitejajanjirala.imdbclone.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.navigation.NavigationBarView;
import com.saitejajanjirala.imdbclone.R;


import com.saitejajanjirala.imdbclone.databinding.ActivityMainBinding;
import com.saitejajanjirala.imdbclone.ui.home.HomeFragment;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
    }


}