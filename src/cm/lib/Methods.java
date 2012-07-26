package cm.lib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang.StringUtils;

import cm.main.FileItem;
import cm.main.MainActivity;
import cm.main.R;

public class Methods {

	static int tempRecordNum = 20;
	
	public static enum DialogTags {
		// Generics
		dlg_generic_dismiss, dlg_generic_dismiss_second_dialog,
		
		// dlg_item_menu.xml
		dlg_item_menu,

		// dlg_add_memos.xml
		dlg_add_memos, dlg_add_memos_add,
		
	}//public static enum DialogTags
	
	public static enum ButtonTags {
		// main.xml
		main_bt_play, main_bt_pause, main_bt_rec,
		
		
	}//public static enum ButtonTags
	
	public static enum ItemTags {
		
		
	}//public static enum ItemTags

	public static enum MoveMode {
		// TIListAdapter.java
		ON, OFF,
		
	}//public static enum MoveMode

	public static enum ButtonModes {
		// Play
		READY, FREEZE, PLAY,
		// Rec
		REC,
	}

	public static enum LongClickTags {
		// MainActivity.java
		main_activity_list,
		
		
	}//public static enum LongClickTags

	public static enum DialogOnItemClickTags {
		
		// dlg_item_menu.xml
		dlg_item_menu,
		
	}//public static enum DialogOnItemClickListener
	
	//
	public static final int vibLength_click = 35;

	/*----------------------------
	 * makeComparator(Comparator comparator)
	 * 
	 * => Used 
	 * 
	 * REF=> C:\WORKS\WORKSPACES_ANDROID\Sample\09_Matsuoka\プロジェクト
	 * 					\Step10\10_LiveWallpaper\src\sample\step10\livewallpaper\FilePicker.java
		----------------------------*/
	public static void makeComparator(Comparator comparator){
		
		comparator=new Comparator<Object>(){
			
			public int compare(Object object1, Object object2) {
			
				int pad1=0;
				int pad2=0;
				
				File file1=(File)object1;
				File file2=(File)object2;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				return pad1-pad2+file1.getName().compareToIgnoreCase(file2.getName());
			}
		};
	}

	public static void sortFileList(File[] files) {
		// REF=> http://android-coding.blogspot.jp/2011/10/sort-file-list-in-order-by-implementing.html
		Comparator<? super File> filecomparator = new Comparator<File>(){
			
			public int compare(File file1, File file2) {
				int pad1=0;
				int pad2=0;
				
				if(file1.isDirectory())pad1=-65536;
				if(file2.isDirectory())pad2=-65536;
				
				// Order => folders, files
//				return pad1-pad2+file1.getName().compareToIgnoreCase(file2.getName());
				
				// Order => files, folders
				return pad2-pad1+file1.getName().compareToIgnoreCase(file2.getName());
				
//				return String.valueOf(file1.getName()).compareTo(file2.getName());
			} 
		 };//Comparator<? super File> filecomparator = new Comparator<File>()
		 
		 //
		Arrays.sort(files, filecomparator);

	}//public static void sortFileList(File[] files)

