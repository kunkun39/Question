package com.changhong.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.changhong.R;
import com.changhong.activity.VoteSubmitActivity;
import com.changhong.service.PreferenceService;

public class VoteSubmitListAdapter extends BaseAdapter {

    /**
     * 保存设置的Service
     */
    private PreferenceService preferenceService;

    private VoteSubmitActivity mContext;

    private int examinationId;

    private int questionIndex;

    private ArrayList<String> dataItems;

	public VoteSubmitListAdapter(VoteSubmitActivity context, int examinationId, int questionIndex, ArrayList<String> dataItems) {
		mContext = context;
        this.preferenceService = new PreferenceService(context);
        this.examinationId = examinationId;
        this.questionIndex = questionIndex;
        this.dataItems = dataItems;
    }

    public void cleanAnswer() {
        notifyDataSetChanged();
    }

	@Override
	public int getCount() {
		return dataItems.size();
	}

	@Override
	public Object getItem(int position) {
		return dataItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

    AnswerViewHolder holder = null;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			holder = new AnswerViewHolder();
			convertView = mContext.getLayoutInflater().inflate(R.layout.vote_submit_listview_item, null);
			holder.select_image = (ImageView) convertView.findViewById(R.id.vote_submit_select_image);
			holder.select_text = (TextView) convertView.findViewById(R.id.vote_submit_select_text);
			holder.question_index = (TextView) convertView.findViewById(R.id.vote_question_index);
			convertView.setTag(holder);
		} else {
			holder = (AnswerViewHolder) convertView.getTag();
		}

        Set<String> selected = preferenceService.getAnswers(String.valueOf(examinationId), String.valueOf(questionIndex));
        if (selected.contains(String.valueOf(position))) {
            holder.select_image.setImageResource(R.drawable.vote_checkbox_select);
            holder.select_text.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            holder.select_image.setImageResource(R.drawable.vote_checkbox_unselect);
            holder.select_text.setTextColor(mContext.getResources().getColor(R.color.white));
        }
            holder.select_text.setTextColor(mContext.getResources().getColor(R.color.black));
        holder.select_text.setText(dataItems.get(position));
        holder.question_index.setText(String.valueOf(questionIndex));
        return convertView;
	}

	/**
	 * @author wisdomhu 自定义类
	 */
	public class AnswerViewHolder {

		ImageView select_image;

		TextView select_text;

		TextView question_index;
    }
}
