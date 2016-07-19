package kultprosvet.com.wheatherforecast;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import kultprosvet.com.wheatherforecast.models.ForecastItem;
import kultprosvet.com.wheatherforecast.databinding.RowForecastBinding;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.Holder> {
    private List<ForecastItem> items;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_forecast, parent, false);

        return new ForecastAdapter.Holder(v);

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null ? 0 : items.size();
    }

    public ForecastAdapter setItems(List<ForecastItem> items) {
        this.items = items;

        return this;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private ForecastItem item;
        private RowForecastBinding binding;
        private Context mContext;

        public Holder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            binding = DataBindingUtil.bind(itemView);
        }

        public ForecastItem getItem() {
            return item;
        }

        public Holder setItem(ForecastItem item) {
            this.item = item;
            binding.setItem(item);
            getRowIcon(item.getWeather().get(0).getMain());
            return this;
        }

        private void getRowIcon(String weatherMainStatus) {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int size = Math.round(metrics.density * 100);
            int statusIcon = R.drawable.cloudy;
            switch (weatherMainStatus) {
                case "Thunderstorm" : statusIcon = R.drawable.storm;
                    break;
                case "Rain" : statusIcon = R.drawable.rain;
                    break;
                case "Clouds" : statusIcon = R.drawable.cloudy;
                    break;
                case "Clear" : statusIcon = R.drawable.sun;
                    break;
                case "Atmosphere" : statusIcon = R.drawable.cloudy;
                    break;
                case "Snow" : statusIcon = R.drawable.cloudy;
                    break;
            }
            Picasso.with(mContext).load(statusIcon)
                    .resize(size, size)
                    .centerInside()
                    .into(binding.rowIcon);
        }
    }
}
