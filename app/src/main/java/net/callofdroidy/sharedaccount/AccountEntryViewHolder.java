package net.callofdroidy.sharedaccount;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by admin on 04/04/16.
 */
public class AccountEntryViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvDate;
    TextView tvValue;

    public AccountEntryViewHolder(View itemView){
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvDate = (TextView) itemView.findViewById(R.id.tv_date);
        tvValue = (TextView) itemView.findViewById(R.id.tv_value);
    }
}
