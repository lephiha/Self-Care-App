package com.lph.selfcareapp.menu.Chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lph.selfcareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageButton buttonSend;
    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    private String url_get_messages = "http://192.168.0.107/selfcare/get_massage.php";
    private String url_send_message = "http://192.168.0.107/selfcare/send_massage.php";
    private String username = "doctor";  // Thay đổi theo người dùng
    private String receiver = "patient";  // Thay đổi theo người dùng

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerViewMessages = findViewById(R.id.chatRecyclerView);
        editTextMessage = findViewById(R.id.edit_text_message);
        buttonSend = findViewById(R.id.send_button);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewMessages.setAdapter(messageAdapter);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // Lấy tin nhắn từ server
        getMessages();
    }

    private void getMessages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_get_messages,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Chuyển đổi phản hồi thành JSONObject trước
                            JSONObject jsonResponse = new JSONObject(response);

                            // Kiểm tra thành công
                            if (jsonResponse.getInt("success") == 1) {
                                // Nếu thành công, lấy mảng tin nhắn
                                JSONArray jsonArray = jsonResponse.getJSONArray("data");  // Thay "data" bằng tên trường thực tế nếu khác
                                messageList.clear();  // Xóa danh sách trước khi thêm mới
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String sender = jsonObject.getString("sender");
                                    String message = jsonObject.getString("message");
                                    String timestamp = jsonObject.getString("timestamp");

                                    messageList.add(new Message(sender, message, timestamp));
                                }
                            } else {
                                // Xử lý thông báo lỗi
                                String message = jsonResponse.getString("message");
                                Log.e("ChatActivity", message); // Log lỗi hoặc hiển thị cho người dùng
                            }
                            messageAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sender", username);
                params.put("receiver", receiver);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void sendMessage() {
        final String message = editTextMessage.getText().toString().trim();
        if (message.isEmpty()) {
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_send_message,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        messageList.add(new Message(username, message, "now"));  // Thay đổi timestamp nếu cần
                        messageAdapter.notifyDataSetChanged();
                        editTextMessage.setText("");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sender", username);
                params.put("receiver", receiver);
                params.put("message", message);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
