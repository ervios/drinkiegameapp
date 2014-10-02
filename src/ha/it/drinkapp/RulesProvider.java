package ha.it.drinkapp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class RulesProvider extends ContentProvider {
	
	public static final String MIME_DIR_PREFIX 	= "vnd.android.cursor.dir";
	public static final String MIME_TYPE_PREFIX = "vnd.android.cursor.item";
	public static final String MIME_ITEM 		= "vnd.ha.it.rule";
	
	public static final String MIME_TYPE_SINGLE 	= MIME_TYPE_PREFIX + "/" + MIME_ITEM;
	public static final String MIME_TYPE_MULTIPLE 	= MIME_DIR_PREFIX + "/" + MIME_ITEM;
	
	public static final String AUTHORITY 		= "ha.it.drinkapp";
	public static final String PATH_SINGLE 		= "rules/#";
	public static final String PATH_MULTIPLE 	= "rules";
	
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH_MULTIPLE);
	
	private static final int ALL_RULES 		= 1;
	private static final int SINGLE_RULE 	= 2;
	
	public static final String KEY_ID = "_id";
	public static final String KEY_CARD_VALUE = "cardValue";
	public static final String KEY_CARD_RULE = "cardRule";
	private MySQLiteOpenHelper myOpenHelper;
	
	
	private static final UriMatcher uriMatcher;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, PATH_MULTIPLE, ALL_RULES);
		uriMatcher.addURI(AUTHORITY, PATH_SINGLE, SINGLE_RULE);
	}
	
	
	@Override
	public boolean onCreate() {
		
		myOpenHelper = new MySQLiteOpenHelper(getContext(),
												MySQLiteOpenHelper.DATABASE_NAME,
												null,
												MySQLiteOpenHelper.DATABASE_VERSION);
		
		return true;
	}
	
	


	@Override
	public String getType(Uri uri) {
	
		switch (uriMatcher.match(uri)) {
		case ALL_RULES:
			return MIME_TYPE_MULTIPLE;
		case SINGLE_RULE:
			return MIME_TYPE_SINGLE;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		
		//Check for dublicates
		String rules = values.getAsString(RulesProvider.KEY_CARD_RULE);
		
		String[] projection = null;
		String groupBy = null;
		String having = null;
		String sortOrder = null;
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
		String selection = RulesProvider.KEY_CARD_RULE + " = '" + rules + "'";
		String[] selectionArgs = null;
		
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, groupBy, having, sortOrder);
		
		if (cursor.moveToFirst()) {
			Log.d("PEEKACARD", "Duplicate rule");
			int pkIndex = cursor.getColumnIndex(KEY_ID);
			long pk = cursor.getLong(pkIndex);
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, pk);
			return insertedId;
		}
		
		String nullColumnHack = null;
		long id = db.insert(MySQLiteOpenHelper.DATABASE_TABLE, nullColumnHack, values);
		
		if (id > -1) {
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		}
		else {
			return null;
		}
		
	}

	


	@Override
	public Cursor query(Uri uri, String[] projection, String selection, 
						String[] selectionArgs, String sortOrder) {
		
		// Open the database
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		
		String groupBy = null;
		String having = null;
		
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(MySQLiteOpenHelper.DATABASE_TABLE);
		
		switch (uriMatcher.match(uri)) {
			case SINGLE_RULE:
				String rowID = uri.getPathSegments().get(1);
				queryBuilder.appendWhere(KEY_ID + "=" + rowID);
			default:
					break;
		}
		
		Cursor cursor = queryBuilder.query(db, projection, selection, 
									selectionArgs, groupBy, having, sortOrder);
		
	    // Register the contexts ContentResolver to be notified if
	    // the cursor result set changes.
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, 
					  String selection, String[] selectionArgs) {	
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
						int row = 0;
						switch(uriMatcher.match(uri)){
						case ALL_RULES:
							row = db.update(MySQLiteOpenHelper.DATABASE_TABLE, values, selection, selectionArgs);
							getContext().getContentResolver().notifyChange(uri, null);
							break;
						default:
							break;
						}
						return row;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();
		int count = db.delete(MySQLiteOpenHelper.DATABASE_TABLE, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	public void getRule() {
		//SQLiteDatabase db = myOpenHelper.getReadableDatabase();
		
		//String test = "SELECT " + RulesProvider.KEY_CARD_RULE + "FROM " + db;
	}
	
	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "cardrules.db";
		private static final String DATABASE_TABLE = "Rules";
		private static final int DATABASE_VERSION = 10;
		
		private static final String DATABASE_CREATE = "CREATE TABLE " +
				DATABASE_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
				KEY_CARD_VALUE + " TEXT NOT NULL, "+
				KEY_CARD_RULE + " TEXT NOT NULL);";
		
		public MySQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
				super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
		
	}
	
	
	
}
