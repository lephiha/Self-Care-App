package com.lph.selfcareapp.menu.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lph.selfcareapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends AppCompatActivity {
    ListView listViewUsers;
    List<String> userList = new ArrayList<>();
    String url_get_users = "http://192.168.0.107/selfcare/get_users.php";  // Đường dẫn API để lấy danh sách người dùng

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_user);

        listViewUsers = findViewById(R.id.list_view_users);

        // Lấy danh sách người dùng từ server
        getUsers();

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUser = userList.get(position);
                Intent intent = new Intent(SelectUserActivity.this, ChatActivity.class);
                intent.putExtra("receiver", selectedUser); // Gửi người nhận đến ChatActivity
                startActivity(intent);
            }
        });
    }

    private void getUsers() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_get_users,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            userList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String username = jsonArray.getJSONObject(i).getString("username");
                                userList.add(username);
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(SelectUserActivity.this,
                                    android.R.layout.simple_list_item_1, userList);
                            listViewUsers.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
                Toast.makeText(SelectUserActivity.this, "Error retrieving users", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
