package com.iavariav.kbmonline.ui.atasan.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iavariav.kbmonline.R;
import com.iavariav.kbmonline.helper.Config;
import com.iavariav.kbmonline.model.UserModel;

import java.util.ArrayList;

public class DataUserAdapter extends RecyclerView.Adapter<DataUserAdapter.ViewHolder> {
    private String id;
    private Context context;
    private ArrayList<UserModel> userModels;

    public DataUserAdapter(Context context, ArrayList<UserModel> userModels) {
        this.context = context;
        this.userModels = userModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.INSTANCE.getSHARED_PREF_NAME(), Context.MODE_PRIVATE);
        id = sharedPreferences.getString(Config.INSTANCE.getSHARED_PREF_ID(), "");
        holder.tvNamaUser.setText(userModels.get(position).getNAMAUSER());
        holder.tvNik.setText(userModels.get(position).getNIKUSER());
        holder.tvRule.setText(userModels.get(position).getRULEUSER());

    }


    @Override
    public int getItemCount() {
        return userModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaUser;
        private TextView tvNik;
        private TextView tvRule;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaUser = itemView.findViewById(R.id.tv_nama_user);
            tvNik = itemView.findViewById(R.id.tv_nik);
            tvRule = itemView.findViewById(R.id.tv_rule);
        }
    }
}
