import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import Helper.DirectoryAndFileHelper;
import Helper.Filter;
import Helper.Record;
import Helper.Records;

public class CSV {
	public Records records;

	/**
	 * Original file
	 */
	private File file;
	

	/**
	 * 
	 * @param origionalFile The merged 1 big file
	 * @param records Records that get from CSV Factory
	 */
	public CSV(File origionalFile, Records records)  {
		this.file = origionalFile;
		this.records = records;
	}
	
//	public void filter(Record.Field condition) {
//		switch(condition) {
//		case FirstSeen: 
//			records.sort_ByFirstSeen();
//			break;
//		}
//	}

}
