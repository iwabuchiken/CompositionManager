package cm.main;

import cm.lib.ButtonOnClickListener;
import cm.lib.ButtonOnTouchListener;
import cm.lib.Methods;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	//
	public static Methods.ButtonModes play_mode = Methods.ButtonModes.OFF;
	public static Methods.ButtonModes pause_mode = Methods.ButtonModes.OFF;
	public static Methods.ButtonModes rec_mode = Methods.ButtonModes.OFF;

	//
	public static ImageButton ib_play;
	public static ImageButton ib_pause;
	public static ImageButton ib_rec;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        set_listeners();
        
    }//public void onCreate(Bundle savedInstanceState)

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
		ib_pause.setImageResource(R.drawable.cm_simple_pause_70x70);

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
}