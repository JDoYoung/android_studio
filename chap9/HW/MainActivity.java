package com.example.chap_9;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int GRID_SIZE = 3;
    private static final int TILE_COUNT = GRID_SIZE * GRID_SIZE;
    private static final int BLANK_TILE_INDEX = 8;
    private static final int ORIGINAL_IMAGE_RES_ID = R.drawable.renoir04;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<Bitmap> originalTiles = new ArrayList<>();
    private List<Bitmap> currentTiles = new ArrayList<>();
    private int blankTilePosition = BLANK_TILE_INDEX;

    private final int[] imageIds = {
            R.id.iv_tile_0, R.id.iv_tile_1, R.id.iv_tile_2,
            R.id.iv_tile_3, R.id.iv_tile_4, R.id.iv_tile_5,
            R.id.iv_tile_6, R.id.iv_tile_7, R.id.iv_tile_8
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button resetButton = findViewById(R.id.btn_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPuzzle();
            }
        });

        for (int id : imageIds) {
            ImageView imageView = findViewById(id);
            if (imageView != null) {
                imageViews.add(imageView);
                imageView.setOnClickListener(this);
            }
        }

        splitImage();
        resetPuzzle();
    }

    private void splitImage() {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), ORIGINAL_IMAGE_RES_ID);
        if (originalBitmap == null) return;

        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();

        int chunkWidth = width / GRID_SIZE;
        int chunkHeight = height / GRID_SIZE;

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (originalTiles.size() == BLANK_TILE_INDEX) {
                    originalTiles.add(null);
                } else {
                    Bitmap chunkBitmap = Bitmap.createBitmap(
                            originalBitmap,
                            col * chunkWidth,
                            row * chunkHeight,
                            chunkWidth,
                            chunkHeight
                    );
                    originalTiles.add(chunkBitmap);
                }
            }
        }
    }

    private void resetPuzzle() {
        currentTiles.clear();
        currentTiles.addAll(originalTiles);

        blankTilePosition = BLANK_TILE_INDEX;

        updatePuzzleView();
        Toast.makeText(this, "퍼즐이 초기화되었습니다.", Toast.LENGTH_SHORT).show();
    }

    private void updatePuzzleView() {
        for (int i = 0; i < TILE_COUNT; i++) {
            ImageView tileView = imageViews.get(i);
            Bitmap tileBitmap = currentTiles.get(i);

            if (tileBitmap == null) {
                tileView.setImageDrawable(null);
                tileView.setVisibility(View.INVISIBLE);
                blankTilePosition = i;
            } else {
                tileView.setImageBitmap(tileBitmap);
                tileView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        ImageView clickedView = (ImageView) v;

        int clickedPosition = imageViews.indexOf(clickedView);

        if (isAdjacent(clickedPosition, blankTilePosition)) {
            swapTiles(clickedPosition, blankTilePosition);
            updatePuzzleView();
        } else {
        }
    }

    private boolean isAdjacent(int pos1, int pos2) {
        int r1 = pos1 / GRID_SIZE;
        int c1 = pos1 % GRID_SIZE;
        int r2 = pos2 / GRID_SIZE;
        int c2 = pos2 % GRID_SIZE;

        return (Math.abs(r1 - r2) + Math.abs(c1 - c2) == 1);
    }

    private void swapTiles(int pos1, int pos2) {
        Collections.swap(currentTiles, pos1, pos2);

        blankTilePosition = pos1;
    }
}