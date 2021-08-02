package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeDoItem;
    private EditText editTextMarca;
    private EditText editTextTipoDeEmbalagem;
    private EditText editTextQuantidadeNaEmbalagem;

    private Spinner spinnerUnidadesDeMedida;

    private RadioGroup radioGroupCategoria;

    private CheckBox checkBoxItemCestaBasica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeDoItem = findViewById(R.id.editTextNomeDoItem);
        editTextMarca = findViewById(R.id.editTextMarca);
        editTextTipoDeEmbalagem = findViewById(R.id.editTextTipoDeEmbalagem);
        editTextQuantidadeNaEmbalagem = findViewById(R.id.editTextQuantidadeNaEmbalagem);

        spinnerUnidadesDeMedida = findViewById(R.id.spinnerUnidadesDeMedida);
        radioGroupCategoria = findViewById(R.id.radioGroupCategoria);
        checkBoxItemCestaBasica = findViewById(R.id.checkBoxItemDeCestaBasica);

        popularUnidadesDeMedida();
    }

    public void limparCampos(View view) {
        editTextNomeDoItem.setText(null);
        editTextMarca.setText(null);
        editTextTipoDeEmbalagem.setText(null);
        editTextQuantidadeNaEmbalagem.setText(null);

        radioGroupCategoria.clearCheck();
        checkBoxItemCestaBasica.setChecked(false);

        editTextNomeDoItem.requestFocus();

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

        spinnerUnidadesDeMedida.setAdapter(adapter);
    }

    public void mostrarItemCadastrado(View view) {
        String nomeDoItem = editTextNomeDoItem.getText().toString();
        String marca = editTextMarca.getText().toString();
        String tipoDeEmbalagem = editTextTipoDeEmbalagem.getText().toString().toLowerCase();
        String quantidadeNaEmbalagem = editTextQuantidadeNaEmbalagem.getText().toString();

        String unidadeDeMedida = spinnerUnidadesDeMedida.getSelectedItem().toString();
        String categoria = getCategoria();
        String itemCestaBasica = "";

        if (nomeDoItem == null || nomeDoItem.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.nome_do_item_nao_pode_ser_vazio,
                    Toast.LENGTH_LONG).show();
            editTextNomeDoItem.requestFocus();
            return;
        }

        if (marca == null || marca.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.marca_do_item_nao_pode_ser_vazia,
                    Toast.LENGTH_LONG).show();
            editTextMarca.requestFocus();
            return;
        }

        if (tipoDeEmbalagem == null || tipoDeEmbalagem.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.tipo_de_embalagem_nao_pode_ser_vazio,
                    Toast.LENGTH_LONG).show();
            editTextTipoDeEmbalagem.requestFocus();
            return;
        }

        if (quantidadeNaEmbalagem == null || quantidadeNaEmbalagem.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.quantidade_na_embalagem_nao_pode_ser_vazia,
                    Toast.LENGTH_LONG).show();
            editTextQuantidadeNaEmbalagem.requestFocus();
            return;
        }

        if (categoria == null) {
            Toast.makeText(this,
                    R.string.categoria_deve_ser_selecionada,
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (checkBoxItemCestaBasica.isChecked()) {
            itemCestaBasica = "\n(" + getString(R.string.item_de_cesta_basica).trim() + ")";
        }

        Toast.makeText(this,
                categoria + ": " +
                        nomeDoItem.trim() + " " +
                        marca.trim() + ", " +
                        tipoDeEmbalagem.trim() + " " +
                        quantidadeNaEmbalagem.trim() +
                        unidadeDeMedida.trim() +
                        itemCestaBasica,
                Toast.LENGTH_LONG).show();
    }

    private String getCategoria() {
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