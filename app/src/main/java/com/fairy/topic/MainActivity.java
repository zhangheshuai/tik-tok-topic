package com.fairy.topic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private RecyclerView rvTopic;
    private TopicAdapter adapter;
    private int lenth = -1;
    private int selectIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.uploading_editText);
        rvTopic = findViewById(R.id.rv_Topic);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvTopic.setLayoutManager(linearLayoutManager);
        adapter = new TopicAdapter(R.layout.topic_item);
        rvTopic.setAdapter(adapter);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (editText.getText().toString().isEmpty()) {
                    rvTopic.setVisibility(View.GONE);
                    return;
                }
                if (editText.getText().toString().length() == lenth) {
                    return;
                }
                selectIndex = editText.getSelectionStart();
                lenth = editText.getText().toString().length();
                if (editText.getText().toString().contains("#")) {
                    SpannableString spannableString = getTextWithSpan("#", " ", editText.getText().toString());
                    editText.setText(spannableString);
                    editText.setSelection(selectIndex);
                }

            }
        });


        findViewById(R.id.uploading_topic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(String.format("%s #", editText.getText().toString()));
                editText.setSelection(editText.getText().toString().length());
                SoftKeyBoardListener.showInput(MainActivity.this, editText);

            }
        });


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                String content = editText.getText().toString();
                String s = content.substring(0, content.lastIndexOf("#"));
                content = s + "#" + adapter.getData().get(position) + " ";
                editText.setText(content);
                editText.setSelection(editText.getText().toString().length());
                rvTopic.setVisibility(View.GONE);
            }
        });


    }

    private SpannableString getTextWithSpan(String from, String to, String content) {
        String str[] = content.split("#");
        SpannableString ss = new SpannableString(content);
        for (int i = 1; i < str.length; i++) {
            String s = "#" + str[i];
            int spaceIndex = s.indexOf(" ");
            int charIndex = content.indexOf(s);
            if (spaceIndex == -1) {
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), content.lastIndexOf("#"), content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //searchPresenter.homeSearch(str[i]);
                sucess();
                rvTopic.setVisibility(View.VISIBLE);
            } else {
                rvTopic.setVisibility(View.GONE);
                ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), charIndex, charIndex + spaceIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        if (content.endsWith("#")) {
            //searchPresenter.homeSearch("");
            sucess();
            rvTopic.setVisibility(View.VISIBLE);
        }
        return ss;

    }


    public void sucess() {
        List<String> s = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            s.add(editText.getText().toString() + i);
        }
        String str = editText.getText().toString();
        s.add(0, str.substring(str.lastIndexOf("#")).replace("#", ""));
        adapter.setNewData(s);
    }

}