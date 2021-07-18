package com.saitejajanjirala.imdbclone.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.saitejajanjirala.imdbclone.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class Utils {



    public static Boolean getConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void LoadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(Constants.IMAGE_BASE_URL + url)
                .error(R.drawable.ic_baseline_error_24)
                .into(imageView);
    }

    public static ArrayList<String> getGenresFromIds(List<Integer> ids) {
        HashMap<Integer,String> map=new HashMap<>();
        map.put(28,"Action");
        map.put(12,"Adventure");
        map.put(16,"Animation");
        map.put(35,"Comedy");
        map.put(80,"Crime");
        map.put(99,"Documentary");
        map.put(18,"Drama");
        map.put(10751,"Family");
        map.put(14,"Fantasy");
        map.put(36,"History");
        map.put(27,"Horror");
        map.put(10402,"Music");
        map.put(9648,"Mystery");
        map.put(10749,"Romance");
        map.put(878,"Science Fiction");
        map.put(10770,"TV Movie");
        map.put(53,"Thriller");
        map.put(10752,"War");
        map.put(37,"Western");
        ArrayList<String> genres = new ArrayList<>();
        for(int i:ids){
            String val=map.get(i);
            if(val!=null){
                genres.add(val);
            }
        }
        return genres;

    }

}
