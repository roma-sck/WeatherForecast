package kultprosvet.com.wheatherforecast.ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.databinding.RowCityBinding;
import kultprosvet.com.wheatherforecast.db.CityDb;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.Holder> {
    private List<CityDb> mItems;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_city, parent, false);
        return new CityListAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        holder.setItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public CityListAdapter setItems(List<CityDb> items) {
        mItems = items;
        return this;
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private CityDb mItem;
        private RowCityBinding mBinding;

        public Holder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public CityDb getItem() {
            return mItem;
        }

        public Holder setItem(CityDb item) {
            mItem = item;
            mBinding.setCity(item);
            return this;
        }
    }
}
