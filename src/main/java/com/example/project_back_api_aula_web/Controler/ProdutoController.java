package com.example.project_back_api_aula_web.Controler;

import com.example.project_back_api_aula_web.model.Produto;
import com.example.project_back_api_aula_web.repository.ProdutoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produto")
@CrossOrigin("*")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping
    public List<Produto> get(
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "order", defaultValue = "asc") String order
    ) {
        List<Produto> produtos = produtoRepository.findAll();

        Comparator<Produto> comparator;
        switch (sortBy.toLowerCase()) {
            case "value":
                comparator = Comparator.comparing(Produto::getPreco);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(Produto::getNome);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return produtos.stream().sorted(comparator).collect(Collectors.toList());
    }
}
