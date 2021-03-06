package br.edu.utfpr.listadecomprasemmercados;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.edu.utfpr.listadecomprasemmercados.model.Grocery;
import br.edu.utfpr.listadecomprasemmercados.persistence.GroceriesDatabase;

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

    public static void addNewItem(AppCompatActivity activity) {
        Intent intent = new Intent(activity, FormActivity.class);

        intent.putExtra(MODE, NEW_ITEM);

        activity.startActivityForResult(intent, NEW_ITEM);
    }

    public static void editGrocery(AppCompatActivity activity, int selectedPosition) {
        GroceriesDatabase groceriesDatabase = GroceriesDatabase.getDatabase(activity);

        Grocery grocery = groceriesDatabase.groceryDao().queryForId(selectedPosition);

        Intent intent = new Intent(activity, FormActivity.class);

        intent.putExtra(MODE, EDIT_ITEM);
        intent.putExtra(ITEM_NAME, grocery.getItemName());
        intent.putExtra(ITEM_BRAND, grocery.getItemBrand());
        intent.putExtra(PACKING_TYPE, grocery.getPackingType());

        intent.putExtra(
                AMOUNT_IN_THE_PACKAGE, String.valueOf((int) grocery.getAmountInThePackage())
        );

        intent.putExtra(UNIT_OF_MEASUREMENT, grocery.getUnitOfMeasurement());
        intent.putExtra(CATEGORY, grocery.getCategory());
        intent.putExtra(IS_BASIC_ITEM, grocery.isBasicItem());

        activity.startActivityForResult(intent, EDIT_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
                setTitle(getString(R.string.edit));
                populateFieldsWithCurrentData(bundle);
            }
        }

        editTextItemName.requestFocus();
    }

    public void eraseFields() {
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
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                                                R.array.unitsofmeasurement,
                                                android.R.layout.simple_list_item_1);

        spinnerUnitOfMeasurement.setAdapter(adapter);
    }

    public void save() {
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

    private void populateFieldsWithCurrentData(Bundle bundle) {
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

        spinnerUnitOfMeasurement.setSelection(findUnitOfMeasurementIndex());

        int radioButtonToCheckId =
                radioGroupCategory.getChildAt(findCategoryIndex(currentCategory)).getId();

        radioGroupCategory.check(radioButtonToCheckId);

        checkBoxBasicItem.setChecked(currentBasicItem);
    }

    private int findUnitOfMeasurementIndex() {
        int unitPosition = 0;

        List<String> listOfUnits =
                Arrays.asList(getResources().getStringArray(R.array.unitsofmeasurement));

        for(String unit : listOfUnits) {
            if (unit.equals(currentUnitOfMeasurement)) {
                unitPosition = listOfUnits.indexOf(unit);
            }
        }

        return unitPosition;
    }

    private int findCategoryIndex(String currentCategory) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menuItemSave:
                save();
                return true;

            case R.id.menuItemClearFields:
                eraseFields();
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}