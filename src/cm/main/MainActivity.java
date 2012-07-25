package cm.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.StringUtils;

import cm.lib.ButtonOnClickListener;
import cm.lib.ButtonOnTouchListener;
import cm.lib.Methods;
import android.app.Activity;
import android.app.ListActivity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends ListActivity {
	
	// Buttons => Initial states
	public static Methods.ButtonModes play_mode = Methods.ButtonModes.READY;
	public static Methods.ButtonModes pause_mode = Methods.ButtonModes.FREEZE;
	public static Methods.ButtonModes rec_mode = Methods.ButtonModes.READY;

	//
	public static ImageButton ib_play;
	public static ImageButton ib_pause;
	public static ImageButton ib_rec;
	
	public static SeekBar sb;
	
	public static TextView tv_progress;

	//
	public static String  rootDir;
	
	public static MediaPlayer mp = null;

	public static MediaRecorder mr = null;

	//
	public static String currentFileName = null;

	//
	public static String targetFolder;

	//
	public static int max = 5;	// Used => test1_setProgress2TextView()
	public static int counter = 0;
	public static Timer timer;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        initial_setup();
        
//        set_listeners();
        
//        //debug
//        // Log
//		Log.d("MainActivity.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", Environment.getExternalStorageDirectory().getAbsolutePath());
		
        
        
    }//public void onCreate(Bundle savedInstanceState)

	private void initial_setup() {
		/*----------------------------
		 * Steps
		 * 1. Listeners
		 * 2. Set list view
		 * 3. MediaPlayer
		 * 4. SeekBar
			----------------------------*/
		
		set_listeners();
		
		/*----------------------------
		 * 2. Set list view
			----------------------------*/
		set_listview();
		
		/*----------------------------
		 * 3. MediaPlayer
			----------------------------*/
		mp = new MediaPlayer();
		
		//debug
//		test1_setProgress2TextView();
		
//		ProgressThread pt = new ProgressThread(this);
//		
//		pt.start();
		
	}//private void initial_setup()

	private void set_listview() {
		/*----------------------------
		 * Steps
		 * 1. Get view
		 * 2. Get file list
		 * 3. Adapter
		 * 4. Set adapter
			----------------------------*/
//		ListView lv = this.getListView();
		
		/*----------------------------
		 * 2. Get file list
			----------------------------*/
//		String targetFolder = "tapeatalk_records";
		targetFolder = "tapeatalk_records";
		
//		String  rootDir = new File(
		rootDir = new File(
						Environment.getExternalStorageDirectory().getAbsolutePath(), 
						targetFolder)
					.getAbsolutePath();
		
		File[] files = new File(rootDir).listFiles();
		
		List<String> file_names = new ArrayList<String>();
		
		for (File file : files) {
			
			file_names.add(file.getName());
			
		}//for (File file : files)
		
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								this,
								android.R.layout.simple_list_item_1,
								file_names
								);
		
		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
