package com.hegel.lockscreen;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;

public class FloatService extends Service implements OnTouchListener {

	private SharedPreferences mySP;
	protected static LayoutParams params;
	protected static WindowManager wm;
	protected static ImageButton suspended;

	int lastX, lastY;
	int paramX, paramY;

	private static final String TAG = "Hegel---FloatService";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

//		mySP = PreferenceManager.getDefaultSharedPreferences(this);
		mySP = getSharedPreferences("myfile", Service.MODE_PRIVATE);

		Log.i(TAG, "onCreate");
		createFloatView();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			paramX = params.x;
			paramY = params.y;
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;
			params.x = paramX + dx;
			params.y = paramY + dy;
			wm.updateViewLayout(suspended, params);
			break;
		}
		return false;
	}

	private void createFloatView() {

		wm = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
		params = new LayoutParams();

		params.type = LayoutParams.TYPE_SYSTEM_ALERT;

		params.format = PixelFormat.RGBA_8888;

		params.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;

		params.gravity = Gravity.LEFT;

		params.width = LayoutParams.WRAP_CONTENT;
		params.height = LayoutParams.WRAP_CONTENT;

		LayoutInflater inflater = LayoutInflater.from(getApplication());
		suspended = (ImageButton) inflater.inflate(R.layout.float_layout, null);
		suspended.setBackgroundColor(Color.TRANSPARENT);
		suspended.setOnTouchListener(this);

		suspended.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if (MainActivity.isActive()) {
					MainActivity.systemLock();
				}
				return true;
			}
		});

		if (mySP.getBoolean("isFloatOK", false)) {
			wm.addView(suspended, params);
		}
	}

	protected static void openFloat() {
		wm.addView(suspended, params);
	}

	protected static void closeFloat() {
		wm.removeViewImmediate(suspended);
	}

}
