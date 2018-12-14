package hk.hku.cs.comp7506_project.Calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.R;
import hk.hku.cs.comp7506_project.Wiki.WikiPage;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {
    private final static String TAG = "Calculator";
    private final static int FV = 0;
    private final static int PV = 1;
    private final static int IR = 2;
    private final static int MONTHLY = 0;
    private final static int QUARTERLY = 1;
    private final static int YEARLY = 2;

    private TextView field1Txt;
    private EditText field1;
    private TextView field2Txt;
    private EditText field2;
    private TextView field3Txt;
    private TextView field3;
    private TextView totalInterestFiled;
    private Button btnCalc;
    private EditText numberOfPeriods;
    private Spinner paymentFrequencySpinner;
    private int paymentFrequency = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        field1Txt = findViewById(R.id.field1_text);
        field1 = findViewById(R.id.field1);
        field2Txt = findViewById(R.id.field2_text);
        field2 = findViewById(R.id.field2);
        field3Txt = findViewById(R.id.field3_text);
        field3 = findViewById(R.id.field3);
        totalInterestFiled = findViewById(R.id.total_interest);
        btnCalc = findViewById(R.id.btn_calc);
        numberOfPeriods = findViewById(R.id.number_of_periods);

        final WikiPage wiki = new WikiPage();
        final SpannableString presentValueText = new SpannableString(getResources().getString(R.string.present_value));
        presentValueText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                wiki.popItUp("Present value", widget.getContext(),
                        getWindow().getDecorView().getRootView());
            }
        },0, getResources().getString(R.string.present_value).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString futureValueText = new SpannableString(getResources().getString(R.string.future_value));
        futureValueText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                wiki.popItUp("Future value", widget.getContext(),
                        getWindow().getDecorView().getRootView());
            }
        },0, getResources().getString(R.string.future_value).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        final SpannableString interestRateText = new SpannableString(getResources().getString(R.string.interest_rate));
        interestRateText.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                wiki.popItUp("Interest rate", widget.getContext(),
                        getWindow().getDecorView().getRootView());
            }
        },0, getResources().getString(R.string.interest_rate).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clear(view);

                switch (position){
                    case FV:
                        field1Txt.setText(presentValueText);
                        field2Txt.setText(interestRateText);
                        field3Txt.setText(futureValueText);

                        btnCalc.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                calculate_FV();
                            }
                        });

                        break;
                    case PV:
                        field1Txt.setText(futureValueText);
                        field2Txt.setText(interestRateText);
                        field3Txt.setText(presentValueText);

                        btnCalc.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                calculate_PV();
                            }
                        });

                        break;
                    case IR:
                        field1Txt.setText(presentValueText);
                        field2Txt.setText(futureValueText);
                        field3Txt.setText(interestRateText);

                        btnCalc.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                calculate_IR();
                            }
                        });

                        break;
                }

                field1Txt.setMovementMethod(LinkMovementMethod.getInstance());
                field2Txt.setMovementMethod(LinkMovementMethod.getInstance());
                field3Txt.setMovementMethod(LinkMovementMethod.getInstance());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        paymentFrequencySpinner = findViewById(R.id.payment_spinner);
        paymentFrequencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case MONTHLY:
                        paymentFrequency = 12;
                        break;
                    case QUARTERLY:
                        paymentFrequency = 4;
                        break;
                    case YEARLY:
                        paymentFrequency = 1;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void clear(View view){
        field1.setText("");
        field2.setText("");
        field3.setText("");
        numberOfPeriods.setText("");
        totalInterestFiled.setText("");
        paymentFrequencySpinner.setSelection(0);
    }

    private void calculate_FV(){
        double presentValue = Double.parseDouble(field1.getText().toString());
        double interest = Double.parseDouble(field2.getText().toString()) / 100;

        double futureValue = presentValue * Math.pow((1 + interest / paymentFrequency),
                Double.parseDouble(numberOfPeriods.getText().toString()));
        double totalInterest = futureValue - presentValue;

        totalInterestFiled.setText(String.format("%.2f", totalInterest));
        field3.setText(String.format("%.2f", futureValue));
    }

    private void calculate_PV(){
        double futureValue = Double.parseDouble(field1.getText().toString());
        double interest = Double.parseDouble(field2.getText().toString()) / 100;

        double presentValue = futureValue / Math.pow((1 + interest / paymentFrequency),
                Double.parseDouble(numberOfPeriods.getText().toString()));
        double totalInterest = futureValue - presentValue;

        totalInterestFiled.setText(String.format("%.2f", totalInterest));
        field3.setText(String.format("%.2f", presentValue));
    }

    private void calculate_IR() {
        double presentValue = Double.parseDouble(field1.getText().toString());
        double futureValue = Double.parseDouble(field2.getText().toString());

        double interest = paymentFrequency * (Math.pow(futureValue / presentValue,
                1 / Double.parseDouble(numberOfPeriods.getText().toString())) - 1) * 100;
        double totalInterest = futureValue - presentValue;

        totalInterestFiled.setText(String.format("%.2f", totalInterest));
        field3.setText(String.format("%.2f", interest));
    }
}