//		lv.setAdapter(adapter);
//		setAdapter(adapter);
		setListAdapter(adapter);
		
		
	}//private void set_listview()

	private void set_listeners() {
		/*----------------------------
		 * Steps
		 * 1. Touch
		 * 2. Click
		 * 
		 * 3. SeekBar
			----------------------------*/
		/*----------------------------
		 * 1. Touch
			----------------------------*/
		ib_play = (ImageButton) findViewById(R.id.main_iv_play);
		ib_pause = (ImageButton) findViewById(R.id.main_iv_pause);
		ib_rec = (ImageButton) findViewById(R.id.main_iv_rec);
		
		// Tags
		ib_play.setTag(Methods.ButtonTags.main_bt_play);
		ib_pause.setTag(Methods.ButtonTags.main_bt_pause);
		ib_rec.setTag(Methods.ButtonTags.main_bt_rec);
		
		// Enable
		setup_buttons();
//		Methods.update_buttonImages(this);
		
		
		
		// Listener
		ib_play.setOnTouchListener(new ButtonOnTouchListener(this));
		ib_pause.setOnTouchListener(new ButtonOnTouchListener(this));
		ib_rec.setOnTouchListener(new ButtonOnTouchListener(this));
		
		/*----------------------------
		 * 2. Click
			----------------------------*/
		ib_play.setOnClickListener(new ButtonOnClickListener(this));
		ib_pause.setOnClickListener(new ButtonOnClickListener(this));
		ib_rec.setOnClickListener(new ButtonOnClickListener(this));
		
		/*----------------------------
		 * 3. SeekBar
			----------------------------*/
		tv_progress = (TextView) findViewById(R.id.main_tv_progress);
		
		sb = (SeekBar) findViewById(R.id.main_sb);
		
		
		
		sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO 自動生成されたメソッド・スタブ
				
				if (fromUser) {
					
					if (mp != null) {
						
						// Log
						Log.d("MainActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2]
										.getLineNumber() + "]", "mp != null");
						
						
						int dur = mp.getDuration();
						
						// Log
						Log.d("MainActivity.java"
								+ "["
								+ Thread.currentThread().getStackTrace()[2]
										.getLineNumber() + "]", "dur: " + dur + " / progress: " + progress);
						
						
//						counter = dur * (int) ((float) progress / 100);
						
						counter = (int) (dur * ((float) progress / 100) / 1000);
						
						mp.seekTo((int) (dur * (float) progress / 100));
						
						
						
//						// Log
//						Log.d("MainActivity.java"
//								+ "["
//								+ Thread.currentThread().getStackTrace()[2]
//										.getLineNumber() + "]", "counter: " + counter);
//						
//						Log.d("MainActivity.java"
//								+ "["
//								+ Thread.currentThread().getStackTrace()[2]
////										.getLineNumber() + "]", "dur * (int) ((float) progress / 100): " + dur * (int) ((float) progress / 100));
//										.getLineNumber() + "]", "(int) dur * ((float) progress / 100): " + (int) dur * ((float) progress / 100));
//
//						Log.d("MainActivity.java"
//								+ "["
//								+ Thread.currentThread().getStackTrace()[2]
////										.getLineNumber() + "]", "dur * (int) ((float) progress / 100): " + dur * (int) ((float) progress / 100));
//										.getLineNumber() + "]", "(int) (dur * ((float) progress / 100) / 1000): " + (int) (dur * ((float) progress / 100) / 1000));

					}//if (mp != null)
					
					
//					tv_progress.setText(String.valueOf(progress));
					
//					int value = (int) (float) progress / 100 * max;
//					
////					tv_progress.setText(String.valueOf(value));
//					tv_progress.setText(String.valueOf(value + 1));
					
				}//if (fromUser)
//				tv_progress.setText(String.valueOf(progress));
				
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO 自動生成されたメソッド・スタブ
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO 自動生成されたメソッド・スタブ
				
			}

				
		});//sb.setOnSeekBarChangeListener

		
	}//private void set_listeners()

	private void setup_buttons() {
		
		// Play
		ib_play.setEnabled(true);
		ib_play.setImageResource(R.drawable.cm_play_70x70_v3);

		// Pause
		ib_pause.setEnabled(false);
//		ib_pause.setImageResource(R.drawable.cm_simple_pause_70x70);
		ib_pause.setImageResource(R.drawable.cm_pause_dis_70x70);

		// Rec
		ib_rec.setEnabled(true);
		ib_rec.setImageResource(R.drawable.cm_simple_rec_70x70);

//		// Play
//		if (play_mode == Methods.ButtonModes.ON) {
//			
//			ib_play.setEnabled(true);
//			
//			ib_play.setImageResource(R.drawable.cm_play_70x70_v3);
//			
//		} else if (play_mode == Methods.ButtonModes.OFF) {//if (play_mode == true)
//			
//			ib_play.setEnabled(false);
//			ib_play.setImageResource(R.drawable.cm_play_dis_70x70);
//			
//		} else if (play_mode == Methods.ButtonModes.STOP) {//if (play_mode == true)
//			
//		}//if (play_mode == true)
//		
//		// Pause
//		if (pause_mode == Methods.ButtonModes.ON) {
//			
//			ib_pause.setEnabled(true);
//			
//			ib_pause.setImageResource(R.drawable.cm_pause_70x70);
//			
//		} else if (pause_mode == Methods.ButtonModes.OFF) {//if (pause_mode == true)
//			
//			ib_pause.setEnabled(false);
//			ib_pause.setImageResource(R.drawable.cm_pause_dis_70x70);
//			
//		}//if (pause_mode == true)
//		
//		// Rec
//		if (rec_mode == Methods.ButtonModes.ON) {
//			
//			ib_rec.setEnabled(true);
//			
//			ib_rec.setImageResource(R.drawable.cm_rec_70x70);
//			
//		} else if (rec_mode == Methods.ButtonModes.OFF) {//if (rec_mode == true)
//			
//			ib_rec.setEnabled(false);
//			ib_rec.setImageResource(R.drawable.cm_rec_dis_70x70);
//			
//		} else if (rec_mode == Methods.ButtonModes.STOP) {//if (rec_mode == true)
//			
//		}//if (rec_mode == true)
//
		
	}//private void setup_buttons()

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "onDestroy");
		
		
	}

	@Override
	protected void onListItemClick(ListView lv, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Play
		 * 2. Set modes
		 * 3. Set file path to view
		 * 4. Set file duration to progress view
		 * 		=> Methods.playFile()
		 * 9. super
			----------------------------*/
		
//		super.onListItemClick(lv, v, position, id);
		
		/*----------------------------
		 * 1. Play
		 * 		1. Recording?
		 * 		2. Play
			----------------------------*/
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", (String) lv.getItemAtPosition(position));
		
		/*----------------------------
		 * 1.1. Recording?
			----------------------------*/
//		play_file((String) lv.getItemAtPosition(position));
		
		if (rec_mode == Methods.ButtonModes.REC) {
			
			// debug
			Toast.makeText(MainActivity.this, 
					"録音中です。再生はしないように設定されてます", 2000).show();
			
			return;
			
		}//if (rec_mode == Methods.ButtonModes.REC)
		
		/*----------------------------
		 * 1.2. Play
			----------------------------*/
//		String currentFileName = (String) lv.getItemAtPosition(position);
		currentFileName = (String) lv.getItemAtPosition(position);
		
//		Methods.playFile(this, (String) lv.getItemAtPosition(position));
		Methods.playFile(this, currentFileName);
		
		/*----------------------------
		 * 2. Set modes
			----------------------------*/
		MainActivity.play_mode = Methods.ButtonModes.PLAY;
		MainActivity.pause_mode = Methods.ButtonModes.READY;
		MainActivity.rec_mode = Methods.ButtonModes.FREEZE;

		Methods.update_buttonImages(this);
		
		/*----------------------------
		 * 3. Set file path to view
			----------------------------*/
		TextView tv = (TextView) findViewById(R.id.main_tv);
		
		String fileFullPath = new File(rootDir, currentFileName).getAbsolutePath();
		
		String[] a_fileFullPath = fileFullPath.split(new File("aaa").separator);
		
		int index = Methods.findIndexFromArray(a_fileFullPath, targetFolder);
		
		String[] sub_a_fileFullPath = Arrays.copyOfRange(a_fileFullPath, index, a_fileFullPath.length);
		
		String pathForTextView = StringUtils.join(sub_a_fileFullPath, "/");
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "pathForTextView: " + pathForTextView);
		
		
//		tv.setText(new File(rootDir, currentFileName).getAbsolutePath());
		tv.setText(pathForTextView);
		
		/*----------------------------
		 * 4. Set file duration to progress view
			----------------------------*/
		
		
		
		/*----------------------------
		 * 9. super
			----------------------------*/
		super.onListItemClick(lv, v, position, id);
		
	}//protected void onListItemClick(ListView l, View v, int position, long id)

	class ProgressThread extends Thread {
		
		public ProgressThread(Activity actv) {
			
		}

		@Override
		public void run() {
			// TODO 自動生成されたメソッド・スタブ
			super.run();
			
			int max = 5;
			
			int counter = 0;
			
			while (counter < max) {
				
				MainActivity.setValue(counter);
				
				counter += 1;
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					
					// Log
					Log.d("MainActivity.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Exception: " + e.toString());
					
				}//try
				
			}//while (counter < max)
			
		}//public void run()

		@Override
		public synchronized void start() {
			// TODO 自動生成されたメソッド・スタブ
			super.start();
			
			
			
			
		}//public synchronized void start()
		
		
		
	}//class ProgressThread extends Thread
	
	
