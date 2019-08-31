package set.www.kalkulator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Button backSpace;
    private EditText editText;
    private TextView textView;
    private String display = "";
    private String currentOperator = "";
    private String tampilKanhasil = "";
    private String hasResult = "";

    @SuppressLint({"WrongConstant", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.operasi);
        textView = (TextView) findViewById(R.id.hasil);
        backSpace = (Button) findViewById(R.id.charHapus);

        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBackspace();
            }
        });

        backSpace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clear();
                updateScreen();
                return true;
            }
        });

        editText.requestFocus();
        editText.setShowSoftInputOnFocus(false);
        editText.setTextDirection(View.TEXT_DIRECTION_LTR);

        editText.setText(display);
        textView.setText(tampilKanhasil);
    }

    @SuppressLint("SetTextI18n")

    public void getBackspace(){
        String str = editText.getText().toString();
        if (str.length() != 0) {
            str = str.substring(0, str.length() - 1);
            display = str;
            editText.setText(display);
        }
    }

    public void updateScreen() {
        editText.setText(display);
    }

    public void onClickNomor(View view) {
        if (hasResult != ""){
            clear();
            updateScreen();
        }
        Button button = (Button) view;
        display += button.getText();
        updateScreen();
    }

    public Boolean isOperator(char operator){
        switch (operator){
            case '+':
            case '-':
            case 'x':
            case '/': return true;
            default: return false;
        }
    }

    public void onClickOperator(View view) {
        if (display == "") return;

        Button button = (Button) view;

        if (hasResult != ""){
            String _display = hasResult;
            clear();
            display = _display;
        }

        if (currentOperator != ""){
            if (isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), button.getText().charAt(0) );
                updateScreen();
                return;
            } else {
                getHasil();
                display = hasResult;
                hasResult = "";
            }
            currentOperator = button.getText().toString();
        }
        display += button.getText();
        currentOperator = button.getText().toString();
        updateScreen();
    }

    public double operatorRumus(String a,  String operator, String b) {
        switch (operator) {
            case "+":
                return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "x":
                return Double.valueOf(a) * Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Kalkulator", e.getMessage());
                }
                default: return -1;
        }
    }

    private Boolean getHasil(){
        if (currentOperator == "") return false;
        String[] operator = display.split(Pattern.quote(currentOperator));
        if (operator.length < 2) return false;
        hasResult = String.valueOf(operatorRumus(operator[0], currentOperator, operator[1]));
        return true;
    }

    public void onClickSamaDengan(View view) {
        if (display == "") return;
        if (!getHasil())return;
        textView.setText(hasResult);
    }

    public void clear(){
        display = "";
        currentOperator = "";
        tampilKanhasil = "";
        textView.setText("");
        hasResult = "";
    }
}
