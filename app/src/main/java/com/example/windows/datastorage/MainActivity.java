package com.example.windows.datastorage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private EditText et1,et2;
    private TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1= (EditText) findViewById(R.id.editText1);
        et2= (EditText) findViewById(R.id.editText2);
        tv1= (TextView) findViewById(R.id.textView);
        Button btn1= (Button) findViewById(R.id.button1);
        Button btn2= (Button) findViewById(R.id.button2);
        Button btn3= (Button) findViewById(R.id.button3);
        Button btn4= (Button) findViewById(R.id.button4);
        Button btn5= (Button) findViewById(R.id.button5);
        Button btn6= (Button) findViewById(R.id.button6);
        btn1.setOnClickListener(listener);
        btn2.setOnClickListener(listener);
        btn3.setOnClickListener(listener);
        btn4.setOnClickListener(listener);
        btn5.setOnClickListener(listener);
        btn6.setOnClickListener(listener);

    }

    View.OnClickListener listener= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button1:
                    spWrite();
                    break;
                case R.id.button2:
                    spRead();
                    break;
                case R.id.button3:
                   sdWriter();
                    break;
                case R.id.button4:
                    sdRead();
                    break;
                case R.id.button5:
                    RomWrite();
                    break;
                case R.id.button6:
                    RomRead();
                    break;
            }
        }
    };
    public void spWrite(){
        if(!isNull()) {
            SharedPreferences user = getSharedPreferences("user", MODE_APPEND);
            SharedPreferences.Editor editor = user.edit();
            editor.putString("accout", et1.getText().toString());
            editor.putString("pass", et2.getText().toString());
            editor.commit();
            Toast.makeText(this, "sp写入成功", Toast.LENGTH_LONG).show();
        }else{
            showMessage("账号或密码不能为空");
        }
    }
    public void spRead(){
        SharedPreferences user=getSharedPreferences("user",MODE_PRIVATE);
        String accout=user.getString("accout","no key");
        String password=user.getString("pass","no key");
        tv1.setText("账号:"+accout+"\n密码:"+password);
        Toast.makeText(this,"sp读取成功",Toast.LENGTH_LONG).show();
    }
    public void RomWrite(){
        if(!isNull()) {
            String accout = "账号:" + et1.getText().toString();
            String password = "\n密码:" + et2.getText().toString();
            String str = accout + password;
            try {
                FileOutputStream fos = openFileOutput("user.txt", MODE_PRIVATE);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(accout);
                bw.write(password);
                bw.flush();
                fos.close();
                Toast.makeText(this, "ROM写入成功", Toast.LENGTH_LONG).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            showMessage("账号或密码不能为空");
        }
    }
    public void RomRead(){
        try {
            FileInputStream fis=openFileInput("user.txt");
            InputStreamReader isr=new InputStreamReader(fis);
            BufferedReader br=new BufferedReader(isr);
            StringBuffer sb=new StringBuffer();
            String s ;
            while((s=br.readLine())!=null){
                sb.append(s+"\n");
            }
            fis.close();
            tv1.setText(sb);
            Toast.makeText(this,"ROM写入成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sdWriter(){
        if(!isNull()) {
            String accout = "账号:" + et1.getText().toString();
            String password = "\n密码:" + et2.getText().toString();
            String str = accout + password;
            //获取SD卡根目录的绝对路径；
            String sdCardRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
            String filename = sdCardRoot + "/test.txt";
            File file = new File(filename);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(str.getBytes());
                fos.flush();
                fos.close();
                Toast.makeText(this, "SD写入成功", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, "SD写入失败", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }else{
            showMessage("账号或密码不能为空");
        }
    }
    public void sdRead(){
        String sdCardRoot= Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename=sdCardRoot+"/test.txt";
        File file=new File(filename);
        int length=(int)file.length();
        byte[] b=new byte[length];
        try {
            FileInputStream fis=new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            tv1.setText(new String(b));
            Toast.makeText(this,"SD读取成功",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public boolean isNull(){
        if(!et1.getText().toString().trim().equals("")&&!et2.getText().toString().trim().equals("")){
            return false;
        }
        return  true;
    }
    public void showMessage(String Message){
        AlertDialog alert=new AlertDialog.Builder(this).create();
        alert.setTitle("温馨提示");
        alert.setMessage(Message);
        alert.setButton(DialogInterface.BUTTON_POSITIVE,"确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }
}
