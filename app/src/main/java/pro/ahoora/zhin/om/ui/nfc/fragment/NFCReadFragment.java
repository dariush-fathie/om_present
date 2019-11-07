package pro.ahoora.zhin.om.ui.nfc.fragment;

import android.content.Context;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import pro.ahoora.zhin.om.R;
import pro.ahoora.zhin.om.event.Listener;
import pro.ahoora.zhin.om.ui.nfc.NfcActivity;

public class NFCReadFragment extends Fragment {

    public static final String TAG = NFCReadFragment.class.getSimpleName();
    private TextView mTvMessage;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mTvMessage = view.findViewById(R.id.tv_message);
    }


    public void onNfcDetected(Ndef ndef) {
        readFromNFC(ndef);
    }

    private void readFromNFC(Ndef ndef) {
        if (ndef != null) {
            try {
                ndef.connect();
                NdefMessage ndefMessage = null;
                ndefMessage = ndef.getNdefMessage();
                String message = new String(ndefMessage.getRecords()[0].getPayload());
                Log.d(TAG, "readFromNFC: " + message);
                mTvMessage.setText(message);
                ndef.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        } else {
            mTvMessage.setText(getString(R.string.TheTagIsEmpty));
        }

    }
}
