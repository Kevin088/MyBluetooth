//package com.example.myapplication.demo;
//
//import android.app.Activity;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.method.ScrollingMovementMethod;
//import android.util.Log;
//import android.view.View;
//import android.widget.CompoundButton;
//import android.widget.ScrollView;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ToggleButton;
//
//public class AmoComActivity extends Activity implements View.OnClickListener {
//  static String ReciveStr;
//
//  static String SendString;
//
//  static String Str_Recv;
//
//  public static int System_OnOff_IF = 0;
//
//  private static final String TAG = "DeviceScanActivity";
//
//  static TextView Text_Recv;
//
//  static int Totol_Send_bytes = 0;
//
//  static int Totol_recv_bytes = 0;
//
//  static int Totol_recv_bytes_temp = 0;
//
//  static boolean ifDisplayInHexStringOnOff = false;
//
//  static boolean ifDisplayTimeOnOff = false;
//
//  static Handler mHandler = new Handler();
//
//  static Switch mSwitch1;
//
//  static final int rssibufferSize = 10;
//
//  static ScrollView scrollView;
//
//  private static TempControlView tempControl;
//
//  static TextView textview_recive_send_info;
//
//  private final String ACTION_CONNECT = "AMOMCU_CONNECT";
//
//  private final String ACTION_NAME_RSSI = "AMOMCU_RSSI";
//
//  boolean if_use_hex_send = false;
//
//  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//      public void onReceive(Context param1Context, Intent param1Intent) {
//        AmoComActivity amoComActivity;
//        double d;
//        int i;
//        String str = param1Intent.getAction();
//        if (str.equals("AMOMCU_RSSI")) {
//          int j = param1Intent.getIntExtra("RSSI", 0);
//          i = 0;
//          d = 0.0D;
//          AmoComActivity.this.rssibuffer[AmoComActivity.this.rssibufferIndex] = j;
//          amoComActivity = AmoComActivity.this;
//          amoComActivity.rssibufferIndex++;
//          if (AmoComActivity.this.rssibufferIndex == 10)
//            AmoComActivity.this.rssiUsedFalg = true;
//          amoComActivity = AmoComActivity.this;
//          amoComActivity.rssibufferIndex %= 10;
//          if (AmoComActivity.this.rssiUsedFalg) {
//            j = 0;
//            i = 0;
//            while (true) {
//              if (i >= 10) {
//                j /= 10;
//                i = j;
//                if (-j < 35)
//                  i = -35;
//                if (-i < --72) {
//                  d = 10 + (-i - 35) / (--72 - 35) * ';
//                } else if (-i < --80) {
//                  d = 10 + (-i - 35) / (--80 - 35) * ';
//                } else {
//                  d = 10 + (-i - 35) / (--88 - 35) * ';
//                }
//              } else {
//                j += AmoComActivity.this.rssibuffer[i];
//                i++;
//                continue;
//              }
//              AmoComActivity.this.getActionBar().setTitle("RSSI:" + i + "dbm" + "," + "+ (int)d + "cm");
//              return;
//            }
//          }
//        } else {
//          if (amoComActivity.equals("AMOMCU_CONNECT")) {
//            if (param1Intent.getIntExtra("CONNECT_STATUC", 0) == 0) {
//              AmoComActivity.this.getActionBar().setTitle(");
//              AmoComActivity.this.finish();
//              return;
//            }
//            AmoComActivity.this.getActionBar().setTitle(");
//            return;
//          }
//          return;
//        }
//        AmoComActivity.this.getActionBar().setTitle("RSSI:" + i + "dbm" + "," + "+ (int)d + "cm");
//      }
//    };
//
//  boolean rssiUsedFalg = false;
//
//  int[] rssibuffer = new int[10];
//
//  int rssibufferIndex = 0;
//
//  ToggleButton toggleHexStr;
//
//  ToggleButton toggleTime;
//
//  static {
//    ifDisplayInHexStringOnOff = true;
//    ifDisplayTimeOnOff = true;
//    Totol_Send_bytes = 0;
//    Totol_recv_bytes = 0;
//    Totol_recv_bytes_temp = 0;
//    SendString = "0123";
//    mSwitch1 = null;
//    tempControl = null;
//  }
//
//  public static String GetLastData() {
//    // Byte code:
//    //   0: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   2: monitorenter
//    //   3: getstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   6: astore_0
//    //   7: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   9: monitorexit
//    //   10: aload_0
//    //   11: areturn
//    //   12: astore_0
//    //   13: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   15: monitorexit
//    //   16: aload_0
//    //   17: athrow
//    // Exception table:
//    //   from	to	target	type
//    //   3	7	12	finally
//  }
//
//  private void Send_Bytes(byte[] paramArrayOfbyte) {
//    int j = paramArrayOfbyte.length;
//    int i = 0;
//    label21: while (true) {
//      int k;
//      if (i >= j)
//        return;
//      if (j - i > 18) {
//        k = 18;
//      } else {
//        k = j - i;
//      }
//      byte[] arrayOfByte = new byte[k];
//      int m;
//      for (m = 0;; m++) {
//        if (m >= k) {
//          i += k;
//          DeviceScanActivity.writeChar6_in_bytes(arrayOfByte);
//          try {
//            Thread.sleep(40L);
//          } catch (InterruptedException interruptedException) {
//            interruptedException.printStackTrace();
//          }
//          continue label21;
//        }
//        interruptedException[m] = paramArrayOfbyte[i + m];
//      }
//      break;
//    }
//  }
//
//  public static void char6_display(String paramString1, byte[] paramArrayOfbyte, String paramString2) {
//    // Byte code:
//    //   0: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   2: monitorenter
//    //   3: ldc 'DeviceScanActivity'
//    //   5: new java/lang/StringBuilder
//    //   8: dup
//    //   9: ldc 'char6_display str = '
//    //   11: invokespecial <init> : (Ljava/lang/String;)V
//    //   14: aload_0
//    //   15: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   18: invokevirtual toString : ()Ljava/lang/String;
//    //   21: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)I
//    //   24: pop
//    //   25: aload_2
//    //   26: getstatic com/amobletool/bluetooth/le/DeviceScanActivity.UUID_HERATRATE : Ljava/lang/String;
//    //   29: invokevirtual equals : (Ljava/lang/Object;)Z
//    //   32: ifeq -> 170
//    //   35: new java/text/SimpleDateFormat
//    //   38: dup
//    //   39: ldc 'HH:mm:ss '
//    //   41: invokespecial <init> : (Ljava/lang/String;)V
//    //   44: new java/util/Date
//    //   47: dup
//    //   48: invokestatic currentTimeMillis : ()J
//    //   51: invokespecial <init> : (J)V
//    //   54: invokevirtual format : (Ljava/util/Date;)Ljava/lang/String;
//    //   57: astore_2
//    //   58: aload_0
//    //   59: invokevirtual length : ()I
//    //   62: newarray byte
//    //   64: astore #6
//    //   66: new java/lang/StringBuilder
//    //   69: dup
//    //   70: new java/lang/StringBuilder
//    //   73: dup
//    //   74: ldc '['
//    //   76: invokespecial <init> : (Ljava/lang/String;)V
//    //   79: aload_2
//    //   80: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   83: ldc '] '
//    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   88: ldc 'HeartRate : '
//    //   90: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   93: aload_1
//    //   94: iconst_0
//    //   95: baload
//    //   96: invokevirtual append : (I)Ljava/lang/StringBuilder;
//    //   99: ldc '='
//    //   101: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   104: aload_1
//    //   105: iconst_1
//    //   106: baload
//    //   107: invokevirtual append : (I)Ljava/lang/StringBuilder;
//    //   110: invokevirtual toString : ()Ljava/lang/String;
//    //   113: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
//    //   116: invokespecial <init> : (Ljava/lang/String;)V
//    //   119: ldc '\\r\\n'
//    //   121: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   124: invokevirtual toString : ()Ljava/lang/String;
//    //   127: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   130: getstatic com/amobletool/bluetooth/le/AmoComActivity.Totol_recv_bytes : I
//    //   133: aload_0
//    //   134: invokevirtual length : ()I
//    //   137: iadd
//    //   138: putstatic com/amobletool/bluetooth/le/AmoComActivity.Totol_recv_bytes : I
//    //   141: getstatic com/amobletool/bluetooth/le/AmoComActivity.Totol_recv_bytes_temp : I
//    //   144: aload_0
//    //   145: invokevirtual length : ()I
//    //   148: iadd
//    //   149: putstatic com/amobletool/bluetooth/le/AmoComActivity.Totol_recv_bytes_temp : I
//    //   152: getstatic com/amobletool/bluetooth/le/AmoComActivity.mHandler : Landroid/os/Handler;
//    //   155: new com/amobletool/bluetooth/le/AmoComActivity$7
//    //   158: dup
//    //   159: invokespecial <init> : ()V
//    //   162: invokevirtual post : (Ljava/lang/Runnable;)Z
//    //   165: pop
//    //   166: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   168: monitorexit
//    //   169: return
//    //   170: aload_2
//    //   171: getstatic com/amobletool/bluetooth/le/DeviceScanActivity.UUID_TEMPERATURE : Ljava/lang/String;
//    //   174: invokevirtual equals : (Ljava/lang/Object;)Z
//    //   177: ifeq -> 199
//    //   180: aload_0
//    //   181: invokevirtual getBytes : ()[B
//    //   184: invokestatic bytesToHexString : ([B)Ljava/lang/String;
//    //   187: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   190: goto -> 130
//    //   193: astore_0
//    //   194: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   196: monitorexit
//    //   197: aload_0
//    //   198: athrow
//    //   199: aload_2
//    //   200: getstatic com/amobletool/bluetooth/le/DeviceScanActivity.UUID_CHAR6 : Ljava/lang/String;
//    //   203: invokevirtual equals : (Ljava/lang/Object;)Z
//    //   206: ifeq -> 561
//    //   209: getstatic com/amobletool/bluetooth/le/AmoComActivity.ifDisplayInHexStringOnOff : Z
//    //   212: ifeq -> 551
//    //   215: ldc '0'
//    //   217: astore #6
//    //   219: aload #6
//    //   221: astore #7
//    //   223: aload #6
//    //   225: astore_2
//    //   226: aload #6
//    //   228: astore_1
//    //   229: aload_0
//    //   230: invokevirtual length : ()I
//    //   233: ifle -> 347
//    //   236: aload #6
//    //   238: astore_2
//    //   239: aload #6
//    //   241: astore_1
//    //   242: aload_0
//    //   243: invokestatic parseInt : (Ljava/lang/String;)I
//    //   246: istore #5
//    //   248: iload #5
//    //   250: i2d
//    //   251: dstore_3
//    //   252: ldc2_w 0.0625
//    //   255: iload #5
//    //   257: i2d
//    //   258: dmul
//    //   259: d2i
//    //   260: istore #5
//    //   262: aload #6
//    //   264: astore_2
//    //   265: aload #6
//    //   267: astore_1
//    //   268: ldc '%.4f'
//    //   270: iconst_1
//    //   271: anewarray java/lang/Object
//    //   274: dup
//    //   275: iconst_0
//    //   276: ldc2_w 0.0625
//    //   279: dload_3
//    //   280: dmul
//    //   281: invokestatic valueOf : (D)Ljava/lang/Double;
//    //   284: aastore
//    //   285: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
//    //   288: astore #6
//    //   290: aload #6
//    //   292: astore_2
//    //   293: aload #6
//    //   295: astore_1
//    //   296: aload #6
//    //   298: invokestatic valueOf : (Ljava/lang/String;)Ljava/lang/Double;
//    //   301: invokevirtual doubleValue : ()D
//    //   304: dstore_3
//    //   305: aload #6
//    //   307: astore_2
//    //   308: aload #6
//    //   310: astore_1
//    //   311: getstatic com/amobletool/bluetooth/le/AmoComActivity.System_OnOff_IF : I
//    //   314: iconst_1
//    //   315: if_icmpne -> 484
//    //   318: dload_3
//    //   319: ldc2_w 30.0
//    //   322: dcmpl
//    //   323: ifle -> 389
//    //   326: aload #6
//    //   328: astore_2
//    //   329: aload #6
//    //   331: astore_1
//    //   332: getstatic com/amobletool/bluetooth/le/AmoComActivity.tempControl : Lcom/rdf/tempcontrolview/TempControlView;
//    //   335: bipush #30
//    //   337: bipush #45
//    //   339: dload_3
//    //   340: invokevirtual setTemp : (IID)V
//    //   343: aload #6
//    //   345: astore #7
//    //   347: new java/lang/StringBuilder
//    //   350: dup
//    //   351: aload #7
//    //   353: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
//    //   356: invokespecial <init> : (Ljava/lang/String;)V
//    //   359: ldc_w '
//    //   362: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   365: ldc '['
//    //   367: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   370: aload_0
//    //   371: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   374: ldc_w ']'
//    //   377: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   380: invokevirtual toString : ()Ljava/lang/String;
//    //   383: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   386: goto -> 130
//    //   389: aload #6
//    //   391: astore_2
//    //   392: aload #6
//    //   394: astore_1
//    //   395: getstatic com/amobletool/bluetooth/le/AmoComActivity.tempControl : Lcom/rdf/tempcontrolview/TempControlView;
//    //   398: bipush #30
//    //   400: bipush #45
//    //   402: ldc2_w 30.0
//    //   405: invokevirtual setTemp : (IID)V
//    //   408: aload #6
//    //   410: astore #7
//    //   412: goto -> 347
//    //   415: astore #6
//    //   417: aload_2
//    //   418: astore_1
//    //   419: getstatic java/lang/System.out : Ljava/io/PrintStream;
//    //   422: new java/lang/StringBuilder
//    //   425: dup
//    //   426: ldc_w '
//    //   429: invokespecial <init> : (Ljava/lang/String;)V
//    //   432: aload #6
//    //   434: invokevirtual append : (Ljava/lang/Object;)Ljava/lang/StringBuilder;
//    //   437: invokevirtual toString : ()Ljava/lang/String;
//    //   440: invokevirtual println : (Ljava/lang/String;)V
//    //   443: new java/lang/StringBuilder
//    //   446: dup
//    //   447: aload_2
//    //   448: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
//    //   451: invokespecial <init> : (Ljava/lang/String;)V
//    //   454: ldc_w '
//    //   457: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   460: ldc '['
//    //   462: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   465: aload_0
//    //   466: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   469: ldc_w ']'
//    //   472: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   475: invokevirtual toString : ()Ljava/lang/String;
//    //   478: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   481: goto -> 130
//    //   484: aload #6
//    //   486: astore_2
//    //   487: aload #6
//    //   489: astore_1
//    //   490: getstatic com/amobletool/bluetooth/le/AmoComActivity.tempControl : Lcom/rdf/tempcontrolview/TempControlView;
//    //   493: bipush #30
//    //   495: bipush #45
//    //   497: ldc2_w 30.0
//    //   500: invokevirtual setTemp : (IID)V
//    //   503: aload #6
//    //   505: astore #7
//    //   507: goto -> 347
//    //   510: astore_2
//    //   511: new java/lang/StringBuilder
//    //   514: dup
//    //   515: aload_1
//    //   516: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
//    //   519: invokespecial <init> : (Ljava/lang/String;)V
//    //   522: ldc_w '
//    //   525: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   528: ldc '['
//    //   530: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   533: aload_0
//    //   534: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   537: ldc_w ']'
//    //   540: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
//    //   543: invokevirtual toString : ()Ljava/lang/String;
//    //   546: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   549: aload_2
//    //   550: athrow
//    //   551: aload_1
//    //   552: invokestatic bytesToHexString : ([B)Ljava/lang/String;
//    //   555: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   558: goto -> 130
//    //   561: aload_0
//    //   562: invokevirtual getBytes : ()[B
//    //   565: invokestatic bytesToHexString : ([B)Ljava/lang/String;
//    //   568: putstatic com/amobletool/bluetooth/le/AmoComActivity.Str_Recv : Ljava/lang/String;
//    //   571: goto -> 130
//    // Exception table:
//    //   from	to	target	type
//    //   3	130	193	finally
//    //   130	166	193	finally
//    //   170	190	193	finally
//    //   199	215	193	finally
//    //   229	236	415	java/lang/Exception
//    //   229	236	510	finally
//    //   242	248	415	java/lang/Exception
//    //   242	248	510	finally
//    //   268	290	415	java/lang/Exception
//    //   268	290	510	finally
//    //   296	305	415	java/lang/Exception
//    //   296	305	510	finally
//    //   311	318	415	java/lang/Exception
//    //   311	318	510	finally
//    //   332	343	415	java/lang/Exception
//    //   332	343	510	finally
//    //   347	386	193	finally
//    //   395	408	415	java/lang/Exception
//    //   395	408	510	finally
//    //   419	443	510	finally
//    //   443	481	193	finally
//    //   490	503	415	java/lang/Exception
//    //   490	503	510	finally
//    //   511	551	193	finally
//    //   551	558	193	finally
//    //   561	571	193	finally
//  }
//
//  public static void update_display_send_recv_info(int paramInt1, int paramInt2) {
//    // Byte code:
//    //   0: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   2: monitorenter
//    //   3: ldc_w 'T:%4d,R:%4d [Byte]'
//    //   6: iconst_2
//    //   7: anewarray java/lang/Object
//    //   10: dup
//    //   11: iconst_0
//    //   12: iload_0
//    //   13: invokestatic valueOf : (I)Ljava/lang/Integer;
//    //   16: aastore
//    //   17: dup
//    //   18: iconst_1
//    //   19: iload_1
//    //   20: invokestatic valueOf : (I)Ljava/lang/Integer;
//    //   23: aastore
//    //   24: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
//    //   27: astore_2
//    //   28: getstatic com/amobletool/bluetooth/le/AmoComActivity.textview_recive_send_info : Landroid/widget/TextView;
//    //   31: aload_2
//    //   32: invokevirtual setText : (Ljava/lang/CharSequence;)V
//    //   35: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   37: monitorexit
//    //   38: return
//    //   39: astore_2
//    //   40: ldc com/amobletool/bluetooth/le/AmoComActivity
//    //   42: monitorexit
//    //   43: aload_2
//    //   44: athrow
//    // Exception table:
//    //   from	to	target	type
//    //   3	35	39	finally
//  }
//
//  public void onClick(View paramView) {
//    switch (paramView.getId()) {
//      default:
//        return;
//      case 2131165201:
//        break;
//    }
//    Text_Recv.setText("");
//    ReciveStr = "";
//    Totol_Send_bytes = 0;
//    Totol_recv_bytes = 0;
//    Totol_recv_bytes_temp = 0;
//    update_display_send_recv_info(Totol_Send_bytes, Totol_recv_bytes);
//    DeviceScanActivity.read_uuid_0xffa6();
//  }
//
//  protected void onCreate(Bundle paramBundle) {
//    super.onCreate(paramBundle);
//    setContentView(2130968581);
//    getActionBar().setTitle(" );
//    registerBoradcastReceiver();
//    findViewById(2131165201).setOnClickListener(this);
//    findViewById(2131165202).setOnClickListener(this);
//    paramBundle = getIntent().getExtras();
//    paramBundle.getString("mac_addr");
//    paramBundle.getString("char_uuid");
//    TextView textView1 = (TextView)findViewById(2131165196);
//    TextView textView2 = (TextView)findViewById(2131165197);
//    textView1.setText("");
//    textView2.setText("");
//    textview_recive_send_info = (TextView)findViewById(2131165207);
//    Text_Recv = (TextView)findViewById(2131165187);
//    ReciveStr = "";
//    Text_Recv.setMovementMethod(ScrollingMovementMethod.getInstance());
//    Totol_Send_bytes = 0;
//    Totol_recv_bytes = 0;
//    Totol_recv_bytes_temp = 0;
//    update_display_send_recv_info(Totol_Send_bytes, Totol_recv_bytes);
//    ifDisplayInHexStringOnOff = true;
//    ifDisplayTimeOnOff = true;
//    this.toggleHexStr = (ToggleButton)findViewById(2131165199);
//    this.toggleTime = (ToggleButton)findViewById(2131165200);
//    this.toggleHexStr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
//            Log.i("DeviceScanActivity", "onCheckedChanged  arg1= " + param1Boolean);
//            AmoComActivity.ifDisplayInHexStringOnOff = param1Boolean;
//            ToggleButton toggleButton = (ToggleButton)AmoComActivity.this.findViewById(2131165200);
//            if (AmoComActivity.ifDisplayInHexStringOnOff) {
//              toggleButton.setChecked(true);
//              if (AmoComActivity.Text_Recv.length() > 0) {
//                String str = Utils.bytesToString(Utils.hexStringToBytes(AmoComActivity.Text_Recv.getText().toString()));
//                AmoComActivity.Text_Recv.setText("");
//                AmoComActivity.Text_Recv.append(str);
//              }
//              toggleButton.setEnabled(true);
//              return;
//            }
//            toggleButton.setChecked(false);
//            if (AmoComActivity.Text_Recv.length() > 0) {
//              String str = Utils.bytesToHexString(AmoComActivity.Text_Recv.getText().toString().getBytes());
//              AmoComActivity.Text_Recv.setText("");
//              AmoComActivity.Text_Recv.append(str);
//            }
//            toggleButton.setEnabled(false);
//          }
//        });
//    this.toggleTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
//            Log.i("DeviceScanActivity", "onCheckedChanged  arg1= " + param1Boolean);
//            AmoComActivity.ifDisplayTimeOnOff = param1Boolean;
//            if (!AmoComActivity.ifDisplayInHexStringOnOff)
//              ((ToggleButton)AmoComActivity.this.findViewById(2131165200)).setEnabled(false);
//            AmoComActivity.Text_Recv.setText("");
//            AmoComActivity.scrollView.fullScroll(33);
//            AmoComActivity.Totol_Send_bytes = 0;
//            AmoComActivity.Totol_recv_bytes = 0;
//            AmoComActivity.Totol_recv_bytes_temp = 0;
//            AmoComActivity.update_display_send_recv_info(AmoComActivity.Totol_Send_bytes, AmoComActivity.Totol_recv_bytes);
//          }
//        });
//    tempControl = (TempControlView)findViewById(2131165205);
//    tempControl.setAngleRate(3);
//    tempControl.setTemp(30, 45, 30.0D);
//    tempControl.setOnTempChangeListener(new TempControlView.OnTempChangeListener() {
//          public void change(int param1Int) {}
//        });
//    tempControl.setOnClickListener(new TempControlView.OnClickListener() {
//          public void onClick(int param1Int) {}
//        });
//    System_OnOff_IF = 0;
//    mSwitch1 = (Switch)findViewById(2131165203);
//    mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
//            if (param1Boolean) {
//              AmoComActivity.System_OnOff_IF = 1;
//              Toast.makeText((Context)AmoComActivity.this, "ON", 0).show();
//              return;
//            }
//            AmoComActivity.System_OnOff_IF = 0;
//            byte[] arrayOfByte = Utils.hexStringToBytes("FF01");
//            AmoComActivity.this.Send_Bytes(arrayOfByte);
//            Toast.makeText((Context)AmoComActivity.this, "OFF", 0).show();
//          }
//        });
//  }
//
//  public void registerBoradcastReceiver() {
//    IntentFilter intentFilter = new IntentFilter();
//    intentFilter.addAction("AMOMCU_RSSI");
//    intentFilter.addAction("AMOMCU_CONNECT");
//    registerReceiver(this.mBroadcastReceiver, intentFilter);
//  }
//}
