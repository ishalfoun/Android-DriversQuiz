package cs.dawson.myapplication;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Isaak Shalfoun and Hannah Ly
 * @version 10-10-2017
 */

public class SearchActivity extends AppCompatActivity {

    public void search(View v){
        String q = "no stop sign";
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, q);
        if(intent.resolveActivity(getPackageManager()) != null){

        }
        startActivity(intent);
    }
}
