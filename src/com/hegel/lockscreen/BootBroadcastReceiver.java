package com.hegel.lockscreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e("Hegel---BootBroadcastReceiver", "receive BOOT_COMPLETED");
		MainActivity.isFirstRun = true;
		Intent mIntent = new Intent(context, MainActivity.class);
		mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(mIntent);
	}
	
} 