package com.example.myapplication;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BluetoothReceiver extends BroadcastReceiver{

	String pin = "000000";  //此处为你要连接的蓝牙设备的初始密钥，一般为1234或0000
	String deviceName="Rdf";
	public BluetoothReceiver() {

	}

	//广播接收器，当远程蓝牙设备被发现时，回调函数onReceiver()会被执行
	@Override
	public void onReceive(Context context, Intent intent) {

		String action = intent.getAction(); //得到action
		Log.e("ssss=", action);
		BluetoothDevice btDevice=null;  //创建一个蓝牙device对象
		// 从Intent中获取设备对象
		btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        //BluetoothGattService



		if(BluetoothDevice.ACTION_FOUND.equals(action)){  //发现设备
			Log.e("ssss:", "["+btDevice.getName()+"]"+":"+btDevice.getAddress());

			if(btDevice.getName()!=null&&btDevice.getName().contains(deviceName))//HC-05设备如果有多个，第一个搜到的那个会被尝试。
			{
				if (btDevice.getBondState() == BluetoothDevice.BOND_NONE) {

					Log.e("ssss", "attemp to bond:"+"["+btDevice.getName()+"]");
					try {
						//通过工具类ClsUtils,调用createBond方法
						ClsUtils.createBond(btDevice.getClass(), btDevice);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}else{
				Log.e("error", "Is faild");
			}

		}else if(action.equals("android.bluetooth.device.action.PAIRING_REQUEST")) //再次得到的action，会等于PAIRING_REQUEST
		{
			Log.e("ssss  action2=", action);
			if(btDevice.getName()!=null&&btDevice.getName().contains(deviceName))
			{
				Log.e("ssss", "OKOKOK");

				try {

					//1.确认配对
					//ClsUtils.setPairingConfirmation(btDevice.getClass(), btDevice, true);
					//2.终止有序广播
					Log.e("ssss...", "isOrderedBroadcast:"+isOrderedBroadcast()+",isInitialStickyBroadcast:"+isInitialStickyBroadcast());
					abortBroadcast();//如果没有将广播终止，则会出现一个一闪而过的配对框。
					//3.调用setPin方法进行配对...
					boolean ret = ClsUtils.setPin(btDevice.getClass(), btDevice, pin);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else
				Log.e("ssss", "这个设备不是目标蓝牙设备");

		}
	}
}