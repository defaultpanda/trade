package com.nut.trade;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by inwso on 4/11/2559.
 */

public class ClientCursorAdapter extends ResourceCursorAdapter {

    public ClientCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.cusitemtitle);
        title.setText(cursor.getString(cursor.getColumnIndex("title")));


    }
}
