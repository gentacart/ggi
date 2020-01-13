package gentalib;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gcart.android.ggi.AppConfiguration;
import com.gcart.android.ggi.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<Product> productList;

    public void updateData(List<Product> _update) {
        productList = _update;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,price,stock;
        public ImageView imgProduct;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.producttitle);
            price = (TextView) view.findViewById(R.id.productprice);
            stock = (TextView) view.findViewById(R.id.productstock);
            imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        }


    }

    public ProductAdapter(List<Product> _productList) {
        this.productList = _productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row, parent, false);
        //View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get(position);
        //holder.title.setText(product.getName() +"\n" + AppConfiguration.formatNumber(String.valueOf(product.getPrice())) + " IDR " + "\n" + "Stock : " + String.valueOf(product.getTotalstock()) );
        holder.title.setText(product.getName() );
        holder.stock.setText("Stock : " + String.valueOf(product.getTotalstock()));
        holder.price.setText( AppConfiguration.formatNumber(String.valueOf(product.getPrice())) + " IDR");

        Picasso.get().load("https://gentadb.com/pic/ggi/t/" + product.getId() + ".jpg").memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE).into(holder.imgProduct);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


}
