//package com.example.myapplication.demo;
//
//import android.app.Activity;
//import android.app.ListActivity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGatt;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothGattDescriptor;
//import android.bluetooth.BluetoothGattService;
//import android.bluetooth.BluetoothManager;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//import java.util.Iterator;
//import java.util.List;
//
//public class DeviceScanActivity extends ListActivity {
//  public static final int REFRESH = 1;
//
//  private static final int REQUEST_CODE = 1;
//
//  private static final long SCAN_PERIOD = 2592000L;
//
//  private static final String TAG = "DeviceScanActivity";
//
//  public static String UUID_0XFFA6;
//
//  public static String UUID_CHAR1;
//
//  public static String UUID_CHAR2;
//
//  public static String UUID_CHAR3;
//
//  public static String UUID_CHAR4;
//
//  public static String UUID_CHAR5;
//
//  public static String UUID_CHAR6;
//
//  public static String UUID_CHAR7;
//
//  public static String UUID_HERATRATE;
//
//  public static String UUID_KEY_DATA = "0000ffe1-0000-1000-8000-00805f9b34fb";
//
//  public static String UUID_TEMPERATURE;
//
//  static BluetoothGattCharacteristic gattCharacteristic_0xffa6;
//
//  static BluetoothGattCharacteristic gattCharacteristic_char1;
//
//  static BluetoothGattCharacteristic gattCharacteristic_char5;
//
//  static BluetoothGattCharacteristic gattCharacteristic_char6;
//
//  static BluetoothGattCharacteristic gattCharacteristic_heartrate;
//
//  static BluetoothGattCharacteristic gattCharacteristic_keydata;
//
//  static BluetoothGattCharacteristic gattCharacteristic_temperature;
//
//  private static BluetoothLeClass mBLE;
//
//  public static String pzhstr;
//
//  private static byte writeValue_char1;
//
//  public String bluetoothAddress;
//
//  private Button btn;
//
//  private byte color = 0;
//
//  private BluetoothAdapter mBluetoothAdapter;
//
//  private Handler mHandler = null;
//
//  private LeDeviceListAdapter mLeDeviceListAdapter = null;
//
//  private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
//      public void onLeScan(BluetoothDevice param1BluetoothDevice, int param1Int, byte[] param1ArrayOfbyte) {
//        final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(param1BluetoothDevice, param1Int, param1ArrayOfbyte);
//        DeviceScanActivity.this.runOnUiThread(new Runnable() {
//              public void run() {
//                (DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mLeDeviceListAdapter.addDevice(ibeacon);
//                (DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mLeDeviceListAdapter.notifyDataSetChanged();
//                if ((DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mScanning) {
//                  (DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mBluetoothAdapter.stopLeScan((DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mLeScanCallback);
//                  (DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mBluetoothAdapter.startLeScan((DeviceScanActivity.null.access$0(DeviceScanActivity.null.this)).mLeScanCallback);
//                }
//              }
//            });
//        Log.i("DeviceScanActivity", "rssi = " + param1Int);
//        Log.i("DeviceScanActivity", "mac = " + param1BluetoothDevice.getAddress());
//        Log.i("DeviceScanActivity", "scanRecord.length = " + param1ArrayOfbyte.length);
//      }
//    };
//
//  private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new BluetoothLeClass.OnDataAvailableListener() {
//      public void onCharacteristicRead(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic, int param1Int) {
//        Log.e("DeviceScanActivity", "onCharRead " + param1BluetoothGatt.getDevice().getName() + " read " + param1BluetoothGattCharacteristic.getUuid().toString() + " -> " + Utils.bytesToHexString(param1BluetoothGattCharacteristic.getValue()));
//        AmoComActivity.char6_display(Utils.bytesToString(param1BluetoothGattCharacteristic.getValue()), param1BluetoothGattCharacteristic.getValue(), param1BluetoothGattCharacteristic.getUuid().toString());
//      }
//
//      public void onCharacteristicWrite(BluetoothGatt param1BluetoothGatt, BluetoothGattCharacteristic param1BluetoothGattCharacteristic) {
//        Log.e("DeviceScanActivity", "onCharWrite " + param1BluetoothGatt.getDevice().getName() + " write " + param1BluetoothGattCharacteristic.getUuid().toString() + " -> " + new String(param1BluetoothGattCharacteristic.getValue()));
//        AmoComActivity.char6_display(Utils.bytesToString(param1BluetoothGattCharacteristic.getValue()), param1BluetoothGattCharacteristic.getValue(), param1BluetoothGattCharacteristic.getUuid().toString());
//      }
//    };
//
//  private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new BluetoothLeClass.OnServiceDiscoverListener() {
//      public void onServiceDiscover(BluetoothGatt param1BluetoothGatt) {
//        DeviceScanActivity.this.displayGattServices(DeviceScanActivity.mBLE.getSupportedGattServices());
//      }
//    };
//
//  private int mRssi;
//
//  private boolean mScanning;
//
//  private MyThread mythread = null;
//
//  static {
//    UUID_CHAR1 = "0000fff1-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR2 = "0000fff2-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR3 = "0000fff3-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR4 = "0000fff4-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR5 = "0000fff5-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR6 = "0000fff6-0000-1000-8000-00805f9b34fb";
//    UUID_CHAR7 = "0000fff7-0000-1000-8000-00805f9b34fb";
//    UUID_HERATRATE = "00002a37-0000-1000-8000-00805f9b34fb";
//    UUID_TEMPERATURE = "00002a1c-0000-1000-8000-00805f9b34fb";
//    UUID_0XFFA6 = "0000ffa6-0000-1000-8000-00805f9b34fb";
//    pzhstr = "00";
//    gattCharacteristic_char1 = null;
//    gattCharacteristic_char5 = null;
//    gattCharacteristic_char6 = null;
//    gattCharacteristic_heartrate = null;
//    gattCharacteristic_keydata = null;
//    gattCharacteristic_temperature = null;
//    gattCharacteristic_0xffa6 = null;
//    writeValue_char1 = 0;
//  }
//
//  private void displayGattServices(List<BluetoothGattService> paramList) {
//    if (paramList == null)
//      return;
//    List list = null;
//    Iterator<BluetoothGattService> iterator = paramList.iterator();
//    paramList = list;
//    label39: while (true) {
//      if (!iterator.hasNext()) {
//        pzhstr = "06";
//        Intent intent = new Intent();
//        intent.setClass((Context)this, AmoComActivity.class);
//        intent.putExtra("mac_addr", this.bluetoothAddress);
//        intent.putExtra("char_uuid", paramList.getUuid().toString());
//        startActivityForResult(intent, 1);
//        return;
//      }
//      BluetoothGattService bluetoothGattService = iterator.next();
//      int i = bluetoothGattService.getType();
//      Log.e("DeviceScanActivity", "-->service type:" + Utils.getServiceType(i));
//      Log.e("DeviceScanActivity", "-->includedServices size:" + bluetoothGattService.getIncludedServices().size());
//      Log.e("DeviceScanActivity", "-->service uuid:" + bluetoothGattService.getUuid());
//      Iterator<BluetoothGattCharacteristic> iterator1 = bluetoothGattService.getCharacteristics().iterator();
//      List<BluetoothGattService> list1 = paramList;
//      label37: while (true) {
//        paramList = list1;
//        if (iterator1.hasNext()) {
//          BluetoothGattCharacteristic bluetoothGattCharacteristic2;
//          BluetoothGattCharacteristic bluetoothGattCharacteristic1 = iterator1.next();
//          Log.e("DeviceScanActivity", "---->char uuid:" + bluetoothGattCharacteristic1.getUuid());
//          i = bluetoothGattCharacteristic1.getPermissions();
//          Log.e("DeviceScanActivity", "---->char permission:" + Utils.getCharPermission(i));
//          i = bluetoothGattCharacteristic1.getProperties();
//          Log.e("DeviceScanActivity", "---->char property:" + Utils.getCharPropertie(i));
//          byte[] arrayOfByte = bluetoothGattCharacteristic1.getValue();
//          if (arrayOfByte != null && arrayOfByte.length > 0)
//            Log.e("DeviceScanActivity", "---->char value:" + new String(arrayOfByte));
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_CHAR5))
//            gattCharacteristic_char5 = bluetoothGattCharacteristic1;
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_CHAR6)) {
//            gattCharacteristic_char6 = bluetoothGattCharacteristic1;
//            bluetoothGattCharacteristic2 = bluetoothGattCharacteristic1;
//            mBLE.setCharacteristicNotification(bluetoothGattCharacteristic1, true);
//            Log.i("DeviceScanActivity", "+++++++++UUID_CHAR6");
//          }
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_HERATRATE)) {
//            gattCharacteristic_heartrate = bluetoothGattCharacteristic1;
//            bluetoothGattCharacteristic2 = bluetoothGattCharacteristic1;
//            mBLE.setCharacteristicNotification(bluetoothGattCharacteristic1, true);
//            Log.i("DeviceScanActivity", "+++++++++UUID_HERATRATE");
//          }
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_KEY_DATA)) {
//            gattCharacteristic_keydata = bluetoothGattCharacteristic1;
//            bluetoothGattCharacteristic2 = bluetoothGattCharacteristic1;
//            mBLE.setCharacteristicNotification(bluetoothGattCharacteristic1, true);
//            Log.i("DeviceScanActivity", "+++++++++UUID_KEY_DATA");
//          }
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_TEMPERATURE)) {
//            gattCharacteristic_temperature = bluetoothGattCharacteristic1;
//            bluetoothGattCharacteristic2 = bluetoothGattCharacteristic1;
//            mBLE.setCharacteristicNotification(bluetoothGattCharacteristic1, true);
//            Log.i("DeviceScanActivity", "+++++++++UUID_TEMPERATURE");
//          }
//          BluetoothGattCharacteristic bluetoothGattCharacteristic3 = bluetoothGattCharacteristic2;
//          if (bluetoothGattCharacteristic1.getUuid().toString().equals(UUID_0XFFA6)) {
//            gattCharacteristic_0xffa6 = bluetoothGattCharacteristic1;
//            bluetoothGattCharacteristic3 = bluetoothGattCharacteristic1;
//            Log.i("DeviceScanActivity", "+++++++++UUID_0XFFA6");
//          }
//          Iterator<BluetoothGattDescriptor> iterator2 = bluetoothGattCharacteristic1.getDescriptors().iterator();
//          while (true) {
//            bluetoothGattCharacteristic2 = bluetoothGattCharacteristic3;
//            if (iterator2.hasNext()) {
//              BluetoothGattDescriptor bluetoothGattDescriptor = iterator2.next();
//              Log.e("DeviceScanActivity", "-------->desc uuid:" + bluetoothGattDescriptor.getUuid());
//              i = bluetoothGattDescriptor.getPermissions();
//              Log.e("DeviceScanActivity", "-------->desc permission:" + Utils.getDescPermission(i));
//              byte[] arrayOfByte1 = bluetoothGattDescriptor.getValue();
//              if (arrayOfByte1 != null && arrayOfByte1.length > 0)
//                Log.e("DeviceScanActivity", "-------->desc value:" + new String(arrayOfByte1));
//              continue;
//            }
//            continue label37;
//          }
//          break;
//        }
//        continue label39;
//      }
//      break;
//    }
//  }
//
//  public static void read_char1() {
//    byte[] arrayOfByte = new byte[1];
//    Log.i("DeviceScanActivity", "readCharacteristic = ");
//    if (gattCharacteristic_char1 != null)
//      mBLE.readCharacteristic(gattCharacteristic_char1);
//  }
//
//  public static void read_uuid_0xffa6() {
//    Log.i("DeviceScanActivity", "readCharacteristic = ");
//    if (gattCharacteristic_0xffa6 != null)
//      mBLE.readCharacteristic(gattCharacteristic_0xffa6);
//  }
//
//  private void scanLeDevice(boolean paramBoolean) {
//    if (paramBoolean) {
//      this.mHandler.postDelayed(new Runnable() {
//            public void run() {
//              DeviceScanActivity.this.mScanning = false;
//              DeviceScanActivity.this.mBluetoothAdapter.stopLeScan(DeviceScanActivity.this.mLeScanCallback);
//              DeviceScanActivity.this.invalidateOptionsMenu();
//            }
//          },  2592000L);
//      this.mScanning = true;
//      this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
//    } else {
//      this.mScanning = false;
//      this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
//    }
//    invalidateOptionsMenu();
//  }
//
//  public static void writeChar1() {
//    byte[] arrayOfByte = new byte[1];
//    Log.i("DeviceScanActivity", "gattCharacteristic_char1 = " + gattCharacteristic_char1);
//    if (gattCharacteristic_char1 != null) {
//      byte b = writeValue_char1;
//      writeValue_char1 = (byte)(b + 1);
//      arrayOfByte[0] = b;
//      Log.i("DeviceScanActivity", "gattCharacteristic_char1.setValue writeValue[0] =" + arrayOfByte[0]);
//      gattCharacteristic_char1.setValue(arrayOfByte);
//      mBLE.writeCharacteristic(gattCharacteristic_char1);
//    }
//  }
//
//  public static void writeChar6(String paramString) {
//    Log.i("DeviceScanActivity", "gattCharacteristic_char6 = " + gattCharacteristic_char6);
//    if (gattCharacteristic_char6 != null) {
//      gattCharacteristic_char6.setValue(paramString.getBytes());
//      mBLE.writeCharacteristic(gattCharacteristic_char6);
//    }
//  }
//
//  public static void writeChar6_in_bytes(byte[] paramArrayOfbyte) {
//    Log.i("DeviceScanActivity", "gattCharacteristic_char6 = " + gattCharacteristic_char6);
//    if (gattCharacteristic_char6 != null) {
//      gattCharacteristic_char6.setValue(paramArrayOfbyte);
//      mBLE.writeCharacteristic(gattCharacteristic_char6);
//    }
//  }
//
//  public void DisplayStart() {
//    Log.i("DeviceScanActivity", "DisplayStart+++");
//    if (this.mythread == null) {
//      this.mythread = new MyThread();
//      this.mythread.start();
//    }
//  }
//
//  public void DisplayStop() {
//    Log.i("DeviceScanActivity", "DisplayStop---");
//  }
//
//  public void onCreate(Bundle paramBundle) {
//    super.onCreate(paramBundle);
//    pzhstr = "01";
//    getActionBar().setTitle("+ pzhstr);
//    if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
//      Toast.makeText((Context)this, 2131034113, 0).show();
//      finish();
//    } else {
//      Log.i("DeviceScanActivity", "initialize Bluetooth, has BLE system");
//    }
//    this.mBluetoothAdapter = ((BluetoothManager)getSystemService("bluetooth")).getAdapter();
//    if (this.mBluetoothAdapter == null) {
//      Toast.makeText((Context)this, 2131034121, 0).show();
//      finish();
//      return;
//    }
//    Log.i("DeviceScanActivity", "mBluetoothAdapter = " + this.mBluetoothAdapter);
//    if (!this.mBluetoothAdapter.isEnabled())
//      this.mBluetoothAdapter.enable();
//    Log.i("DeviceScanActivity", "mBluetoothAdapter.enable");
//    mBLE = new BluetoothLeClass((Context)this);
//    if (!mBLE.initialize()) {
//      Log.e("DeviceScanActivity", "Unable to initialize Bluetooth");
//      finish();
//    }
//    Log.i("DeviceScanActivity", "mBLE = e" + mBLE);
//    mBLE.setOnServiceDiscoverListener(this.mOnServiceDiscover);
//    mBLE.setOnDataAvailableListener(this.mOnDataAvailable);
//    this.mHandler = new Handler() {
//        int count = 0;
//
//        public void handleMessage(Message param1Message) {
//          if (param1Message.what == 1) {
//            this.count++;
//            if (this.count == 0) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 1) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 2) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 3) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 4) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 5) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else if (this.count == 6) {
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            } else {
//              this.count = 0;
//              DeviceScanActivity.this.getActionBar().setTitle("+ DeviceScanActivity.pzhstr);
//            }
//          }
//          super.handleMessage(param1Message);
//        }
//      };
//    (new MyThread()).start();
//  }
//
//  protected void onDestroy() {
//    Log.i("DeviceScanActivity", "---> onDestroy");
//    super.onDestroy();
//    Log.e("DeviceScanActivity", "start onDestroy~~~");
//    scanLeDevice(false);
//    mBLE.disconnect();
//    mBLE.close();
//    pzhstr = "05";
//  }
//
//  protected void onListItemClick(ListView paramListView, View paramView, int paramInt, long paramLong) {
//    iBeaconClass.iBeacon iBeacon = this.mLeDeviceListAdapter.getDevice(paramInt);
//    if (iBeacon == null)
//      return;
//    if (this.mScanning) {
//      this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
//      this.mScanning = false;
//    }
//    Log.i("DeviceScanActivity", "mBluetoothAdapter.enable");
//    this.bluetoothAddress = iBeacon.bluetoothAddress;
//    boolean bool = mBLE.connect(iBeacon.bluetoothAddress);
//    Log.i("DeviceScanActivity", "connect bRet = " + bool);
//    Toast toast = Toast.makeText(getApplicationContext(), ", 1500);
//    toast.setGravity(17, 0, 0);
//    toast.show();
//  }
//
//  protected void onPause() {
//    scanLeDevice(false);
//    Log.i("DeviceScanActivity", "---> onPause");
//    super.onPause();
//    pzhstr = "03";
//  }
//
//  protected void onResume() {
//    pzhstr = "02";
//    Log.i("DeviceScanActivity", "---> onResume");
//    super.onResume();
//    mBLE.close();
//    this.mLeDeviceListAdapter = new LeDeviceListAdapter((Activity)this);
//    setListAdapter((ListAdapter)this.mLeDeviceListAdapter);
//    scanLeDevice(true);
//  }
//
//  protected void onStop() {
//    Log.i("DeviceScanActivity", "---> onStop");
//    super.onStop();
//    DisplayStop();
//    pzhstr = "04";
//  }
//
//  public class MyThread extends Thread {
//    public void run() {
//      while (true) {
//        if (Thread.currentThread().isInterrupted())
//          return;
//        Message message = new Message();
//        message.what = 1;
//        DeviceScanActivity.this.mHandler.sendMessage(message);
//        try {
//          Thread.sleep(200L);
//        } catch (InterruptedException interruptedException) {
//          interruptedException.printStackTrace();
//        }
//      }
//    }
//  }
//}
