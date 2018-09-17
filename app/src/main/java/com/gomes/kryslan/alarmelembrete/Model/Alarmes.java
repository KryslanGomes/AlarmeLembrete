package com.gomes.kryslan.alarmelembrete.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Alarmes implements Parcelable {
    public Alarmes(){}

    private int id;
    private int hora;
    private int minuto;
    private String lembrete;
    private int ativado;

    //region GETs/SETs
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public int getHora() {return hora;}
    public void setHora(int hora) {this.hora = hora;}

    public int getMinuto() {return minuto;}
    public void setMinuto(int minuto) {this.minuto = minuto;}

    public String getLembrete() {return lembrete;}
    public void setLembrete(String lembrete) {this.lembrete = lembrete;}

    public int isAtivado() {return ativado;}
    public void setAtivado(int ativado) {this.ativado = ativado;}
    //endregion

    //region PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    public Alarmes(Parcel parcel){  //Equivalente ao "Set" normal mas como parcelable.
        setId(parcel.readInt());
        setHora(parcel.readInt());
        setMinuto(parcel.readInt());
        setLembrete(parcel.readString());
        setAtivado(parcel.readInt());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {  //Equivalente ao "Get" normal mas como parcelable.
        dest.writeInt(getId());
        dest.writeInt(getHora());
        dest.writeInt(getMinuto());
        dest.writeString(getLembrete());
        dest.writeInt(isAtivado());
    }

    public static final Parcelable.Creator<Alarmes> CREATOR = new Parcelable.Creator<Alarmes>(){
        @Override
        public Alarmes createFromParcel(Parcel source) {  //Chamar esta entidade para pegar os dados.
            return new Alarmes(source);
        }
        @Override
        public Alarmes[] newArray(int size) {
            return new Alarmes[size];
        }
    };
    //endregion
}
