package com.arl.importdutycalc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ResultFinal extends AppCompatActivity {

    TextView phscode, pbm, pppn, pppnbm, ppph, pdesc, ppost, pposteng, bab, baben;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_final);

        AdView mAdView             = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("8E3698F6055A47A2E672FB6CC49DD1FF").build();
        mAdView.loadAd(adRequest);

        Intent sender    = getIntent();
        phscode = findViewById(R.id.texthscode);
        pbm     = findViewById(R.id.textbm);
        pppn    = findViewById(R.id.textppn);
        pppnbm  = findViewById(R.id.textppnbm);
        ppph    = findViewById(R.id.textpph);
        pdesc   = findViewById(R.id.textdesc);
        ppost   = findViewById(R.id.textpost);
        pposteng= findViewById(R.id.textposten);
        bab     = findViewById(R.id.textbab);
        baben   = findViewById(R.id.textbabeng);

        phscode.setText(Html.fromHtml("<b>HSCode</b><br>"+sender.getExtras().getString("hscode")));
        pbm.setText(Html.fromHtml("<b>BM</b><br>"+sender.getExtras().getString("bm")));
        pppn.setText(Html.fromHtml("<b>PPN</b><br>"+sender.getExtras().getString("ppn")));
        pppnbm.setText(Html.fromHtml("<b>PPNBM</b><br>"+sender.getExtras().getString("ppnbm")));
        ppph.setText(Html.fromHtml("<b>PPH</b><br>"+sender.getExtras().getString("pph")));
        pdesc.setText(Html.fromHtml("<b>DESKRIPSI</b><br>"+sender.getExtras().getString("desc")));
        bab.setText(Html.fromHtml("<b>BAB</b><br>"+sender.getExtras().getString("bab")));
        baben.setText(Html.fromHtml("<b>BAB ENG</b><br>"+sender.getExtras().getString("baben")));
        ppost.setText(Html.fromHtml("<b>POST TARIF</b><br>"+sender.getExtras().getString("post")));
        pposteng.setText(Html.fromHtml("<b>POST TARIF ENG</b><br>"+sender.getExtras().getString("posten")));

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-6593971228900391/4264850397");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });
        requestNewInterstitial();

    }

    private InterstitialAd mInterstitialAd;
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("8E3698F6055A47A2E672FB6CC49DD1FF")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        mInterstitialAd.show();
        super.onBackPressed();
    }


}
