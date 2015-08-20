package com.kutear.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.kutear.app.R;

/**
 * Created by kutear.guo on 2015/8/16.
 */
public class KDialogFragment extends DialogFragment {
    public static final String TAG = KDialogFragment.class.getSimpleName();
    public static final String DIALOG_TYPE = "type";
    public static final int LOADING_DIALOG = 0x0000;
    private int mDialogType;

    public static KDialogFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(DIALOG_TYPE, type);
        KDialogFragment fragment = new KDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static void showDialog(FragmentManager manager) {
        KDialogFragment dialogFragment = newInstance(LOADING_DIALOG);

        dialogFragment.show(manager, TAG);
    }

    public static void hiddenDialog(FragmentManager manager) {
        if (manager == null) {
            return;
        }
        KDialogFragment dialogFragment = (KDialogFragment) manager.findFragmentByTag(TAG);
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogType = bundle.getInt(DIALOG_TYPE);
        if (mDialogType == LOADING_DIALOG) {
            getDialog().setCanceledOnTouchOutside(false);
        }
        return inflater.inflate(R.layout.fragment_dialog, null);
    }


}
