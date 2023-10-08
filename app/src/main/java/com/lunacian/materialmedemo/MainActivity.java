package com.lunacian.materialmedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private String title;
    private String info;
    // Member variables.
    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;



    static int imageResource;

    public int getImageResource(){
        return imageResource;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mSportsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);
        helper.attachToRecyclerView(mRecyclerView);

        // Get the data.
        initializeData();
    }

    /**
     * Initialize the sports data from resources.
     */
        private void initializeData() {
            // Get the resources from the XML file.
            String[] sportsList = getResources()
                    .getStringArray(R.array.sports_titles);
            String[] sportsInfo = getResources()
                    .getStringArray(R.array.sports_info);
            TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);



            // Clear the existing data (to avoid duplication).
            mSportsData.clear();

            // Create the ArrayList of Sports objects with titles and
            // information about each sport.
            for (int  i=0; i<sportsList.length; i++){
                mSportsData.add(new Sport(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i,0)));
            }

            sportsImageResources.recycle();

            // Notify the adapter of the change.
            mAdapter.notifyDataSetChanged();
        }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            Collections.swap(mSportsData,from, to);
            mAdapter.notifyItemMoved(from, to);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mSportsData.remove(viewHolder.getAdapterPosition());
            mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }
    });


}