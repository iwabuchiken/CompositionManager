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

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
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

import cm.main.MainActivity;
import cm.main.R;

public class Methods {

	static int tempRecordNum = 20;
	
	public static enum DialogTags {
		
		
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
	
	/****************************************
	 *		insertDataIntoDB()
	 * 
	 * <Caller> 
	 * 1. public static boolean refreshMainDB(ListActivity actv)
	 * 
	 * <Desc> 1. <Params> 1.
	 * 
	 * <Return> 1.
	 * 
	 * <Steps> 1.
	 ****************************************/
	private static int insertDataIntoDB(SQLiteDatabase wdb, DBUtils dbu, Cursor c,
									String tableName, String backupTableName) {
		/*----------------------------
		 * Steps
		 * 1. Move to first
		 * 2. Set variables
		 * 3. Obtain data
		 * 4. Insert data
		 * 5. Return => counter
			----------------------------*/
		
		//
		c.moveToFirst();
		
		//
//		String[] columns = DBUtils.cols;
		
		/*----------------------------
		 * 2. Set variables
			----------------------------*/
		int counter = 0;
//		long threshHoldTime = getMillSeconds(2012, 7, 5);
		long threshHoldTime = getMillSeconds(2012, 6, 5);
		
//		// Log
//		Log.d("Methods.java" + "["
//				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//				+ "]", "threshHoldTime => " + threshHoldTime);
		
		/*----------------------------
		 * 3. Obtain data
			----------------------------*/
//		for (int i = c.getCount() - tempRecordNum; i < c.getCount(); i++) {
		for (int i = 0; i < c.getCount(); i++) {
			//
//			if(c.getLong(3) >= threshHoldTime) {
//			if(c.getLong(3) * 1000 >= threshHoldTime) {
//			if(i > c.getCount() - tempRecordNum) {
				//
				String[] values = {
						String.valueOf(c.getLong(0)),
						c.getString(1),
						c.getString(2),
						String.valueOf(c.getLong(3)),
						String.valueOf(c.getLong(4))
				};

				/*----------------------------
				 * 4. Insert data
				 * 		1. Insert data to tableName
				 * 		2. Record result
				 * 		3. Insert data to backupTableName
				 * 		4. Record result
					----------------------------*/
//				boolean blResult = true;

//				boolean blResult = dbu.insertData(wdb, tableName, columns, values);
				boolean blResult = 
							dbu.insertData(wdb, tableName, DBUtils.cols_for_insert_data, values);
				
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", 
//						StringUtils.join(values, "/"));
				
				
				
//				if (blResult == false) {
//					// Log
//					Log.d("Methods.java"
//							+ "["
//							+ Thread.currentThread().getStackTrace()[2]
//									.getLineNumber() + "]", "i => " + i + "/" + "c.getLong(0) => " + c.getLong(0));
//				} 	
				
				
				if (blResult == false) {
					// Log
					Log.d("Methods.java"
							+ "["
							+ Thread.currentThread().getStackTrace()[2]
									.getLineNumber() + "]", "i => " + i + "/" + "c.getLong(0) => " + c.getLong(0));
				} else {//if (blResult == false)
					counter += 1;
				}
//			}//if(c.getLong(3) >= threshHoldTime)
			
			//
			c.moveToNext();
			
			
		}//for (int i = 0; i < c.getCount(); i++)
		
		// Log
		Log.d("Methods.java" + "["
				+ Thread.currentThread().getStackTrace()[2].getLineNumber()
				+ "]", "counter => " + counter);
		
		/*----------------------------
		 * 5. Return => counter
			----------------------------*/
		return counter;
		
	}//private static int insertDataIntoDB(Cursor c)

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

	public static Dialog dlg_template_cancel(Activity actv, int layoutId, int titleStringId,
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

//			MainActivity.mp.
			
//			stopPlayer();
			stopPlayer(actv);
			
//			MainActivity.mp.stop();
//			
//			MainActivity.mp.release();
			
//			try {
//				MainActivity.mp.prepare();
//				
//				// Log
//				Log.d("Methods.java"
//						+ "["
//						+ Thread.currentThread().getStackTrace()[2]
//								.getLineNumber() + "]", "mp => Prepared");
//				
//				
//			} catch (IllegalStateException e) {
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Exception => " + e.toString());
//				
//				// debug
//				Toast.makeText(actv, "Exception => " + e.toString(), 2000).show();
//
//				return;
//				
//			} catch (IOException e) {
//				// Log
//				Log.d("Methods.java" + "["
//						+ Thread.currentThread().getStackTrace()[2].getLineNumber()
//						+ "]", "Exception => " + e.toString());
//				
//				// debug
//				Toast.makeText(actv, "Exception => " + e.toString(), 2000).show();
//				
//				return;
//				
//			}//try
		}//if (mp.isPlaying())
		
		/*----------------------------
		 * 3. Play
			----------------------------*/
//		MainActivity.mp.release();
		
		MainActivity.mp = new MediaPlayer();
		
		try {
			MainActivity.mp.setDataSource(filePath);
			
//			MainActivity.mp.prepare();
//			
//			MainActivity.mp.start();
//			
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
		
		MainActivity.mp.start();
				
	}//public static void playFile(String itemAtPosition)

	public static void  stopPlayer(Activity actv) {
		/*----------------------------
		 * Steps
		 * 1. Stop and release
		 * 2. Prepare
			----------------------------*/
		if (MainActivity.mp != null && MainActivity.mp.isPlaying()) {
			MainActivity.mp.stop();
			
//			MainActivity.mp.release();

		}//if (MainActivity.mp != null && MainActivity.mp.isPlaying())
		
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
	
}//public class Methods
