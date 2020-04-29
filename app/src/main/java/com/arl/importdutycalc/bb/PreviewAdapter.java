package com.arl.importdutycalc.bb;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arl.importdutycalc.R;
import com.arl.importdutycalc.ResultFinal;

import java.util.List;

public class PreviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<PreviewGetter> previewList;
    private Activity act;

    public PreviewAdapter(Activity act, List<PreviewGetter> customerList) {
        this.act = act;
        this.previewList = customerList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.preview, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PreviewGetter previewGetter = previewList.get(position);
        ((ViewHolder)holder).phscode.setText(Html.fromHtml("<b>HSCode</b><br>"+previewGetter.getNOHS()));
        ((ViewHolder)holder).pbm.setText(Html.fromHtml("<b>BM</b><br>"+previewGetter.getBM()));
        ((ViewHolder)holder).pppn.setText(Html.fromHtml("<b>PPN</b><br>"+previewGetter.getPPN()));
        ((ViewHolder)holder).pppnbm.setText(Html.fromHtml("<b>PPNBM</b><br>"+previewGetter.getPPNBM()));
        ((ViewHolder)holder).ppph.setText(Html.fromHtml("<b>PPH</b><br>"+previewGetter.getPPH()));
        ((ViewHolder)holder).pdesc.setText(Html.fromHtml("<b>DESKRIPSI</b><br>"+previewGetter.getDESKRIPSI()));

        ((ViewHolder)holder).klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa;
                aa = new Intent(act, ResultFinal.class);
                aa.putExtra("hscode",previewGetter.getNOHS());
                aa.putExtra("bm", previewGetter.getBM()); aa.putExtra("ppn",previewGetter.getPPN());
                aa.putExtra("ppnbm", previewGetter.getPPNBM()); aa.putExtra("pph",previewGetter.getPPH());
                aa.putExtra("desc", previewGetter.getDESKRIPSI());aa.putExtra("bab", previewGetter.getBAB());
                aa.putExtra("baben", previewGetter.getBABENGLISH());aa.putExtra("post", previewGetter.getPOSTARIF());
                aa.putExtra("posten", previewGetter.getPOSTARIFENGLISH());
                aa.putExtra("pref",previewGetter.getPREFDATA());
                act.startActivity(aa);

            }
        });
    }

    @Override
    public int getItemCount() {
        return previewList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView phscode, pbm, pppn, pppnbm, ppph, pdesc, pposteng;
        public CardView klik;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            phscode = itemLayoutView.findViewById(R.id.texthscode);
            pbm     = itemLayoutView.findViewById(R.id.textbm);
            pppn    = itemLayoutView.findViewById(R.id.textppn);
            pppnbm  = itemLayoutView.findViewById(R.id.textppnbm);
            ppph    = itemLayoutView.findViewById(R.id.textpph);
            pdesc   = itemLayoutView.findViewById(R.id.textdesc);
            klik    = itemLayoutView.findViewById(R.id.layoutklik);
            this.setIsRecyclable(false);
        }
    }


}

