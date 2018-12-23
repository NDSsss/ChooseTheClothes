package com.example.nds.choosetheclothe.clothe;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nds.choosetheclothe.R;

public class ChangeClotheParamsDialog extends DialogFragment {
    public static final String CLOTHE = "ChangeClotheParamsDialog.CLOTHE";
    public static final String TAG = "ChangeClotheParamsDialog.TAG";
    private Clothe clothe;
    private EditText etName, etMinTemp, etMaxTemp, etRaiting;
    private SaveClothe saveClothe;
    private Button btnSave;

    public static ChangeClotheParamsDialog newInstance(Clothe clothe) {
        ChangeClotheParamsDialog actionsDialog = new ChangeClotheParamsDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CLOTHE, clothe);
        actionsDialog.setArguments(bundle);
        return actionsDialog;
    }

    public void setSaveClothe(SaveClothe saveClothe) {
        this.saveClothe = saveClothe;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArgs();
    }

    private void getArgs() {
        if (getArguments() != null) {
            clothe = getArguments().getParcelable(CLOTHE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_clothe, null, false);
        etName = view.findViewById(R.id.tv_dialog_change_clothe_name);
        etMinTemp = view.findViewById(R.id.tv_dialog_change_clothe_min_temp);
        etMaxTemp = view.findViewById(R.id.tv_dialog_change_clothe_max_temp);
        etRaiting = view.findViewById(R.id.tv_dialog_change_clothe_raiting);
        btnSave = view.findViewById(R.id.btn_dialog_change_clothe_save);
        btnSave.setOnClickListener(v -> saveClicked());
        etName.setText(clothe.getName());
        etMinTemp.setText(String.valueOf(clothe.getMinTemp()));
        etMaxTemp.setText(String.valueOf(clothe.getMaxTemp()));
        etRaiting.setText(String.valueOf(clothe.getRaiting()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

    private void saveClicked() {
        clothe.setName(etName.getText().toString());
        clothe.setMinTemp(Integer.parseInt(etMinTemp.getText().toString()));
        clothe.setMaxTemp(Integer.parseInt(etMaxTemp.getText().toString()));
        clothe.setRaiting(Integer.parseInt(etRaiting.getText().toString()));
        saveClothe.saveClothe(clothe);
        dismiss();
    }

    public interface SaveClothe {
        void saveClothe(Clothe clothe);
    }

}