	/*----------------------------
	 * deleteDirectory(File target)()
	 * 
	 * 1. REF=> http://www.rgagnon.com/javadetails/java-0483.html
		----------------------------*/
	public static boolean deleteDirectory(File target) {
		
		if(target.exists()) {
			//
			File[] files = target.listFiles();
			
			//
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					
					deleteDirectory(files[i]);
					
				} else {//if (files[i].isDirectory())
					
					String path = files[i].getAbsolutePath();
					
					files[i].delete();
					
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "Removed => " + path);
					
					
				}//if (files[i].isDirectory())
				
			}//for (int i = 0; i < files.length; i++)
			
		}//if(target.exists())
		
		return (target.delete());
	}

	public static void toastAndLog(Activity actv, String message, int duration) {
		// 
		// debug
		Toast.makeText(actv, message, duration)
				.show();
		
		// Log
		Log.d("ImageFileManager8Activity.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String message)

	public static void toastAndLog(Activity actv, String fileName, String message, int duration) {
		// 
		// debug
		Toast.makeText(actv, message, duration)
				.show();
		
		// Log
		Log.d(fileName + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String fileName, String message, int duration)

	public static void recordLog(Activity actv, String fileName, String message) {
		// Log
		Log.d(fileName + 
				"[" + Thread.currentThread().getStackTrace()[2].getLineNumber() + "]", 
				message);
		
	}//public static void toastAndLog(Activity actv, String message)

	public static long getMillSeconds(int year, int month, int date) {
		// Calendar
		Calendar cal = Calendar.getInstance();
		
		// Set time
		cal.set(year, month, date);
		
		// Date
		Date d = cal.getTime();
		
		return d.getTime();
		
	}//private long getMillSeconds(int year, int month, int date)

	public static List<String> getTableList(SQLiteDatabase wdb) {
		//=> source: http://stackoverflow.com/questions/4681744/android-get-list-of-tables : "Just had to do the same. This seems to work:"
		String q = "SELECT name FROM " + "sqlite_master"+
						" WHERE type = 'table' ORDER BY name";
		
		Cursor c = null;
		try {
			c = wdb.rawQuery(q, null);
		} catch (Exception e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception => " + e.toString());
		}
		
		// Table names list
		List<String> tableList = new ArrayList<String>();
		
		// Log
		if (c != null) {
			c.moveToFirst();
			
			for (int i = 0; i < c.getCount(); i++) {
				// Log
				Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "c.getString(0) => " + c.getString(0));
				
				//
				tableList.add(c.getString(0));
				
				// Next
				c.moveToNext();
				
			}//for (int i = 0; i < c.getCount(); i++)

		} else {//if (c != null)
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c => null");
		}//if (c != null)
		
		return tableList;
	}//public static List<String> getTableList()

	public static Dialog dlg_template_okCancel(Activity actv, int layoutId, int titleStringId,
									int okButtonId, int cancelButtonId, DialogTags okTag, DialogTags cancelTag) {
		/*----------------------------
		 * Steps
		 * 1. Set up
		 * 2. Add listeners => OnTouch
		 * 3. Add listeners => OnClick
			----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		 * 2. Add listeners => OnTouch
			----------------------------*/
		//
		Button btn_ok = (Button) dlg.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		 * 3. Add listeners => OnClick
			----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		//
//		dlg.show();
		
		return dlg;
		
	}//public static Dialog dlg_template_okCancel()

	public static Dialog dlg_template_okCancel_2_dialogues(
											Activity actv, int layoutId, int titleStringId,
											int okButtonId, int cancelButtonId, 
											DialogTags okTag, DialogTags cancelTag,
											Dialog dlg1) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg2 = new Dialog(actv);
		
		//
		dlg2.setContentView(layoutId);
		
		// Title
		dlg2.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_ok = (Button) dlg2.findViewById(okButtonId);
		Button btn_cancel = (Button) dlg2.findViewById(cancelButtonId);
		
		//
		btn_ok.setTag(okTag);
		btn_cancel.setTag(cancelTag);
		
		//
		btn_ok.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg2));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_ok.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg1, dlg2));
		
		//
		//dlg.show();
		
		return dlg2;
		
	}//public static Dialog dlg_template_okCancel_2_dialogues()

	public static Dialog dlg_template_cancel(
										// Activity, layout
										Activity actv, int layoutId,
										// Title
										int titleStringId,
										// Cancel button, DialogTags => Cancel
										int cancelButtonId, DialogTags cancelTag) {
		/*----------------------------
		* Steps
		* 1. Set up
		* 2. Add listeners => OnTouch
		* 3. Add listeners => OnClick
		----------------------------*/
		
		// 
		Dialog dlg = new Dialog(actv);
		
		//
		dlg.setContentView(layoutId);
		
		// Title
		dlg.setTitle(titleStringId);
		
		/*----------------------------
		* 2. Add listeners => OnTouch
		----------------------------*/
		//
		Button btn_cancel = (Button) dlg.findViewById(cancelButtonId);
		
		//
		btn_cancel.setTag(cancelTag);
		
		//
		btn_cancel.setOnTouchListener(new DialogButtonOnTouchListener(actv, dlg));
		
		/*----------------------------
		* 3. Add listeners => OnClick
		----------------------------*/
		//
		btn_cancel.setOnClickListener(new DialogButtonOnClickListener(actv, dlg));
		
		//
		//dlg.show();
		
		return dlg;
		
	}//public static Dialog dlg_template_okCancel()

	/****************************************

	 * findIndexFromArray(String[] ary, String target)
	 * 
	 * <Caller> 
	 * 1. convertAbsolutePathIntoPath()
	 * 
	 * <Desc> 
	 * 1. 
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 
	 * 		-1		=> Couldn't find
	 * 		
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static int findIndexFromArray(String[] ary, String target) {
		
		int index = -1;
		
		for (int i = 0; i < ary.length; i++) {
			
			if (ary[i].equals(target)) {
				
				return i;
				
			}//if (ary[i].equals(tar))
			
		}//for (int i = 0; i < ary.length; i++)
		
		return index;
		
	}//public static int findIndexFromArray(String[] ary, String target)

	
	public static void update_buttonImages(Activity actv) {
		// Play
		if (MainActivity.play_mode == Methods.ButtonModes.READY) {
			
			MainActivity.ib_play.setEnabled(true);
			
			MainActivity.ib_play.setImageResource(R.drawable.cm_play_70x70_v3);
			
		} else if (MainActivity.play_mode == Methods.ButtonModes.FREEZE) {//if (play_mode == true)
			
			MainActivity.ib_play.setEnabled(false);
			MainActivity.ib_play.setImageResource(R.drawable.cm_play_dis_70x70);
			
		} else if (MainActivity.play_mode == Methods.ButtonModes.PLAY) {//if (play_mode == true)
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "MainActivity.play_mode == Methods.ButtonModes.PLAY");
			
			
//			MainActivity.ib_play.setEnabled(false);
			MainActivity.ib_play.setEnabled(true);
			
//			MainActivity.ib_pause.setImageResource(R.drawable.cm_stop_70x70);
			MainActivity.ib_play.setImageResource(R.drawable.cm_stop_70x70);
			
		}//if (play_mode == true)
		
		// Pause
		if (MainActivity.pause_mode == Methods.ButtonModes.READY) {
			
			MainActivity.ib_pause.setEnabled(true);
			
			MainActivity.ib_pause.setImageResource(R.drawable.cm_simple_pause_70x70);
			
		} else if (MainActivity.pause_mode == Methods.ButtonModes.FREEZE) {//if (pause_mode == true)
			
			MainActivity.ib_pause.setEnabled(false);
			MainActivity.ib_pause.setImageResource(R.drawable.cm_pause_dis_70x70);
			
		}//if (pause_mode == true)
		
		// Rec
		if (MainActivity.rec_mode == Methods.ButtonModes.READY) {
			
			MainActivity.ib_rec.setEnabled(true);
			
			MainActivity.ib_rec.setImageResource(R.drawable.cm_simple_rec_70x70);
			
		} else if (MainActivity.rec_mode == Methods.ButtonModes.FREEZE) {//if (MainActivity.rec_mode == true)
			
			MainActivity.ib_rec.setEnabled(false);
			MainActivity.ib_rec.setImageResource(R.drawable.cm_simple_rec_70x70_dis);
			
		} else if (MainActivity.rec_mode == Methods.ButtonModes.REC) {//if (MainActivity.rec_mode == true)
			
			MainActivity.ib_rec.setEnabled(true);
			
			MainActivity.ib_rec.setImageResource(R.drawable.cm_stop_70x70);
			
		}//if (MainActivity.rec_mode == true)

		
	}//public static void update_buttonImages(Activity actv)

	
	public static void playFile(Activity actv, String fileName) {
		/*----------------------------
		 * Steps
		 * 1. Get file path
		 * 2. If isPlaying => Stop
		 * 3. Play
		 * 
			----------------------------*/
		String filePath = new File(MainActivity.rootDir, fileName).getAbsolutePath();
		
		
		/*----------------------------
		 * 2. If isPlaying => Stop
			----------------------------*/
		if (MainActivity.mp != null && MainActivity.mp.isPlaying()) {

			stopPlayer(actv);
			
		}//if (mp.isPlaying())
		
		/*----------------------------
		 * 3. Play
		 * 		1. MediaPlayer
		 * 		2. Set data source
		 * 		3. Prepare mp
		 * 
		 * 		3-2. Set duration to the list item
		 * 
		 * 		4. Start
		 * 		5. Set duration to the view
			----------------------------*/
