package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstValue = 0.0;

    private boolean isOperatorPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Set up button listeners
        setNumberButtonListeners();
        setOperatorButtonListeners();
        setClearButtonListener(); // Set up clear button listener
    }

    private void setNumberButtonListeners() {
        int[] numberButtonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.button_
        };

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                currentInput += button.getText().toString();
                display.setText(currentInput);
            }
        };

        for (int id : numberButtonIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }
    }

    private void setOperatorButtonListeners() {
        int[] operatorButtonIds = {
                R.id.buttonADD, R.id.buttonSubtract, R.id.buttonMultiplication,
                R.id.buttonDevide, R.id.buttoneEquals
        };

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();

                if (buttonText.equals("=")) {
                    calculateResult();
                } else {
                    if (!currentInput.isEmpty() && !isOperatorPressed) {
                        try {
                            firstValue = Double.parseDouble(currentInput);
                            operator = buttonText;
                            currentInput = "";
                            isOperatorPressed = true;
                        } catch (NumberFormatException e) {
                            display.setText("Error");
                        }
                    }
                }
            }
        };

        for (int id : operatorButtonIds) {
            findViewById(id).setOnClickListener(operatorClickListener);
        }
    }

    private void setClearButtonListener() {
        Button clearButton = findViewById(R.id.buttonClear);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearDisplay();
            }
        });
    }

    private void clearDisplay() {
        currentInput = "";
        operator = "";
        firstValue = 0.0;
        isOperatorPressed = false;
        display.setText("0");
    }

    private void calculateResult() {
        if (isOperatorPressed && !currentInput.isEmpty()) {
            try {
                double secondValue = Double.parseDouble(currentInput);
                double result = 0.0;

                switch (operator) {
                    case "+":
                        result = firstValue + secondValue;
                        break;
                    case "-":
                        result = firstValue - secondValue;
                        break;
                    case "*":
                        result = firstValue * secondValue;
                        break;
                    case "/":
                        if (secondValue != 0) {
                            result = firstValue / secondValue;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                    default:
                        display.setText("Error");
                        return;
                }

                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                isOperatorPressed = false;
            } catch (NumberFormatException e) {
                display.setText("Error");
            }
        }
    }
}
