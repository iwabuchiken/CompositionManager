package cm.dbadmin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class DBAdminActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		/*----------------------------
		 * Steps
		 * 1. Super
		 * 2. Set content
		 * 3. Listeners
		 * 		1. OnClick
		 * 		2. OnTouch
		 * 4. Disenable => "Create table"
		----------------------------*/
		super.onCreate(savedInstanceState);

		//
		//
		setListeners();
		
	}//public void onCreate(Bundle savedInstanceState)

	private void setListeners() {
    	/*----------------------------
		 * 1. Buttons
			----------------------------*/
    	
    	/*----------------------------
		 * 2. Tags
			----------------------------*/

		/*----------------------------
		 * 3. Listeners
		 * 		1. OnClick
		 * 		2. OnTouch
			----------------------------*/
    	/*----------------------------
		 * 3.1. OnClick
			----------------------------*/
    	
    	/*----------------------------
		 * 3.2. OnTouch
			----------------------------*/
    	
    	/*----------------------------
		 * 4. Disenable => "Create table"
			----------------------------*/
//    	bt_create.setEnabled(false);
    	
	}//private void setListeners()
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		super.onDestroy();
	}
	
}
