package com.uca.unicapi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uca.unicapi.R;
import com.uca.unicapi.models.UniversityModel;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Stephane on 05/03/2018.
 */

public class UniversityFromDatabaseAdapter extends RecyclerView.Adapter<UniversityFromDatabaseAdapter.ViewHolder> {
    private RealmResults<UniversityModel> mDataset;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView uniname;
        public TextView uniphone;
        public TextView uniaddress;
        public TextView unidescription;
        public TextView unidepartment;
        public LinearLayout item;
        public Context context;
        public ViewHolder(View view, Context _context) {
            super(view);
            uniname = view.findViewById(R.id.uniname);
            uniphone = view.findViewById(R.id.uniphone);
            uniaddress = view.findViewById(R.id.uniaddress);
            unidescription = view.findViewById(R.id.unidescription);
            unidepartment =view.findViewById(R.id.unidepartment);
            item = view.findViewById(R.id.item);
            context = _context;
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public UniversityFromDatabaseAdapter(RealmResults<UniversityModel> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public UniversityFromDatabaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_university, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new UniversityFromDatabaseAdapter.ViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(UniversityFromDatabaseAdapter.ViewHolder holder, final int position) {
        final UniversityModel universityModel = mDataset.get(position);

        Log.i("name", universityModel.getUniname());
        Log.i("address", universityModel.getUniaddress());
        Log.i("phone", universityModel.getUniphone());
        Log.i("description", universityModel.getUnidescription());
        Log.i("department", universityModel.getUnidepartment());


        holder.uniname.setText(universityModel.getUniname());
        holder.uniaddress.setText(universityModel.getUniaddress());
        holder.uniphone.setText(universityModel.getUniphone());
        holder.unidescription.setText(universityModel.getUnidescription());
        holder.unidepartment.setText(universityModel.getUnidepartment());

        /*LONG PRESS DELETE
        holder.item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new MaterialDialog.Builder(holder.context)
                        .content("Desea borrar este registro.")
                        .positiveText("Borrar")
                        .negativeText("No")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                remoteItemFromDatabase(mDataset.get(position));
                            }
                        })
                        .show();
                return true;
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
