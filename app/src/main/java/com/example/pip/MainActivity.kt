package com.example.pip

import android.app.AlertDialog
import android.app.PictureInPictureParams
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Point
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.Display
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnCompletionListener {
    var vw: VideoView? = null
    var videolist = ArrayList<Int>()
    var currvideo = 0
    private var mApplication: MyApplication? = null
    var context:Context?=null
    var mediaController:MediaController?=null
//    var videoUrl =
//        "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1"

    var videoUrl ="https://www.rmp-streaming.com/media/bbb-360p.mp4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//
//        mApplication = applicationContext as MyApplication
//
//        if (mApplication!!.mode == MyApplication.MODE_NONE) {
//            saveDpi();
//        } else {
//            setDpi();
//        }
        setContentView(R.layout.activity_main)
        vw = findViewById<View>(R.id.vidvw) as VideoView

        mediaController?.setAnchorView(vw);
        mediaController?.setMediaPlayer(vw);
         mediaController = MediaController(this)

        vw!!.setMediaController(mediaController);


        vw!!.setOnCompletionListener(this)
      ;

        // video name should be in lower case alphabet.
//        videolist.add("https://www.rmp-streaming.com/media/bbb-360p.mp4")
//        videolist.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1")
//        videolist.add("https://www.rmp-streaming.com/media/bbb-360p.mp4")
//        vw!!.setVideoURI(Uri.parse(videolist[0]));
        videolist.add(R.raw.demo)
        videolist.add(R.raw.earth)
        videolist.add(R.raw.big)

        setVideo(videolist[0])
        Log.e(".......", videolist[0].toString())
    }

    fun setVideo(id: Int) {
        Log.e("id....",id.toString())
        Log.i(TAG,"This is an info message");
        val uriPath = ("android.resource://"
                + packageName + "/" + id)
        Log.w(TAG, "This is a warning message$uriPath");

        val uri = Uri.parse(uriPath)
        vw!!.setVideoURI(uri)
        vw!!.start()
    }
//    private fun saveDpi() {
//        val configuration: Configuration = resources.configuration
//        mApplication!!.orgDensityDpi = configuration.densityDpi
//    }

//    private fun setDpi() {
//        val configuration: Configuration = resources.configuration
//        val metrics = resources.displayMetrics
//        if (mApplication!!.mode == MyApplication.MODE_PIP) {
//            configuration.densityDpi = mApplication!!.orgDensityDpi / 3
//        } else {
//            configuration.densityDpi = mApplication!!.orgDensityDpi
//        }
//
//        baseContext.resources.updateConfiguration(configuration, metrics)
//
//    }


    override fun onCompletion(mediapalyer: MediaPlayer) {
        val obj: AlertDialog.Builder = AlertDialog.Builder(this)
        obj.setTitle("Playback Finished!")
        obj.setIcon(R.mipmap.ic_launcher)
        val m: MyListener = MyListener()
        obj.setPositiveButton("Replay", m)
        obj.setNegativeButton("Next", m)
        obj.setMessage("Want to replay or play next video?")
        obj.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onUserLeaveHint() {
//        val pictureInPictureParams = PictureInPictureParams.Builder().build()
//        enterPictureInPictureMode(pictureInPictureParams)

        val ratio = Rational(vw!!.width*4, vw!!.height*5)

        val pip_Builder = PictureInPictureParams.Builder()
       val param= pip_Builder.setAspectRatio(ratio).build()
        enterPictureInPictureMode(param)
    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onPictureInPictureModeChanged(
//        isInPictureInPictureMode: Boolean,
//        newConfig: Configuration
//    ) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
//        if (isInPictureInPictureMode) {
//            mApplication!!.mode = MyApplication.MODE_PIP;
//        } else {
//            mApplication!!.mode = MyApplication.MODE_FULL;
//        }
//    }
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean,
                                               newConfig: Configuration
        ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if (isInPictureInPictureMode) {

            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            // Restore the full-screen UI.
        }
        }
    internal inner class MyListener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface, which: Int) {
            if (which == -1) {
                vw!!.seekTo(0)
                vw!!.start()
            } else {
                ++currvideo
                if (currvideo == videolist.size) currvideo = 0
                setVideo(videolist[currvideo])
            }
        }
    }
}