//	private void play_file(String fileName) {
//		/*----------------------------
//		 * Steps
//		 * 1.
//			----------------------------*/
//		String filePath = new File(rootDir, fileName).getAbsolutePath();
//		
//		try {
//			mp.setDataSource(filePath);
//			
//			mp.prepare();
//			
//			mp.start();
//			
//		} catch (IllegalArgumentException e) {
//			
//			// Log
//			Log.d("MainActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//			
//			// debug
//			Toast.makeText(MainActivity.this, "Exception", 2000).show();
//			
//		} catch (IllegalStateException e) {
//			
//			// Log
//			Log.d("MainActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//			// debug
//			Toast.makeText(MainActivity.this, "Exception", 2000).show();
//			
//		} catch (IOException e) {
//			// Log
//			Log.d("MainActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//			// debug
//			Toast.makeText(MainActivity.this, "Exception", 2000).show();
//			
//		}//try
//		
//	}//private void play_file(String fileName)

	public static void setValue(int value) {
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "setValue(int value)");
		
		if (tv_progress != null) {
			
			tv_progress.setText(String.valueOf(value));
			
		} else {//if (tv_progress != null)
			
//			tv_progress = (TextView) MainActivity.this.findViewById(R.id.main_tv_progress);
			
		}//if (tv_progress != null)
		