//		MainActivity.mp.release();
		
		/*----------------------------
		 * 3.1. MediaPlayer
			----------------------------*/
		MainActivity.mp = new MediaPlayer();
		
		MainActivity.mp.setOnCompletionListener(
							new Methods().new MPOnCompletionListener(actv));

		/*----------------------------
		 * 3.2. Set data source
			----------------------------*/
		try {

			MainActivity.mp.setDataSource(filePath);
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());
			
			// debug
			Toast.makeText(actv, "Exception", 2000).show();
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

			// debug
			Toast.makeText(actv, "Exception", 2000).show();
			
		} catch (IOException e) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

			// debug
			Toast.makeText(actv, "Exception", 2000).show();
			
		}//try
		
		/*----------------------------
		 * 3.3. Prepare mp
			----------------------------*/
		try {

			MainActivity.mp.prepare();
			
		} catch (IllegalStateException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

			// debug
			Toast.makeText(actv, "Exception", 2000).show();
			
		} catch (IOException e) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Exception: " + e.toString());

			// debug
			Toast.makeText(actv, "Exception", 2000).show();
			
		}//try
		
		/*----------------------------
		 * 3.3-2. Set duration to the list item
		 * 		1. Prepare digit label
		 * 		2.
			----------------------------*/
		int duration = MainActivity.mp.getDuration() / 1000;
		
		String s_duration = 
					Methods.convert_millSeconds2digitsLabel(MainActivity.mp.getDuration());
		
		SharedPreferences prefs = 
				actv.getSharedPreferences(MainActivity.PREFS_HIGHLIGHT, MainActivity.MODE_PRIVATE);
		
		int current_position = prefs.getInt(MainActivity.PREFS_HIGHLIGHT, -1);
		
		if (current_position != -1) {

			View v_list_row = (View) MainActivity.lv_main.getChildAt(current_position);
			
			if (v_list_row != null) {
				
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "View => Obtained");
				
				TextView tv_duration = (TextView) v_list_row.findViewById(R.id.list_row_tv_duration);
				tv_duration.setText(s_duration);
				
			} else {//if (v_list_row != null)
				
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "v_list_row == null");
				
			}//if (v_list_row != null)
			
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "View => Obtained");
//			
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "View => Obtained");
//			
//			TextView tv_duration = (TextView) v_list_row.findViewById(R.id.list_row_tv_duration);
//			tv_duration.setText(s_duration);
			
