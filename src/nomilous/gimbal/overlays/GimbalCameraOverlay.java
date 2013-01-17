package nomilous.gimbal.overlays;

import java.io.IOException;

import android.content.Context;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.hardware.Camera;
import java.util.List;

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
        return cameraPreview.getView();
    }

    public void start() {
        if( getCamera() ) cameraPreview.loadCamera(camera);
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
        cameraPreview.loadCamera(null);
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    private static class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

        private SurfaceView surfaceView;
        private SurfaceHolder surfaceHolder;
        private Camera camera;
        private List<Camera.Size> supportedPreviewSizes;

        CameraPreview(Context context) {
            super(context);
            surfaceView = new SurfaceView(context);
            addView(surfaceView);    // hmmmmm. (Look into ViewGroup...)
        }

        public void start() {
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

        public void loadCamera(Camera camera) {
            Util.debug("CameraPreview.loadCamera()");
            if( this.camera == camera ) return;
            Util.debug("CameraPreview.loadCamera() LOADING CAMERA");
            stopPreviewAndFreeCamera();
            this.camera = camera;
            if( camera == null ) return;

            List<Camera.Size> sizes = camera.getParameters().getSupportedPreviewSizes();
            supportedPreviewSizes = sizes;
            requestLayout();

            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                Util.debug("CameraPreview.loadCamera() PREVIEW STARTED");
            } catch (IOException ios) { 
                Util.debug("Failed to load camera");
                ios.printStackTrace(); 
            }

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
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(w, h);
            requestLayout();
            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override // SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {
            Util.debug("CameraPreview.surfaceDestroyed()");
            if( camera == null ) return;
            stopPreviewAndFreeCamera();
        }

        private void stopPreviewAndFreeCamera() {
            if (camera == null) return;

            //
            // stop the camera from streaming video to the surfaceView
            //
            camera.stopPreview(); 

            //
            // allow other apps to use the camera once again
            //
            camera.release();
            camera = null;
        }

    }

}
