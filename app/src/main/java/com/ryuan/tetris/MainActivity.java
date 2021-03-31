package com.ryuan.tetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    MainView mMainView;
    FrameLayout mMainLayout;
    LinearLayout mButtonLayout;

    Button mLeft, mRight, mRotate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainLayout = new FrameLayout(this);

        mMainView = new MainView(this);
        mMainView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mMainLayout.addView(mMainView);

        mButtonLayout = new LinearLayout(this);
        mButtonLayout.setOrientation(LinearLayout.HORIZONTAL);
        mButtonLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mButtonLayout.setGravity(1);

        mLeft = new Button(this);
        mRotate = new Button(this);
        mRight = new Button(this);
        mLeft.setText("<");
        mRotate.setText(" ROTATE ");
        mRight.setText(">");
        mButtonLayout.addView(mLeft);
        mButtonLayout.addView(mRotate);
        mButtonLayout.addView(mRight);

        mMainLayout.addView(mButtonLayout);
        setContentView(mMainLayout);
        mMainView.Start();
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
    private int BOARD_START_Y = 200;
    private int BOARD_WIDTH = 10;
    private int BOARD_HEIGHT = 20;

    private int[][] mBoard = new int[BOARD_WIDTH + 2][ BOARD_HEIGHT + 1];

    private class MainView extends View {
        public MainView(Context context) {
            super(context);;
        }

        public void Start() {
            // initialize board
            for (int i = 1; i < BOARD_WIDTH; ++i) {
                for (int j = 0; j < BOARD_HEIGHT; ++j) {
                    mBoard[i][j] = 0;
                }
            }
            for (int j = 0; j < BOARD_HEIGHT + 1; ++j) {
                mBoard[0][j] = 2; // left wall
                mBoard[BOARD_WIDTH+1][j] = 2; // right wall
            }
            for (int i = 1; i < BOARD_WIDTH; ++i) {
                mBoard[i][BOARD_HEIGHT] = 2; // bottom wall
            }
            mTimer.run();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLUE);
            canvas.drawPaint(paint);

            paint.setColor(Color.GREEN);
            canvas.drawRect(BOARD_START_X, BOARD_START_Y, BOARD_START_X + BOARD_WIDTH * BLOCK_UNIT_SIZE, BOARD_START_Y + BOARD_HEIGHT * BLOCK_UNIT_SIZE, paint);

            paint.setAntiAlias(false);
            paint.setColor(Color.WHITE);
            for (int row = 0; row < 4; ++row) {
                for (int col = 0; col < 4; ++col) {
                    if (mBlock[0][row][col] == 0)
                        continue;

                    int computedRow = mBlockLine + row;
                    int computedRowPosition = BOARD_START_Y + computedRow * BLOCK_UNIT_SIZE;
                    canvas.drawRect(col * BLOCK_UNIT_SIZE, computedRowPosition, (col+1) * BLOCK_UNIT_SIZE, computedRowPosition + BLOCK_UNIT_SIZE, paint);
                }
            }
        }

        private int mBlockLine = 0;
        void processNext() {
            int nextBlockLine = mBlockLine+1;
            // check block is overlapped with other block or wall
            for (int row = 0; row < 4; ++row) {
                for (int col = 0; col < 4; ++col) {
                    if (mBlock[0][row][col] == 0)
                        continue;
                    int computedRow = nextBlockLine + row;
                    int computedCol = col;
                    if (mBoard[computedCol][computedRow] != 0)
                        return;
                }
            }
            mBlockLine++;

        }
        Handler mHandler = new Handler(Looper.getMainLooper());
        Runnable mTimer = new Runnable() {
            @Override
            public void run() {
                processNext();
                invalidate();
                mHandler.postDelayed(this, 1000);
            }
        };
    }
}
