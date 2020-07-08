package com.ziko.isaac.recyclerviewanimation.AsyncTasks;

import android.content.Context;

import com.ziko.isaac.recyclerviewanimation.Database.DataBaseHelper;

public class AsyncTask extends android.os.AsyncTask<String, Void, Long> {

    //TODO: Weak reference for Context
    private Context context;
    private DataBaseHelper db;
    private long result;

    //c-tor
    public AsyncTask(Context ct) {
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
        super.onPostExecute(aLong);
    }
}
