package com.example.lxchild.util;

import java.io.ByteArrayOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;

public class mFaceDetect {

	private static final String TAG = mFaceDetect.class.getSimpleName();
	Bitmap mbitmap;
	HttpRequests mrequest;
	ByteArrayOutputStream mstream;
	byte[] marray;
	String apikey="5a95259d4e53af4eda4cf66ca8237c57";
	String apisecreat="8Nqpba1Ay4YmCGxvweOYx-FdG_DQxiUh";
	JSONObject mobject,mobject1;
	String mfaceid=null;

	public mFaceDetect(Bitmap mbtm)
	{
		mbitmap=mbtm.copy(Bitmap.Config.RGB_565, true);
		mstream=new ByteArrayOutputStream();
		mrequest= new HttpRequests(apikey,apisecreat,true,true);
		mbitmap.compress(Bitmap.CompressFormat.JPEG, 100 , mstream);
		marray = mstream.toByteArray();
		try {
			mobject1= mrequest.detectionDetect(new PostParameters().setImg(marray));

		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			mfaceid= mobject1.getJSONArray("face").getJSONObject(0).getString("face_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mobject= mrequest.detectionLandmark(new PostParameters().setImg(marray).setFaceId(mfaceid));
		} catch (FaceppParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		Log.d(TAG, mobject1.toString() + "," + mobject.toString() + "," + mfaceid);
	}

    public JSONObject getMobject() {
        return mobject;
    }

    public JSONObject findpoint()
	{

		PointF 	contour_chin=null,
				contour_left1=null,
				contour_left2=null,
				contour_left3=null,
				contour_left4=null,
				contour_left5=null,
				contour_left6=null,
				contour_left7=null,
				contour_left8=null,
				contour_left9=null,
				contour_right1=null,
				contour_right2=null,
				contour_right3=null,
				contour_right4=null,
				contour_right5=null,
				contour_right6=null,
				contour_right7=null,
				contour_right8=null,
				contour_right9=null,
				left_eye_bottom=null,
				left_eye_center=null,
				left_eye_left_corner=null,
				left_eye_lower_left_quarter=null,
				left_eye_lower_right_quarter=null,
				left_eye_pupil=null,
				left_eye_right_corner=null,
				left_eye_top=null,
				left_eye_upper_left_quarter=null,
				left_eye_upper_right_quarter=null,
				left_eyebrow_left_corner=null,
				left_eyebrow_lower_left_quarter=null,
				left_eyebrow_lower_middle=null,
				left_eyebrow_lower_right_quarter=null,
				left_eyebrow_right_corner=null,
				left_eyebrow_upper_left_quarter=null,
				left_eyebrow_upper_middle=null,
				left_eyebrow_upper_right_quarter=null,
				mouth_left_corner=null,
				mouth_lower_lip_bottom=null,
				mouth_lower_lip_left_contour1=null,
				mouth_lower_lip_left_contour2=null,
				mouth_lower_lip_left_contour3=null,
				mouth_lower_lip_right_contour1=null,
				mouth_lower_lip_right_contour2=null,
				mouth_lower_lip_right_contour3=null,
				mouth_lower_lip_top=null,
				mouth_right_corner=null,
				mouth_upper_lip_bottom=null,
				mouth_upper_lip_left_contour1=null,
				mouth_upper_lip_left_contour2=null,
				mouth_upper_lip_left_contour3=null,
				mouth_upper_lip_right_contour1=null,
				mouth_upper_lip_right_contour2=null,
				mouth_upper_lip_right_contour3=null,
				mouth_upper_lip_top=null,
				nose_contour_left1 = null,
				nose_contour_left2=null,
				nose_contour_left3=null,
				nose_contour_lower_middle=null,
				nose_contour_right1=null,
				nose_contour_right2=null,
				nose_contour_right3=null,
				nose_left=null,
				nose_right=null,
				nose_tip=null,
				right_eye_bottom=null,
				right_eye_center=null,
				right_eye_left_corner=null,
				right_eye_lower_left_quarter=null,
				right_eye_lower_right_quarter=null,
				right_eye_pupil=null,
				right_eye_right_corner=null,
				right_eye_top=null,
				right_eye_upper_left_quarter=null,
				right_eye_upper_right_quarter=null,
				right_eyebrow_left_corner=null,
				right_eyebrow_lower_left_quarter=null,
				right_eyebrow_lower_middle=null,
				right_eyebrow_lower_right_quarter=null,
				right_eyebrow_right_corner=null,
				right_eyebrow_upper_left_quarter=null,
				right_eyebrow_upper_middle=null,
				right_eyebrow_upper_right_quarter=null;

		try {
			nose_contour_left1.x = (float) mobject.getDouble("nose_contour_left1.x");
			nose_contour_left1.y = (float) mobject.getDouble("nose_contour_left1.y");
			nose_contour_left2.x = (float) mobject.getDouble("nose_contour_left2.x");
			nose_contour_left2.y = (float) mobject.getDouble("nose_contour_left2.y");
			nose_contour_left3.x = (float) mobject.getDouble("nose_contour_left3.x");
			nose_contour_left3.y = (float) mobject.getDouble("nose_contour_left3.y");
			nose_contour_lower_middle.x = (float) mobject.getDouble("nose_contour_lower_middle.x");
			nose_contour_lower_middle.y = (float)mobject.getDouble("nose_contour_lower_middle.y");
			nose_contour_right1.x= (float) mobject.getDouble("nose_contour_right1.x");
			nose_contour_right1.y= (float) mobject.getDouble("nose_contour_right1.y");
			nose_contour_right2.x = (float) mobject.getDouble("nose_contour_right2.x");
			nose_contour_right2.y = (float) mobject.getDouble("nose_contour_right2.y");
			nose_contour_right3.x = (float) mobject.getDouble("nose_contour_right3.x");
			nose_contour_right3.y = (float) mobject.getDouble("nose_contour_right3.y");
			nose_left.x = (float) mobject.getDouble("nose_left.x");
			nose_left.y = (float) mobject.getDouble("nose_left.y");
			nose_right.x = (float) mobject.getDouble("nose_right.x");
			nose_right.y = (float) mobject.getDouble("nose_right.y");
			nose_tip.x = (float) mobject.getDouble("nose_tip.x");
			nose_tip.y = (float) mobject.getDouble("nose_tip.y");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mobject; //∑µªÿ÷µø…“‘“™OBJECTµƒ“™ «≤ª∫√ ∂±µ„¥¶¿Ì∫√¡À‘⁄…œ√Êƒ«∏ˆtry catchΩ·ππ¿Ô
	}
}
