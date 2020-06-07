package com.example.covid19india.ui.india;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19india.R;
import com.example.covid19india.ui.country.CountryFragment;
import com.example.covid19india.ui.country.ItemClickSupport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndiaFragment extends Fragment {

    RecyclerView rvCovidIndia;
    ProgressBar progressBar;
    CovidIndiaAdapter covidIndiaAdapter;
    private static  final String TAG= IndiaFragment.class.getSimpleName();
    List<CovidIndia> covidStates;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_india, container, false);

        setHasOptionsMenu(true);
        rvCovidIndia=root.findViewById(R.id.rvCovidIndia);
        progressBar=root.findViewById(R.id.progress_circular_india);
        rvCovidIndia.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(rvCovidIndia.getContext(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(),R.drawable.line_divider));
        rvCovidIndia.addItemDecoration(dividerItemDecoration);

        covidStates=new ArrayList<>();


        getDataFromServerSortTotalCase();

        return root;
    }

    private void showRecyclerView(){

        covidIndiaAdapter= new CovidIndiaAdapter(covidStates,getActivity());
        rvCovidIndia.setAdapter(covidIndiaAdapter);

        ItemClickSupports.addTo(rvCovidIndia).setOnItemClickListener(new ItemClickSupports.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCovidStates(covidStates.get(position));
            }
        });
    }

    private void showSelectedCovidStates(CovidIndia covidIndia){

            Intent covidCovidIndiaDetail= new Intent(getActivity(),CovidIndiaDetail.class);
            covidCovidIndiaDetail.putExtra("EXTRA_COVID_DATA",covidIndia);
            startActivity(covidCovidIndiaDetail);
    }

    private void getDataFromServerSortTotalCase() {

        String url="https://coronavirus-tracker-india-covid-19.p.rapidapi.com/api/getStatewiseSorted";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject data=array.getJSONObject(i);

                            covidStates.add(new CovidIndia(data.getString("name"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                            ));
                        }

                        getActivity().setTitle(array.length()-1+" Results");
                        showRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Log.e(TAG,"onResponse:"+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("x-rapidapi-key","5a764084a6mshff19204089a1a40p11cee3jsn11ccdda2e7de");
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void getDataFromServerSortAlphabet() {

        String url="https://coronavirus-tracker-india-covid-19.p.rapidapi.com/api/getStatewise";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject data=array.getJSONObject(i);

                            covidStates.add(new CovidIndia(data.getString("name"),
                                    data.getString("cases"),
                                    data.getString("deaths"),
                                    data.getString("recovered")
                            ));
                        }

                        getActivity().setTitle(array.length()-1+" Results");
                        showRecyclerView();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Log.e(TAG,"onResponse:"+error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=UTF-8");
                params.put("x-rapidapi-key","5a764084a6mshff19204089a1a40p11cee3jsn11ccdda2e7de");
                return params;
            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.india_menu,menu);
        MenuItem searchItem=menu.findItem(R.id.action_search_india);
        SearchView searchView =new SearchView(getActivity());
        searchView.setQueryHint("Search");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(covidIndiaAdapter!=null){
                    covidIndiaAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sort_alpha_india:
                Toast.makeText(getContext(),"Sort Alphabetically",Toast.LENGTH_SHORT).show();
                covidStates.clear();
                progressBar.setVisibility(View.VISIBLE);
                getDataFromServerSortAlphabet();
                return true;
            case R.id.action_sort_cases_india:
                Toast.makeText(getContext(),"Sort by Cases",Toast.LENGTH_SHORT).show();
                covidStates.clear();
                progressBar.setVisibility(View.VISIBLE);
                getDataFromServerSortTotalCase();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}