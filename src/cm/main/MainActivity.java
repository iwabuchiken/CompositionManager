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
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

	// Prefs name for highlighting the item
	public static final String PREFS_HIGHLIGHT = "PREFS_HIGHLIGHT";

	//
	static ArrayAdapter<String> adapter;
	static FileListAdapter flAdapter;
	
	static List<FileItem> fiList;

	
	
	//
	public static ListView lv_main;	// Used => Methods.playFile()

	//
	public static Vibrator vib;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        vib = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        
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
		
//		mp.setOnCompletionListener(new OnCompletionListener(){
//
//			@Override
//			public void onCompletion(MediaPlayer mp) {
//				
//				Methods.stopPlayer(MainActivity.this);
//				
//				// debug
//				Toast.makeText(MainActivity.this, "Complete", 2000).show();
//			}//public void onCompletion(MediaPlayer mp)
//			
//		});//public void onCompletion(MediaPlayer mp)
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "OnCompletionListener => Set");
		
		
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
		 * 		1. List<String>
		 * 		2. List<FileItem>
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
		 * 2.2. List<FileItem>
			----------------------------*/
//		flAdapter = new FileListAdapter<FileItem>();
		
		fiList = new ArrayList<FileItem>();
		
		for (File file : files) {
			
			FileItem fi = new FileItem(file.getName());
			
			fiList.add(fi);
			
		}//for (File file : files)
		
		
		
		/*----------------------------
		 * 3. Adapter
			----------------------------*/
//		adapter = new ArrayAdapter<String>(
//								this,
//								android.R.layout.simple_list_item_1,
//								file_names
//								);
		
		flAdapter = new FileListAdapter(
				this,
				R.layout.main,
				fiList
				);
		
		/*----------------------------
		 * 4. Set adapter
			----------------------------*/
//		lv.setAdapter(adapter);
//		setAdapter(adapter);
//		setListAdapter(adapter);
		
		setListAdapter(flAdapter);
		
		
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
		 * 0. Initialise ListView lv_main
		 * 1. Play
		 * 2. Set modes
		 * 3. Set file path to view
		 * 4. Set file duration to progress view
		 * 		=> Methods.playFile()
		 * 
		 * 5. Hightlight the item
		 * 9. super
			----------------------------*/
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "position: " + position + " / " + "id: " + id);
		
		
//		super.onListItemClick(lv, v, position, id);
		
		/*----------------------------
		 * 0. Initialise ListView lv_main
			----------------------------*/
		lv_main = lv;
		
		/*----------------------------
		 * 1. Play
		 * 		1. Recording?
		 * 		2. Play
			----------------------------*/
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
//		currentFileName = (String) lv.getItemAtPosition(position);
		
		currentFileName = ((FileItem) lv.getItemAtPosition(position)).getFile_name();
		
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
		 * 5. Hightlight the item
		 * 		1. Prefrences registered?
		 * 		2. If yes, change the backgrounds => Prev, current
		 * 		3. Set a new prefs value
		 * 
		 * 		4. Notify fiAdapter
			----------------------------*/
		/*----------------------------
		 * 5.1. Prefrences registered?
			----------------------------*/
		SharedPreferences prefs = 
					getSharedPreferences(PREFS_HIGHLIGHT, MODE_PRIVATE);

//		int prev_position = prefs.getInt(PREFS_HIGHLIGHT, -1);
//		
//		if (prev_position == -1) {
//			
//			v.setBackgroundColor(Color.BLUE);
//			
//		} else {//if (prev_position == -1)
//			
//			// Log
//			Log.d("MainActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "prev_position: " + prev_position);
//			
//			
//			View prev_view = lv.getChildAt(prev_position);
//			
//			// Log
//			Log.d("MainActivity.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "lv.getChildCount(): " + lv.getChildCount());
//			
//			
//			if (prev_view != null) {
//				
//				prev_view.setBackgroundColor(Color.BLACK);
//				
//			} else {//if (prev_view != null)
//				
//				// Log
//				Log.d("MainActivity.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "prev_view == null");
//				
//			}//if (prev_view != null)
//			
//			
//			v.setBackgroundColor(Color.BLUE);
//			
//		}//if (prev_position == -1)
//		
		//
//		adapter.notifyDataSetChanged();
//		flAdapter.notifyDataSetChanged();
		
//		// Log
//		Log.d("MainActivity.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "Adapter notified");
		
		
		/*----------------------------
		 * 5.3. Set a new prefs value
			----------------------------*/
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putInt(PREFS_HIGHLIGHT, position);
		
		editor.commit();
		
		/*----------------------------
		 * 5.4. Notify fiAdapter
			----------------------------*/
		flAdapter.notifyDataSetChanged();
		
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Adapter notified");
		
		//
//		v.setBackgroundColor(Color.BLUE);
		
//		currentFileName
		
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

	public boolean onCreateOptionsMenu(Menu menu) {
		// 
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.main_menu, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		
		case R.id.main_opt_menu_refresh_db://---------------------------------------

			vib.vibrate(Methods.vibLength_click);
			
			Methods.refreshMainDB(this);
			
			break;
			
		case R.id.main_opt_menu_db_activity://-----------------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			break;
			
			
		}//switch (item.getItemId())
		
		return super.onOptionsItemSelected(item);
		
	}//public boolean onOptionsItemSelected(MenuItem item)

}//public class MainActivity extends ListActivity

