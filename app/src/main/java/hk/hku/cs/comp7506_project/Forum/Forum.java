package hk.hku.cs.comp7506_project.Forum;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.R;


public class Forum extends AppCompatActivity {

    ListView list;
    String[] web = {
            "how to learn stocks?",
            "how to solve question in PV",
            "which option should I buy",
            "why FRA and futures can hedge against another",
            "what is bond"
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        ForumAdapter listAdapter = new ForumAdapter(this, web, null);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(listAdapter);

    }
}
