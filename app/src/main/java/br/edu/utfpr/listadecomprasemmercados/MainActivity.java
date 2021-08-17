package br.edu.utfpr.listadecomprasemmercados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<GroceryItem> listAdapter;
    private List<GroceryItem> groceries;

    private int selectedPosition = -1;

    private ListView listViewGroceries;

    private ActionMode actionMode;

    private View selectedView;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.edit_remove_options, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuItemEdit:
                    editGrocery();
                    mode.finish();
                    return true;

                case R.id.menuItemRemove:
                    removeGrocery();
                    mode.finish();
                    return true;

                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if (selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            selectedView = null;

            listViewGroceries.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewGroceries = findViewById(R.id.listViewGroceryItems);

        listViewGroceries.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewGroceries.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,
                                           View view,
                                           int position,
                                           long id) {

                if (actionMode != null) {
                    return false;
                }

                selectedPosition = position;

                view.setBackgroundColor(Color.LTGRAY);

                selectedView = view;

                listViewGroceries.setEnabled(false);

                actionMode = startSupportActionMode(mActionModeCallback);

                return true;
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
        groceries = new ArrayList<>();

        listAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                groceries);

        listViewGroceries.setAdapter(listAdapter);
    }

    public void editGrocery() {
        GroceryItem grocery = groceries.get(selectedPosition);
        FormActivity.editItem(this, grocery);
    }

    public void removeGrocery() {
        groceries.remove(selectedPosition);
        listAdapter.notifyDataSetChanged();
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
                GroceryItem groceryItem = groceries.get(selectedPosition);
                groceryItem.setItemName(itemName);
                groceryItem.setItemBrand(itemBrand);
                groceryItem.setPackingType(packingType);
                groceryItem.setAmountInThePackage(amountInThePackage);
                groceryItem.setUnitOfMeasurement(unitOfMeasurement);
                groceryItem.setCategory(category);
                groceryItem.setBasicItem(basicItem);

                selectedPosition = -1;
            } else {
                groceries.add(new GroceryItem(
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