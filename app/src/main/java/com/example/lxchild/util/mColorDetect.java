package com.example.lxchild.util;

import android.graphics.Bitmap;
import android.graphics.PointF;

import org.json.JSONException;
import org.json.JSONObject;


public class mColorDetect {

    JSONObject faceobject;
    PointF nose_contour_left2; //��
    PointF nose_contour_right2;//��
    PointF nose_contour_tip;//��
    String index;
    Bitmap mbitmap = null;
    int mArrayColor[] = null;
    int maxPixel = 0;
    Bitmap jiequ = null;

    public mColorDetect(JSONObject object, Bitmap bitmap) {
        mbitmap = bitmap;
        faceobject = object;
        try {
            nose_contour_tip.x = (float) faceobject.getDouble("nose_contour_tip.x");
            nose_contour_tip.y = (float) faceobject.getDouble("nose_contour_tip.y");
            nose_contour_left2.x = (float) faceobject.getDouble("nose_contour_left2.x");
            nose_contour_left2.y = (float) faceobject.getDouble("nose_contour_left2.y");
            nose_contour_right2.x = (float) faceobject.getDouble("nose_contour_right2.x");
            nose_contour_right2.y = (float) faceobject.getDouble("nose_contour_right2.y");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        jiequ = Bitmap.createBitmap(mbitmap,
                (int) nose_contour_left2.x, (int) nose_contour_left2.y, (int) (nose_contour_right2.x - nose_contour_left2.x), (int) (nose_contour_tip.y - nose_contour_left2.y));
        maxPixel = jiequ.getHeight() * jiequ.getWidth();
        mArrayColor = new int[maxPixel];
    }

    public int[] getColor() {
        int count = 0;
        for (int i = 0; i < jiequ.getHeight(); i++) {
            for (int j = 0; j < jiequ.getWidth(); j++) {
                int color = jiequ.getPixel(j, i);
                mArrayColor[count] = color;
                count++;
            }
        }
        return mArrayColor;
    }
}
