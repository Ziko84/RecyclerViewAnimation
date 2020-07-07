package com.ziko.isaac.recyclerviewanimation.AsyncTasks;

import android.content.Context;
import android.widget.Toast;

import com.ziko.isaac.recyclerviewanimation.DataBaseHelper;

public class AsyncTask extends android.os.AsyncTask<String, Void, Long> {
    Context context;
    DataBaseHelper db;
    long result;

    public AsyncTask(Context ct){
        context = ct;
        db = new DataBaseHelper(ct);
    }

    @Override
    protected Long doInBackground(String... strings) {
        String creator = strings[0];
        String img_url = strings[1];
        int likes = Integer.parseInt(strings[2]);
        result = db.onInsert(creator, img_url, likes);
        return result;
    }

    @Override
    protected void onPostExecute(Long aLong) {
//        Toast.makeText(context, "Data Inserted To The SQLite Database" + aLong, Toast.LENGTH_LONG).show();
        super.onPostExecute(aLong);
    }
}
