package com.example.proyectico;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectico.Adaptadores.ListaPersonasAdapter;
import com.example.proyectico.Models.Oferta;

import com.example.proyectico.Models.Persona;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class CrudOfertas extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private ArrayList<Oferta> listaOfertas = new ArrayList<>();

    ListaPersonasAdapter listaPersonasAdapter;
    LinearLayout linearLayoutEditar;
    ListView listViewPersonas;
    EditText txt_titulo,txt_nombreEmpresa,txt_salario,txt_descripcion,txt_requisitos, txt_otros;
    Button btn_Cancelar;
    Oferta ofertaSeleccionada;
    ScrollView scrollView;
    //firebase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_ofertas);

        txt_titulo = findViewById(R.id.txt_Titulo);
        txt_descripcion = findViewById(R.id.txt_Descripcion);
        txt_nombreEmpresa = findViewById(R.id.txt_NombreEmpresa);
        txt_salario = findViewById(R.id.txt_Salario);
        txt_requisitos = findViewById(R.id.txt_Requisitos);
        txt_otros = findViewById(R.id.txt_Otros);

        btn_Cancelar = findViewById(R.id.btn_Cancelar);
        listViewPersonas = findViewById(R.id.list_ofertas);
        linearLayoutEditar = findViewById(R.id.crudOfertas);



        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                ofertaSeleccionada = (Oferta) parent.getItemAtPosition(position);
                txt_titulo.setText(ofertaSeleccionada.getTitulo());
                txt_descripcion.setText(ofertaSeleccionada.getDescrpcion());
                txt_nombreEmpresa.setText(ofertaSeleccionada.getNombreEmpresa());
                txt_salario.setText(ofertaSeleccionada.getSalario());
                txt_otros.setText(ofertaSeleccionada.getOtros());
                txt_requisitos.setText(ofertaSeleccionada.getRequisitos());
                linearLayoutEditar.setVisibility(View.VISIBLE);

            }
        });
        btn_Cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutEditar.setVisibility(View.GONE);
                ofertaSeleccionada = null;
            }
        });

        inicializarFirebase();
        listarPersonas();

    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarPersonas(){
        databaseReference.child("Ofertas").orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaOfertas.clear();
                for(DataSnapshot objSnaptshot : dataSnapshot.getChildren()){
                    Oferta p = objSnaptshot.getValue(Oferta.class);
                    listaOfertas.add(p);
                }
                listaPersonasAdapter = new ListaPersonasAdapter(CrudOfertas.this,listaOfertas);

                //arrayAdapterPersona = new ArrayAdapter<Persona>(CrudOfertas.this, android.R.layout.simple_list_item_1, listaPersonas);

                listViewPersonas.setAdapter(listaPersonasAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String titulo = txt_titulo.getText().toString();
        String nombreEmpresa = txt_nombreEmpresa.getText().toString();
        String salario = txt_salario.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String requisitos = txt_requisitos.getText().toString();
        String otros = txt_otros.getText().toString();

        switch(item.getItemId()){
            case R.id.btn_menuAgregar:
                insertar();
                break;
            case R.id.btn_menuGuardar:
                if(ofertaSeleccionada != null){
                    if(validarInputs()==false){
                        Oferta p= new Oferta();
                        p.setIdOferta(ofertaSeleccionada.getIdOferta());
                        p.setTitulo(titulo);
                        p.setDescrpcion(descripcion);
                        p.setSalario(salario);
                        p.setNombreEmpresa(nombreEmpresa);
                        p.setOtros(otros);
                        p.setRequisitos(requisitos);

                        p.setFecharegistro(ofertaSeleccionada.getFecharegistro());
                        p.setTimestamp(ofertaSeleccionada.getTimestamp());
                        databaseReference.child("Ofertas").child(p.getIdOferta()).setValue(p);
                        Toast.makeText(CrudOfertas.this, "Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                        linearLayoutEditar.setVisibility(View.GONE);
                        ofertaSeleccionada = null;
                    }
                }else{
                    Toast.makeText(CrudOfertas.this, "Seleccione Una Oferta", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_menuEliminar:
                if(ofertaSeleccionada != null){
                    Oferta p2 = new Oferta();
                    p2.setIdOferta(ofertaSeleccionada.getIdOferta());
                    databaseReference.child("Ofertas").child(p2.getIdOferta()).removeValue();
                    linearLayoutEditar.setVisibility(View.GONE);
                    ofertaSeleccionada = null;
                    Toast.makeText(CrudOfertas.this, "Eliminada Correctamente", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(CrudOfertas.this, "Seleccione Una Oferta", Toast.LENGTH_SHORT).show();

                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertar(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(CrudOfertas.this);
        View mView = getLayoutInflater().inflate(R.layout.agregar_oferta,null);
        Button btn_agregar = (Button) mView.findViewById(R.id.btn_Agregar);
        final EditText txt_titulo = (EditText) mView.findViewById(R.id.txt_Titulo);
        final EditText txt_nombreEmpresa = (EditText) mView.findViewById(R.id.txt_NombreEmpresa);
        final EditText txt_salario = (EditText) mView.findViewById(R.id.txt_Salario);
        final EditText txt_requisitos = (EditText) mView.findViewById(R.id.txt_Requisitos);
        final EditText txt_descripcion = (EditText) mView.findViewById(R.id.txt_Descripcion);
        final EditText txt_otros = (EditText) mView.findViewById(R.id.txt_Otros);



        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = txt_titulo.getText().toString();
                String nombreEmpresa = txt_nombreEmpresa.getText().toString();
                String salario = txt_salario.getText().toString();
                String descripcion = txt_descripcion.getText().toString();
                String requisitos = txt_requisitos.getText().toString();
                String otros = txt_otros.getText().toString();

                if(titulo.isEmpty() || titulo.length()<3){
                    showError(txt_titulo,"Nombre Incompleto");
                }else if(salario.isEmpty() || salario.length()<5){
                    showError(txt_salario,"Salario completo");
                }else if(nombreEmpresa.isEmpty() || nombreEmpresa.length()<3){
                    showError(txt_nombreEmpresa,"Nombre incompleto");
                }else if(descripcion.isEmpty() || descripcion.length()<10){
                    showError(txt_descripcion,"Descripcion incompleta");
                } else if(requisitos.isEmpty() || requisitos.length()<10){
                    showError(txt_requisitos,"requisitos incompletos");
                }else if(otros.isEmpty() || otros.length()<5){
                    otros = "N/A";
                }else{
                    Oferta p = new Oferta();
                    p.setIdOferta(UUID.randomUUID().toString());
                    //p.setIdOferta(ofertaSeleccionada.getIdOferta());
                    p.setTitulo(titulo);
                    p.setDescrpcion(descripcion);
                    p.setSalario(salario);
                    p.setNombreEmpresa(nombreEmpresa);
                    p.setOtros(otros);
                    p.setRequisitos(requisitos);

                    p.setFecharegistro(getFechaNormal(getFechaMilisegundos()));
                    p.setTimestamp(getFechaMilisegundos()*-1);
                    databaseReference.child("Ofertas").child(p.getIdOferta()).setValue(p);
                    Toast.makeText(
                            CrudOfertas.this,
                            "Registrado correctamente",
                            Toast.LENGTH_SHORT).show();
                     dialog.dismiss();
                }
            }
        });
    }

    public void showError(EditText input, String s){
        input.requestFocus();
        input.setError(s);
    }
    public long getFechaMilisegundos(){
        Calendar calendar = Calendar.getInstance();
        long tiempounix = calendar.getTimeInMillis();
        return tiempounix;
    }
    public String getFechaNormal(Long fechamilisegundos){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));

        String fecha = sdf.format(fechamilisegundos);
        return fecha;
    }
    public boolean validarInputs() {
        String titulo = txt_titulo.getText().toString();
        String nombreEmpresa = txt_nombreEmpresa.getText().toString();
        String salario = txt_salario.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String requisitos = txt_requisitos.getText().toString();
        String otros = txt_otros.getText().toString();

        if(titulo.isEmpty() || titulo.length()<3){
            showError(txt_titulo,"Nombre Incompleto");
            return true;
        }else if(salario.isEmpty() || salario.length()<5){
            showError(txt_salario,"Salario completo");
            return true;
        }else if(nombreEmpresa.isEmpty() || nombreEmpresa.length()<3){
            showError(txt_nombreEmpresa,"Nombre incompleto");
            return true;
        }else if(descripcion.isEmpty() || descripcion.length()<10){
            showError(txt_descripcion,"Descripcion incompleta");
            return true;
        } else if(requisitos.isEmpty() || requisitos.length()<5){
            showError(txt_requisitos,"requisitos incompletos");
            return true;
        }else {
            return false;
        }
    }


}