package com.changhong.view;

import java.util.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.changhong.R;
import com.changhong.activity.QuestionListActivity;
import com.changhong.activity.VoteListActivity;
import com.changhong.activity.VoteSubmitActivity;
import com.changhong.domain.Examination;
import com.changhong.service.HttpClientService;
import com.changhong.service.PreferenceService;

public class VoteSubmitAdapter extends PagerAdapter {

    private VoteSubmitActivity mContext;

    /**
     * 保存设置的Service
     */
    private PreferenceService preferenceService;

    /**
     * 每一道题选择的答案
     */
    private Map<Integer, Set<String>> selected = new HashMap<Integer, Set<String>>();

    /**
     * 总共问题的数目
     */
    private int totalQuestions;

    /**
     * 现在正在进行的调查问卷
     */
    private Examination examination;

    /**
     * 传递过来的页面view的集合
     */
    private List<View> viewItems;

    /**
     * 每个item的页面view
     */
    private View convertView;

    /**
     * 传递过来的所有数据
     */
    private ArrayList<VoteSubmitItem> dataItems;

    /**
     * 题目选项的adapter
     */
    private VoteSubmitListAdapter listAdapter;

    /**
     * ViewHolder
     */
    private QuestionViewHolder holder = null;

    /**
     * 主线程处理
     */
    private Handler handler;

