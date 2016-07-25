package com.example.user.pokemon;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

//implements 使用 "類型"."想做的事" 會自行生成他所需要的格式在下方, 使用Override為開頭,
//並將我們要做的設定及判斷寫在對應的 { } 內.
public class MainActivity extends AppCompatActivity implements View.OnClickListener,RadioGroup.OnCheckedChangeListener,EditText.OnEditorActionListener,CheckBox.OnCheckedChangeListener
{
    //宣告變數 Android 元件 與其 在java的變數名稱
    TextView infoText;
    EditText name;
    RadioGroup radioGroup;
    Button button;
    CheckBox checkBox;
    boolean hide = true; //一開始預設訓練加名稱要 hide
    int selectOptionIndex = 0; //預設選第0個

    String[] pokemonNames = new String[]{
            "小火龍",
            "傑尼龜",
            "妙蛙種子"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //利用findViewByid 拉取Layout裡面的值
        //java變數名稱 = (Android 元件)findViewById(R.id.Layout_id)

        //因infoText無輸入部分,因此不需要設定Listener
        infoText = (TextView)findViewById(R.id.infoText);

        //EditText Setting
        //1.findViewById 外 2.設定listener: EditText 的動作是 Editor 3.當listen以後要執行"this"function, 在下面定義這些function
        name = (EditText)findViewById(R.id.name);
        name.setOnEditorActionListener(this);

        //Button Setting
        //1.先找到button 2.設定listener: button的動作是click
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);

        //RadioGroup Setting
        //1.找到radio group 2. 設定listener: radio group 的動作是 check,
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);

        //CheckBox Setting
        checkBox = (CheckBox)findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(this);
    }

    //設定當上述物件 listen 後的執行動作 (類似function的概念)
    //功能一:當按確認以後, 上方的infoText要顯示歡迎文字
    @Override
    public void onClick(View view) {

        int viewId = view.getId();
        if (viewId == R.id.button){
            if(hide) {
                infoText.setText(String.format("你好,訓練家 歡迎來到神奇寶貝的世界\n 你的第一隻夥伴是%s", pokemonNames[selectOptionIndex]));
            }else{
                infoText.setText(String.format("你好,訓練家%s 歡迎來到神奇寶貝的世界\n 你的第一隻夥伴是%s", name.getText(), pokemonNames[selectOptionIndex]));
            }
        }
    }


    //功能二:確認選的是哪一隻神奇寶貝
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
    //1.宣告變數, 抓到App中被選取的那個radio
    //2.利用判斷是給予被選取的radio對應的編號
        int radioGrpId = radioGroup.getId();
        if (radioGrpId == R.id.radioGroup){
            switch(checkId){
                case R.id.radioButton0:
                    selectOptionIndex = 0;
                    break;
                case R.id.radioButton1:
                    selectOptionIndex = 1;
                    break;
                case R.id.radioButton2:
                    selectOptionIndex = 2;
                    break;
            }
        }

    }

    //功能三:抓取鍵盤按鍵的event
    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE ){
            //dismiss virtual keyboard
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(textView.getWindowToken(),0);

            //simulate button clicked
            button.performClick();
            return true;
        }
        return false;
    }

    //功能四:抓取checkbox是否選取, 若選取則 hide==true; 若無則 hide==false
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int compoundButtonId=compoundButton.getId();
        if (compoundButtonId == R.id.checkBox){
            if(isChecked){
                hide = true;
            }else{
                hide = false;
            }
        }
    }
}
