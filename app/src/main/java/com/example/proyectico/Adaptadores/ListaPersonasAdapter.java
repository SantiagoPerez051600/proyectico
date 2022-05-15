package com.example.proyectico.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.proyectico.Models.Oferta;
import com.example.proyectico.R;

import java.util.ArrayList;

public class ListaPersonasAdapter extends BaseAdapter {
    Context context;
    ArrayList<Oferta> ofertaData;
    LayoutInflater layoutInflater;
    Oferta ofertaModel;

    public ListaPersonasAdapter(Context context, ArrayList<Oferta> ofertaData) {
        this.context = context;
        this.ofertaData = ofertaData;
        layoutInflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return ofertaData.size();
    }

    @Override
    public Object getItem(int position) {
        return ofertaData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View rowView = convertView;
        if(rowView==null){
            rowView = layoutInflater.inflate(R.layout.lista_ofertas,
                    null,
                    true
                    );
        }

        TextView Titulo = rowView.findViewById(R.id.txt_Titulo);
        TextView Descripcion = rowView.findViewById(R.id.txt_Descripcion);
        TextView nombreEmpresa = rowView.findViewById(R.id.txt_NombreEmpresa);
        TextView salario = rowView.findViewById(R.id.txt_Salario);
        TextView Requisitos = rowView.findViewById(R.id.txt_Requisitos);
        TextView otros = rowView.findViewById(R.id.txt_Otros);

        ofertaModel = ofertaData.get(position);
        Titulo.setText(ofertaModel.getTitulo());
        Descripcion.setText(ofertaModel.getDescrpcion());
        nombreEmpresa.setText(ofertaModel.getNombreEmpresa());
        salario.setText(ofertaModel.getSalario());
        Requisitos.setText(ofertaModel.getRequisitos());
        otros.setText(ofertaModel.getOtros());

        return rowView;
    }
}