//			TextView tv_duration = (TextView) MainActivity.lv_main.getChildAt(current_position);
//			tv_duration.setText(s_duration);
			
		} else {//if (current_position != -1)

			View v_list_row = (View) MainActivity.lv_main.getChildAt(current_position);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "View => Obtained");
			
			TextView tv_duration = (TextView) v_list_row.findViewById(R.id.list_row_tv_duration);
			tv_duration.setText("No data");

//			TextView tv_duration = (TextView) MainActivity.lv_main.getChildAt(current_position);
//			tv_duration.setText("No data");
			
		}//if (current_position != -1)
		
//		MainActivity.lv_main.getItemAtPosition(position)
		
//		TextView tv_duration = 
//					(TextView) MainActivity.lv_main.findViewById(R.id.list_row_tv_duration);
//		
//		tv_duration.setText(s_duration);
		
		/*----------------------------
		 * 3.4. Start
			----------------------------*/
		MainActivity.mp.start();
				
		/*----------------------------
		 * 3.5. Set duration to the view
			----------------------------*/
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "Duration: " + String.valueOf(MainActivity.mp.getDuration()));
		
		setProgress2TextView();
		
	}//public static void playFile(String itemAtPosition)

	private static void setProgress2TextView() {
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
		
		
		/*----------------------------
		 * 1. Get max
			----------------------------*/
		MainActivity.max = MainActivity.mp.getDuration() / 1000;
		
		/*----------------------------
		 * 2. Instantiate => Timer
		 * 3. Instantiate => Handler
			----------------------------*/
		MainActivity.timer = new Timer();
		
		final android.os.Handler handler = new android.os.Handler();
		
//		final int counter = 0;
//		final int max = 10;
		
		/*----------------------------
		 * 3-2. Set zero to the progress text view
			----------------------------*/
//		TextView tv_progress = (TextView) findViewById(R.id.main_tv_progress);
//		tv_progress.setText("0");
		
		MainActivity.counter = 0;
		
		
		/*----------------------------
		 * 4. Schedule
			----------------------------*/
		MainActivity.timer.schedule(
			new TimerTask(){

				@Override
				public void run() {
					handler.post(new Runnable(){

						@Override
						public void run() {
							
							if (MainActivity.counter < MainActivity.max) {
								/*----------------------------
								 * Steps
								 * 1. Set progress to text view
								 * 1-2. Set progress => SeekBar
								 * 2. Count
									----------------------------*/
								
								
//								// Log
//								Log.d("Methods.java"
//										+ "["
//										+ Thread.currentThread()
//												.getStackTrace()[2]
//												.getLineNumber() + "]",
//										"Calling => setValue()");
								
								/*----------------------------
								 * 1. Set progress to text view
									----------------------------*/
//								MainActivity.setValue(MainActivity.counter);
								setValue(MainActivity.counter);

								/*----------------------------
								 * 1-2. Set progress => SeekBar
									----------------------------*/
//								MainActivity.sb.setProgress(MainActivity.counter);
								
								int sbValue = (int) (100 * (float) MainActivity.counter / MainActivity.max);
								
//								MainActivity.sb.setProgress(sbValue);
								MainActivity.sb.setProgress(sbValue + 1);
								
								/*----------------------------
								 * 2. Count
									----------------------------*/
								MainActivity.counter += 1;
								
							}//if (counter < max)
							
						}//public void run() // Runnable
						
					});
				
				}//public void run()  // 
				
			}, //new TimerTask()
			0, 
			1000
		);//timer.schedule(new TimerTask()
		
	}//private static void setProgress2TextView()

	public static void  stopPlayer(Activity actv) {
		/*----------------------------
		 * Steps
		 * 0. Update buttons
		 * 1. Stop and release (=> Stop only. No release)
		 * 1-2. Stop timer
		 * 2. Prepare
			----------------------------*/
		/*----------------------------
		 * 0. Update buttons
			----------------------------*/
		MainActivity.play_mode = Methods.ButtonModes.READY;
		MainActivity.pause_mode = Methods.ButtonModes.FREEZE;
		MainActivity.rec_mode = Methods.ButtonModes.READY;
		
		Methods.update_buttonImages(actv);
		
		/*----------------------------
		 * 1. Stop and release
			----------------------------*/
		if (MainActivity.mp != null && MainActivity.mp.isPlaying()) {
			MainActivity.mp.stop();
			
//			MainActivity.mp.release();

		}//if (MainActivity.mp != null && MainActivity.mp.isPlaying())
		
		/*----------------------------
		 * 1-2. Stop timer
			----------------------------*/
		MainActivity.timer.cancel();
		
		MainActivity.timer = null;
		
		/*----------------------------
		 * 2. Prepare
			----------------------------*/
//		try {
//
//			MainActivity.mp.prepare();
//			
//		} catch (IllegalStateException e) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//			// debug
//			Toast.makeText(actv, "Exception", 2000).show();
//			
//		} catch (IOException e) {
//			
//			// Log
//			Log.d("Methods.java" + "["
//					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//					+ "]", "Exception: " + e.toString());
//
//			// debug
//			Toast.makeText(actv, "Exception", 2000).show();
//			
//		}//try

	}//public static void  stopPlayer()

	public static String  convert_millSeconds2digitsLabel(long millSeconds) {
		/*----------------------------
		 * Steps
		 * 1. Prepare variables
		 * 2. Build a string
		 * 3. Return
			----------------------------*/
		/*----------------------------
		 * 1. Prepare variables
			----------------------------*/
		int seconds = (int)(millSeconds / 1000);
		
		int minutes = seconds / 60;
		
		int digit_seconds = seconds % 60;
		
		/*----------------------------
		 * 2. Build a string
			----------------------------*/
		StringBuilder sb = new StringBuilder();
		
		if (String.valueOf(minutes).length() < 2) {
			
			sb.append("0");
			
		}//if (String.valueOf(minutes).length() < 2)
		
		sb.append(String.valueOf(minutes));
		sb.append(":");

		if (String.valueOf(digit_seconds).length() < 2) {
			
			sb.append("0");
			
		}//if (String.valueOf(digit_seconds).length() < 2)

		sb.append(String.valueOf(digit_seconds));
		
		/*----------------------------
		 * 3. Return
			----------------------------*/
		return sb.toString();
		
	}//public static void  convert_millSeconds2digitsLabel()

	/****************************************
	 * setValue(int value)
	 * 
	 * <Caller> 
	 * 1. setProgress2TextView
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static void setValue(int value) {
		
		if (MainActivity.tv_progress != null) {
			
			MainActivity.tv_progress.setText(String.valueOf(value));
			
		} else {//if (tv_progress != null)
			
//			tv_progress = (TextView) MainActivity.this.findViewById(R.id.main_tv_progress);
			
		}//if (tv_progress != null)
		
//		tv_progress.setText(String.valueOf(value));
		
	}//public static void setValue(int value)

	class MPOnCompletionListener implements OnCompletionListener {

		//
		Activity actv;
		
		public MPOnCompletionListener(Activity actv) {
			
			this.actv = actv;
			
		}
		
		@Override
		public void onCompletion(MediaPlayer mp) {
			// TODO 自動生成されたメソッド・スタブ
			Methods.stopPlayer(actv);
			
			// debug
			Toast.makeText(actv, "Complete", 2000).show();
			
		}
		
	}//class MPOnCompletionListener implements OnCompletionListener

	public static boolean refreshMainDB(Activity actv) {
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "refreshMainDB => Started");
		
		
		/*----------------------------
		 * Steps
		 * 1. Set up DB(writable)
		 * 2. Table exists?
		 * 2-1. If no, then create one

		 * 3. Prepare data

		 * 4. Insert data into db
		 *
		 * 5. Update table "refresh_log"
		 * 
		 * 9. Close db
		 * 
		 * 10. Return
			----------------------------*/
		/*----------------------------
		 * 1. Set up DB(writable)
			----------------------------*/
		//
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
//		//debug
//		String sql = "SELECT * FROM " + DBUtils.mainTableName;
//		
//		Cursor c = wdb.rawQuery(sql, null);
//		
//		actv.startManagingCursor(c);
//		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "c.getCount() => " + c.getCount());
		
		
		/*----------------------------
		 * 2. Table exists?
		 * 2-1. If no, then create one
			----------------------------*/
		boolean res = dbu.tableExists(wdb, DBUtils.mainTableName);
		
		if (res == false) {
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "テーブルを作ります");
			
			res = dbu.createTable(
								wdb, 
								DBUtils.mainTableName, 
								DBUtils.cols_main_table, 
								DBUtils.types_main_table);
			
			if (res == false) {
				
//				// debug
//				Toast.makeText(actv, "テーブルを作れませんでした", 2000).show();
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "テーブルを作れませんでした");
				
				wdb.close();
				
				return false;
				
			} else {//if (res == false)
				
//				// debug
//				Toast.makeText(
//						actv, "テーブルをつくりました: " + DBUtils.mainTableName, 2000).show();
				// Log
				Log.d("Methods.java" + "["
						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
						+ "]", "テーブルを作りました");
				
			}//if (res == false)
			
		} else {//if (res == false)
			
//			// debug
//			Toast.makeText(
//					actv, "テーブルはすでにあります: " + DBUtils.mainTableName, 2000).show();
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "テーブルはすでにあります");
			
		}//if (res == false)
		
		/*----------------------------
		 * 3. Prepare data
			----------------------------*/
		List<FileItem> fileItems = prepare_FileItemList(wdb);

		if (fileItems == null) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "fileItems == null");
			
			return false;
			
		}//if (fileItems == null)
		
		/*----------------------------
		 * 4. Insert data into db
			----------------------------*/
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "fileItems.size(): " + fileItems.size());
		
		int counter = 0;
		
		for (FileItem fileItem : fileItems) {
			
			res = storeFileItem2DB(actv, wdb, dbu, DBUtils.mainTableName, fileItem);
		
			if (res == false) {
				
				counter += 1;
				
			}//if (res == false)
			
		}//for (FileItem fileItem : fileItems)
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "counter: " + counter + " / " + "File items: " + fileItems.size());
		
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		wdb.close();
		
		/*----------------------------
		 * 10. Return
			----------------------------*/
		if (counter > 0) {

			return false;
			
		} else {//if (counter != 0)
			
			return true;
			
		}//if (counter != 0)
		
