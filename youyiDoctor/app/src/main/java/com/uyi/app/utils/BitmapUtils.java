package com.uyi.app.utils;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import android.graphics.Bitmap;

public class BitmapUtils {
	/**
	 * bitmap 转base64
	 * @param bitmap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encode(Bitmap bitmap) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        //base64 encode
        return Base64.encode(bytes);
	}
}
