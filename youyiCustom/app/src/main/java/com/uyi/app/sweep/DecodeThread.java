package com.uyi.app.sweep;

import java.util.concurrent.CountDownLatch;

import android.os.Handler;
import android.os.Looper;
import com.uyi.app.sweep.CaptureActivity;
/**
 * 作者: zdw
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

	CaptureActivity activity;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	DecodeThread(CaptureActivity activity) {
		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);
	}

	Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
