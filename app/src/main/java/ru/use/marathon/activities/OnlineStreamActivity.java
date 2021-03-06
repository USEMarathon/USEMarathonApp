
/**
 * Created by Marat on 24-July-18.
 */
//package ru.use.marathon.activities;
//
//import ru.use.marathon.libstreaming.streaming.Session;
//import ru.use.marathon.libstreaming.streaming.SessionBuilder;
//import net.majorkernelpanic.streaming.audio.AudioQuality;
//import net.majorkernelpanic.streaming.gl.SurfaceView;
//import net.majo.VideoQuality;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.Window;
//import android.view.WindowManager;
//
//import ru.use.marathon.R;
//
//public class OnlineStreamActivity extends Activity implements RtspClient.Callback,
//        Session.Callback, SurfaceHolder.Callback {
//    // log tag
//    public final static String TAG = MainActivity.class.getSimpleName();
//
//    // surfaceview
//    private static SurfaceView mSurfaceView;
//
//    // Rtsp session
//    private Session mSession;
//    private static RtspClient mClient;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        setContentView(R.layout.activity_main);
//
//        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
//
//        mSurfaceView.getHolder().addCallback(this);
//
//        // Initialize RTSP client
//        initRtspClient();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        toggleStreaming();
//    }
//
//    @Override
//    protected void onPause(){
//        super.onPause();
//
//        toggleStreaming();
//    }
//
//    private void initRtspClient() {
//        // Configures the SessionBuilder
//        mSession = SessionBuilder.getInstance()
//                .setContext(getApplicationContext())
//                .setAudioEncoder(SessionBuilder.AUDIO_NONE)
//                .setAudioQuality(new AudioQuality(8000, 16000))
//                .setVideoEncoder(SessionBuilder.VIDEO_H264)
//                .setSurfaceView(mSurfaceView).setPreviewOrientation(0)
//                .setCallback(this).build();
//
//        // Configures the RTSP client
//        mClient = new RtspClient();
//        mClient.setSession(mSession);
//        mClient.setCallback(this);
//        mSurfaceView.setAspectRatioMode(SurfaceView.ASPECT_RATIO_PREVIEW);
//        String ip, port, path;
//
//        // We parse the URI written in the Editext
//        Pattern uri = Pattern.compile("rtsp://(.+):(\\d+)/(.+)");
//        Matcher m = uri.matcher(AppConfig.STREAM_URL);
//        m.find();
//        ip = m.group(1);
//        port = m.group(2);
//        path = m.group(3);
//
//        mClient.setCredentials(AppConfig.PUBLISHER_USERNAME,
//                AppConfig.PUBLISHER_PASSWORD);
//        mClient.setServerAddress(ip, Integer.parseInt(port));
//        mClient.setStreamPath("/" + path);
//    }
//
//    private void toggleStreaming() {
//        if (!mClient.isStreaming()) {
//            // Start camera preview
//            mSession.startPreview();
//
//            // Start video stream
//            mClient.startStream();
//        } else {
//            // already streaming, stop streaming
//            // stop camera preview
//            mSession.stopPreview();
//
//            // stop streaming
//            mClient.stopStream();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mClient.release();
//        mSession.release();
//        mSurfaceView.getHolder().removeCallback(this);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public void onSessionError(int reason, int streamType, Exception e) {
//        switch (reason) {
//            case Session.ERROR_CAMERA_ALREADY_IN_USE:
//                break;
//            case Session.ERROR_CAMERA_HAS_NO_FLASH:
//                break;
//            case Session.ERROR_INVALID_SURFACE:
//                break;
//            case Session.ERROR_STORAGE_NOT_READY:
//                break;
//            case Session.ERROR_CONFIGURATION_NOT_SUPPORTED:
//                break;
//            case Session.ERROR_OTHER:
//                break;
//        }
//
//        if (e != null) {
//            alertError(e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private void alertError(final String msg) {
//        final String error = (msg == null) ? "Unknown error: " : msg;
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setMessage(error).setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                    }
//                });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    @Override
//    public void onRtspUpdate(int message, Exception exception) {
//        switch (message) {
//            case RtspClient.ERROR_CONNECTION_FAILED:
//            case RtspClient.ERROR_WRONG_CREDENTIALS:
//                alertError(exception.getMessage());
//                exception.printStackTrace();
//                break;
//        }
//    }
//
//    @Override
//    public void onPreviewStarted() {
//    }
//
//    @Override
//    public void onSessionConfigured() {
//    }
//
//    @Override
//    public void onSessionStarted() {
//    }
//
//    @Override
//    public void onSessionStopped() {
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//    }
//
//    @Override
//    public void onBitrareUpdate(long bitrate) {
//    }
//
//}
