package com.changhong.activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.content.Intent;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.SoundPool;
import com.changhong.R;
import com.changhong.assember.JsonAssember;
import com.changhong.domain.AppDescription;
import com.changhong.domain.Category;
import com.changhong.service.HttpClientService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class VoteListActivity extends Activity {

    /**
     * 进入问卷立列表按�?
     */
    private Button buttonNext;

    /**
     * 系统描述
     */
    private TextView AppDescriptionText;

    /**
     * 系统欢迎页面数据
     */
    private AppDescription appDescription;
    /**
     * 问题目录
     */
    private List<Category> categories;
    /**
     * 线程处理
     */
    private Handler handler;
/*
* 点击音效
 */
    static public SoundPool sp;//声明一个SoundPool
    static public int music;//定义一个整型用load（）；来设置suondID
    static public int error;
    /**
     *  整 个layout的隐藏和现实
     */
    private RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        initAudio();
        initialView();
        initialData();
        initialEvent();



    }
    private void initialData() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                /**
                 * 初始化得到的appDescription对象
                 */
                try {
                    String welcomeJson = HttpClientService.getWelcomePageResponse();
                    appDescription = JsonAssember.convertToAppDescription(welcomeJson);
                    categories = JsonAssember.convertToAppCategories(welcomeJson);
                    handler.sendMessage(handler.obtainMessage(21, appDescription.getDescription()));
                } catch (Exception e) {
                    handler.sendMessage(handler.obtainMessage(22, "网络设置有错，请重新设置网络"));
                }
            }
        };
        thread.start();
    }

    private void initialView() {

        setContentView(R.layout.activity_vote_list);
        buttonNext = (Button) findViewById(R.id.button1);
        AppDescriptionText = (TextView) findViewById(R.id.destextview);
        layout = (RelativeLayout)findViewById(R.id.vot_list_layout);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 21) {
                    String description = (String) msg.obj;
                    if (description == null || "".equals(description)) {
                        Intent intent = new Intent(VoteListActivity.this, QuestionListActivity.class);

                        startActivity(intent);
                    }
                    else
                    {
                        layout.setVisibility(View.VISIBLE);
                    }
                    AppDescriptionText.setText(description);
                } else if(what ==22)
                {
                    String description = (String)msg.obj;
                    Dialog dialog = new AlertDialog.Builder(VoteListActivity.this)
                            .setIcon(android.R.drawable.btn_plus)
                            .setTitle("提示消息")
                            .setMessage(description)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                                    {
                                        VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                                    }
                                    SysApplication.getInstance().exit();


                                }
                            }).create();
                    dialog.show();

                }
            }
        };

    }
    private void initAudio() {
        sp= new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);//第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
        music = sp.load(this, R.raw.move, 1);
        error = sp.load(this, R.raw.error, 1);
    }
    private void initialEvent() {
        buttonNext.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VoteListActivity.this, QuestionListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("categories", (Serializable)categories);
                intent.putExtras(bundle);
                if((sp!=null)&&(music>0))
                {
                    sp.play(music, 1, 1, 0, 0, 1);
                }
                startActivity(intent);
            }
        });
    }




}
