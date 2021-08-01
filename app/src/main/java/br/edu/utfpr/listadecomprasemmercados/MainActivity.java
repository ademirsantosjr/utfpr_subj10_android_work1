package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeDoItem;

    private Spinner spinnerUnidadesDeMedida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeDoItem = findViewById(R.id.editTextNomeDoItem);
        spinnerUnidadesDeMedida = findViewById(R.id.spinnerUnidadesDeMedida);

        popularUnidadesDeMedida();
    }

    public void limparCampos(View view) {
        editTextNomeDoItem.setText(null);

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

        if (nomeDoItem == null || nomeDoItem.trim().isEmpty()) {
            Toast.makeText(this,
                    R.string.nome_do_item_nao_pode_ser_vazio,
                    Toast.LENGTH_LONG).show();
            editTextNomeDoItem.requestFocus();
            return;
        }

        Toast.makeText(this,
                nomeDoItem.trim(),
                Toast.LENGTH_LONG).show();
    }
}