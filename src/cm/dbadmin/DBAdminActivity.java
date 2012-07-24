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
		// TODO 自動生成されたメソッド・スタブ
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO 自動生成されたメソッド・スタブ
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause() {
		// TODO 自動生成されたメソッド・スタブ
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDestroy();
	}
	
}
