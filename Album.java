package com.example.kgitbank.kakao.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.kgitbank.kakao.R;

public class Album extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album);
        final Context ctx = Album.this;
        GridView myAlbum = findViewById(R.id.myAlbum);
        String[] arr = {"photo_01","photo_11","photo_21","photo_31","photo_41"};
        myAlbum.setAdapter(new Picture(ctx, arr));
    }

    private class Picture extends BaseAdapter{
        private Context ctx;
        private String[] pictures;

        public Picture(Context ctx, String[] pictures){
            this.ctx = ctx;
            this.pictures = pictures;
        }

        @Override
        public int getCount(){
            return pictures.length;
        }

        @Override
        public Object getItem(int i) {
            return pictures[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
            View gridView;
            if(v==null){
                gridView = new GridView(ctx);
                gridView = inflater.inflate(null,null);
                ImageView iv = gridView.findViewById(R.id.photo);
                String ph = pictures[i];
                switch (ph){
                    case "photo_01" : iv.setImageDrawable(R.drawable.photo_01); break;
                    case "photo_11" : iv.setImageDrawable(R.drawable.photo_11); break;
                    case "photo_21" : iv.setImageDrawable(R.drawable.photo_21); break;
                    case "photo_31" : iv.setImageDrawable(R.drawable.photo_31); break;
                    case "photo_41" : iv.setImageDrawable(R.drawable.photo_41); break;
                }
            } else {
                gridView = null;
            }
            return null;
        }
    }
}
