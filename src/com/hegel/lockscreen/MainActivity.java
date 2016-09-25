package com.hegel.lockscreen;

import android.os.Bundle;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "Hegel---MainActivity";
	private static DevicePolicyManager policyManager;
	private static ComponentName componentName;
	protected static boolean isFirstRun = false;
	private SharedPreferences mySP;
	private Button custlock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// mySP = PreferenceManager.getDefaultSharedPreferences(this);
		mySP = getSharedPreferences("myfile", Activity.MODE_PRIVATE);

		// 获取设备管理服务
		policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

		// AdminReceiver 继承自 DeviceAdminReceiver
		componentName = new ComponentName(this, AdminReceiver.class);

		if (isFirstRun) {
			isFirstRun = false;

			startService(new Intent(this, FloatService.class));

			finish();
		}

		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		Button active = (Button) findViewById(R.id.active);
		Button unactive = (Button) findViewById(R.id.unactive);
		Button syslock = (Button) findViewById(R.id.syslock);
		custlock = (Button) findViewById(R.id.custlock);
		if (mySP.getBoolean("isFloatOK", false)) {
			custlock.setText(R.string.custlock_off);
		} else {
			custlock.setText(R.string.custlock_on);
		}

		active.setOnClickListener(this);
		unactive.setOnClickListener(this);
		syslock.setOnClickListener(this);
		custlock.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.active:
			activeManage();
			break;
		case R.id.unactive:
			unActiveManage();
			break;
		case R.id.syslock:
			systemLock();
			break;
		case R.id.custlock:
			Log.i(TAG, "click custlock");
			if (FloatService.wm == null || FloatService.suspended == null || FloatService.params == null) {
				startService(new Intent(this, FloatService.class));
				Toast.makeText(this, "后台服务初始化中……", Toast.LENGTH_SHORT).show();
				break;
			}
			startService(new Intent(this, FloatService.class));
			SharedPreferences.Editor editor = mySP.edit();
			if (mySP.getBoolean("isFloatOK", false)) {
				FloatService.closeFloat();
				custlock.setText(R.string.custlock_on);
				editor.putBoolean("isFloatOK", false);
			} else {
				FloatService.openFloat();
				custlock.setText(R.string.custlock_off);
				editor.putBoolean("isFloatOK", true);
			}
			editor.apply();
			break;
		default:
			break;
		}
	}

	/**
	 * 激活设备管理权限 成功执行激活时，DeviceAdminReceiver中的 onEnabled 会响应
	 */
	private void activeManage() {
		// 启动设备管理(隐式Intent) - 在AndroidManifest.xml中设定相应过滤器
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

		// 权限列表
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

		// 描述(additional explanation)
		// intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
		// "------ 其他描述 ------");

		startActivityForResult(intent, 0);
	}

	/**
	 * 应用是否已在设备管理中激活
	 */
	protected static boolean isActive() {
		boolean active = policyManager.isAdminActive(componentName);
		return active;
	}

	/**
	 * 禁用设备管理权限 成功执行禁用时，DeviceAdminReceiver中的 onDisabled 会响应
	 */
	private void unActiveManage() {
		if (isActive()) {
			policyManager.removeActiveAdmin(componentName);
		}
	}

	/**
	 * 调出系统锁
	 */
	protected static void systemLock() {
		if (isActive()) {
			policyManager.lockNow();
		}
	}

}