    public VoteSubmitAdapter(VoteSubmitActivity context, Examination examination, List<View> viewItems, ArrayList<VoteSubmitItem> dataItems) {
        /**
         * 初始化系统组件
         */
        mContext = context;
        this.preferenceService = new PreferenceService(context);
        this.totalQuestions = examination.getQuestions().size();
        this.examination = examination;
        this.viewItems = viewItems;
        this.dataItems = dataItems;

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
            }
        };
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewItems.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int questionIndex) {
        holder = new QuestionViewHolder();
        convertView = viewItems.get(questionIndex);
        holder.title = (TextView) convertView.findViewById(R.id.vote_submit_title);
        holder.question = (TextView) convertView.findViewById(R.id.vote_submit_question);
        holder.listView = (ListView) convertView.findViewById(R.id.vote_submit_listview);
        holder.previousBtn = (LinearLayout) convertView.findViewById(R.id.vote_submit_linear_previous);
        holder.nextBtn = (LinearLayout) convertView.findViewById(R.id.vote_submit_linear_next);
        holder.nextText = (TextView) convertView.findViewById(R.id.vote_submit_next_text);
        holder.previousText = (TextView) convertView.findViewById(R.id.vote_submit_previous_text);

        holder.nextImage = (ImageView) convertView.findViewById(R.id.vote_submit_next_image);

        holder.title.setText("总共" + totalQuestions + "题，当前在" + (questionIndex + 1) + "题");
        listAdapter = new VoteSubmitListAdapter(mContext, examination.getId(), questionIndex, dataItems.get(questionIndex).voteAnswers);
        holder.question.setText(dataItems.get(questionIndex).voteQuestion);
        holder.listView.setDividerHeight(0);
        holder.listView.setAdapter(listAdapter);
        holder.listView.setOnItemClickListener(new ListViewOnClickListener(listAdapter));

        // 第一页隐藏"上一步"按钮
        if (questionIndex == 0) {
            holder.previousText.setText("返回");
            holder.previousBtn.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, QuestionListActivity.class);
                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                    {
                      VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                    }
                    mContext.startActivity(intent);
                }

            });
        } else {
            holder.previousBtn.setVisibility(View.VISIBLE);
            holder.previousBtn.setOnClickListener(new LinearOnClickListener(questionIndex - 1));
        }

        // 最后一页修改"下一步"按钮文字
        if (questionIndex == viewItems.size() - 1) {
            holder.nextText.setText("提交");
            holder.nextImage.setImageResource(R.drawable.vote_submit_finish);
        }

        holder.nextBtn.setOnClickListener(new LinearOnClickListener(questionIndex + 1));
        container.addView(viewItems.get(questionIndex));
        return viewItems.get(questionIndex);
    }

    /**
     * 自定义listview的item点击事件
     */
    class ListViewOnClickListener implements OnItemClickListener {

        private VoteSubmitListAdapter mListAdapter;

        public ListViewOnClickListener(VoteSubmitListAdapter VoteSubmlistAdapteritListAdapter) {
            mListAdapter = listAdapter;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int answerIndex, long id) {
            VoteSubmitListAdapter.AnswerViewHolder answerItem = (VoteSubmitListAdapter.AnswerViewHolder) view.getTag();

            /**
             * 设置更新选中项图片和文本变化
             */
            int currentQuestionIndex = Integer.valueOf(String.valueOf(answerItem.question_index.getText()));
            boolean alreadySelected = false;
            Set<String> answerSelected = selected.get(currentQuestionIndex);
            if (answerSelected == null) {
                answerSelected = new HashSet<String>();
                selected.put(currentQuestionIndex, answerSelected);
            }
            String answerIndexStr = String.valueOf(answerIndex);
            if (!answerSelected.contains(answerIndexStr)) {
                answerSelected.add(answerIndexStr);
            } else {
                answerSelected.remove(answerIndexStr);
                alreadySelected = true;
            }

            /**
             * 缓存答案
             */
            preferenceService.saveAnswers(String.valueOf(examination.getId()), String.valueOf(currentQuestionIndex), answerSelected);

            /**
             * 更新选中的状态
             */
            if (!alreadySelected) {
                answerItem.select_image.setImageResource(R.drawable.vote_checkbox_select);
                answerItem.select_text.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
                answerItem.select_image.setImageResource(R.drawable.vote_checkbox_unselect);
                answerItem.select_text.setTextColor(mContext.getResources().getColor(R.color.black));
            }
        }
    }

    /**
     * @author wisdomhu 设置上一步和下一步按钮监听
     *
     */
    class LinearOnClickListener implements OnClickListener {

        private int mPosition;

        public LinearOnClickListener(int position) {
            mPosition = position;
        }
        @Override
        public void onClick(View v) {
            /**
             * 提交答案，清楚零时记录的答案
             */

            if (mPosition == viewItems.size()) {
                /**
                 * 判断问卷是否做完
                 */
                final String allAnswers = preferenceService.getAllAnswers(examination);
                if (allAnswers.equals("")) {
                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                    {
                        VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                    }
                    Toast.makeText(mContext, "请填写完所有的问题，谢谢!", Toast.LENGTH_SHORT).show();
                } else {
                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                    {
                        VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                    }
                    String result = preferenceService.getAllAnswersShow(examination);
                    Dialog dialog = new AlertDialog.Builder(mContext)
                            .setIcon(android.R.drawable.btn_plus)
                            .setTitle("您反馈信息如下")
                            .setMessage(result)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveButton("提交", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                                    {
                                        VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                                    }

                                    /**
                                     * 清楚这次选择的结果
                                     */
                                    preferenceService.cleanAllAnswers(examination);
                                    selected = new HashMap<Integer, Set<String>>();
                                    listAdapter.cleanAnswer();

                                    /**
                                     * 上传代码到服务器
                                     * TODO:没获得MAC地址
                                     */
                                    Thread thread = new Thread() {
                                        @Override
                                        public void run() {
                                            try {
                                                HttpClientService.sendExaminationResult(examination.getId(), allAnswers);
                                            } catch (Exception e) {
                                                handler.sendMessage(handler.obtainMessage(22, "网络设置有错，请重新设置网络"));
                                            }
                                        }
                                    };
                                    thread.start();

                                    /**
                                     * 返回主界面问卷列表
                                     */
                                    Intent intent = new Intent(mContext, QuestionListActivity.class);
                                    mContext.startActivity(intent);
                                }
                            }).create();
                    dialog.show();
                    WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
                    layoutParams.width = 600;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(layoutParams);
                }
            } else {
                if(VoteListActivity.sp!=null&&VoteListActivity.music>0)
                {
                    VoteListActivity.sp.play(VoteListActivity.music, 1, 1, 0, 0, 1);
                }
                mContext.setCurrentView(mPosition);
            }
        }

    }

    @Override
    public int getCount() {
        if (viewItems == null) {
            return 0;
        }
        return viewItems.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    /**
     * @author wisdomhu 自定义类
     */
    class QuestionViewHolder {

        ListView listView;

        TextView title;

        TextView question;

        TextView answer;

        LinearLayout previousBtn, nextBtn;

        TextView nextText;

        TextView previousText;

        ImageView nextImage;
    }

}
