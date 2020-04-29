package com.arl.importdutycalc;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public String SYMsel, CURRtxt, CIFtxt, BMsel, PPhSel, BMin, PPhIn, BMfin, PPhFin, CURtemp, CIFtemp;
    public Double c, b, n, h, t;
    public TextView CURRtv, BMpctTv, PPhPctTv, CIF, BM, PPN, PPh, TOTAL;
    public EditText CURRinput, CIFinput, BMinput, PPhInput;
    public Spinner CURRlist, BMpct, PPhPct;
    public boolean BMmanual, PPhManual;
    public Switch BMsw, PPhSw;
    String xx = ".";
    public Button Refresh, Clear;
    public RelativeLayout mainLayout, loadingLayout;
    public ProgressBar loading;
    public SharedPreferences sPrefs;
    public SharedPreferences.Editor editor;
    public static final String LAST_VALUE = "LastValue";
    String sel = "";
    String x = "p";
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handshakeserver();
        sPrefs = getSharedPreferences(LAST_VALUE, Context.MODE_PRIVATE);
        editor = sPrefs.edit();
        //edittext
        CURRinput = findViewById(R.id.CurrIn);
        CIFinput = findViewById(R.id.CIFin);
        CURRinput.setText(sPrefs.getString(sPrefs.getString("CUR", "USD"), "0"));
        CURRtxt = CURRinput.getText().toString();
        CIFinput.setText(sPrefs.getString("CIF", "0"));
        CIFtxt = CIFinput.getText().toString();
        //spinner
        CURRlist = findViewById(R.id.CurrList);
        CURRlist.setOnItemSelectedListener(this);
        BMpct = findViewById(R.id.BM);
        BMpct.setOnItemSelectedListener(this);
        PPhPct = findViewById(R.id.PPh);
        PPhPct.setOnItemSelectedListener(this);
        //switch
        BMsw = findViewById(R.id.BMsw);
        BMinput = findViewById(R.id.BMin);
        PPhSw = findViewById(R.id.PPhSw);
        PPhInput = findViewById(R.id.PPhIn);
        //textview
        CURRtv = findViewById(R.id.CurrTv);
        BMpctTv = findViewById(R.id.BMpctTv);
        PPhPctTv = findViewById(R.id.PPhPctTv);
        CIF = findViewById(R.id.CIFIDR);
        BM = findViewById(R.id.BMrsltTv);
        PPN = findViewById(R.id.PPNrsltTv);
        PPh = findViewById(R.id.PPhRsltTv);
        TOTAL = findViewById(R.id.TOTAL);
        //button
        Refresh = findViewById(R.id.Refresh);
        Clear = findViewById(R.id.Clear);
        //layout
        mainLayout = findViewById(R.id.MainLayout);
        loadingLayout = findViewById(R.id.LoadingLayout);
        //progressbar
        loading = findViewById(R.id.loading);


        AdView mAdView = findViewById(R.id.adViewx);
        AdRequest adRequest = new AdRequest
                .Builder()
                .addTestDevice("8E3698F6055A47A2E672FB6CC49DD1FF")
                .build();
        mAdView.loadAd(adRequest);

        List<String> CURlist = new ArrayList<String>();
        CURlist.add("USD");
        CURlist.add("CNY");
        CURlist.add("EUR");
        CURlist.add("SGD");
        CURlist.add("GBP");
        CURlist.add("HKD");
        CURlist.add("JPY");
        CURlist.add("KRW");
        CURlist.add("AUD");

        ArrayAdapter<String> CURdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CURlist);
        CURdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CURRlist.setAdapter(CURdataAdapter);
        CURRlist.setSelection(CURdataAdapter.getPosition(
                sPrefs.getString("CUR", "USD")));
        CURRtv.setText("Currency  " + CURRlist.getSelectedItem().toString());



        List<String> BMlist = new ArrayList<String>();
        BMlist.add("0");
        BMlist.add("5");
        BMlist.add("7.5");
        BMlist.add("10");
        BMlist.add("12.5");
        BMlist.add("15");
        BMlist.add("20");
        BMlist.add("25");

        ArrayAdapter<String> BMdataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BMlist);
        BMdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        BMpct.setAdapter(BMdataAdapter);
        BMpct.setSelection(BMdataAdapter.getPosition(
                sPrefs.getString("BM", "5")));
        BMsel = BMpct.getSelectedItem().toString();
        BMpctTv.setText(BMsel + "%");

        List<String> PPhList = new ArrayList<String>();
        PPhList.add("2.5");
        PPhList.add("7.5");
        PPhList.add("10");
        PPhList.add("12.5");

        ArrayAdapter<String> PPhDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PPhList);
        PPhDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PPhPct.setAdapter(PPhDataAdapter);
        PPhPct.setSelection(PPhDataAdapter.getPosition(
                sPrefs.getString("PPh", "7.5")));
        PPhSel = PPhPct.getSelectedItem().toString();
        PPhPctTv.setText(PPhSel + "%");

        try {
            CIFinput.requestFocus();
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            CIFinput.setSelectAllOnFocus(true);
            CIFinput.selectAll();
            CIFinput.setFocusedByDefault(true);
            CURRinput.setFocusedByDefault(false);
            CURRinput.setSelectAllOnFocus(true);
        } catch (Throwable th) {
            Toast.makeText(getApplicationContext(), "1" + String.valueOf(th), Toast.LENGTH_SHORT).show();
        }

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (CURRinput.getText().toString().length() != 0) {
                        CURtemp = CURRinput.getText().toString();
                        CURRinput.setText("");
                        CURRtxt = "0";
                        Calc();
                    }
                    if (CIFinput.getText().toString().length() != 0) {
                        CIFtemp = CIFinput.getText().toString();
                        CIFinput.setText("");
                        CIFtxt = "0";
                        Calc();
                    }
                    loadingLayout.setVisibility(View.VISIBLE);
                    mainLayout.setActivated(false);
                    CURRinput.setText(CURtemp);
                    CURRtxt = CURtemp;
                    CIFinput.setText(CIFtemp);
                    CIFtxt = CIFtemp;
                    Calc();
                    if (Refresh.getText().toString() == "Undo") {
                        Refresh.setText("Refresh");
                        Toast.makeText(getApplicationContext(), "Undoing deleted '" + CURRlist.getSelectedItem().toString() + CURtemp + "' and 'CIF Rp" + CIFtemp + "'", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                    }
                    CIFinput.requestFocus();
                    CIFinput.selectAll();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "2" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    loadingLayout.setVisibility(View.VISIBLE);
                    mainLayout.setActivated(false);
                    CURtemp = CURRinput.getText().toString();
                    CIFtemp = CIFinput.getText().toString();
                    //CURRinput.setText("");
                    CIFinput.setText("");
                    CURRtxt = "0";
                    CIFtxt = "0";
                    Toast.makeText(getApplicationContext(), "Cleared all field", Toast.LENGTH_SHORT).show();
                    Calc();
                    Refresh.setText("Undo");
                    CIFinput.requestFocus();
                    CIFinput.selectAll();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "3" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        CURRtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    panggilkurs();
                    CURRlist.performClick();

                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "4a" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BMpctTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BMpct.performClick();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "4b" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        PPhPctTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    PPhPct.performClick();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "4c" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        TOTAL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text", TOTAL.getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "'" + TOTAL.getText() + "' Copied", Toast.LENGTH_SHORT).show();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "5" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BMsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton BMcb, boolean bmChecked) {
                try {
                    BMmanual = bmChecked;
                    if (bmChecked) {
                        BMinput.setVisibility(View.VISIBLE);
                        BMpct.setVisibility(View.INVISIBLE);
                        BMpctTv.setVisibility(View.INVISIBLE);
                        BMin = BMpct.getSelectedItem().toString();
                        BMinput.setText(BMin);
                        BMinput.setSelectAllOnFocus(true);
                        BMinput.requestFocus();
                        BMinput.selectAll();
                    } else {
                        Calc();
                        BMinput.setVisibility(View.INVISIBLE);
                        BMpct.setVisibility(View.VISIBLE);
                        BMpctTv.setVisibility(View.VISIBLE);
                        CIFinput.requestFocus();
                        CIFinput.selectAll();
                    }
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "6" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        PPhSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton PPhCb, boolean pphChecked) {
                try {
                    PPhManual = pphChecked;
                    if (pphChecked) {
                        PPhInput.setVisibility(View.VISIBLE);
                        PPhPct.setVisibility(View.INVISIBLE);
                        PPhPctTv.setVisibility(View.INVISIBLE);
                        PPhIn = PPhPct.getSelectedItem().toString();
                        PPhInput.setText(PPhIn);
                        PPhInput.setSelectAllOnFocus(true);
                        PPhInput.requestFocus();
                        PPhInput.selectAll();
                    } else {
                        Calc();
                        PPhInput.setVisibility(View.INVISIBLE);
                        PPhPct.setVisibility(View.VISIBLE);
                        PPhPctTv.setVisibility(View.VISIBLE);
                        CIFinput.requestFocus();
                        CIFinput.selectAll();
                    }
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "7" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        BMinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (BMinput.getText().toString().length() == 0) {
                        BMin = "0";
                    } else {
                        BMin = BMinput.getText().toString();
                    }
                    Calc();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "8" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        PPhInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (PPhInput.getText().toString().length() == 0) {
                        PPhIn = "0";
                    } else {
                        PPhIn = PPhInput.getText().toString();
                    }
                    Calc();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "9" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        CURRinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (CURRinput.getText().toString().length() == 0) {
                        CURRtxt = "0";
                    } else {
                        CURRtxt = CURRinput.getText().toString();
                        editor.putString("CUR", SYMsel);
                        editor.putString(SYMsel, CURRtxt);
                        editor.commit();
                    }
                    Calc();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "10" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });

        CIFinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (CIFinput.getText().toString().length() == 0) {
                        CIFtxt = "0";
                    } else {
                        CIFtxt = CIFinput.getText().toString();
                        editor.putString("CIF", CIFtxt);
                        editor.commit();
                    }
                    Calc();
                } catch (Throwable th) {
                    Toast.makeText(getApplicationContext(), "11" + String.valueOf(th), Toast.LENGTH_SHORT).show();
                }
            }
        });


        Calc();


    }