//		return true;
		
	}//public static boolean refreshMainDB(Activity actv)

	
	/****************************************
	 * storeFileItem2DB()
	 * 
	 * <Caller> 
	 * 1.  refreshMainDB(Activity actv)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static boolean storeFileItem2DB(Activity actv, SQLiteDatabase wdb, 
										DBUtils dbu, String tableName, FileItem fileItem) {
		/*----------------------------
		 * Steps
		 * 1. Is the item already in the table?
		 * 2. If not, insert data
			----------------------------*/
		/*----------------------------
		 * 1. Is the item already in the table?
			----------------------------*/
		boolean res = DBUtils.isInTable(
							actv, wdb, tableName, DBUtils.cols_main_table[0], fileItem.getFile_name());
		
		// If the item is in the table, the return will be true, thus this method itself
		//	returns false.
		if (res == true) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "res == true: " + fileItem.getFile_name());
			
			return false;
			
		}//if (res == false)
		
		
		/*----------------------------
		 * 2. If not, insert data
			----------------------------*/
		Object[] values = {
				
				fileItem.getFile_name(),
				fileItem.getFile_path(),
				
				fileItem.getDuration(),
				
				fileItem.getDate_added(),
				fileItem.getDate_modified(),
				
				fileItem.getFile_info(),
				fileItem.getMemos(),
				
				fileItem.getLocated_at()
		};
		
		res = dbu.insertData(wdb, tableName, DBUtils.cols_main_table, values);
		
		if (res == false) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Insert into db failed: " + fileItem.getFile_name());
			
			return false;
			
		} else {//if (res == false)

			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "Data inserted: " + fileItem.getFile_name());
			
			return true;
			
		}//if (res == false)
		
		
	}//private static boolean storeFileItem2DB

	/****************************************
	 * prepare_FileItemList(SQLiteDatabase wdb)
	 * 
	 * <Caller> 
	 * 1. refreshMainDB(Activity actv)
	 * 
	 * <Desc> 
	 * 1. Convert file list into FileItem list
	 * 		=> The other method in Methods.java, "convert_DB2FileItemList"
	 * 				converts data in database into FileItem list
	 * 
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static List<FileItem> prepare_FileItemList(SQLiteDatabase wdb) {
		File[] files = new File(MainActivity.rootDir).listFiles();
		
		List<FileItem> fileItems = new ArrayList<FileItem>();
		
		
		for (File file : files) {
			/*----------------------------
			 * Steps
			 * 1. Each datum
			 * 2. Duration
			 * 3. Instatiate FileItem object
			 * 4. Add to list
			 * 5. Return
				----------------------------*/
			
			
