package com.shanqb.wanka.FloatView;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.shanqb.wanka.BaseApplication;

public class FloatView extends ImageView {
    private float x;
    private float y;

    private float xm;
    private float ym;

    private float mTouchX;
    private float mTouchY;

    private float mStartX;
    private float mStartY;

    private OnClickListener mClickListener;

    private WindowManager windowManager = (WindowManager) getContext()
            .getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
    private WindowManager.LayoutParams windowManagerParams = ((BaseApplication) getContext()
            .getApplicationContext()).getWindowParams();

    public FloatView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){

        Rect frame = new Rect();
        getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        System.out.println("statusBarHeight:"+statusBarHeight);

        x = event.getRawX();
        y = event.getRawY() - statusBarHeight;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                mTouchX = event.getX();
//                mTouchY = event.getY();
                xm = event.getRawX();
                ym = event.getRawY();
                mStartX = x;
                mStartY = y;
                Log.i("tag", "down============");
                break;
            case MotionEvent.ACTION_MOVE:
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("tag", "ACTION_UP============");
                //updateViewPosition();
//                mTouchX = mTouchY = 0;
                if ((x - mStartX) < 5 && (y - mStartY) < 5) {
                    if(mClickListener!=null) {
                        mClickListener.onClick(this);
                    }
                }
                break;
        }
        return false;

    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mClickListener = l;
    }

    private void updateViewPosition() {
        Log.i("tag", "xxxxx==" + x + "====startY"
                + y);
        Log.i("tag", "ACTION_MOVE" + mTouchX + "====startY"
                + mTouchY);
        Log.i("tag", "xm" + xm + "====startY"
                + ym);
        windowManagerParams.x = (int) (xm - x - mTouchX);
        windowManagerParams.y = (int) (y + mTouchY - ym);
        Log.i("tag", "windowManagerParams==" + windowManagerParams.x + "====startY"
                + windowManagerParams.x);
        windowManager.updateViewLayout(this, windowManagerParams);
    }
}
