package de.pixyel.dhbw.pixyel;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CardViewHolder> {
    private LinkedList<ImageCard> mDataset;
    private Activity mActivity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public TextView mLikes;
        public ImageButton mUp;
        public ImageButton mDown;
        public CardViewHolder(View v) {
            super(v);
            mImage = (ImageView) v.findViewById(R.id.CardImage);
            mLikes = (TextView) v.findViewById(R.id.CardLikes);
            mUp = (ImageButton) v.findViewById(R.id.CardUp);
            mDown = (ImageButton) v.findViewById(R.id.CardDown);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(LinkedList<ImageCard> myDataset, Activity myActivity) {
        mDataset = myDataset;
        mActivity = myActivity;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        CardViewHolder vh = new CardViewHolder(v);  //You need a cast here
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CardViewHolder holder, int index) {

        Glide.with(mActivity).load(mDataset.get(index).url).into(holder.mImage);
        holder.mLikes.setText("hallo");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}