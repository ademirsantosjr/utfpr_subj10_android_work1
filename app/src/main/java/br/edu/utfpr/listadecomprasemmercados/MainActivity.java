package br.edu.utfpr.listadecomprasemmercados;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNomeDoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNomeDoItem = findViewById(R.id.editTextNomeDoItem);
    }

    public void limparCampos(View view) {
        editTextNomeDoItem.setText(null);

        editTextNomeDoItem.requestFocus();

        Toast.makeText(this,
                R.string.todos_os_campos_foram_limpos,
                Toast.LENGTH_LONG).show();
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
                nomeDoItem.trim(), Toast.LENGTH_LONG).show();
    }
}