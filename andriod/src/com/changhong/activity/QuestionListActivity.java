package com.changhong.activity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.changhong.R;
import com.changhong.assember.JsonAssember;
import com.changhong.domain.Category;
import com.changhong.domain.Question;
import com.changhong.service.HttpClientService;


public class QuestionListActivity extends Activity {

    /**
     * 问题列表的VIEW
     */
    private ListView listView;

    /**
     * 问题列表的DATA
     */
    private List<Map<String, String>> mData;

    /**
     * 问题目录
     */
    private List<Category> categories;

    /**
     * 线程处理
     */
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);

        initView();

        categories = (List<Category>) getIntent().getSerializableExtra("categories");
        if (categories == null) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    /**
                     * 初始化得到的appDescription对象
                     */
                    try {
                        String welcomeJson = HttpClientService.getWelcomePageResponse();
                        categories = JsonAssember.convertToAppCategories(welcomeJson);
                        handler.sendEmptyMessage(21);
                    } catch (Exception e) {
                        handler.sendMessage(handler.obtainMessage(22, "网络设置有错，请重新设置网络"));
                    }
                }
            };
            thread.start();
        } else {
            initData();
        }
    }

    private void initView() {
        setContentView(R.layout.activity_question_list);

        listView = (ListView) findViewById(R.id.listView);
        listView.setDividerHeight(0);
        listView.setItemsCanFocus(true);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                int what = msg.what;
                if (what == 21) {
                    initData();
                }
                if(what ==22)
                {
                    String description = (String)msg.obj;
                    Dialog dialog = new AlertDialog.Builder(QuestionListActivity.this)
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
        private void initData() {
        mData = getData();

        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    //获取动态数组数�? 可以由其他地方传�?json�?
    private List<Map<String, String>> getData() {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (int i = 0; i < categories.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            Category category = categories.get(i);
            map.put("id", category.getId() + "");
            map.put("title", "问卷" + (i + 1) + ":" + category.getTitle());
            list.add(map);
        }
        return list;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public MyAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();

                //可以理解为从vlist获取view  之后把view返回给ListView
                convertView = mInflater.inflate(R.layout.item, null);
                holder.title = (TextView) convertView.findViewById(R.id.evaluatetitle);
                holder.info = (TextView) convertView.findViewById(R.id.evaluateinfo);
                holder.viewBtn = (Button) convertView.findViewById(R.id.button);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Map<String, String> map = mData.get(position);
            holder.info.setText(map.get("title"));
            holder.viewBtn.setTag(position);
            //给Button添加单击事件  添加Button之后ListView将失去焦�? 需要的直接把Button的焦点去�?
            holder.viewBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button button = (Button)v;
                    Intent ruleIntent = new Intent();
                    int examinationId = categories.get((Integer) button.getTag()).getId();
                    ruleIntent.setClass(QuestionListActivity.this, VoteRuleActivity.class);
                    ruleIntent.putExtra("examinationId", examinationId);
                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                    {
                        VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                    }
                    startActivity(ruleIntent);
                }
            });

            return convertView;
        }
    }

    //提取出来方便�?
    public final class ViewHolder {

        public TextView title;

        public TextView info;

        public Button viewBtn;
    }
}
