package com.example.GradeCircle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.broadcastbestpractice.R;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.ItemAction;

import java.util.ArrayList;

public class album_grade extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_grade);


        /*Album.image(this) // Image selection.
                .multipleChoice()
                .camera()
                .columnCount()
                .selectCount()
                .checkedList(mAlbumFiles)
                .filterSize() // Filter the file size.
                .filterMimeType() // Filter file format.
                .afterFilterVisibility() // Show the filtered files, but they are not available.
                .onResult(new Action<ArrayList<AlbumFile>>() {
                    @Override
                    public void onAction(@NonNull ArrayList<AlbumFile> result) {
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();


        Album.camera(this) // Camera function.
                .image() // Take Picture.
                .filePath() // File save path, not required.
                .onResult(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();


        // Preview AlbumFile:
        Album.galleryAlbum(this);
        Album.gallery(this)
                .checkedList(imageList) // List of image to view: ArrayList<String>.
                .checkable(true) // Whether there is a selection function.
                .onResult(new Action<ArrayList<String>>() { // If checkable(false), action not required.
                    @Override
                    public void onAction(@NonNull ArrayList<String> result) {
                    }
                })
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {
                    }
                })
                .start();


        Album.gallery(this).itemClick(new ItemAction<String>() {
            @Override
            public void onAction(Context context, String item) {
            }
        })
                .itemLongClick(new ItemAction<String>() {
                    @Override
                    public void onAction(Context context, String item) {
                    }
                })
                .start();*/


    }
}
