package com.example.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText textField =(EditText) findViewById(R.id.nameEditText);
        String name = textField.getText().toString();

        CheckBox whippedCreamCheckBox =(CheckBox) findViewById(R.id.checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateBox =(CheckBox) findViewById(R.id.chocolateCheckBox);
        boolean hasChocolate=chocolateBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);

        String orderSummary= createOrderSummary(price,hasWhippedCream,hasChocolate,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the order summary on the screen.
     */


    public int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;

    }

   public void increment(View view){
        if (quantity==100){
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        display(quantity);
   }

   public void decrement(View view){
        if (quantity==1){
            Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
   }

   public String createOrderSummary(int priceOfOrder, boolean addWhippedCream, boolean addChocolate, String nameOfCustomer){
        return "Name: Ojey Elizaphan\n Add Whipped Cream? "+addWhippedCream+"\n Add Chocolate? "+addChocolate+ "\nQuantity: "+quantity+"\n Total: $"+priceOfOrder+"\nThank you!";
   }
}