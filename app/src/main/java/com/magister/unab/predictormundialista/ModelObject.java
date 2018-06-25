package com.magister.unab.predictormundialista;

public enum ModelObject {

    A(R.string.a, R.layout.grupo_a),
    B(R.string.b, R.layout.grupo_b),
    C(R.string.c, R.layout.grupo_c),
    D(R.string.d, R.layout.grupo_d),
    E(R.string.e, R.layout.grupo_e),
    F(R.string.f, R.layout.grupo_f),
    G(R.string.g, R.layout.grupo_g),
    H(R.string.h, R.layout.grupo_h),
    OCTAVOS(R.string.octavos, R.layout.octavos),
    TAREAS(R.string.tareas, R.layout.tareas);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}