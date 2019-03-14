package com.example.fedor.starbuzz;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor farietesCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavorietesListView();
    }
    private void setupOptionsListView(){
    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> listView,View itemView,int position, long id) {
            if (position==0){
                Intent intent= new Intent(TopLevelActivity.this,DrinkCategoryActivity.class);
                startActivity(intent);
            }
        }
    };
        ListView listView =(ListView)findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }
    private void setupFavorietesListView(){
        ListView listFavorites = (ListView)findViewById(R.id.list_favorietes);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db=starbuzzDatabaseHelper.getReadableDatabase();
            farietesCursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favorieteAdapter=
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            farietesCursor,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1},0 );
            listFavorites.setAdapter(favorieteAdapter);
        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,"no db",Toast.LENGTH_SHORT);
            toast.show();
        }
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onRestart (){
        super.onRestart();
        Cursor newCursor = db.query("DRINK",
                new String[]{"_id","NAME"},
                "FAVORITE = 1",
                null,null,null,null);
        ListView listFavorietes=(ListView)findViewById(R.id.list_favorietes);
        CursorAdapter adapter = (CursorAdapter) listFavorietes.getAdapter();
        adapter.changeCursor(newCursor);
        farietesCursor=newCursor;
    }
    @Override
    public  void onDestroy(){
        super.onDestroy();
        farietesCursor.close();
        db.close();
    }
}