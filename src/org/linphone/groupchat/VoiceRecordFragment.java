package org.linphone.groupchat;

import java.io.IOException;
import java.util.Random;

import org.linphone.R;

import android.support.v4.app.Fragment;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.net.Uri;

public class VoiceRecordFragment extends Fragment implements OnCompletionListener{
	
	Uri audioUri;
	
	private static VoiceRecordFragment instance;
	
	 private MediaRecorder myRecorder;

	  private MediaPlayer myPlayer;

	   private String outputFile = null;

	   private Button startBtn;

	   private Button stopBtn;

	   private Button playBtn;

	   private Button stopPlayBtn;

	   private TextView text;
	   
	   private ImageView img;
	   
	   private Button sendBtn;
	   
	   private Chronometer mychronometer;
	   

	   @Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			instance = this;
			View view = inflater.inflate(R.layout.activity_voice_record, container, false);
			
			setRetainInstance(true);
			   
			DisplayMetrics dm = new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
			
			int width = dm.widthPixels;
			int height = dm.heightPixels;
			
			getActivity().getWindow().setLayout((int)(width*0.8),(int)(height*0.6));
		  text = (TextView) view.findViewById(R.id.rec_text);
	      stopBtn = (Button) view.findViewById(R.id.stop);
	      startBtn = (Button) view.findViewById(R.id.start);
	      playBtn = (Button) view.findViewById(R.id.play);
	      stopPlayBtn = (Button) view.findViewById(R.id.stopPlay);
	      sendBtn = (Button) view.findViewById(R.id.sendbtn);
	      img = (ImageView) view.findViewById(R.id.micrec);
	      mychronometer = (Chronometer) view.findViewById(R.id.chronometer1);
	    
	      // store it to sd card
	    outputFile = Environment.getExternalStorageDirectory().

	             getAbsolutePath() + "/linphoneaudio.3gpp";

	     myRecorder = new MediaRecorder();

	      myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

	      myRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

	      myRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

	      myRecorder.setOutputFile(outputFile);



	      startBtn.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {

	            // TODO Auto-generated method stub

	           start(v);

	        }

	      });
	      
	 

	      stopBtn.setOnClickListener(new OnClickListener() {
	        @Override

	        public void onClick(View v) {
          // TODO Auto-generated method stub

	          stop(v);

	        }

	      });

	    

	      playBtn.setOnClickListener(new OnClickListener() {

	        @Override

	        public void onClick(View v) {
	            // TODO Auto-generated method stub
	              play(v);   

	        }

	      });

	       

	

	      stopPlayBtn.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {

	            // TODO Auto-generated method stub

	          stopPlay(v);

	        }

	      });
	      
	      return view;
	  }

		
  public void start(View view){

	       try {

	          myRecorder.prepare();

	          myRecorder.start();
	          
	          mychronometer.start();
	          
	  
	       } catch (IllegalStateException e) {

	          // start:it is called before prepare()

	          // prepare: it is called after start() or before setOutputFormat()


	       } catch (IOException e) {

	           // prepare() fails

	           e.printStackTrace();

	        }

	        

	       text.setText("Recording Point: Recording");

	       startBtn.setEnabled(false);

	      stopBtn.setEnabled(true);
	  

	        

	       Toast.makeText(getActivity(), "Start recording...",

	               Toast.LENGTH_SHORT).show();

	   }



	public void stop(View view){

	       try {

	          myRecorder.stop();

	          myRecorder.release();

	          myRecorder  = null;
	          mychronometer.stop();
	          stopBtn.setEnabled(false);

	          playBtn.setEnabled(true);
	          sendBtn.setEnabled(true);
	         
	          text.setText("Recording Point: Stop recording");
	           

	          Toast.makeText(getActivity(), "Stop recording...",

	                  Toast.LENGTH_SHORT).show();

	       } catch (IllegalStateException e) {

	            //  it is called before start()

	            e.printStackTrace();

	       } catch (RuntimeException e) {

	            // no valid audio/video data has been received

	            e.printStackTrace();

	       }

	   }

	   

	   public void play(View view) {

	       try{

	           myPlayer = new MediaPlayer();

	           myPlayer.setDataSource(outputFile);
	           myPlayer.prepare();

	           myPlayer.start();

	           playBtn.setEnabled(false);
	           stopPlayBtn.setEnabled(true);

          text.setText("Recording Point: Playing");

	            

	           Toast.makeText(getActivity(), "Start play the recording...",

	                   Toast.LENGTH_SHORT).show();

	       } catch (Exception e) {

	            // TODO Auto-generated catch block

	            e.printStackTrace();

	        }

	   }

	    

	   public void stopPlay(View view) {

	       try {

	           if (myPlayer != null) {

	               myPlayer.stop();

	               myPlayer.release();

	               myPlayer = null;

	               playBtn.setEnabled(true);

	               stopPlayBtn.setEnabled(false);

	               text.setText("Recording Point: Stop playing");

	                

	               Toast.makeText(getActivity(), "Stop playing the recording...",

	                       Toast.LENGTH_SHORT).show();

	           }

	       } catch (Exception e) {

	            // TODO Auto-generated catch block

	            e.printStackTrace();

	        }

	   }
	   
	   //Method for sending the voice note on the group
	
	   public void send(View view){
		   //TODO
	   }


	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}
	   
	   


}
