package com.ryuan.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainView = new MainView(this);
        setContentView(mMainView);
    }

    private int [][][] mBlock = {
           {{0,1,0,0},
            {0,1,0,0},
            {0,1,0,0},
            {0,1,0,0}},

           {{0,1,1,0},
            {0,1,0,0},
            {0,1,0,0},
            {0,0,0,0}},

           {{0,1,1,0},
            {0,0,1,0},
            {0,0,1,0},
            {0,0,0,0}},

           {{0,0,1,0},
            {0,1,1,0},
            {0,1,0,0},
            {0,0,0,0}},

           {{0,1,0,0},
            {0,1,1,0},
            {0,0,1,0},
            {0,0,0,0}},

           {{0,0,0,0},
            {0,1,1,0},
            {0,1,1,0},
            {0,0,0,0}},

           {{0,1,0,0},
            {1,1,1,0},
            {0,0,0,0},
            {0,0,0,0}}};

    private int BLOCK_UNIT_SIZE = 50;


    private int BOARD_START_X = 0;
    private int BOARD_START_Y = 0;
    private int BOARD_WIDTH = BLOCK_UNIT_SIZE * 10;
    private int BOARD_HEIGHT = BLOCK_UNIT_SIZE * 20;

    private class MainView extends View {
        public MainView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);
            canvas.drawPaint(paint);

            paint.setColor(Color.GREEN);
            canvas.drawRect(BOARD_START_X, BOARD_START_Y, BOARD_START_X + BOARD_WIDTH, BOARD_START_Y + BOARD_HEIGHT, paint);

            paint.setAntiAlias(false);
            paint.setColor(Color.WHITE);
            for (int row = 0; row < 4; ++row) {
                for (int col = 0; col < 4; ++col) {
                    if (mBlock[0][row][col] == 0)
                        continue;
                    canvas.drawRect(col * BLOCK_UNIT_SIZE, row * BLOCK_UNIT_SIZE, (col+1) * BLOCK_UNIT_SIZE, (row+1) * BLOCK_UNIT_SIZE, paint);
                }
            }
        }
    }
}
