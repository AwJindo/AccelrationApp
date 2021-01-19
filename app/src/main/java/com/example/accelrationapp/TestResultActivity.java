package com.example.accelrationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;

public class TestResultActivity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    public int size;

    public Integer i;
    public Integer s;
    public int medel = 0;
    public Double maxvalue;
    public Integer maxvalues;
    TextView Max_Force;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        Max_Force = (TextView) findViewById(R.id.Max_Force);

        size = DeviceControlActivity.ArrData.size();
        System.out.println(size);

        double t=0;
        double N;
        int mass =128;
        for (i = 0; i < size ; i++ ) {
            for (s = i + 1; s < size; s++) {
                if (DeviceControlActivity.ArrData.get(s) - 2 == DeviceControlActivity.ArrData.get(i) || DeviceControlActivity.ArrData.get(s) - 1 == DeviceControlActivity.ArrData.get(i) || DeviceControlActivity.ArrData.get(s) + 2 == DeviceControlActivity.ArrData.get(i) || DeviceControlActivity.ArrData.get(s) + 1 == DeviceControlActivity.ArrData.get(i) && i + 1 != size) {
                    DeviceControlActivity.ArrData.set(s, DeviceControlActivity.ArrData.get(i));
                } else {
                    DeviceControlActivity.ArrData.get(i);
                }
            }
        }


        for (i=0; i < 10; i++)
        {
            medel = medel + DeviceControlActivity.ArrData.get(i);
        }
        medel= medel/10;
        System.out.println("test" +medel);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();
        for(i =0; i < size; i++) {
            t += 0.02;
            N = Math.sqrt((DeviceControlActivity.ArrData.get(i)-medel)*(DeviceControlActivity.ArrData.get(i)-medel)) *(9.82*mass/100);
            series.appendData(new DataPoint(t, N), true, 10000000);
        }
        graph.addSeries(series);

        maxvalues = Collections.max(DeviceControlActivity.ArrData);
        maxvalue = (maxvalues-medel)*(9.82*mass/100);
        Max_Force.setText(String.valueOf(maxvalue)+"N");
    }
}