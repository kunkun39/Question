package com.changhong.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.changhong.R;
import com.changhong.assember.JsonAssember;
import com.changhong.domain.Examination;
import com.changhong.service.HttpClientService;

import java.util.List;

/**
 * Created by Jack Wang
 */
public class VoteRuleActivity extends Activity {

    /**
     * 这里得到试卷
     */
    private Examination examination;

    /**
     * 进去调查按钮
     */
    private Button ruleButton;

    /**
     * 规则介绍按钮
     */
    private TextView ruleText;

    /**
     * 主线程处�?
     */
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);

        initView();

        initData();

        initEvent();
    }

    private void initView() {
        setContentView(R.layout.activity_vote_rule);

        ruleButton = (Button) findViewById(R.id.examination_rule_button);
        ruleText = (TextView) findViewById(R.id.examination_rule_text);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 21) {
                    String description = (String) msg.obj;
                    if (description != null && !"".equals(description)) {
                        ruleText.setText(description);
                    } else {
                        ruleText.setText(R.string.rule_description);
                    }
                } else if(what == 22) {
                    String description = (String)msg.obj;
                    Dialog dialog = new AlertDialog.Builder(VoteRuleActivity.this)
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
                                    if(QuestionListActivity.sp!=null&&QuestionListActivity.music>0)
                                    {
                                        QuestionListActivity.sp.play(QuestionListActivity.music, 1, 1, 0, 0, 1);
                                    }
                                    SysApplication.getInstance().exit();


                                }
                            }).create();
                    dialog.show();

                }
            }
        };
    }

    private void initEvent() {
        ruleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ruleIntent = new Intent();
                ruleIntent.setClass(VoteRuleActivity.this, VoteSubmitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("examination", examination);
                ruleIntent.putExtras(bundle);
                if(QuestionListActivity.sp!=null&&QuestionListActivity.music>0)
                {
                    QuestionListActivity.sp.play(QuestionListActivity.music, 1, 1, 0, 0, 1);
                }
                startActivity(ruleIntent);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        final int examinationId = intent.getIntExtra("examinationId", 1);

        Thread thread = new Thread() {
            @Override
            public void run() {
                /**
                 * 初始化得到的EXAMINATION对象
                 */
                try {
                    String json = HttpClientService.getExaminationById(examinationId);
                    examination = JsonAssember.convertToExamination(json);
                    handler.sendMessage(handler.obtainMessage(21, examination.getDescription()));
                } catch (Exception e) {
                    handler.sendMessage(handler.obtainMessage(22, "网络设置有错，请重新设置网络"));
                }
            }
        };
        thread.start();
    }
}
