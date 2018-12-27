package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;
    private boolean whippedCream = false;
    private boolean chocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = calculatePrice();
        createOrderSummary(price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method will increment the quantity
     */
    public void increment(View view) {
        quantity++;
        if(quantity>100){
            Toast.makeText(getApplicationContext(),"You can't order more than 100 coffee.",
                    Toast.LENGTH_SHORT).show();
            quantity = 100;
        }
        displayQuantity(quantity);
    }

    /**
     * This method will decrement the quantity
     */
    public void decrement(View view) {
        quantity--;
        if(quantity<1) {
            Toast.makeText(getApplicationContext(), "You can't order less than 1 coffee",
                    Toast.LENGTH_SHORT).show();
            quantity = 1;
        }
        displayQuantity(quantity);
    }

    /**
     * Method to calculate the Price
     */
    private int calculatePrice() {
        int price = 5;
        if(whippedCream)
            price = price +1;
        if(chocolate)
            price = price +2;
        return quantity*price;
    }

    public void toppingCheckBox(View view) {
        CheckBox hasWhippedCream = findViewById(R.id.whippedCream_checkbox);
        CheckBox hasChocolate = findViewById(R.id.chocolate_checkbox);
        if (hasWhippedCream.isChecked())
            whippedCream = true;
        else
            whippedCream = false;
        if (hasChocolate.isChecked())
            chocolate = true;
        else
            chocolate = false;
    }

    private void createOrderSummary(int price) {
       EditText userName = findViewById(R.id.name_edit_text);
        String name = userName.getText().toString();
        String message = getResources().getString(R.string.userName)+name+
                "\n"+getResources().getString(R.string.addWC) +whippedCream+
                "\n"+getResources().getString(R.string.addChoc) +chocolate+
                "\n"+getResources().getString(R.string.quantity) + quantity+
                "\n"+getResources().getString(R.string.total) +price+
                "\n"+getResources().getString(R.string.thanks);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java Order for " +name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}