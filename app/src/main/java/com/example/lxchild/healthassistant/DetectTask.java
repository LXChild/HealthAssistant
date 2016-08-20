package com.example.lxchild.healthassistant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.lxchild.result.ResultActivity;
import com.example.lxchild.util.mColorDetect;
import com.example.lxchild.util.mFaceDetect;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by LXChild on 8/6/15.
 */
public class DetectTask extends AsyncTask<Bitmap, Void, String> {
    private Context cxt;
    private static final String TAG = DetectTask.class.getSimpleName();
    private ProgressDialog progressDialog;

    public DetectTask(Context cxt) {
        this.cxt = cxt;
    }

    @Override
    protected void onPreExecute() {
        showProgressDialog(cxt);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Bitmap... params) {
//        mFaceDetect faceDetect = new mFaceDetect(params[0]);
//        mColorDetect colorDetect = new mColorDetect(faceDetect.findpoint(), params[0]);
//        int[] color_a = colorDetect.getColor();
//        Log.d(TAG, Arrays.toString(color_a));
//        return faceDetect.getMobject().toString();

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        showResult(result);
        super.onPostExecute(result);
    }

    private void showProgressDialog(Context cxt) {

        progressDialog = new ProgressDialog(cxt);
        progressDialog.setTitle("Loading......");
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    private void showResult(String result) {
        Intent intent = new Intent();
        intent.putExtra("result", result);
        intent.setClass(cxt, ResultActivity.class);
        cxt.startActivity(intent);
    }
}
