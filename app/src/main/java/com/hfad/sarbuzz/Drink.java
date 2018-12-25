package com.hfad.sarbuzz;
import com.example.fedor.starbuzz.R;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    public static final Drink[] drinks={
    new Drink("Latte","A cup of espresso shots with steamed milk", R.drawable.latte),
    new Drink("Cappucchino","Espresso,hot milk,and a steamed milk foam",R.drawable.cappuccino),
    new Drink("Filter","Highest quality beans roasted and brewed fresh",R.drawable.filter)
    };

    private Drink (String name ,String description ,int imageResourceId){
        this.name=name;
        this.description=description;
        this.imageResourceId=imageResourceId;
    }
    public String getDescription(){
        return description;
    }
    public String getName(){
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
    public String toString(){
        return this.name;
    }
}
