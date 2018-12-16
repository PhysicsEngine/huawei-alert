package yyyymmdd.jp.huaweialert;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    /** BLE機器のスキャンを行うクラス */
    private BluetoothAdapter mBluetoothAdapter;
    /** BLE機器のスキャンを別スレッドで実行するためのHandler */
    private Handler mHandler;

    final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            //Log.d(MainActivity.class.getName(), "callbackType = " + callbackType);
            BluetoothDevice bluetoothDevice = result.getDevice();
            String name = bluetoothDevice.getName() != null ? bluetoothDevice.getName() : "";
            if (name.indexOf("HUAWEI")>=0) {
                Log.d("BLE", "address:" + bluetoothDevice.getAddress() + ",name:" + name);
            }
            if (bluetoothDevice.getAddress().startsWith("A0:57:E3")) {
                Log.d("BLE", "address" + bluetoothDevice.getAddress());
            }
            //Log.d(MainActivity.class.getName(), "address:" + bluetoothDevice.getAddress());
            //Log.d(MainActivity.class.getName(), "name:" + bluetoothDevice.getName());
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    /** Bluetooth Receiver */
    /*
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BLEActivity", "on receive event");
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String mResult = "";
                mResult += "Name : " + device.getName() + "\n";
                mResult += "Device Class : " + device.getBluetoothClass().getDeviceClass() + "\n";
                mResult += "MAC Address : " + device.getAddress() + "\n";
                //mResult += "State : " + getBondState(device.getBondState()) + "\n";
                Log.d("BLEActivity", mResult);
            }
        }
    };
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // インテントフィルタの作成
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        // ブロードキャストレシーバの登録
        //registerReceiver(mReceiver, filter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                mBluetoothAdapter.getBluetoothLeScanner().startScan(scanCallback);
                /*
                if (!mBluetoothAdapter.isEnabled()) {
                    Log.d("onClick", "bluetooth enable");
                    mBluetoothAdapter.enable();
                }
                if (mBluetoothAdapter.isDiscovering()) {
                    Log.d("onClick", "bluetooth is discovering");
                } else {
                    Log.d("onClick", "start discovery bluetooth");
                    if(!mBluetoothAdapter.startDiscovery()) {
                        Log.d("onClick", "start discovery not enable");
                    }
                }
                */
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        //unregisterReceiver(mReceiver);
    }
}
