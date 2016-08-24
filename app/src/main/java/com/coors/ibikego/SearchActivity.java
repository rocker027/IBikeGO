package com.coors.ibikego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarDrawerToggle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.coors.ibikego.search.SearchAttractionsActivity;
import com.coors.ibikego.search.SearchBlogActivity;
import com.coors.ibikego.search.SearchBreakActivity;


public class SearchActivity extends AppCompatActivity {
    private Spinner spinner;
    private String[] list = {"請選擇","單車日誌", "景點", "休息站"};
    private EditText etkeyword;
    private ArrayAdapter<String> searchList;
//    private String keyword;
    private StringBuilder selectItem;


    //spinner
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etkeyword = (EditText) findViewById(R.id.etSearchKey);

        spinner = (Spinner)findViewById(R.id.spinner);
        searchList = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(searchList);
        spinner.setSelection(0, true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if("單車日誌".equals(list[position].toString()))
                {
//                    String key = "單車日誌";
                    selectItem = new StringBuilder(list[position].toString());
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();

                }

                if("景點".equals(list[position].toString()))
                {
                    selectItem = new StringBuilder(list[position].toString());
//                    selectItem = list[position].toString();
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();
                }

                if("休息站".equals(list[position].toString()))
                {
                    selectItem = new StringBuilder(list[position].toString());
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();
                }

                if("請選擇".equals(list[position].toString()))
                {
                    selectItem = new StringBuilder("noSelect");
//                    Toast.makeText(SearchActivity.this, "尚未選擇要搜尋的項目", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(SearchActivity.this, "請選擇想要查詢的項目",Toast.LENGTH_SHORT).show();
            }
        });

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this,MainActivity.class));
            }
        });
    }

    public void onClickSearch(View view) {
        if(etkeyword.getText().toString().trim().length() <= 0){
            etkeyword.setError("請輸入想查詢的關鍵字");
            return;
        }
        StringBuilder keyword = new StringBuilder("%"+etkeyword.getText().toString()+"%");

        if("單車日誌".equals(selectItem.toString())) {
            Intent intent = new Intent(SearchActivity.this, SearchBlogActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("keyword",keyword.toString());
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }

        if("景點".equals(selectItem.toString())) {
            Intent intent = new Intent(SearchActivity.this, SearchAttractionsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("keyword1",keyword.toString());
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }

        if("休息點".equals(selectItem.toString())) {
            Intent intent = new Intent(SearchActivity.this, SearchBreakActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("keyword",keyword);
            intent.putExtras(bundle);
            startActivity(intent);
            return;
        }

        if("noSelect".equals(selectItem.toString())) {
            Toast.makeText(SearchActivity.this, "請選擇想要查詢的項目",Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void onClickReset(View view) {
        etkeyword.setText("");
        selectItem = new StringBuilder("");
        spinner.setSelection(0, true);

    }


}
