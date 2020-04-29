package com.arl.importdutycalc;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arl.importdutycalc.aa.fill;
import com.arl.importdutycalc.aa.fillspinneradpter;
import com.arl.importdutycalc.bb.PreviewAdapter;
import com.arl.importdutycalc.bb.PreviewGetter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class InswActivity extends AppCompatActivity {
    fillspinneradpter adapterx;
    Spinner filterspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insw);
        handshakeserver();
        fill[] fills = new fill[2];
        fills[0] = new fill();
        fills[0].set_id(1);
        fills[0].set_uraian("Uraian");

        fills[1] = new fill();
        fills[1].set_id(2);
        fills[1].set_uraian("Kode HS");

        adapterx = new fillspinneradpter(InswActivity.this,
                android.R.layout.simple_spinner_item,
                fills);
        filterspinner = findViewById(R.id.spinnerfilter);
        filterspinner.setAdapter(adapterx);


        filterspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                fill fillt = adapterx.getItem(position);
                // Here you can do the action you want to...
//                Toast.makeText(MainActivity.this, "ID: " + fillt.get_id() + "\nName: " + fillt.get_uraian(),
//                        Toast.LENGTH_SHORT).show();
                idne = fillt.get_id();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {  }
        });

        mAdView             = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("8E3698F6055A47A2E672FB6CC49DD1FF").build();
        mAdView.loadAd(adRequest);

        filtertext      = findViewById(R.id.editfilter);
        cari            = findViewById(R.id.caribtn);
        filterlayout    = findViewById(R.id.layoutfilter);

//        filtertext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                // TODO Auto-generated method stub
//
//                if (hasFocus) {
//                    if (filtertext.getText().toString().trim()     //try using value.getText().length()<3**
//                        .length() < 2) {             //instead of the value.getText().trim().length()**
//                        filterlayout.setError("Minimal Pencarian 2 Digit");
//                    } else {
//                        filterlayout.setError(null);
//                    }}}});

        filtertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterlayout.setError(null);
            }
        });

        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filtertext.getText().toString().isEmpty())
                    filterlayout.setError("Kolom ga boleh Kosong !");
                else{
                    filterlayout.setError(null);
                    caribro();
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                }

            }
        });

    }


    String judul,msg_serv;
    private void handshakeserver() {
        String xyz = "appversion";
        AsyncHttpClient AHC = new AsyncHttpClient();
        String urlDownload = "http://a"+x+x+xx+"lartas"+xx+"com/getdatapengumuman";
        RequestParams params = new RequestParams();
        params.put("password", "1234");
        AHC.post(urlDownload, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //Log.d("Respon HTTP", "StatusCode :" + statusCode + ",\r\n Text :" + responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("jkl",responseString);
                try{
                    JSONObject jsonObject = new JSONObject(responseString);
                    String datax = jsonObject.getString("data");
                    JSONObject jsonresult = new JSONObject(datax);
                    msg_serv = jsonresult.getString("ISI").toString();
                    judul    = jsonresult.getString("JUDUL").toString();


                    if (judul.equals("Hai")) {
                        final AlertDialog d = new AlertDialog.Builder(InswActivity.this)
                                .setTitle("Pengumuman")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(R.drawable.icon)
                                .setMessage(Html.fromHtml(msg_serv))
                                .create();
                        d.show();
                        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                    } else if(judul.equals("Hai3")){
                        final AlertDialog d = new AlertDialog.Builder(InswActivity.this)
                                .setTitle("Pengumuman")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(R.drawable.icon)
                                .setMessage(Html.fromHtml(msg_serv))
                                .create();
                        d.show();
                        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                    }else{}
                }catch (Exception e){

                }
            }
        });
    }

    String x = "p";
    String xx = ".";
    private void caribro() {
        final ProgressDialog Dialog = new ProgressDialog(InswActivity.this);
        //Log.d("asd",cek+"");
        final int DEFAULT_TIMEOUT = 20 * 2000;
        AsyncHttpClient AHC = new AsyncHttpClient();
        AHC.setTimeout(DEFAULT_TIMEOUT);
        String urlDownload =  "http://a"+x+x+xx+"lartas"+xx+"com/searchkodehsv2";
        RequestParams data = new RequestParams();
//        if(idne == 1)
//            data.put("uraian", filtertext.getText().toString());
//        else {
//            data.put("kodehs", filtertext.getText().toString());
//        }
        data.put("query", filtertext.getText().toString());
        data.put("password", "1234");

        Dialog.setTitle("HS CODE INSW");
        Dialog.setMessage("Mengambil Data dari server, Harap Tunggu..");
        Dialog.setCancelable(true);
        Dialog.show();
        AHC.post(urlDownload, data, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.d("Respon HTTP", "StatusCode :" + statusCode + ",\r\n Text :" + responseString);
                if(statusCode == 404){
                    Log.d("asd","00");
                    Dialog.setTitle("HS CODE INSW");
                    Dialog.setMessage("No Data");
                    Dialog.show();
                }
            }

            String NOHS,DESKRIPSI,BAB,BABENGLISH,POSTARIF,POSTARIFENGLISH,BM,PPN,PPNBM,PPH,PREFDATA;
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                // String bast       = responseString;
                JSONObject jsonResponse ;
                Log.d("asd",responseString);
                Dialog.dismiss();

                try {
//                    custAdapterList.clear();
                    custAdapterList = new ArrayList<>();
                    jsonResponse = new JSONObject(responseString);
                    String sts = jsonResponse.getString("status");

                    if (sts.equals("404")){
                        Dialog.dismiss();

                        new AlertDialog.Builder(InswActivity.this)
                                .setTitle("HS CODE INSW")
                                .setMessage("Data Tidak Ditemukan")
                                .setNegativeButton(android.R.string.yes, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }

                    JSONArray cast = jsonResponse.getJSONArray("data");
                    setTitle("HSCode Result : "+cast.length()+"");
                    for (int i=0; i<cast.length(); i++) {
                        JSONObject insw = cast.getJSONObject(i);
                        NOHS            = insw.getString("NOHS");
                        DESKRIPSI       = insw.getString("DESKRIPSI");
                        BAB             = insw.getString("BAB");
                        BABENGLISH      = insw.getString("BABENGLISH");
                        POSTARIF        = insw.getString("POSTARIF");
                        POSTARIFENGLISH = insw.getString("POSTARIFENGLISH");
                        BM              = insw.getString("BM");
                        PPN             = insw.getString("PPN");
                        PPNBM           = insw.getString("PPNBM");
                        PPH             = insw.getString("PPH");
                        PREFDATA        = insw.getString("PREFDATA");

                        PreviewGetter pl = new PreviewGetter(
                                NOHS,DESKRIPSI,BAB,BABENGLISH,POSTARIF,POSTARIFENGLISH,BM,PPN,PPNBM,PPH,PREFDATA);
                        custAdapterList.add(pl);

                    }
                    showlist();
                } catch (JSONException e) {
//                    Toast.makeText(MainActivity.this, e+"", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }

    TextInputLayout filterlayout;
    private AdView mAdView;
    public static int idne = 1;
    EditText filtertext;
    Button cari;
    private RecyclerView reclist;
    private List<PreviewGetter> custAdapterList;
    private LinearLayoutManager mLinearLayoutManager;
    @SuppressLint("WrongConstant")
    private void showlist() {
        PreviewAdapter asd = new PreviewAdapter(this, custAdapterList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        reclist     = findViewById(R.id.recyclerpreview);
        reclist.setHasFixedSize(true);
        reclist.setLayoutManager(mLinearLayoutManager);
        reclist.setAdapter(asd);
    }



}
