package cm.lib;

import cm.main.FileItem;
import cm.main.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogButtonOnClickListener implements OnClickListener {
	/*----------------------------
	 * Fields
		----------------------------*/
	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;		//=> Used in dlg_input_empty_btn_XXX
	FileItem fi;
	//
	Vibrator vib;
	
	// Used in => Methods.dlg_addMemo(Activity actv, long file_id, String tableName)
	long file_id;
	String tableName;
	
	public DialogButtonOnClickListener(Activity actv, Dialog dlg) {
		//
		this.actv = actv;
		this.dlg = dlg;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg1,
			Dialog dlg2) {
		//
		this.actv = actv;
		this.dlg = dlg1;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		this.tableName = tableName;
		
		this.file_id = file_id;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg, long file_id, String tableName)

	public DialogButtonOnClickListener(Activity actv, FileItem fi) {
		
	}

	public DialogButtonOnClickListener(Activity actv, Dialog dlg, FileItem fi) {
		
		this.actv = actv;
		this.dlg = dlg;
		this.fi = fi;
		
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);
		
	}//public DialogButtonOnClickListener(Activity actv, Dialog dlg, FileItem fi)

	@Override
	public void onClick(View v) {
		//
		Methods.DialogTags tag_name = (Methods.DialogTags) v.getTag();

		//
		switch (tag_name) {
		
		case dlg_generic_dismiss://------------------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			
			dlg.dismiss();
			
			break;
			
		case dlg_add_memos_add://------------------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			
//			EditText et = (EditText) dlg.findViewById(R.id.dlg_add_memos_et_content);
			
			Methods.addMemo(actv, dlg, fi);
			
//			// debug
//			Toast.makeText(actv, et.getText().toString(), 2000).show();
			
			break;

		case dlg_register_patterns_register://---------------------------------------
			
			vib.vibrate(Methods.vibLength_click);
			
			Methods.dlg_register_patterns_isInputEmpty(actv, dlg);
			
			break;
		default: // ----------------------------------------------------
			break;
		}//switch (tag_name)
	}

}
