package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<GroceryItem> listAdapter;
    private List<GroceryItem> groceryItems;

    private ListView listViewGroceryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewGroceryItems = findViewById(R.id.listViewGroceryItems);

        listViewGroceryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroceryItem groceryItem =
                        (GroceryItem) listViewGroceryItems.getItemAtPosition(position);

                Toast.makeText(getApplicationContext(),
                        getString(R.string.item_selecionado) + groceryItem.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });

        populateListOfItems();
    }

    private void populateListOfItems() {
        groceryItems = new ArrayList<>();

        listAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        groceryItems);

        listViewGroceryItems.setAdapter(listAdapter);
    }

    public void addNewItem(View view) {
        FormActivity.addNewItem(this);
    }

    public void openPageAbout(View view) { AboutActivity.about(this); }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            String itemName = bundle.getString(FormActivity.ITEM_NAME);
            String itemBrand = bundle.getString(FormActivity.ITEM_BRAND);
            String packingType = bundle.getString(FormActivity.PACKING_TYPE);

            int amountInThePackage =
                    Integer.parseInt(bundle.getString(FormActivity.AMOUNT_IN_THE_PACKAGE));

            String category = bundle.getString(FormActivity.CATEGORY);

            String unitOfMeasurement = bundle.getString(FormActivity.UNIT_OF_MEASUREMENT);
            boolean basicItem = bundle.getBoolean(FormActivity.BASIC_ITEM);

            groceryItems.add(new GroceryItem(
                    itemName,
                    itemBrand,
                    packingType,
                    amountInThePackage,
                    unitOfMeasurement,
                    category,
                    basicItem
            ));
        }

        listAdapter.notifyDataSetChanged();
    }
}