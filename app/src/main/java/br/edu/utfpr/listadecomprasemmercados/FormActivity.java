package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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
    public static final String BASIC_ITEM = "BASIC_ITEM";

    public static final int NEW_ITEM = 1;
    public static final int EDIT_ITEM = 2;

    private EditText editTextItemName;
    private EditText editTextBrand;
    private EditText editTextPackingType;
    private EditText editTextAmountInThePackage;

    private Spinner spinnerUnitOfMeasurement;

    private RadioGroup radioGroupCategoria;

    private CheckBox checkBoxItemCestaBasica;

    private int mode;

    public static void addNewItem(AppCompatActivity activity) {
        Intent intent = new Intent(activity, FormActivity.class);

        intent.putExtra(MODE, NEW_ITEM);

        activity.startActivityForResult(intent, NEW_ITEM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTextItemName = findViewById(R.id.editTextNomeDoItem);
        editTextBrand = findViewById(R.id.editTextMarca);
        editTextPackingType = findViewById(R.id.editTextTipoDeEmbalagem);
        editTextAmountInThePackage = findViewById(R.id.editTextQuantidadeNaEmbalagem);

        spinnerUnitOfMeasurement = findViewById(R.id.spinnerUnidadesDeMedida);
        radioGroupCategoria = findViewById(R.id.radioGroupCategoria);
        checkBoxItemCestaBasica = findViewById(R.id.checkBoxItemDeCestaBasica);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            mode = bundle.getInt(MODE, NEW_ITEM);

            if (mode == NEW_ITEM) {
                setTitle(getString(R.string.add_new_item));
            } else {
                setTitle(getString(R.string.edit_item));
            }
        }

        editTextItemName.requestFocus();

        popularUnidadesDeMedida();
    }

    public void limparCampos(View view) {
        editTextItemName.setText(null);
        editTextBrand.setText(null);
        editTextPackingType.setText(null);
        editTextAmountInThePackage.setText(null);

        radioGroupCategoria.clearCheck();
        checkBoxItemCestaBasica.setChecked(false);

        editTextItemName.requestFocus();

        Toast.makeText(this,
                R.string.todos_os_campos_foram_limpos,
                Toast.LENGTH_LONG).show();
    }

    private void popularUnidadesDeMedida() {
        List<String> lista = new ArrayList<>();

        lista.add(getString(R.string.unidade));
        lista.add(getString(R.string.miligrama));
        lista.add(getString(R.string.grama));
        lista.add(getString(R.string.quilograma));
        lista.add(getString(R.string.mililitro));
        lista.add(getString(R.string.litro));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);

        spinnerUnitOfMeasurement.setAdapter(adapter);
    }

    public void mostrarItemCadastrado(View view) {
        String itemName = editTextItemName.getText().toString();
        String itemBrand = editTextBrand.getText().toString();
        String packingType = editTextPackingType.getText().toString().toLowerCase();

        String amountInThePackage = editTextAmountInThePackage.getText().toString();

        String unitOfMeasurement = spinnerUnitOfMeasurement.getSelectedItem().toString();
        String category = getCategory();
        String basicItem = "false";

        if (itemName == null || itemName.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.nome_do_item_nao_pode_ser_vazio,
                    Toast.LENGTH_LONG).show();
            editTextItemName.requestFocus();
            return;
        }

        if (itemBrand == null || itemBrand.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.marca_do_item_nao_pode_ser_vazia,
                    Toast.LENGTH_LONG).show();
            editTextBrand.requestFocus();
            return;
        }

        if (packingType == null || packingType.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.tipo_de_embalagem_nao_pode_ser_vazio,
                    Toast.LENGTH_LONG).show();
            editTextPackingType.requestFocus();
            return;
        }

        if (amountInThePackage == null ||
                amountInThePackage.trim().isEmpty() ||
                amountInThePackage.equals("0") ||
                amountInThePackage.startsWith(".")) {

            Toast.makeText(this,
                    R.string.quantity_in_the_package_cannot_be_zero,
                    Toast.LENGTH_LONG).show();
            editTextAmountInThePackage.requestFocus();
            return;
        }

        if (category == null) {
            Toast.makeText(this,
                    R.string.categoria_deve_ser_selecionada,
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (checkBoxItemCestaBasica.isChecked()) {
            basicItem = "true";
        }

        Intent intent = new Intent();
        intent.putExtra(ITEM_NAME, itemName);
        intent.putExtra(ITEM_BRAND , itemBrand);
        intent.putExtra(PACKING_TYPE, packingType);
        intent.putExtra(AMOUNT_IN_THE_PACKAGE, amountInThePackage);
        intent.putExtra(UNIT_OF_MEASUREMENT, unitOfMeasurement);
        intent.putExtra(BASIC_ITEM, basicItem);

        setResult(Activity.RESULT_OK, intent);

        finish();

        /*Toast.makeText(this,
                categoria + ": " +
                        nomeDoItem.trim() + " " +
                        marca.trim() + ", " +
                        tipoDeEmbalagem.trim() + " " +
                        quantidadeNaEmbalagem.trim() +
                        unidadeDeMedida.trim() +
                        itemCestaBasica,
                Toast.LENGTH_LONG).show();*/
    }

    private String getCategory() {
        String categoria = null;

        switch (radioGroupCategoria.getCheckedRadioButtonId()) {
            case R.id.radioButtonAlimento:
                categoria = getString(R.string.alimento);
                break;
            case R.id.radioButtonUtensilio:
                categoria = getString(R.string.utensilio);
                break;
            case R.id.radioButtonOutro:
                categoria = getString(R.string.outro);
        }

        return categoria;
    }

}