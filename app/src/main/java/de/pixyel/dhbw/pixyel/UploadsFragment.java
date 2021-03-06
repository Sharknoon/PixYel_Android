package de.pixyel.dhbw.pixyel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

import de.pixyel.dhbw.pixyel.ConnectionManager.ConnectionListener;
import de.pixyel.dhbw.pixyel.ConnectionManager.ConnectionManager;
import de.pixyel.dhbw.pixyel.ConnectionManager.XML;

import static android.support.v7.recyclerview.R.styleable.RecyclerView;

public class UploadsFragment extends Fragment {
    private static RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;
    private static SwipeRefreshLayout mSwipeRefreshLayout;

    private static ByteArrayOutputStream stream;
    private static byte[] imgByte;

    public static LinkedList<ImageCard> imageList = new LinkedList<>();
    public static LinkedList<Picture> pictureList = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.primary_layout,null);
        imageList = new LinkedList<ImageCard>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        BitmapFactory.Options options = new BitmapFactory.Options();// Create object of bitmapfactory's option method for further option use
        options.inJustDecodeBounds = true;

        //Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.jet);
//        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //System.out.println("Bytes:" + image.getByteCount());
        //imgByte = stream.toByteArray();




        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(imageList, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                System.out.println("Refresh");
                refreshItems();
            }
        });

        return rootView;
    }

    public static void refreshItems(){
        imageList.clear();
        mAdapter.notifyDataSetChanged();
        XML xml = XML.createNewXML("getItemListUploadedByMe");
        MainActivity.requestFlag = "Upload";
        ConnectionManager.sendToServer(xml);
    }

    public static void onItemsLoadComplete(){
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static void refreshItem(int position){
        mAdapter.notifyItemChanged(position);
    }

    public static void addPhoto(String id, String date, String upvotes, String downvotes, String votedByUser, String rank){
        imageList.add(new ImageCard(id, date, upvotes, downvotes, votedByUser, rank));
        onItemsLoadComplete();
    }


}