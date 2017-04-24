package com.example.nocturnal;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.nocturnal.swapingtab.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchCountry extends Fragment {

    private ListView searchListView;
    private ArrayAdapter<String>adapter;
    private ArrayList<String>arrayCountry;

    private MyItemListener myItemListener;


    public SearchCountry() {
        // Required empty public constructor
    }

    public interface MyItemListener{
        void getItem(String item);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myItemListener = (MyItemListener) context;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search_country, container, false);
        setHasOptionsMenu(true);
        searchListView = (ListView) v.findViewById(R.id.listViewCountry);

        SearchView searchView = (SearchView) v.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        arrayCountry = new ArrayList<>();
        arrayCountry.addAll(Arrays.asList(getResources().getStringArray(R.array.countries_array)));
        adapter = new ArrayAdapter<>(
                inflater.getContext(),
                android.R.layout.simple_list_item_1,
                arrayCountry
        );

        searchListView.setAdapter(adapter);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String newCountry = parent.getItemAtPosition(position).toString();
                myItemListener.getItem(newCountry);
            }
        });



        return v;
    }


}
