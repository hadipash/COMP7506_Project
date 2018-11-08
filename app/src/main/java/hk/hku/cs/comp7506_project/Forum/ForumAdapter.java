package hk.hku.cs.comp7506_project.Forum;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import hk.hku.cs.comp7506_project.R;

public class ForumAdapter extends ArrayAdapter<String> {

    private AppCompatActivity context;
    private String[] web;
    private Integer[] imageId;

    public ForumAdapter (AppCompatActivity context, String[] web, Integer[] imageId) {
        super(context, R.layout.activity_forum_listview, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_forum_listview, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);
        //imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
