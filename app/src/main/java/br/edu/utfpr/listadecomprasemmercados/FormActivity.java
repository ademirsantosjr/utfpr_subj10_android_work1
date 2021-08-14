package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormActivity extends AppCompatActivity {

    public static final String MODE = "MODE";
    public static final String ITEM_NAME = "ITEM_NAME";
    public static final String ITEM_BRAND = "ITEM_BRAND";
    public static final String PACKING_TYPE = "PACKING_TYPE";
    public static final String AMOUNT_IN_THE_PACKAGE = "AMOUNT_IN_THE_PACKAGE";
    public static final String UNIT_OF_MEASUREMENT = "UNIT_OF_MEASUREMENT";
    public static final String CATEGORY = "CATEGORY";
    public static final String IS_BASIC_ITEM = "BASIC_ITEM";

    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;

    private EditText editTextItemName;
    private EditText editTextItemBrand;
    private EditText editTextPackingType;
    private EditText editTextAmountInThePackage;
    private Spinner spinnerUnitOfMeasurement;
    private RadioGroup radioGroupCategory;
    private CheckBox checkBoxBasicItem;

    private int mode;

    private String currentItemName;
    private String currentItemBrand;
    private String currentPackingType;
    private String currentAmountInThePackage;
    private String currentUnitOfMeasurement;
    private String currentCategory;
    private boolean currentBasicItem;

    private static List<String> listOfUnits;

    public static void addNewItem(AppCompatActivity activity) {
        Intent intent = new Intent(activity, FormActivity.class);

        intent.putExtra(MODE, NEW_ITEM);

        activity.startActivityForResult(intent, NEW_ITEM);
    }

    public static void editItem(AppCompatActivity activity, GroceryItem groceryItem) {
        Intent intent = new Intent(activity, FormActivity.class);

        intent.putExtra(MODE, EDIT_ITEM);
        intent.putExtra(ITEM_NAME, groceryItem.getItemName());
        intent.putExtra(ITEM_BRAND, groceryItem.getItemBrand());
        intent.putExtra(PACKING_TYPE, groceryItem.getPackingType());
        intent.putExtra(AMOUNT_IN_THE_PACKAGE, String.valueOf((int)groceryItem.getAmountInThePackage()));
        intent.putExtra(UNIT_OF_MEASUREMENT, groceryItem.getUnitOfMeasurement());
        intent.putExtra(CATEGORY, groceryItem.getCategory());
        intent.putExtra(IS_BASIC_ITEM, groceryItem.isBasicItem());

        activity.startActivityForResult(intent, EDIT_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemBrand = findViewById(R.id.editTextBrand);
        editTextPackingType = findViewById(R.id.editTextPackingType);
        editTextAmountInThePackage = findViewById(R.id.editTextAmountInThePackage);

        spinnerUnitOfMeasurement = findViewById(R.id.spinnerUnitOfMeasurement);
        radioGroupCategory = findViewById(R.id.radioGroupCategory);
        checkBoxBasicItem = findViewById(R.id.checkBoxBasicItem);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        populateUnitOfMeasurementList();

        if (bundle != null) {
            mode = bundle.getInt(MODE, NEW_ITEM);

            if (mode == NEW_ITEM) {
                setTitle(getString(R.string.add_new_item));
            } else {
                currentItemName = bundle.getString(ITEM_NAME);
                currentItemBrand = bundle.getString(ITEM_BRAND);
                currentPackingType = bundle.getString(PACKING_TYPE);
                currentAmountInThePackage = bundle.getString(AMOUNT_IN_THE_PACKAGE);
                currentUnitOfMeasurement = bundle.getString(UNIT_OF_MEASUREMENT);
                currentCategory = bundle.getString(CATEGORY);
                currentBasicItem = bundle.getBoolean(IS_BASIC_ITEM);

                editTextItemName.setText(currentItemName);
                editTextItemBrand.setText(currentItemBrand);
                editTextPackingType.setText(currentPackingType);
                editTextAmountInThePackage.setText(currentAmountInThePackage);

                int unitPosition = 0;
                for(String unit : listOfUnits) {
                    if (unit.equals(currentUnitOfMeasurement)) {
                        unitPosition = listOfUnits.indexOf(unit);
                    }
                }
                spinnerUnitOfMeasurement.setSelection(unitPosition);


                int radioButtonToCheckId =
                        radioGroupCategory.getChildAt(getIndex(currentCategory)).getId();

                radioGroupCategory.check(radioButtonToCheckId);
                checkBoxBasicItem.setChecked(currentBasicItem);

                setTitle(getString(R.string.edit_item));
            }
        }

        editTextItemName.requestFocus();
    }

    public void eraseFields(View view) {
        editTextItemName.setText(null);
        editTextItemBrand.setText(null);
        editTextPackingType.setText(null);
        editTextAmountInThePackage.setText(null);

        radioGroupCategory.clearCheck();
        checkBoxBasicItem.setChecked(false);

        editTextItemName.requestFocus();

        Toast.makeText(this,
                R.string.all_fields_were_erased,
                Toast.LENGTH_LONG).show();
    }

    private void populateUnitOfMeasurementList() {
        listOfUnits = new ArrayList<>();

        listOfUnits.add(getString(R.string.unidade));
        listOfUnits.add(getString(R.string.miligrama));
        listOfUnits.add(getString(R.string.grama));
        listOfUnits.add(getString(R.string.quilograma));
        listOfUnits.add(getString(R.string.mililitro));
        listOfUnits.add(getString(R.string.litro));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listOfUnits);

        spinnerUnitOfMeasurement.setAdapter(adapter);
    }

    public void save(View view) {
        String itemName = editTextItemName.getText().toString();
        String itemBrand = editTextItemBrand.getText().toString();
        String packingType = editTextPackingType.getText().toString().toLowerCase();

        String amountInThePackage = editTextAmountInThePackage.getText().toString();

        String unitOfMeasurement = spinnerUnitOfMeasurement.getSelectedItem().toString();
        String category = getCheckedCategory();
        boolean basicItem = false;

        if (itemName == null || itemName.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.item_name_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            editTextItemName.requestFocus();
            return;
        }

        if (itemBrand == null || itemBrand.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.item_brand_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            editTextItemBrand.requestFocus();
            return;
        }

        if (packingType == null || packingType.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.packing_type_cannot_be_empty,
                    Toast.LENGTH_LONG).show();
            editTextPackingType.requestFocus();
            return;
        }

        if (amountInThePackage == null ||
                amountInThePackage.trim().isEmpty() ||
                amountInThePackage.equals("0")) {

            Toast.makeText(this,
                    R.string.quantity_in_the_package_cannot_be_zero,
                    Toast.LENGTH_LONG).show();
            editTextAmountInThePackage.requestFocus();
            return;
        }

        if (amountInThePackage.contains(".") || amountInThePackage.contains(",")) {
            Toast.makeText(this,
                    R.string.only_round_number_allowed,
                    Toast.LENGTH_LONG).show();
            editTextAmountInThePackage.requestFocus();
            return;
        }

        if (category == null) {
            Toast.makeText(this,
                    R.string.category_must_be_informed,
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (checkBoxBasicItem.isChecked()) {
            basicItem = true;
        }

        Intent intent = new Intent();
        intent.putExtra(ITEM_NAME, itemName);
        intent.putExtra(ITEM_BRAND , itemBrand);
        intent.putExtra(PACKING_TYPE, packingType);
        intent.putExtra(AMOUNT_IN_THE_PACKAGE, amountInThePackage);
        intent.putExtra(UNIT_OF_MEASUREMENT, unitOfMeasurement);
        intent.putExtra(CATEGORY, category);
        intent.putExtra(IS_BASIC_ITEM, basicItem);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    private String getCheckedCategory() {
        String category = null;

        switch (radioGroupCategory.getCheckedRadioButtonId()) {
            case R.id.radioButtonFood:
                category = getString(R.string.food);
                break;
            case R.id.radioButtonUtensils:
                category = getString(R.string.utensil);
                break;
            case R.id.radioButtonOther:
                category = getString(R.string.other);
        }

        return category;
    }

    private int getIndex(String currentCategory) {
        int size = radioGroupCategory.getChildCount();
        int radioButtonIndex = size - 1;

        for (int i = 0; i < size; i++) {
            if (((RadioButton) radioGroupCategory.getChildAt(i))
                                                 .getText()
                                                 .equals(currentCategory)) {
                radioButtonIndex = i;
            }
        }

        return radioButtonIndex;
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }
}