//			file_names.add(file.getName());
			
			String file_name = file.getName();
			String file_path = file.getAbsolutePath();
			
			long duration = Methods.getDuration(file_path);
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "name: " + file_name + " / " + "duration: " + duration);
			
			
			long date_added = file.lastModified();
			long date_modified = file.lastModified();
			
			String file_info = "";
			String memos = "";
			
			String located_at = DBUtils.mainTableName;
			
			/*----------------------------
			 * 3. Instatiate FileItem object
			 * 4. Add to list
				----------------------------*/
			fileItems.add(new FileItem(
									file_name, file_path,
									duration,
									date_added, date_modified, 
									file_info, memos,
									located_at));
			
		}//for (File file : files)

		/*----------------------------
		 * 5. Return
			----------------------------*/
		return fileItems;
		
	}//private static List<FileItem> prepare_FileItemList(SQLiteDatabase wdb)
	
	private static long getDuration(String file_path) {
		/*----------------------------
		 * 2. Duration
		 * 		1. temp_mp
		 * 		2. Set source
		 * 		2-1. Prepare
		 * 		2-2. Get duration
		 * 		2-3. Release temp_mp
		 * 
		 * 		3. Prepare	=> NOP
		 * 		4. Start			=> NOP
			----------------------------*/
		MediaPlayer temp_mp = new MediaPlayer();
		
		try {
			
			temp_mp.setDataSource(file_path);
			
		} catch (IllegalArgumentException e) {
			
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IllegalStateException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		}//try
		
		/*----------------------------
		 * 2.2-1. Prepare
			----------------------------*/
		try {
			
			temp_mp.prepare();
			
		} catch (IllegalStateException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		} catch (IOException e) {
			// Log
			Log.d("Methods.java"
					+ "["
					+ Thread.currentThread().getStackTrace()[2]
							.getLineNumber() + "]", "Exception: " + e.toString());
			
			return -1;
			
		}//try

		/*----------------------------
		 * 2.2-2. Get duration
			----------------------------*/
		long duration = temp_mp.getDuration();
		
		/*----------------------------
		 * 2.2-3. Release temp_mp
			----------------------------*/
		temp_mp.reset();
		temp_mp.release();
		temp_mp = null;
		
		return duration;
		
	}//private static long getDuration(String file_path)

	public static boolean dropTable(Activity actv, String tableName) {
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase wdb = dbu.getWritableDatabase();
		
		boolean res = dbu.dropTable(actv, wdb, tableName);
		
		if (res == false) {

			Toast.makeText(actv, "Drop table => Failed", 2000)
			.show();
			
			wdb.close();
			
			return false;
			
		} else {//if (res == false)

			Toast.makeText(actv, "Table dropped: " + tableName, 2000)
			.show();
			
			wdb.close();
			
			return true;
			
		}//if (res == false)
		

	}//public static boolean dropTable(Activity actv, String tableName)

	
	/****************************************
	 * prepare_fiList(Activity actv, String tableName)
	 * 
	 * <Caller> 
	 * 1. MainActivity.set_listview()
	 * 
	 * <Desc> 
	 * 1. The above caller calls this method when setting up the list view.
	 * 
	 * <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	public static List<FileItem> prepare_fiList(Activity actv, String tableName) {
		/*----------------------------
		 * Steps
		 * 1. db
		 * 2. Cursor
		 * 3. List<>
		 * 
		 * 
		 * 9. Close db
		 * 10. Return
			----------------------------*/
		DBUtils dbu = new DBUtils(actv, DBUtils.dbName);
		
		//
		SQLiteDatabase rdb = dbu.getReadableDatabase();
		
		/*----------------------------
		 * 2. Cursor
			----------------------------*/
		String sql = "SELECT * FROM " + tableName;
		Cursor c = rdb.rawQuery(sql, null);
		
		if (c.getCount() < 1) {
			
			// Log
			Log.d("Methods.java" + "["
					+ Thread.currentThread().getStackTrace()[2].getLineNumber()
					+ "]", "c.getCount() < 1");
			
			return null;
			
		}//if (c.getCount() < 1)
		
		/*----------------------------
		 * 3. List<>
			----------------------------*/
		List<FileItem> fiList = new ArrayList<FileItem>();
		
		c.moveToFirst();
		
		for (int i = 0; i < c.getCount(); i++) {
			
			fiList.add(Methods.convert_cursor2FileItem(c));
			
			c.moveToNext();
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		/*----------------------------
		 * 9. Close db
			----------------------------*/
		rdb.close();
		
		/*----------------------------
		 * 10. Return
			----------------------------*/
		
		return fiList;
		
	}//public static List<FileItem> prepare_fiList(Activity actv)

	private static FileItem convert_cursor2FileItem(Cursor c) {
		/*----------------------------
		 * Steps
		 * 1.
			----------------------------*/
		return new FileItem(
				c.getString(1),		// file_name
				c.getString(2),		// file_path
				
				c.getLong(3),		// duration
				
				c.getLong(4),		// date_added
				c.getLong(5),		// date_modified
				
				c.getString(6),		// file_info
				c.getString(7),		// memos
				c.getString(8)		// located_at
				);
	}//private static FileItem convert_cursor2FileItem(Cursor c)

	public static void dlg_item_menu(Activity actv, FileItem fi) {
		/*----------------------------
		 * Steps
		 * 1. Get a generic dialog
		 * 2. Prepare data
		 * 3. Prepare adapter
		 * 
		 * 4. Set data to adapter
		 * 5. Set adapter to the list
		 * 5-1. Listener to the view
		 *
		 * 6. Show dialog
			----------------------------*/
		/*----------------------------
		 * 1. Get a generic dialog
			----------------------------*/
		Dialog dlg = dlg_template_cancel(
						// Activity actv, int layoutId,
						actv, R.layout.dlg_item_menu,
						// Title
						R.string.generic_tv_menu, 
						// Cancel button, DialogTags => Cancel
						R.id.dlg_item_menu_bt_cancel, DialogTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. Prepare data
			----------------------------*/
		List<String> itemList = new ArrayList<String>();
		
		itemList.add(actv.getString(R.string.dlg_item_menu_item_add_memo));
		itemList.add(actv.getString(R.string.dlg_item_menu_item_move));
		itemList.add(actv.getString(R.string.dlg_item_menu_item_delete));
		
		/*----------------------------
		 * 3. Prepare adapter
		 * 4. Set data to adapter
			----------------------------*/
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						actv,
						android.R.layout.simple_list_item_1, 
						itemList
				);

		/*----------------------------
		 * 5. Set adapter to the list
			----------------------------*/
		ListView lv = (ListView) dlg.findViewById(R.id.dlg_item_menu_lv);
		
		lv.setAdapter(adapter);
		
		
		/*----------------------------
		 * 5-1. Listener to the view
			----------------------------*/
		lv.setTag(Methods.DialogOnItemClickTags.dlg_item_menu);
		
		lv.setOnItemClickListener(
						new DialogOnItemClickListener(
								actv, 
								dlg, 
								fi));
		
		/*----------------------------
		 * 6. Show dialog
			----------------------------*/
		dlg.show();
		
		
	}//public static void dlg_thumb_actv_item_menu(Activity actv, ThumbnailItem ti)

	public static void dlg_addMemo(Activity actv, FileItem fi) {
		/*----------------------------
		 * Steps
		 * 1. Dialog
		 * 2. "Add" button
		 * 9. Show
			----------------------------*/
		Dialog dlg = dlg_template_okCancel(
								actv, R.layout.dlg_add_memos, 
								R.string.dlg_item_menu_item_title,
								R.id.dlg_add_memos_bt_add, R.id.dlg_add_memos_cancel, 
								DialogTags.dlg_add_memos_add, DialogTags.dlg_generic_dismiss);
		
		/*----------------------------
		 * 2. "Add" button
		 * 		1. Touch
		 * 		2. Click
			----------------------------*/
		Button bt_add = (Button) dlg.findViewById(R.id.dlg_add_memos_bt_add);
		
		bt_add.setTag(Methods.DialogTags.dlg_add_memos_add);
		
		/*----------------------------
		 * 2.1. Touch
			----------------------------*/
		bt_add.setOnTouchListener(new DialogButtonOnTouchListener(actv));
		
		/*----------------------------
		 * 2.2. Click
			----------------------------*/
		bt_add.setOnClickListener(new DialogButtonOnClickListener(actv, fi));
		
		/*----------------------------
		 * 9. Show
			----------------------------*/
		dlg.show();
		
		
	}//public static void dlg_addMemo(Activity actv, FileItem fi)

}//public class Methods
