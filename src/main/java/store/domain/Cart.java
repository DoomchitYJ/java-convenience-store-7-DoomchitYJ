package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {

    private List<CartItem> items;

    public Cart() {
        items = new ArrayList<CartItem>();
    }

    public void addItem(Product product, int quantity, int free) {
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                item.setQuantity(item.getQuantity() + quantity);
                item.setFree(item.getFree() + free);
                return;
            }
        }
        items.add(new CartItem(product, quantity, free));
    }

    public List<String> getItemsName() {
        return items.stream()
                .map(CartItem::getProduct)
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    public List<Integer> getItemsQuantity() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Integer> getItemsPrice() {
        return items.stream()
                .mapToInt(CartItem::getPrice)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Integer> getItemsFree() {
        return items.stream()
                .mapToInt(CartItem::getFree)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Integer> getItemsCost() {
        return items.stream()
                .mapToInt(CartItem::getCost)
                .boxed()
                .collect(Collectors.toList());
    }
}
