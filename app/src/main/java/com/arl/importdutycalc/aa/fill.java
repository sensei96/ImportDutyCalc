package com.arl.importdutycalc.aa;

public class fill {
    private int _id;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_uraian() {
        return _uraian;
    }

    public void set_uraian(String _uraian) {
        this._uraian = _uraian;
    }

    private String _uraian;

    public fill() {
        this._id = 0;
        this._uraian = "";
    }
}
