package com.example.pm2e10634;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pm2e10634.tablas.Paises;
import com.example.pm2e10634.transacciones.Transacciones;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   SQLiteConexion conexion;
    EditText nombre,telefono,nota;
    Spinner comboPais;
    ArrayList<Paises> lista;
    ArrayList<String> ArregloPaises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conexion = new SQLiteConexion(this, Transacciones.NameDateBase,null,1);
        Button btn = (Button)findViewById(R.id.btnGuardar);
        nombre = (EditText)findViewById(R.id.txtNombre);
        telefono = (EditText)findViewById(R.id.txtNumero);
        nota = (EditText)findViewById(R.id.txtNota);
        comboPais = (Spinner)findViewById(R.id.spPais);


        ObtenerListaPaises();
        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,ArregloPaises);
        comboPais.setAdapter(adp);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgregarContacto();
            }
        });
    }

    private void ObtenerListaPaises() {
        SQLiteDatabase db = conexion.getReadableDatabase();
        lista = new ArrayList<Paises>();
        Paises listPaises = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + Transacciones.tablapaises, null);

        while(cursor.moveToNext()){
            listPaises = new Paises();
            listPaises.setId(cursor.getInt(0));
            listPaises.setPais(cursor.getString(1));
            listPaises.setCodigo(cursor.getString(2));
            lista.add(listPaises);
        }
        fillCombo();
    }

    private void fillCombo() {
        ArregloPaises = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++){
            ArregloPaises.add(lista.get(i).getPais() + " - "
                    +lista.get(i).getCodigo());
        }
    }

    private void AgregarContacto() {
        try {

            if (nombre.getText().toString().isEmpty() || telefono.getText().toString().isEmpty()
                    || nota.getText().toString().isEmpty()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Alerta Examen");
                builder.setMessage("No Puede dejar campos Vacios");
                builder.setPositiveButton("Aceptar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                //String pais = comboPais.getSelectedItem().toString();
                int posicion = (int) comboPais.getSelectedItemId();

                String pais = lista.get(posicion).getPais();
                String codigo = lista.get(posicion).getCodigo();

                SQLiteConexion conexion = new SQLiteConexion( this, Transacciones.NameDateBase, null, 1 );
                SQLiteDatabase db = conexion.getWritableDatabase();
                ContentValues valores = new ContentValues();
                valores.put(Transacciones.pais, pais);
                valores.put(Transacciones.nombre, nombre.getText().toString());
                valores.put(Transacciones.telefono, codigo+telefono.getText().toString());
                valores.put(Transacciones.nota, nota.getText().toString());
                valores.put(Transacciones.idSpinner, posicion);

                Long resultado = db.insert(Transacciones.tablacontactos,Transacciones.id, valores);
                //Toast
                Toast.makeText(getApplicationContext(), "Registro ingresado: " + resultado.toString(),Toast.LENGTH_LONG).show();
                db.close();

                ClearScreen();
            }


        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Ha ocurrido un error al guardar",Toast.LENGTH_LONG).show();
        }

    }

    private void ClearScreen() {
        comboPais.setSelection(0);
        nombre.setText("");
        telefono.setText("");
        nota.setText("");
    }



    public void ClickConsultarDatos(View view) {
        Intent intent = new Intent(this,ConsultaActivity.class );
        startActivity(intent);
    }

    public void clickAgregarPais(View view) {
        Intent intent = new Intent(this,PaisesActivity.class );
        startActivity(intent);
    }

    public  void  buscarI(View view)
    {

        Intent intent = new Intent(this,BuscarActivity.class );
        startActivity(intent);

    }

}