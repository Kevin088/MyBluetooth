//package com.example.myapplication.demo;
//
//import android.app.Service;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCallback;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import android.util.Log;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.UUID;
//
//public class BluetoothLeClass extends Service {
//  public static final UUID CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
//
//  private static final String TAG = "BluetoothLeClass";
//
//  private final String ACTION_CONNECT = "AMOMCU_CONNECT";
//
//  private final String ACTION_NAME_RSSI = "AMOMCU_RSSI";
//
//  private BluetoothAdapter mBluetoothAdapter;
//
//  public String mBluetoothDeviceAddress;
//
//  public BluetoothGatt mBluetoothGatt;
//
//  private BluetoothManager mBluetoothManager;
//
//  private Context mContext;
//
//  private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
//      public void onCharacteristicChanged(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic) {
//        if (BluetoothLeClass.this.mOnDataAvailableListener != null)
//          BluetoothLeClass.this.mOnDataAvailableListener.onCharacteristicWrite(param1BluetoothGatt, param1BluetoothGattCharacteristic);
//      }
//
//      public void onCharacteristicRead(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, int param1Int) {
//        if (BluetoothLeClass.this.mOnDataAvailableListener != null)
//          BluetoothLeClass.this.mOnDataAvailableListener.onCharacteristicRead(param1BluetoothGatt, param1BluetoothGattCharacteristic, param1Int);
//      }
//
//      public void onConnectionStateChange(BluetoothGatt param1BluetoothGatt, int param1Int1, int param1Int2) {
//        Intent intent;
//        if (param1Int2 == 2) {
//          if (BluetoothLeClass.this.mOnConnectListener != null)
//            BluetoothLeClass.this.mOnConnectListener.onConnect(param1BluetoothGatt);
//          Log.i("BluetoothLeClass", "Connected to GATT server.");
//          Log.i("BluetoothLeClass", "Attempting to start service discovery:" + BluetoothLeClass.this.mBluetoothGatt.discoverServices());
//          TimerTask timerTask = new TimerTask() {
//              public void run() {
//                if ((BluetoothLeClass.null.access$0(BluetoothLeClass.null.this)).mBluetoothGatt != null)
//                  (BluetoothLeClass.null.access$0(BluetoothLeClass.null.this)).mBluetoothGatt.readRemoteRssi();
//              }
//            };
//          (new Timer()).schedule(timerTask, 160L, 160L);
//          intent = new Intent("AMOMCU_CONNECT");
//          intent.putExtra("CONNECT_STATUC", 1);
//          BluetoothLeClass.this.mContext.sendBroadcast(intent);
//          return;
//        }
//        if (param1Int2 == 0) {
//          if (BluetoothLeClass.this.mOnDisconnectListener != null)
//            BluetoothLeClass.this.mOnDisconnectListener.onDisconnect((BluetoothGatt)intent);
//          Log.i("BluetoothLeClass", "Disconnected from GATT server.");
//          intent = new Intent("AMOMCU_CONNECT");
//          intent.putExtra("CONNECT_STATUC", 0);
//          BluetoothLeClass.this.mContext.sendBroadcast(intent);
//          return;
//        }
//      }
//
//      public void onReadRemoteRssi(BluetoothGatt param1BluetoothGatt, int param1Int1, int param1Int2) {
//        Log.i("BluetoothLeClass", "--onReadRemoteRssi--: " + param1Int2 + ",   rssi:" + param1Int1 + "----------------------------------");
//        BluetoothLeClass.this.updateRssiBroadcast(param1Int1);
//      }
//
//      public void onServicesDiscovered(BluetoothGatt param1BluetoothGatt, int param1Int) {
//        if (param1Int == 0 && BluetoothLeClass.this.mOnServiceDiscoverListener != null) {
//          BluetoothLeClass.this.mOnServiceDiscoverListener.onServiceDiscover(param1BluetoothGatt);
//          return;
//        }
//        Log.w("BluetoothLeClass", "onServicesDiscovered received: " + param1Int);
//      }
//    };
//
//  private OnConnectListener mOnConnectListener;
//
//  private OnDataAvailableListener mOnDataAvailableListener;
//
//  private OnDisconnectListener mOnDisconnectListener;
//
//  private OnReadRemoteRssiListener mOnReadRemoteRssiListener;
//
//  private OnServiceDiscoverListener mOnServiceDiscoverListener;
//
//  public BluetoothLeClass(Context paramContext) {
//    this.mContext = paramContext;
//  }
//
//  public void close() {
//    if (this.mBluetoothGatt == null)
//      return;
//    this.mBluetoothGatt.close();
//    this.mBluetoothGatt = null;
//  }
//
//  public boolean connect(String paramString) {
//    boolean bool = true;
//    if (this.mBluetoothAdapter == null || paramString == null) {
//      Log.w("BluetoothLeClass", "BluetoothAdapter not initialized or unspecified address.");
//      return false;
//    }
//    if (this.mBluetoothDeviceAddress != null && paramString.equals(this.mBluetoothDeviceAddress) && this.mBluetoothGatt != null) {
//      Log.d("BluetoothLeClass", "Trying to use an existing mBluetoothGatt for connection.");
//      return !this.mBluetoothGatt.connect() ? false : bool;
//    }
//    BluetoothDevice bluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(paramString);
//    if (bluetoothDevice == null) {
//      Log.w("BluetoothLeClass", "Device not found.  Unable to connect.");
//      return false;
//    }
//    this.mBluetoothGatt = bluetoothDevice.connectGatt(this.mContext, false, this.mGattCallback);
//    Log.d("BluetoothLeClass", "Trying to create a new connection.");
//    this.mBluetoothDeviceAddress = paramString;
//    return true;
//  }
//
//  public void disconnect() {
//    if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
//      Log.w("BluetoothLeClass", "BluetoothAdapter not initialized");
//      return;
//    }
//    this.mBluetoothGatt.disconnect();
//  }
//
//  public List<BluetoothGattService> getSupportedGattServices() {
//    return (this.mBluetoothGatt == null) ? null : this.mBluetoothGatt.getServices();
//  }
//
//  public boolean initialize() {
//    if (this.mBluetoothManager == null) {
//      this.mBluetoothManager = (BluetoothManager)this.mContext.getSystemService("bluetooth");
//      if (this.mBluetoothManager == null) {
//        Log.e("BluetoothLeClass", "Unable to initialize BluetoothManager.");
//        return false;
//      }
//    }
//    this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
//    if (this.mBluetoothAdapter == null) {
//      Log.e("BluetoothLeClass", "Unable to obtain a BluetoothAdapter.");
//      return false;
//    }
//    return true;
//  }
//
//  public IBinder onBind(Intent paramIntent) {
//    return null;
//  }
//
//  public void readCharacteristic(BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
//    if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
//      Log.w("BluetoothLeClass", "BluetoothAdapter not initialized");
//      return;
//    }
//    this.mBluetoothGatt.readCharacteristic(paramBluetoothGattCharacteristic);
//  }
//
//  public void setCharacteristicNotification(BluetoothGattCharacteristic paramBluetoothGattCharacteristic, boolean paramBoolean) {
//    if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
//      Log.w("BluetoothLeClass", "BluetoothAdapter not initialized");
//      return;
//    }
//    if (paramBoolean) {
//      Log.i("BluetoothLeClass", "Enable Notification");
//      this.mBluetoothGatt.setCharacteristicNotification(paramBluetoothGattCharacteristic, true);
//      bluetoothGattDescriptor = paramBluetoothGattCharacteristic.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
//      bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//      this.mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
//      return;
//    }
//    Log.i("BluetoothLeClass", "Disable Notification");
//    this.mBluetoothGatt.setCharacteristicNotification((BluetoothGattCharacteristic)bluetoothGattDescriptor, false);
//    BluetoothGattDescriptor bluetoothGattDescriptor = bluetoothGattDescriptor.getDescriptor(CLIENT_CHARACTERISTIC_CONFIG_DESCRIPTOR_UUID);
//    bluetoothGattDescriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
//    this.mBluetoothGatt.writeDescriptor(bluetoothGattDescriptor);
//  }
//
//  public void setOnConnectListener(OnConnectListener paramOnConnectListener) {
//    this.mOnConnectListener = paramOnConnectListener;
//  }
//
//  public void setOnDataAvailableListener(OnDataAvailableListener paramOnDataAvailableListener) {
//    this.mOnDataAvailableListener = paramOnDataAvailableListener;
//  }
//
//  public void setOnDisconnectListener(OnDisconnectListener paramOnDisconnectListener) {
//    this.mOnDisconnectListener = paramOnDisconnectListener;
//  }
//
//  public void setOnServiceDiscoverListener(OnServiceDiscoverListener paramOnServiceDiscoverListener) {
//    this.mOnServiceDiscoverListener = paramOnServiceDiscoverListener;
//  }
//
//  public void setReadRemoteRssiListener(OnReadRemoteRssiListener paramOnReadRemoteRssiListener) {
//    this.mOnReadRemoteRssiListener = paramOnReadRemoteRssiListener;
//  }
//
//  public void updateRssiBroadcast(int paramInt) {
//    Log.i("BluetoothLeClass", "updateRssiBroadcast1 " + paramInt);
//    Intent intent = new Intent("AMOMCU_RSSI");
//    intent.putExtra("RSSI", paramInt);
//    this.mContext.sendBroadcast(intent);
//  }
//
//  public void writeCharacteristic(BluetoothGattCharacteristic paramBluetoothGattCharacteristic) {
//    this.mBluetoothGatt.writeCharacteristic(paramBluetoothGattCharacteristic);
//  }
//
//  public static interface OnConnectListener {
//    void onConnect(BluetoothGatt param1BluetoothGatt);
//  }
//
//  public static interface OnDataAvailableListener {
//    void onCharacteristicRead(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, int param1Int);
//
//    void onCharacteristicWrite(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic);
//  }
//
//  public static interface OnDisconnectListener {
//    void onDisconnect(BluetoothGatt param1BluetoothGatt);
//  }
//
//  public static interface OnReadRemoteRssiListener {
//    void onReadRemoteRssi(BluetoothGatt param1BluetoothGatt, int param1Int);
//  }
//
//  public static interface OnServiceDiscoverListener {
//    void onServiceDiscover(BluetoothGatt param1BluetoothGatt);
//  }
//}
