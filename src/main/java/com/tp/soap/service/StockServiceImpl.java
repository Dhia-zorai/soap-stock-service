package com.tp.soap.service;

import com.tp.soap.model.Produit;
import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;

@WebService(
        endpointInterface = "com.tp.soap.service.StockService",
        serviceName = "StockService"
)
@HandlerChain(file = "handler-chain.xml")
public class StockServiceImpl implements StockService {

    @Override
    public Produit chercherProduit(Long id) throws ProduitInexistantException {
        if (id != null && id == 10) {
            return new Produit(10L, "Unité Centrale i7", 750.00);
        }
        throw new ProduitInexistantException("Produit introuvable : " + id, "NOT_FOUND_ERR");
    }
}
