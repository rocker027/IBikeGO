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

public class SearchActivity extends AppCompatActivity {
    private Spinner spinner;
    private String[] list = {"請選擇","單車日誌", "景點", "休息站"};
    private EditText etkeyword;
    private ArrayAdapter<String> searchList;
    private String keyword,selectItem;


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
                    selectItem = list[position].toString();
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();
                }

                if("景點".equals(list[position].toString()))
                {
                    selectItem = list[position].toString();
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();
                }

                if("休息站".equals(list[position].toString()))
                {
                    selectItem = list[position].toString();
                    Toast.makeText(SearchActivity.this, "你選的是"+list[position], Toast.LENGTH_SHORT).show();
                }

                if("請選擇".equals(list[position].toString()))
                {
                    selectItem ="";
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
    }

    public void onClickReset(View view) {
        etkeyword.setText("");
        keyword = "";
        selectItem = "";
        spinner.setSelection(0, true);

    }



}
