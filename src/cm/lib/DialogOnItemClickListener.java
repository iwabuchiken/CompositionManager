package cm.lib;

import cm.lib.Methods.DialogTags;
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

	public DialogOnItemClickListener(Activity actv, Dialog dlg, Methods.DialogTags dlgTag) {
		// 
		this.actv = actv;
		this.dlg = dlg;
		this.dlgTag = dlgTag;
		
		//
		vib = (Vibrator) actv.getSystemService(Context.VIBRATOR_SERVICE);
		
	}//public DialogOnItemClickListener(Activity actv, Dialog dlg)

	public DialogOnItemClickListener(Activity actv, 
												Dialog dlg, Dialog dlg2,
												Methods.DialogTags dlgTag) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.actv = actv;
		this.dlg = dlg;
		this.dlgTag = dlgTag;
		this.dlg2 = dlg2;
		
		//
		vib = (Vibrator) actv.getSystemService(actv.VIBRATOR_SERVICE);

		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
	}//public void onItemClick(AdapterView<?> parent, View v, int position, long id)
}
