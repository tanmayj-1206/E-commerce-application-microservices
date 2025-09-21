package com.tanmay.e_commerce_application.search_service.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tanmay.e_commerce_application.search_service.Entity.Product;
import com.tanmay.e_commerce_application.search_service.Repository.ProductRepo;
import com.tanmay.e_commerce_application.search_service.Wrapper.ProductWrapper;
import com.tanmay.e_commerce_application.search_service.Wrapper.RequestWrapper;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;

@Service
public class ProductSearchService {

    @Autowired
    private ElasticsearchClient elasticsearchClient;
    
    @Autowired
    private ProductRepo productRepo;

    public void addProduct(Product product){
        productRepo.save(product);
    }

    public List<ProductWrapper> getProducts(RequestWrapper req) throws ElasticsearchException, IOException{
        BoolQuery.Builder qBuilder = new BoolQuery.Builder();
        qBuilder.must(q -> q.fuzzy(f -> f.field("name").value(req.getQ()).fuzziness("AUTO")));
        if(req.getCategory() != null){
            qBuilder.filter(q -> q.match(m -> m.field("category").query(req.getCategory())));
        }

        Query query = Query.of(q -> q.bool(qBuilder.build()));
        SearchResponse<Product> response = elasticsearchClient.search(s -> s
            .index("products")
            .query(query)
            .from(req.getPage() * req.getSize())
            .size(req.getSize()),
            Product.class
        );
        System.out.println(response.hits().hits());
        return response.hits().hits().stream()
            .map(res -> ProductWrapper.fromEntity(res.source()))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    public List<String> getSuggestions(String q) throws ElasticsearchException, IOException{
        SearchResponse<Product> response = elasticsearchClient.search(s -> s
            .index("products")
            .suggest(ss -> ss.suggesters("product-suggest", su -> su.prefix(q)
                .completion(c -> c.field("suggestion")
                    .size(5)
                    )
                )),
            Product.class
        );

        return response.suggest().get("product-suggest").stream()
            .flatMap(s -> s.completion().options().stream())
            .map(su -> su.text())
            .collect(Collectors.toList());
    }
}
