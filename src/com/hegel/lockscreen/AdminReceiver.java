package com.hegel.lockscreen;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AdminReceiver extends DeviceAdminReceiver {

	@Override
	public DevicePolicyManager getManager(Context context) {
		// TODO Auto-generated method stub
		return super.getManager(context);
	}

	@Override
	public ComponentName getWho(Context context) {
		// TODO Auto-generated method stub
		return super.getWho(context);
	}

	@Override
	public CharSequence onDisableRequested(Context context, Intent intent) {
		// TODO Auto-generated method stub
		return super.onDisableRequested(context, intent);
	}

	@Override
	public void onDisabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onDisabled(context, intent);
		Toast.makeText(context, "禁用设备管理", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEnabled(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onEnabled(context, intent);
		Toast.makeText(context, "启动设备管理", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPasswordChanged(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordChanged(context, intent);
	}

	@Override
	public void onPasswordExpiring(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordExpiring(context, intent);
	}

	@Override
	public void onPasswordFailed(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordFailed(context, intent);
	}

	@Override
	public void onPasswordSucceeded(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onPasswordSucceeded(context, intent);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public IBinder peekService(Context myContext, Intent service) {
		// TODO Auto-generated method stub
		return super.peekService(myContext, service);
	}

}
