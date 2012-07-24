package cm.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	// Buttons => Initial states
	public static Methods.ButtonModes play_mode = Methods.ButtonModes.READY;
	public static Methods.ButtonModes pause_mode = Methods.ButtonModes.FREEZE;
	public static Methods.ButtonModes rec_mode = Methods.ButtonModes.READY;

	//
	public static ImageButton ib_play;
	public static ImageButton ib_pause;
	public static ImageButton ib_rec;

	//
	public static String  rootDir;
	
	public static MediaPlayer mp = null;

	public static MediaRecorder mr = null;
	
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
		String targetFolder = "tapeatalk_records";
		
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
		 * 3. super
			----------------------------*/
		
//		super.onListItemClick(lv, v, position, id);
		
		/*----------------------------
		 * 1. Play
			----------------------------*/
		// Log
		Log.d("MainActivity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", (String) lv.getItemAtPosition(position));
		
//		play_file((String) lv.getItemAtPosition(position));
		
		if (rec_mode == Methods.ButtonModes.REC) {
			
			// debug
			Toast.makeText(MainActivity.this, 
					"録音中です。再生はしないように設定されてます", 2000).show();
			
			return;
			
		}//if (rec_mode == Methods.ButtonModes.REC)
		
		Methods.playFile(this, (String) lv.getItemAtPosition(position));
		
		/*----------------------------
		 * 2. Set modes
			----------------------------*/
		
		
		/*----------------------------
		 * 3. super
			----------------------------*/
		super.onListItemClick(lv, v, position, id);
		
	}//protected void onListItemClick(ListView l, View v, int position, long id)

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
	
}//public class MainActivity extends ListActivity
