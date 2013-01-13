package nomilous.gimbal;

import android.content.Context;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.hardware.Camera;

import nomilous.Util;

class GimbalCameraOverlay extends GimbalOverlay {

    private CameraPreview cameraPreview;
    private Camera camera;

    GimbalCameraOverlay(Object android) {
        super(android);
        Util.debug("START GimbalCameraOverlay");   
    }

    public SurfaceView view() {
        Util.debug("GimbalCameraOverlay.view()");
        cameraPreview = new CameraPreview(context);
        getCamera();
        releaseCamera();
        return cameraPreview.getView();
    }

    //
    // This should ideally be done on a Thread
    // it may take a while...
    //
    public boolean getCamera( /* int id */ ) { // there may be more than one
        Util.debug("GimbalCameraOverlay.getCamera()");
        try {
            releaseCamera();
            //camera = Camera.open(id);
            camera = Camera.open();  // default camera (presumably)
        } catch (Exception e) {
            Util.debug("Failed to open camera");
            e.printStackTrace();
        }
        return (camera != null);
    }

    private void releaseCamera() {
        Util.debug("GimbalCameraOverlay.releaseCamera()");
        cameraPreview.setCamera(null);
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private static class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

        private SurfaceView surfaceView;
        private SurfaceHolder surfaceHolder;
        private Camera camera;

        CameraPreview(Context context) {
            super(context);

            surfaceView = new SurfaceView(context);

            // 
            // controlling the view insertion in GimbalUIOverlay
            // 
            //addView(surfaceView);    // hmmmmm. (Look into ViewGroup...)

            //
            // May not have the holder yet... (pending)
            // 

            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        public SurfaceView getView() { 
            return surfaceView;
        }

        public SurfaceHolder getHolder() {
            return surfaceHolder;
        }

        public void setCamera(Camera camera) {
            if( this.camera == camera ) return;




            //
            // PENDING attach camera to surfaceView
            //




        }

        @Override // ViewGroup/../View
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            Util.debug("CameraPreview.onLayout()");
        }

        @Override // SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {
            Util.debug("CameraPreview.surfaceCreated()");
        }

        @Override // SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Util.debug("CameraPreview.surfaceChanged()");
        }

        @Override // SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            Util.debug("CameraPreview.surfaceDestroyed()");
        }

    }

}
