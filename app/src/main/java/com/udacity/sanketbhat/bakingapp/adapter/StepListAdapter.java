package com.udacity.sanketbhat.bakingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sanketbhat.bakingapp.R;
import com.udacity.sanketbhat.bakingapp.Utils;
import com.udacity.sanketbhat.bakingapp.model.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private static final int VIEW_TYPE_STEP = 0;
    private static final int VIEW_TYPE_INGREDIENTS = 1;

    private StepClickListener mStepClickListener;
    private List<Step> stepList;
    private Context mContext;

    public StepListAdapter(Context context, List<Step> stepList, StepClickListener listener) {
        this.stepList = stepList;
        mStepClickListener = listener;
        this.mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 ? VIEW_TYPE_INGREDIENTS : VIEW_TYPE_STEP);
    }

    public List<Step> getStepList() {
        return stepList;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == VIEW_TYPE_STEP) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.step_list_item_ingredients, parent, false);
        }
        return new StepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_STEP) {
            String title = "Step " + (stepList.get(position - 1).getId() + 1);
            holder.title.setText(title);
            holder.subTitle.setText(stepList.get(position - 1).getShortDescription());

            String imageUrl = stepList.get(position - 1).getThumbnailURL();
            if (imageUrl != null && !imageUrl.equals("")) {
                Picasso.with(mContext)
                        .load(imageUrl)
                        .into(holder.image);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (stepList != null) return stepList.size() + 1;
        //Ingredients item shown anyway
        return 1;
    }

    public interface StepClickListener {
        void onStepClick(View v, int position);
    }

    class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        TextView title, subTitle;
        ImageView image;
        View revealView;
        int x, y;

        StepViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.stepTitle);
            subTitle = itemView.findViewById(R.id.stepSubtitle);
            image = itemView.findViewById(R.id.stepImage);
            revealView = itemView.findViewById(R.id.revealEffectView);
            itemView.setOnTouchListener(this);
        }

        @Override
        public void onClick(View v) {
            Utils.animateReveal(revealView, x, y);
            if (mStepClickListener != null) mStepClickListener.onStepClick(v, getAdapterPosition());
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            x = (int) event.getX();
            y = (int) event.getY();
            return false;
        }
    }
}
