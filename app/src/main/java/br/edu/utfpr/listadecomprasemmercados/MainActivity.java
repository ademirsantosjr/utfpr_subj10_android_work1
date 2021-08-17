package br.edu.utfpr.listadecomprasemmercados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewGroceryItems = findViewById(R.id.listViewGroceryItems);

        listViewGroceryItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {

                selectedPosition = position;
                editItem();
            }
        });

        listViewGroceryItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           int position,
                                           long id) {
                selectedPosition = position;
                editItem();
                return false;
            }
        });

        populateListOfItems();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                FormActivity.addNewItem(this);
                return true;

            case R.id.menuItemAbout:
                AboutActivity.about(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateListOfItems() {
        groceryItems = new ArrayList<>();

        listAdapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        groceryItems);

        listViewGroceryItems.setAdapter(listAdapter);
    }

    public void editItem() {
        GroceryItem groceryItem = groceryItems.get(selectedPosition);
        FormActivity.editItem(this, groceryItem);
    }

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
            boolean basicItem = bundle.getBoolean(FormActivity.IS_BASIC_ITEM);

            if (requestCode == FormActivity.EDIT_ITEM) {
                GroceryItem groceryItem = groceryItems.get(selectedPosition);
                groceryItem.setItemName(itemName);
                groceryItem.setItemBrand(itemBrand);
                groceryItem.setPackingType(packingType);
                groceryItem.setAmountInThePackage(amountInThePackage);
                groceryItem.setUnitOfMeasurement(unitOfMeasurement);
                groceryItem.setCategory(category);
                groceryItem.setBasicItem(basicItem);

                selectedPosition = -1;
            } else {
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
}