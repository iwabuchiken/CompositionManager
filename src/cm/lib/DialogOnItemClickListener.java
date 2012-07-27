package cm.lib;

import cm.lib.Methods.DialogTags;
import cm.main.FileItem;
import cm.main.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DialogOnItemClickListener implements OnItemClickListener {

	//
	Activity actv;
	Dialog dlg;
	Dialog dlg2;
	FileItem fi;
	
	//
	Vibrator vib;
	
	//
	Methods.DialogTags dlgTag = null;

	public DialogOnItemClickListener(Activity actv, Dialog dlg) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, Dialog dlg, FileItem fi) {

		this.actv = actv;
		this.dlg = dlg;
		this.fi = fi;
		
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg, FileItem fi)

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		/*----------------------------
		 * Steps
		 * 1. Get tag
		 * 2. Vibrate
		 * 3. Switching
			----------------------------*/
		
		Methods.DialogOnItemClickTags tag = (Methods.DialogOnItemClickTags) parent.getTag();
		
		vib.vibrate(Methods.vibLength_click);
		
		/*----------------------------
		 * 3. Switching
			----------------------------*/
		switch (tag) {
		
		case dlg_item_menu://------------------------------------------------------
			/*----------------------------
			 * Steps
			 * 1. 
				----------------------------*/
			String item = (String) parent.getItemAtPosition(position);
			
			if (item.equals(actv.getString(R.string.dlg_item_menu_item_add_memo))) {
				
				dlg.dismiss();
				
				Methods.dlg_addMemo(actv, fi);
				
			} else if (item.equals(actv.getString(R.string.dlg_item_menu_item_move))) {
				
			} else if (item.equals(actv.getString(R.string.dlg_item_menu_item_delete))) {
				
			}//if (item.equals(actv.getString(R.string.)))
			
			
			break;// case dlg_item_menu
		
		case dlg_register_patterns_gv://-----------------------------------------------
			/*----------------------------
			 * Steps
			 * 1. Get item
			 * 2. EditText
				----------------------------*/
			
			item = (String) parent.getItemAtPosition(position);
			
//			// debug
//			Toast.makeText(actv, item, 2000)
//					.show();
			
			/*----------------------------
			 * 2. EditText
				----------------------------*/
			EditText et = (EditText) dlg.findViewById(R.id.dlg_add_memos_et_content);

			String text = et.getText().toString();
			
			text += " " + item + " ";
			
			et.setText(text);
			
			et.setSelection(et.getText().toString().length());

			
			
			break;// case dlg_register_patterns_gv
			
		}//switch (tag)
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)
}
