package co.edu.pdam.eci.persistenceapiintegration.network;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import co.edu.pdam.eci.persistenceapiintegration.R;
import co.edu.pdam.eci.persistenceapiintegration.adapter.TeamsAdapter;

/**
 * Created by 2105409 on 4/18/18.
 */

public class TeamImageGet implements Runnable {

    private TeamsAdapter.ViewHolder viewHolder;
    private String imageUrl;
    private Activity activity;
    private Bitmap bitMap;

    public TeamImageGet(TeamsAdapter.ViewHolder viewHolder, String imageUrl, Activity activity) {
        this.viewHolder = viewHolder;
        this.imageUrl = imageUrl;
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            bitMap = BitmapFactory.decodeStream(new java.net.URL(imageUrl).openStream());
        } catch (IOException e) {
            bitMap = null;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(bitMap != null){
                    viewHolder.image.setImageBitmap(bitMap);
                }else{
                    viewHolder.image.setImageResource(R.mipmap.ic_launcher_round);
                }
            }
        });


    }
}