//		tv_progress.setText(String.valueOf(value));
		
	}
	
	public static void test1_setProgress2TextView() {
		/*----------------------------
		 * Steps
		 * 1. Get max
		 * 2. Instantiate => Timer
		 * 3. Instantiate => Handler
		 * 
		 * 3-2. Set zero to the progress text view
		 * 			=> Set the global field "counter" to zero
		 * 
		 * 4. Schedule
			----------------------------*/
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "test1_setProgress2TextView() => Starts");
		
		/*----------------------------
		 * 1. Get max
			----------------------------*/
		max = mp.getDuration() / 1000;
		
		/*----------------------------
		 * 2. Instantiate => Timer
		 * 3. Instantiate => Handler
			----------------------------*/
		timer = new Timer();
		
		final android.os.Handler handler = new android.os.Handler();
		
//		final int counter = 0;
//		final int max = 10;
		
		/*----------------------------
		 * 3-2. Set zero to the progress text view
			----------------------------*/
//		TextView tv_progress = (TextView) findViewById(R.id.main_tv_progress);
//		tv_progress.setText("0");
		
		counter = 0;
		
		
		/*----------------------------
		 * 4. Schedule
			----------------------------*/
		timer.schedule(
			new TimerTask(){

				@Override
				public void run() {
					handler.post(new Runnable(){

						@Override
						public void run() {
							
							if (counter < max) {
								
								
								MainActivity.setValue(counter);

								counter += 1;
								
							}//if (counter < max)
							
						}//public void run() // Runnable
						
					});
				
				}//public void run()  // 
				
			}, //new TimerTask()
			0, 
			1000
		);//timer.schedule(new TimerTask()
		
		
	}//public static void test1_setProgress2TextView()
}//public class MainActivity extends ListActivity

