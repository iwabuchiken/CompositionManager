package cm.lib;
import java.io.File;

import cm.main.MainActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;

	//
	Vibrator vib;
	
	//
	int position;
	
	//
	ListView lv;
	
	public ButtonOnClickListener(Activity actv) {
		//
		this.actv = actv;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public ButtonOnClickListener(Activity actv, int position) {
		//
		this.actv = actv;
		this.position = position;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
		
		
	}//public ButtonOnClickListener(Activity actv, int position)

	public ButtonOnClickListener(Activity actv, ListView lv) {
		// 
		this.actv = actv;
		this.lv = lv;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	@Override
	public void onClick(View v) {
		//
		Methods.ButtonTags tag_name = (Methods.ButtonTags) v.getTag();

		//
		switch (tag_name) {
		case main_bt_play:
			
			vib.vibrate(Methods.vibLength_click);
			
			if (MainActivity.play_mode == Methods.ButtonModes.ON) {

				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "MainActivity.play_mode: " + MainActivity.play_mode.name());

//				MainActivity.play_mode = Methods.ButtonModes.OFF;
//				MainActivity.pause_mode = true;
//				MainActivity.rec_mode = false;
//				
//				Methods.update_buttonImages(actv);
				
			} else if (MainActivity.play_mode == Methods.ButtonModes.OFF) {//if (MainActivity.pla)

				// Log
				Log.d("ButtonOnClickListener.java"
						+ "["
						+ Thread.currentThread().getStackTrace()[2]
								.getLineNumber() + "]", "MainActivity.play_mode: " + MainActivity.play_mode.name());
				
				
				MainActivity.play_mode = Methods.ButtonModes.ON;
				MainActivity.pause_mode = Methods.ButtonModes.ON;
				MainActivity.rec_mode = Methods.ButtonModes.OFF;
				
				Methods.update_buttonImages(actv);
			}//if (MainActivity.pla)
			
			
			break;
			
		case main_bt_pause:
			
			vib.vibrate(Methods.vibLength_click);
			
			
			
			break;
			
		case main_bt_rec:
			
			vib.vibrate(Methods.vibLength_click);
			
			break;
			
		default:
			break;
		}//switch (tag_name)
	}

}
