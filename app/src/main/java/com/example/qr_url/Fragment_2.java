package com.example.qr_url;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_2 extends Fragment {
    Button getShorturlbtn;
    EditText txtv;
    TextView shorturl;
    ImageButton copybtn;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_2 newInstance(String param1, String param2) {
        Fragment_2 fragment = new Fragment_2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_2, container, false);
        getShorturlbtn = view.findViewById(R.id.button2);
        txtv = view.findViewById(R.id.edit_text_url1);
        shorturl = view.findViewById(R.id.outputview);
        copybtn = view.findViewById(R.id.copybtn);


        getShorturlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                requestQueue = Volley.newRequestQueue(getContext());
                String url = "";
                url = txtv.getText().toString();
                JSONObject jsonObject = new JSONObject();
                Toast.makeText(getContext(), "Short URL Generated", Toast.LENGTH_SHORT).show();

                try {
                    jsonObject.put("url", url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//        final String requestBody = jsonObject.toString();
//        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("url", "https:www.google.com");

                JsonObjectRequest jarr = new JsonObjectRequest(Request.Method.POST, "https://cleanuri.com/api/v1/shorten", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("My Volley App", "Response is successful" + response.getString("result_url"));
                            String surl = response.getString("result_url");
                            shorturl.setText(surl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // As of f605da3 the following should work
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                // Now you can use any deserializer to make sense of data
                                JSONObject obj = new JSONObject(res);
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                                Log.d("Volley string", "Volley error");
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                Log.d("Volley error", "Volley error");
                                e2.printStackTrace();
                            }
                        }
                    }
//            public void onErrorResponse(VolleyError error) {
//                Log.d("My Volley App", "Something went wrong");
//            }
                });
                requestQueue.add(jarr);
            }
        });

        copybtn.setOnClickListener(v -> {
            ClipData myClip;
            //code for copy and paste in clipboard
            String text = shorturl.getText().toString();

            myClip = ClipData.newPlainText("text", text);
            clipboard.setPrimaryClip(myClip);

            Toast.makeText(getContext(), "Text Copied",
                    Toast.LENGTH_SHORT).show();
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.share).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }
}


