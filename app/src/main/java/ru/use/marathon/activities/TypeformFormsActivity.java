package ru.use.marathon.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.use.marathon.R;
import ru.use.marathon.adapters.TypeformFormsAdapter;
import ru.use.marathon.models.typeform.Forms;
import ru.use.marathon.utils.ItemClickSupport;
import ru.use.marathon.utils.typeform.TypeformManager;

import static ru.use.marathon.Constants.DEBUG;

public class TypeformFormsActivity extends AbstractActivity {

    public static final String TAG = TypeformFormsActivity.class.getSimpleName();

    private Forms forms;

    @BindView(R.id.typeform_rv)
    RecyclerView recyclerView;
    @BindView(R.id.progressTypeforms)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typeform_forms);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));

        TypeformManager.getForms().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    forms = new Forms(response);
                    recyclerView.setAdapter(new TypeformFormsAdapter(forms));
                    progressBar.setVisibility(View.GONE);
                }else{
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                        Toast.makeText(TypeformFormsActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showInfoDialog("Ошибка", "Ошибка при загрузке со стороннего сервера. Сообщение: " + t.getMessage());
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent i = new Intent(TypeformFormsActivity.this,TypeformQuizActivity.class);
                i.putExtra("title",forms.itemTitle(position));
                i.putExtra("id",forms.itemId(position));
                startActivity(i);
                if(DEBUG)Toast.makeText(TypeformFormsActivity.this, "Data: " + forms.itemTitle(position)+"\n link: https://edway.typeform.com/to/" + forms.itemId(position), Toast.LENGTH_LONG).show();
            }
        });

    }


}
