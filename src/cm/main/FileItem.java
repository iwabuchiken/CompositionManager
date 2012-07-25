package cm.main;

public class FileItem {

//	int db_id;
	String file_name;
	String file_path;
	
	long date_added;
	long date_modified;
	
	String file_info;
	String memos;
	
	/****************************************
	 * Constructors
	 ****************************************/
	public FileItem(
						String file_name, String file_path, 
						long date_added, long date_modified, 
						String file_info, String memos) {
		
		this.file_name = file_name;
		this.file_path = file_path;
		
		this.date_added = date_added; 
		this.date_modified = date_modified;
		
		this.file_info = file_info; 
		this.memos = memos;
		
	}//public FileItem()

	public FileItem(String file_name) {
	
		this.file_name = file_name;
	
	}//public FileItem()

	/****************************************
	 * Methods
	 ****************************************/
//	public int getDb_id() {
//		return db_id;
//	}

	public String getFile_name() {
		return file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public long getDate_added() {
		return date_added;
	}

	public long getDate_modified() {
		return date_modified;
	}

	public String getFile_info() {
		return file_info;
	}

	public String getMemos() {
		return memos;
	}
	
}//public class FileItem