package com.lph.selfcareapp.tuvanOnline;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lph.selfcareapp.R;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> implements Filterable {

    private List<String> questionList;
    private List<String> questionListFull; // Danh sách đầy đủ để không bị mất dữ liệu khi lọc

    public QuestionAdapter(List<String> questionList) {
        this.questionList = questionList;
        this.questionListFull = new ArrayList<>(questionList); // Sao lưu danh sách gốc
    }

    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        String question = questionList.get(position);
        holder.questionTextView.setText(question);
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    // ViewHolder cho mỗi item trong danh sách
    public class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;

        public QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
        }
    }

    // Cài đặt chức năng Filter để tìm kiếm câu hỏi
    @Override
    public Filter getFilter() {
        return questionFilter;
    }

    private Filter questionFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(questionListFull); // Hiển thị toàn bộ câu hỏi nếu không có gì được tìm
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (String item : questionListFull) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        filteredList.add(item); // Thêm vào danh sách nếu có từ khoá trùng khớp
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            questionList.clear();
            questionList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

