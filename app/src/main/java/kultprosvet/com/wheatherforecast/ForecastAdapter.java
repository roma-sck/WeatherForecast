package kultprosvet.com.wheatherforecast;

import android.databinding.DataBindingUtil;
import android.databinding.tool.DataBindingBuilder;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import kultprosvet.com.wheatherforecast.api.ForecastItem;
import kultprosvet.com.wheatherforecast.databinding.RowForecastBinding;

/**
 * Created by Stanislav Volnyansky on 14.07.16.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.Holder> {

    List<ForecastItem> items;

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
        return items==null?0:items.size();
    }

    public ForecastAdapter setItems(List<ForecastItem> items) {
        this.items = items;

        return this;
    }

    public static class Holder extends RecyclerView.ViewHolder{
        ForecastItem item;
        RowForecastBinding binding;
        public Holder(View itemView) {
            super(itemView);
            binding=DataBindingUtil.bind(itemView);
            //binding=DataBindingUtil.
        }

        public ForecastItem getItem() {
            return item;
        }

        public Holder setItem(ForecastItem item) {
            this.item = item;
            binding.setItem(item);
            return this;
        }
    }
}