//public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            loadingLayout.setVisibility(View.VISIBLE);
            mainLayout.setActivated(false);
            sel = parent.getItemAtPosition(position).toString();
            String item = "Unknown Error";
            sPrefs = parent.getContext().getSharedPreferences(LAST_VALUE, Context.MODE_PRIVATE);
            editor = sPrefs.edit();
            TextView CIFtv = findViewById(R.id.CIFtv);
            if (parent.getId() == R.id.CurrList) {

                SYMsel = sel;
                boolean changed = (sPrefs.getString("CUR", "USD") != SYMsel)
                        || (sPrefs.getString(SYMsel, "0") != CURRinput.getText().toString())
                        || (sPrefs.getString(SYMsel,"0") == "0");
                CURRtv.setText("Currency " + SYMsel);
                CIFtv.setText("CIF " + SYMsel);
                panggilkurs();
                editor.putString("CUR", SYMsel);
                CURRinput.setText(sPrefs.getString(SYMsel, "0"));
                CURRtxt = CURRinput.getText().toString();

                item = "Selected '1 " + SYMsel + " : " + CURRtxt + " IDR' as currency";
                if (changed) {
                    CURRinput.requestFocus();
                    CURRinput.selectAll();
                }
            } else if (parent.getId() == R.id.BM) {
                BMsel = sel;
                BMpctTv.setText(sel.replace(".0","")+"%");
                editor.putString("BM", BMsel);
                item = "Selected BM :" + sel.replace(".0","")+"%";
                CIFinput.requestFocus();
                CIFinput.selectAll();
            } else if (parent.getId() == R.id.PPh) {
                PPhSel = sel;
                PPhPctTv.setText(sel.replace(".0","")+"%");
                editor.putString("PPh", PPhSel);
                item = "Selected PPh :" + sel.replace(".0","")+"%";
                CIFinput.requestFocus();
                CIFinput.selectAll();
            } else {
                item = "Error";
            }
            editor.commit();
            Calc();
            mainLayout.setActivated(true);
            loadingLayout.setVisibility(View.INVISIBLE);
            Toast.makeText(parent.getContext(), item, Toast.LENGTH_SHORT).show();
        } catch(Throwable th) {
            Toast.makeText(parent.getContext(), "12"+String.valueOf(th), Toast.LENGTH_SHORT).show();
        }
    }

    String tes = "";
    private String panggilkurs() {
        AsyncHttpClient AHC = new AsyncHttpClient();
        AHC.setMaxRetriesAndTimeout(0, 20000);

        String urlDownload = "http://lartas.com/kurs/index.php?kurs="+SYMsel;
        RequestParams params = new RequestParams();
        AHC.post(urlDownload, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Download Gagal, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {

                tes = responseString;
                tes = tes.replace(",","");
                //String tes2 = tes.replace(",",".");
                CURRinput.setText(tes);
                Log.d("kursnya   ","    "+tes);

            }
        });
        return tes;
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        try {
            Calc();
        } catch(Exception th) {

        }
    }

    public final void Calc() {
        loadingLayout.setVisibility(View.VISIBLE);
        CIF.performHapticFeedback(1,1);
        Locale ID = new Locale("in", "ID");
        DecimalFormatSymbols sym = new DecimalFormatSymbols(ID);
        sym.setGroupingSeparator('.');
        sym.setDecimalSeparator(',');
        String pat = "'Rp '#,###.##";
        DecimalFormat cur = new DecimalFormat(pat, sym);
        try {
            if (BMmanual) {
                BMfin = BMin;
            } else {
                BMfin = BMsel;
            }
            if (PPhManual) {
                PPhFin = PPhIn;
            } else {
                PPhFin = PPhSel;
            }
            if (CURRtxt.length()!=0 && CIFtxt.length()!=0 && BMfin.length()!=0 && PPhFin.length()!=0) {

                c = Double.valueOf(Math.ceil(Double.parseDouble(CURRtxt)*Double.parseDouble(CIFtxt)));
                b = Double.valueOf(Math.ceil(((c*Double.valueOf(BMfin))/100)/1000)*1000);
                n = Double.valueOf(Math.ceil(((c+b)*10/100)/1000)*1000);
                h = Double.valueOf(Math.ceil(((c+b)*Double.valueOf(PPhFin)/100)/1000)*1000);
                t = b+n+h;

                CIF.setText(cur.format(c));
                BM.setText(cur.format(b));
                PPN.setText(cur.format(n));
                PPh.setText(cur.format(h));
                TOTAL.setText(cur.format(t));

            } else {
                //par null value
                Toast.makeText(getApplicationContext(), "13.a"+CURRtxt+CIFtxt+BMfin+PPhFin, Toast.LENGTH_SHORT).show();
            }
            mainLayout.setActivated(true);
            loadingLayout.setVisibility(View.INVISIBLE);
        } catch(Throwable th) {
            Toast.makeText(getApplicationContext(), "13.b"+String.valueOf(th)+CURRtxt+CIFtxt+BMfin+PPhFin, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.hscode:
                Intent aa;
                aa = new Intent(this, InswActivity.class);
                startActivity(aa);
                return true;
            case R.id.sharebutton:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.sharethis)+" https://play.google.com/store/apps/details?id=arl.waunknown.chat");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            case R.id.ratebutton:
                ratethis();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    private void ratethis() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
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
                        final AlertDialog d = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Pengumuman")
                                .setPositiveButton(android.R.string.ok, null)
                                .setIcon(R.drawable.icon)
                                .setMessage(Html.fromHtml(msg_serv))
                                .create();
                        d.show();
                        ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
                    } else if(judul.equals("Hai3")){
                        final AlertDialog d = new AlertDialog.Builder(MainActivity.this)
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

}

