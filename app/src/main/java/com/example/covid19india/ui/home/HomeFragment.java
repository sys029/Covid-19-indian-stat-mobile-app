package com.example.covid19india.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19india.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {

    private TextView tvTotalConfirmed,tvTotalDeaths,tvTotalRecovered,tvLastUpdated;
    private ProgressBar progressBar;
    Dialog dialog;
    private LinearLayout layout,retry_layout;
    Button btn_retry,btnAbout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tvTotalConfirmed=root.findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths=root.findViewById(R.id.tvTotalDeath);
        tvTotalRecovered=root.findViewById(R.id.tvTotalRecovered);
        tvLastUpdated=root.findViewById(R.id.tvLastUpdated);
        progressBar=root.findViewById(R.id.progress_circular_home);
        btn_retry=root.findViewById(R.id.retry_btn);

        btnAbout=root.findViewById(R.id.aboutUs);
        dialog=new Dialog(getActivity());

        layout=root.findViewById(R.id.layout_h);
        retry_layout=root.findViewById(R.id.layout_retry);

        getActivity().setTitle("COVID-19");
        getData();

        if (ConnectionManager.checkConnection(getContext())){

            retry_layout.setVisibility(root.GONE);
            layout.setVisibility(root.VISIBLE);
        }else {
            retry_layout.setVisibility(root.VISIBLE);
            layout.setVisibility(root.INVISIBLE);
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction=getParentFragmentManager().beginTransaction();
                    transaction.detach(HomeFragment.this).attach(HomeFragment.this).commit();
                }
            });

        }

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return root;
    }

    private String getDate(long milliSecond){
        SimpleDateFormat formatter= new SimpleDateFormat("EEE,dd MMM yyyy hh:mm:ss aaa");
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());

    }

    private void getData() {

        RequestQueue queue= Volley.newRequestQueue(getActivity());

        String url="https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvTotalConfirmed.setText(jsonObject.getString("cases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTotalRecovered.setText(jsonObject.getString("recovered"));
                    tvLastUpdated.setText("Last Updated"+"\n"+getDate(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response",error.toString());
            }

        });

        queue.add(stringRequest);
    }

    public void showDialog() {
        Button btnClose;
        dialog.setCancelable(true);
        View view = getActivity().getLayoutInflater().inflate(R.layout.about_us_pop_up, null);
        dialog.setContentView(view);
        dialog.show();
        btnClose = dialog.findViewById(R.id.close);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}