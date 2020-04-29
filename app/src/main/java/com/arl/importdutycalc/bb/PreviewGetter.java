package com.arl.importdutycalc.bb;

import java.io.Serializable;

public class PreviewGetter implements Serializable {
    String NOHS;
    String DESKRIPSI;
    String BAB;
    String BABENGLISH;
    String POSTARIF;
    String POSTARIFENGLISH;
    String BM;
    String PPN;
    String PPNBM;
    String PPH;

    public String getNOHS() {
        return NOHS;
    }

    public void setNOHS(String NOHS) {
        this.NOHS = NOHS;
    }

    public String getDESKRIPSI() {
        return DESKRIPSI;
    }

    public void setDESKRIPSI(String DESKRIPSI) {
        this.DESKRIPSI = DESKRIPSI;
    }

    public String getBAB() {
        return BAB;
    }

    public void setBAB(String BAB) {
        this.BAB = BAB;
    }

    public String getBABENGLISH() {
        return BABENGLISH;
    }

    public void setBABENGLISH(String BABENGLISH) {
        this.BABENGLISH = BABENGLISH;
    }

    public String getPOSTARIF() {
        return POSTARIF;
    }

    public void setPOSTARIF(String POSTARIF) {
        this.POSTARIF = POSTARIF;
    }

    public String getPOSTARIFENGLISH() {
        return POSTARIFENGLISH;
    }

    public void setPOSTARIFENGLISH(String POSTARIFENGLISH) {
        this.POSTARIFENGLISH = POSTARIFENGLISH;
    }

    public String getBM() {
        return BM;
    }

    public void setBM(String BM) {
        this.BM = BM;
    }

    public String getPPN() {
        return PPN;
    }

    public void setPPN(String PPN) {
        this.PPN = PPN;
    }

    public String getPPNBM() {
        return PPNBM;
    }

    public void setPPNBM(String PPNBM) {
        this.PPNBM = PPNBM;
    }

    public String getPPH() {
        return PPH;
    }

    public void setPPH(String PPH) {
        this.PPH = PPH;
    }

    public PreviewGetter(String NOHS, String DESKRIPSI, String BAB, String BABENGLISH, String POSTARIF, String POSTARIFENGLISH, String BM, String PPN, String PPNBM, String PPH, String PREFDATA) {
        this.NOHS = NOHS;
        this.DESKRIPSI = DESKRIPSI;
        this.BAB = BAB;
        this.BABENGLISH = BABENGLISH;
        this.POSTARIF = POSTARIF;
        this.POSTARIFENGLISH = POSTARIFENGLISH;
        this.BM = BM;
        this.PPN = PPN;
        this.PPNBM = PPNBM;
        this.PPH = PPH;
        this.PREFDATA=PREFDATA;
    }

    public String getPREFDATA() {
        return PREFDATA;
    }

    public void setPREFDATA(String PREFDATA) {
        this.PREFDATA = PREFDATA;
    }

    String PREFDATA;

}
