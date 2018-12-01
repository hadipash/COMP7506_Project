package hk.hku.cs.comp7506_project.Forum;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import hk.hku.cs.comp7506_project.Forum.service.NewService;
import hk.hku.cs.comp7506_project.Forum.util.markdown.MarkdownProcessor;
import hk.hku.cs.comp7506_project.R;

public class NewActivity extends AppCompatActivity {
    Toolbar mToolbar;
    EditText mTitle;
    EditText mTag;
    EditText mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (EditText) findViewById(R.id.title);
        mTag = (EditText) findViewById(R.id.tag);
        mContent = (EditText) findViewById(R.id.content);
        if (mToolbar != null) {
            mToolbar.setTitle(R.string.title_activity_new);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            ImageButton imageButton = (ImageButton) mToolbar.findViewById(R.id.new_button);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mTitle.getText().toString().length() > 0 &&
                            mTag.getText().toString().replace("，",",").split(",").length > 0) {
                        MarkdownProcessor mMarkdownProcessor = new MarkdownProcessor();
                        String contentHTML = mMarkdownProcessor.markdown(mContent.getText().toString());
                        Intent intent = new Intent(NewActivity.this, NewService.class);
                        intent.putExtra("Title", mTitle.getText().toString());
                        intent.putExtra("Tag", mTag.getText().toString());
                        intent.putExtra("Content", contentHTML);
                        startService(intent);
                        onBackPressed();
                    }else{
                        Snackbar.make(view, getString(R.string.content_empty), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
