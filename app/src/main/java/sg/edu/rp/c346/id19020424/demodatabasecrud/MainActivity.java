package sg.edu.rp.c346.id19020424.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button edit,retrieve,insert;
    EditText etContent;
    ListView lv;
    ArrayList<Note> al;
    ArrayAdapter<Note> aa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit=findViewById(R.id.edit);
        retrieve=findViewById(R.id.retrieve);
        insert=findViewById(R.id.insert);
        lv=findViewById(R.id.lv);
        etContent=findViewById(R.id.enter);
        al = new ArrayList<Note>();
        aa = new ArrayAdapter<Note>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                // al.addAll(dbh.getAllNotes());
                String filterText = etContent.getText().toString().trim();
                if(filterText.length() == 0) {
                    al.addAll(dbh.getAllNotes(filterText));
                }
                else{
                    al.addAll(dbh.getAllNotes(filterText));
                }
                aa.notifyDataSetChanged();
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etContent.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertNote(data);

                if (inserted_id != -1){
                    al.clear();
                    al.addAll(dbh.getAllNotes());
                    aa.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });




        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note target = al.get(0);

                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", target);
                startActivity(i);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Note data = al.get(position);
                Intent i = new Intent(MainActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve.performClick();
    }
}