package ir.sass.sampleprojects.firebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper
{
    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String ID = "id";

    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_table";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_MESSAGE = "message";

    private static final String[] COLUMNS = {ID, TITLE, MESSAGE};

    public final String TAG = "ALISASS SQLiteDatabaseHandler";

    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATION_TABLE = "CREATE TABLE my_table ( "
                + "id INTEGER  PRIMARY KEY AUTOINCREMENT, " + "title TEXT, "
                + "message TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public boolean deleteOne(MessageFireBase message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean ret = db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(message.id) }) > 0;
        db.close();
        Log.i(TAG,"message for delete -> "+message.toString());
        return ret;
    }

    public void addNotif(MessageFireBase message)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, message.title);
        values.put(KEY_MESSAGE, message.message);
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public List<MessageFireBase> allMessage() {

        List<MessageFireBase> messages = new LinkedList<MessageFireBase>();
        String query = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MessageFireBase temp = null;

        if (cursor.moveToFirst()) {
            do {
                temp = new MessageFireBase(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2));
                messages.add(temp);
                Log.i(TAG,"all messages -> "+temp.toString());
            } while (cursor.moveToNext());
        }
        return messages;
    }
}
