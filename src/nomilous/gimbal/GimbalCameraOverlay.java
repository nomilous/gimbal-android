package nomilous.gimbal;

import android.content.Context;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import nomilous.Util;

class GimbalCameraOverlay extends GimbalOverlay {

    GimbalCameraOverlay(Object android) {
        super(android);
        Util.debug("START GimbalCameraOverlay");   
    }

    public SurfaceView view() {
        return new SurfaceView(context);
    }

    private static class CameraPreview extends ViewGroup implements SurfaceHolder.Callback {

        SurfaceView surfaceView;
        SurfaceHolder holder;

        CameraPreview(Context context) {
            super(context);

            surfaceView = new SurfaceView(context);


            addView(surfaceView);    // hmmmmm. (Look into ViewGroup...)


            holder = surfaceView.getHolder();
            holder.addCallback(this);
            holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override // ViewGroup/../View
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {}

        @Override // SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder holder) {}

        @Override // SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {}

        @Override // SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder holder) {}

    }

}
