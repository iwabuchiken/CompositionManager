package cm.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FileListAdapter extends ArrayAdapter<FileItem>{

	// Inflater
	LayoutInflater inflater;

	
	
	public FileListAdapter(Context con, int resourceId, List<FileItem> items) {
		super(con, resourceId, items);
		
		// Inflater
		inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		
	}//public FileListAdapter(Context con, int resourceId, List<FileItem> items)

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		/*----------------------------
		 * Steps
		 * 1. Get view
		 * 2. Set text => File name
			----------------------------*/
		/*----------------------------
		 * 1. Get view
			----------------------------*/
		View v;
		
    	if (convertView != null) {
			v = convertView;
		} else {//if (convertView != null)
//			v = inflater.inflate(R.layout.list_row, null);
			v = inflater.inflate(R.layout.list_row, null);
		}//if (convertView != null)

    	FileItem fi = getItem(position);
    	
    	/*----------------------------
		 * 2. Set text => File name
			----------------------------*/
		TextView tv = (TextView) v.findViewById(R.id.list_row_tv_file_name);
		
		tv.setText(fi.getFile_name());

		return v;
		
//		return super.getView(position, convertView, parent);
	}//public View getView(int position, View convertView, ViewGroup parent)

}//public class FileListAdapter<FileItem> extends ArrayAdapter<FileItem>
