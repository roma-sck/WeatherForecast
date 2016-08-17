package kultprosvet.com.wheatherforecast.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import kultprosvet.com.wheatherforecast.R;
import kultprosvet.com.wheatherforecast.models.ForecastItem;
import kultprosvet.com.wheatherforecast.databinding.RowForecastBinding;
import kultprosvet.com.wheatherforecast.utils.WeatherIconSwitcher;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.Holder> {
    private List<ForecastItem> mItems;
    private int mIconSet;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_forecast, parent, false);
        return new ForecastAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setItem(mItems.get(position), mIconSet);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public ForecastAdapter setItems(List<ForecastItem> items, int iconSetId) {
        mItems = items;
        mIconSet = iconSetId;
        return this;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private ForecastItem mItem;
        private RowForecastBinding mRowBinding;
        private Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mRowBinding = DataBindingUtil.bind(itemView);
        }

        public ForecastItem getItem() {
            return mItem;
        }

        public Holder setItem(ForecastItem item, int iconSetId) {
            mItem = item;
            mRowBinding.setItem(item);
            getRowIcon(item.getWeather().get(0).getMain(), iconSetId);
            return this;
        }

        private void getRowIcon(String weatherMainStatus, int iconSetId) {
            int size = WeatherIconSwitcher.getIconSize(mContext);
            int icon = WeatherIconSwitcher.switchIcon(weatherMainStatus, iconSetId);
            Picasso.with(mContext).load(icon)
                    .resize(size, size)
                    .centerInside()
                    .into(mRowBinding.rowIcon);
        }
    }
}
