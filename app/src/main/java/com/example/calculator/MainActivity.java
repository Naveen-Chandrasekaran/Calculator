package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView c, display;
    ImageView modulus, divide, add, sub, multiply, equal, delete;
    ImageView[] number = new ImageView[11];
    String currentValue = "", answer = "";
    double result = 0; // Changed to double
    boolean isResultModified=false;
    ArrayList<Double> valuesList = new ArrayList<>(); // Changed to ArrayList of Double
    ArrayList<String> operatorsList = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        modulus = findViewById(R.id.modulus);
        divide = findViewById(R.id.divide);
        add = findViewById(R.id.addition);
        sub = findViewById(R.id.subtract);
        multiply = findViewById(R.id.multiply);
        equal = findViewById(R.id.equal);
        display = findViewById(R.id.display);
        number[0] = findViewById(R.id.zero);
        number[1] = findViewById(R.id.one);
        number[2] = findViewById(R.id.two);
        number[3] = findViewById(R.id.three);
        number[4] = findViewById(R.id.four);
        number[5] = findViewById(R.id.five);
        number[6] = findViewById(R.id.six);
        number[7] = findViewById(R.id.seven);
        number[8] = findViewById(R.id.eight);
        number[9] = findViewById(R.id.nine);
        c = findViewById(R.id.clear);
        number[10] = findViewById(R.id.dot); // dot
        delete = findViewById(R.id.delete);

        for (int i = 0; i < number.length; i++) {
            int finalI = i;
            number[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==10){
                        currentValue+=".";
                        display.setText(display.getText() + ".");
                    }
                    else {
                        currentValue += finalI;
                        display.setText(display.getText() + String.valueOf(finalI));
                    }


                }
            });
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValuesToList();

                operatorsList.add("+");
                display.setText(display.getText() + "+");
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValuesToList();

                operatorsList.add("-");
                display.setText(display.getText() + "-");
            }
        });

        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValuesToList();

                operatorsList.add("*");
                display.setText(display.getText() + "*");
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValuesToList();

                operatorsList.add("/");
                display.setText(display.getText() + "/");
            }
        });

        modulus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addValuesToList();

                operatorsList.add("%");
                display.setText(display.getText() + "%");
            }
        });

        equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentValue.isEmpty()) {
                    valuesList.add(Double.parseDouble(currentValue));
                    currentValue = "";
                }

                if (!valuesList.isEmpty() && !operatorsList.isEmpty()) {
                    double intermediateResult = valuesList.get(0);

                    for (int i = 1; i < valuesList.size(); i++) {
                        double n1 = valuesList.get(i);
                        String currentOperator = operatorsList.get(i - 1);

                        switch (currentOperator) {
                            case "+":
                                intermediateResult += n1;
                                break;
                            case "-":
                                intermediateResult -= n1;
                                break;
                            case "*":
                                intermediateResult *= n1;
                                break;
                            case "/":
                                if (n1 != 0) {
                                    intermediateResult /= n1;
                                } else {
                                    Toast.makeText(MainActivity.this, "Division by zero is not allowed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                            case "%":
                                if (n1 != 0) {
                                    intermediateResult %= n1;
                                } else {
                                    Toast.makeText(MainActivity.this, "Modulus by zero is not allowed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                break;
                        }
                    }

                    result = intermediateResult;
                    valuesList.clear();
                    operatorsList.clear();
                    valuesList.add(result); // Store the result as the first value for subsequent calculations
                    answer = String.valueOf(result);
                    display.setText(answer);
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!currentValue.isEmpty()) {
                    // If there's a current value, delete the last digit.
                    currentValue = currentValue.substring(0, currentValue.length() - 1);
                    display.setText(display.getText().subSequence(0, display.getText().length() - 1));
                } else if (!valuesList.isEmpty()) {
                    isResultModified=true;
                    // Remove the last value from the valuesList
                    valuesList.remove(valuesList.size() - 1);

                    // Update the display by removing the last character
                    String currentDisplayText = display.getText().toString();
                    currentDisplayText = currentDisplayText.substring(0, currentDisplayText.length() - 1);
                    display.setText(currentDisplayText);
                } else if (!operatorsList.isEmpty()) {
                    // Remove the last operator from the operatorsList
                    operatorsList.remove(operatorsList.size() - 1);

                    // Update the display by removing the last character
                    String currentDisplayText = display.getText().toString();
                    currentDisplayText = currentDisplayText.substring(0, currentDisplayText.length() - 1);
                    display.setText(currentDisplayText);
                }
            }
        });





        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setText("");
                valuesList.clear();
                operatorsList.clear();
                currentValue = "";
                result = 0;
                answer = "";
            }
        });


    }
    void addValuesToList(){
        if(isResultModified==true){
            String temp=display.getText().toString();
            valuesList.add(Double.parseDouble(temp)); // Parse to double
            currentValue = "";
        }

        if (!currentValue.isEmpty()) {
            valuesList.add(Double.parseDouble(currentValue)); // Parse to double
            currentValue = "";
        }

    }

}
