package com.example.myaccelertiondemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textView, textInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get an instance of the SensorManager
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        textInfo = findViewById(R.id.text_info);

        //get an instance of the Text_view
        textView = findViewById(R.id.text_view);

    }

    @Override
    protected void onResume(){
        super.onResume();
        //Listenerの登録
        Sensor accel = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_NORMAL);
        //sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_FASTEST);
        //sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_GAME);
        //sensorManager.registerListener(this,accel,SensorManager.SENSOR_DELAY_UI);
    }

    //解除するコードも入れる
    @Override
    protected  void onPause(){
        super.onPause();
        //Listenerを解除
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensorX, sensorY, sensorZ;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            sensorX = event.values[0];
            sensorY = event.values[1];
            sensorZ = event.values[2];

            String strTmp = "加速度センサー\n"
                    + "X:" + sensorX + "\n"
                    + "Y:" + sensorY + "\n"
                    + "Z:" + sensorZ;
            textView.setText(strTmp);

            showInfo(event);
        }
    }

    // （お好みで）加速度センサーの各種情報を表示
    private  void showInfo(SensorEvent event){
        //センサー名
        StringBuffer info = new StringBuffer("Name: ");
        info.append("\n");

        //ベンダー名
        info.append("Vendor: ");
        info.append(event.sensor.getVendor());

        //型番
        info.append("Type: ");
        info.append(event.sensor.getType());
        info.append("\n");

        //最小遅れ
        int data = event.sensor.getMinDelay();
        info.append(String.valueOf(data));
        info.append("usec\n");

        //最大遅れ
        data = event.sensor.getMaxDelay();
        info.append("Maxdelay");
        info.append(String.valueOf(data));
        info.append("usec\n");

        //レポートモード
        data = event.sensor.getReportingMode();
        String stinfo = "unknown";
        if(data == 0){
            stinfo = "REPORTING_MODE_CONTINUOUS";
        }else if(data==1){
            stinfo = "RPORTING_MODE_ON_CHANGE";
        }else if(data==2){
            stinfo = "REPORTING_MODE_ONE_SHOT";
        }
        info.append("ReportingMode: ");
        info.append(stinfo);
        info.append("\n");

        //  最大レンジ
        info.append("MaxRange: ");
        float fData = event.sensor.getMaximumRange();
        info.append(String.valueOf(fData));
        info.append("\n");

        // 分解能
        info.append("Resolution: ");
        fData = event.sensor.getResolution();
        info.append(String.valueOf(fData));
        info.append("mA\n");

        textInfo.setText(info);

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
