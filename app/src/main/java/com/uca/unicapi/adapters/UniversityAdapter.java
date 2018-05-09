package com.uca.unicapi.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.uca.unicapi.R;
import com.uca.unicapi.models.UniversityModel;

import java.util.List;

/**
 * Created by Stephane on 04/25/2018.
 */

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder>{
    private List<UniversityModel> universities;

    public UniversityAdapter(List<UniversityModel> universities) {
        this.universities = universities;
    }

    @Override
    public UniversityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_university, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UniversityAdapter.ViewHolder holder, int position) {
        UniversityModel university = universities.get(position);
        holder.unidepartment.setText(university.getUnidepartment());
        holder.uniname.setText(university.getUniname());
        holder.uniaddress.setText(university.getUniaddress());
        holder.uniphone.setText(university.getUniphone());
        holder.unidescription.setText(university.getUnidescription());

    }

    @Override
    public int getItemCount() {
        return this.universities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView unidepartment;
        TextView uniname;
        TextView uniaddress;
        TextView uniphone;
        TextView unidescription;

        public ViewHolder(View itemView) {
            super(itemView);


            unidepartment = itemView.findViewById(R.id.unidepartment);
            uniname = itemView.findViewById(R.id.uniname);
            uniaddress = itemView.findViewById(R.id.uniaddress);
            uniphone = itemView.findViewById(R.id.uniphone);
            unidescription = itemView.findViewById(R.id.unidescription);
        }
    }
}
