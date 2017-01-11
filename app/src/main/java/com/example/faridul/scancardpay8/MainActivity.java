package com.example.faridul.scancardpay8;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_SCAN_REQUEST_CODE =  99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scanCreditCard = (Button)findViewById(R.id.button);
        assert scanCreditCard != null;
        scanCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanIntent = new Intent(MainActivity.this, CardIOActivity.class);
                // customize these values to suit your needs.
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, false); // default: false
//                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, false); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true);

//
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true);
//                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false);
//                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_OVERLAY_LAYOUT_ID, true);
//                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN, true);
//                scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE, true);
                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_RESULT, true);

//

                // hides the manual entry button
                // if set, developers should provide their own manual entry mechanism in the app
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true ); // default: false

                // matches the theme of your application
//                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, false); // default: false

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                Log.d(TAG, "Card Number " + resultDisplayStr);
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                }
                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                }
                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }

                Toast.makeText(this,  scanResult.getRedactedCardNumber(), Toast.LENGTH_LONG).show();
            }
            else {
                resultDisplayStr = "Scan was canceled.";
                Toast.makeText(this, resultDisplayStr, Toast.LENGTH_LONG).show();
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultDisplayStr);
        }
        // else handle other activity results
    }
}
