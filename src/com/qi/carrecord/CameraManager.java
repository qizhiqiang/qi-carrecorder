package com.qi.carrecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;

public class CameraManager {
    ProgressDialog alertDialog;
    private Handler autoFocusHandler;
    private Camera camera;
    private static CameraManager cameraManager;
    private final Context context;
    private Handler previewHandler;
    private int previewMessage;
    private boolean previewing;
    
    public static void init(Context context) {
        if(cameraManager == null) {
            cameraManager = new CameraManager(context);
        }
    }
    
    public static CameraManager get() {
        return cameraManager;
    }
    
    private CameraManager(Context context) {
    	this.context = context;
        camera = null;
        previewing = false;
    }
    
    public String openDriver(SurfaceHolder holder) throws IOException {
        String result = null;
        if(camera == null) {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        }
        return result;
    }
    
    public void closeDriver() {
        if(camera != null) {
            camera.release();
            camera = null;
        }
    }
    
    public void startPreview() {
        if((camera != null) && (!previewing)) {
            camera.startPreview();
            previewing = true;
        }
    }
    
    public void stopPreview() {
        if((camera != null) && (previewing)) {
            camera.stopPreview();
            previewHandler = null;
            autoFocusHandler = null;
            previewing = false;
        }
    }
    
    public void requestPreviewFrame(Handler handler, int message) {
        if((camera != null) && (previewing)) {
            previewHandler = handler;
            previewMessage = message;
            camera.takePicture(null, null, jpegCallback);
        }
    }
    
    public void requestAutoFocus(Handler handler, int message) {
    }
    
    private ShutterCallback shuuterCallback = new ShutterCallback() {
    	
        public void onShutter() {
        }
    };
    private Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
 
        public void onPictureTaken(byte[] data, Camera camera) {
            if(previewHandler != null) {
                try {
                    Bitmap $bitmap = BitmapFactory.decodeByteArray(data, 0x0, data.length);
                    int sizew = 0xf0;
                    int sizeh = 0x140;
                    float scaleWidth = (float)sizew / (float)$bitmap.getWidth();
                    float scaleHeight = (float)sizeh / (float)$bitmap.getHeight();
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap resizedBitmap = Bitmap.createBitmap($bitmap, 0x0, 0x0, $bitmap.getWidth(), $bitmap.getHeight(), matrix, true);
                    ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 0x4b, out);
                    byte[] array = out.toByteArray();
                    out.flush();
                    out.close();
                    Message message = previewHandler.obtainMessage(previewMessage, array);
                    message.sendToTarget();
                    previewHandler = null;
                    System.gc();
                    return;
                } catch(Exception localException1) {
                }
            }
        }
    };
    private Camera.PictureCallback rawCallback = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };
}
