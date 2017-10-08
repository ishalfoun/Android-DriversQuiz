package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 *
 */

public class SearchActivity extends AppCompatActivity {

    public void search(View v){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "https://google.com");
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
