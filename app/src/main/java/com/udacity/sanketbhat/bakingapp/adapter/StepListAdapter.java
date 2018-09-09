package com.udacity.sanketbhat.bakingapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.model.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private StepClickListener mStepClickListener;
    private List<Step> stepList;

    public StepListAdapter(List<Step> stepList, StepClickListener listener) {
        this.stepList = stepList;
        mStepClickListener = listener;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.step_list_item, parent, false);
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.title.setText("Step " + stepList.get(position).getId());
        holder.subTitle.setText(stepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (stepList != null) return stepList.size();
        return 0;
    }

    public interface StepClickListener {
        void onStepClick(View v, int position);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, subTitle;

        public StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.stepTitle);
            subTitle = itemView.findViewById(R.id.stepSubtitle);
        }

        @Override
        public void onClick(View v) {
            if (mStepClickListener != null) mStepClickListener.onStepClick(v, getAdapterPosition());
        }
    }
}
