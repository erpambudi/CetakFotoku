package com.ngodingyuk.cetakfotoku.ui.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ngodingyuk.cetakfotoku.R;
import com.ngodingyuk.cetakfotoku.adapter.PelangganAdapter;
import com.ngodingyuk.cetakfotoku.model.Foto;
import com.ngodingyuk.cetakfotoku.ui.DaftarHargaActivity;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment {

    public static final String DATA = "DATA";

    private List<Foto> listData;
    private PelangganAdapter pelangganAdapter;

    private DatabaseReference database;

    public RecyclerView rvData;
    private ProgressDialog loading;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_transaction, container, false);

        database = FirebaseDatabase.getInstance().getReference();

        rvData = root.findViewById(R.id.rv_pelanggan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvData.setLayoutManager(layoutManager);

        if (savedInstanceState == null){
            loading = ProgressDialog.show(
                    getActivity(),
                    null,
                    getResources().getString(R.string.loading),
                    true,
                    false
            );
            setData();
        }else {
            onActivityCreated(savedInstanceState);
        }

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void setData(){
        database.child("Pelanggan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listData = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : snapshot.getChildren()){

                    Foto foto = noteDataSnapshot.getValue(Foto.class);
                    foto.setId(noteDataSnapshot.getKey());

                    listData.add(foto);
                }

                pelangganAdapter = new PelangganAdapter(getActivity(), listData);
                rvData.setAdapter(pelangganAdapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getDetails()+" "+error.getMessage());
                loading.dismiss();
            }
        });
    }

    public void searchData(String str){
        String query = str.toLowerCase();

        database.child("Pelanggan")
                .orderByChild("namaPelanggan")
                .startAt(query)
                .endAt(query + "\uf8ff")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listData = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Foto foto = dataSnapshot.getValue(Foto.class);
                        foto.setId(dataSnapshot.getKey());

                        listData.add(foto);
                    }
                    pelangganAdapter = new PelangganAdapter(getActivity(), listData);
                    rvData.setAdapter(pelangganAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.change_setting) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.view_price_list) {
            Intent intent = new Intent(getActivity(), DaftarHargaActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DATA, new ArrayList<>(pelangganAdapter.getListPelanggan()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            listData = savedInstanceState.getParcelableArrayList(DATA);
            pelangganAdapter = new PelangganAdapter(getActivity(), listData);
            rvData.setAdapter(pelangganAdapter);
        }
    }
}