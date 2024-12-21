package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository repository;

    @Transactional(readOnly = false)
    public void save(Item item) {
        this.repository.save(item);
    }

    @Transactional(readOnly = false)
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item item = repository.findOne(id);
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    public Item findOne(Long id) {
        return this.repository.findOne(id);
    }

    public List<Item> findItems() {
        return this.repository.findAll();
    }
}
