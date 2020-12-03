package com.example.accelrationapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class BTConectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "BTConectionActivity";

    BluetoothAdapter mBluetoothAdapter;
    private Button buttononoff;
    private Button enablediscover;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public DeviceListAdapter mDeviceListAdapter;
    ListView newdevices;
    BluetoothGatt bluetoothGatt;

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch (state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "onReceive: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "onReceive: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "onReceive: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    //BroadcastReceiver for Discoverability mode on/off or expire
    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "onReceive: Discoverability Enabled.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "onReceive: Discoverability disabled. Able to receive connections");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "onReceive: Discoverability disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "onReceive: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "onReceive: Connected");
                        break;
                }
            }
        }
    };

    //Broadcastreceiver for listing devices that are not yet paired exec by buttondiscover
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: Action found.");
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive:" + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                newdevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    //Broadcastreceiver for pairing
    private final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                //case 1 bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "onReceive: BOND_BONDED ");
                }
                //case2: Creating a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING){
                    Log.d(TAG, "onReceive: BOND_BONDING");
                }
                //case3: breakning bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE){
                    Log.d(TAG, "onReceive: BOND_NONE");
                }
            }
        }
    };


   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called.");
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_t_conection);
        buttononoff = (Button) findViewById(R.id.buttononoff);                                      //button to put on or off bluetooth
        enablediscover = (Button) findViewById(R.id.enablediscover);                                //button for devices to discover our device
        newdevices = (ListView) findViewById(R.id.newdevices);                                      //listview of our new devices
        mBTDevices = new ArrayList<>();

        //Broadcasts when band state changes (pairing)
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();                                   //variable for the bluetoothadapter
        newdevices.setOnItemClickListener(BTConectionActivity.this);



        buttononoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: enabling/dissabling.");
                enableDisableBT();
            }
        });
    }


    public void enableDisableBT(){                                                                  //function for put on or off button
        if(mBluetoothAdapter == null){
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if(!mBluetoothAdapter.isEnabled()){                                                         //if bluetooth disable, enable bluetooth
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);
        }
        if (mBluetoothAdapter.isEnabled()){                                                         //if bluetooth disable, disable bluetooth
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);
        }
    }

    public void buttonEnDiDiscoverable(View view) {                                                 //function for our device to be discoreable
        Log.d(TAG, "buttonEnDiDiscoverable: Making device Discoverable for 300s.");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);                                                          //make our device discoverable for other devices for 300sec

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void buttondiscover(View view) {                                                         //function to se available bluetooth devices
        Log.d(TAG, "buttondiscover: looking for unpaired devices.");

        if(mBluetoothAdapter.isDiscovering()){                                                      //if we are discovering devices, we need to cancel it in order to start it again
            mBluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "buttondiscover: Canceling discovery.");

            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
        if(!mBluetoothAdapter.isDiscovering()){                                                     // if discovering devices isnt on, enable discovering.

            checkBTPermissions();

            mBluetoothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissioncheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissioncheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissioncheck !=0){

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1011);
            }
        }
        else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {              //function for pairing and bonding devices
        mBluetoothAdapter.cancelDiscovery();                                                        //first cancel disccovery because it is very memory intensive

        Log.d(TAG, "onItemClick: you clicked on a device");
        String deviceName = mBTDevices.get(position).getName();                                     //when we click on a deevice, we get make a string of their name and adress
        String deviceAdress = mBTDevices.get(position).getAddress();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAdress);

        //create bond, requires API17+
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "onItemClick: Trying to pair with " + deviceName);
            mBTDevices.get(position).connectGatt(this,true,btleGattCallback);                                                  //we try to pair with the device we click on.
        }
    }

    private final BluetoothGattCallback btleGattCallback = new BluetoothGattCallback() {
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };
}