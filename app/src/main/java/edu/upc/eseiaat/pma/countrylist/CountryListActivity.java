package edu.upc.eseiaat.pma.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    private ArrayList<String> country_list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        String[] countries = getResources().getStringArray(R.array.countries);
        country_list = new ArrayList<>(Arrays.asList(countries));

        ListView list = (ListView) findViewById(R.id.countrylist);
        // Un listView necessita sempre d'un adaptador per gestionar els items d'aquesta. Per
        //estalviar recursos diposem d'un nombre limitat d'items i al fer scroll down es reutilitzen
        //els items existents però cambiant el seu contingut.
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list);
                // 'this' és un punter a aquesta activitat
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id){
                String msg = getString(R.string.chosen);
                Toast.makeText( CountryListActivity.this,
                                String.format("%s '%s'", msg , country_list.get(pos)),
                                Toast.LENGTH_SHORT).show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id){
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.confirm);
                String msg = getResources().getString(R.string.confirm_message);
                builder.setMessage(msg + " " + country_list.get(pos)+ "?");
                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        country_list.remove(pos);
                        adapter.notifyDataSetChanged(); // notifiquem a l'adaptador que hem esborrat un element.
                    }
                });
                builder.setNegativeButton(android.R.string.cancel,null); //android té traduit el recurs cancel en tots els idiomes
                //null indica que no volem un listener ja que el boto no ha de fer cap canvi.
                builder.create().show();

                return true; // retornant true estem diguent que s'ha realitzant un long click i no s'
                //aplica el codi per al click normal.
            }

        });



    }
}
